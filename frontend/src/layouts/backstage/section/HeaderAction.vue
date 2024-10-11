<!--
 * @Author: gongxiaofei
 * @Date: 2022-08-04 19:23:58
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 13:36:44
 * @Description: 
-->
<template>
  <section v-if="running">
    <a-popconfirm placement="bottomRight" okText="OK" :showCancel="false">
      <template #icon></template>
      <template #title>
        <div
          v-for="item of actionList"
          :key="item.action"
          class="px-10px"
          style="margin-left: -22px"
        >
          <template v-if="item.running">
            <h4>{{ item.actionLabel }} (耗时:{{ item.duration }})</h4>
            <div class="my-3" style="width: 300px">
              <div class="mb-1 truncate">
                {{ item.message }}
              </div>
              <a-progress :percent="item.percent" />
            </div>
          </template>
        </div>
      </template>
      <a-spin :indicator="indicator" />
    </a-popconfirm>
  </section>
</template>

<script setup>
import { computed, h, onMounted } from "vue";
import { useWebSocketStore } from "@/store/modules/websocket";
import { Loading3QuartersOutlined } from "@ant-design/icons-vue";
import { useAppStore } from "@/store/modules/app";

const indicator = h(Loading3QuartersOutlined, {
  style: {
    fontSize: "24px",
  },
  spin: true,
});

const websocketStore = useWebSocketStore();
const appStore = useAppStore();

const actionList = computed(() => {
  const message = websocketStore.message;
  let data = [];
  if (message && message.data) {
    data = JSON.parse(message.data);
  }
  appStore.updateAction(data);
  return data;
});

const running = computed(() => {
  return actionList.value.filter((s) => s.running).length > 0;
});

onMounted(() => {
  websocketStore.connect();
  window.onbeforeunload = () => {
    //刷新时弹出提示
    websocketStore.close();
  };
});
</script>

<style lang="less" scoped>
:deep(.ant-popover-message-title) {
  padding: 0 10px;
}
</style>
