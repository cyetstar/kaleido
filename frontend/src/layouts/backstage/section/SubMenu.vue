<!--
 * @Author: gongxiaofei
 * @Date: 2023-04-12 16:24:01
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-26 09:24:04
 * @Description:
-->
<template>
  <a-sub-menu
    :key="menuInfo.path ? menuInfo.path : menuInfo.name"
    v-bind="$attrs"
  >
    <template #title>
      <span class="flex items-center">
        <!-- <img
          v-if="level === 1 && menuInfo.meta.icon"
          class="w-16px h-16px mr-7px"
          :src="proxy.$getAssetsFile(menuInfo.meta.icon, 'icons')"
        /> -->
        <span>{{ menuInfo.title }}</span>
      </span>
    </template>
    <template
      v-for="item in menuInfo.children"
      :key="item.path ? item.path : item.name"
    >
      <template v-if="item.children && item.children.length">
        <sub-menu
          :menu-info="item"
          :key="item.path ? item.path : item.name"
          :level="level + 1"
        />
      </template>
      <template v-else>
        <a-menu-item
          v-if="!item.hidden"
          :key="item.path"
          @click="router.push(item.path)"
        >
          <!--          <span v-if="level === 1" class="block w-6px h-6px border rounded-6px mr-10px"-->
          <!--                :style="{ borderColor: `var(&#45;&#45;menu-font-color)` }"></span>-->
          <span>{{ item.title }}</span>
        </a-menu-item>
      </template>
    </template>
  </a-sub-menu>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const { proxy } = getCurrentInstance() as any;

const props = defineProps({
  level: {
    type: Number,
    default: 1,
  },
  menuInfo: {
    type: Object,
    default: () => {},
  },
});
</script>

<style lang="less" scoped></style>
