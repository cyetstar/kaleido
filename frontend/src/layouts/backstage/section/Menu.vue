<!--
 * @Author: gongxiaofei
 * @Date: 2023-04-17 11:37:01
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-17 16:24:34
 * @Description: menu
-->
<template>
  <section class="menu-container is-side-menu">
    <a-menu
      v-model:selected-keys="state.selectedKeys"
      :open-keys="state.openKeys"
      mode="inline"
      :collapsed="props.collapsed"
      collapsible
      :inlineIndent="21"
    >
    </a-menu>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from "vue";
import { useRoute } from "vue-router";

const props = defineProps({
  collapsed: {
    type: Boolean,
  },
});

const currentRoute = useRoute();
// console.log(currentRoute);
const state = reactive({
  openKeys: [] as string[],
  selectedKeys: [currentRoute.name] as string[],
});

const getOpenKeys = () => {
  const meta = currentRoute.meta;
  return meta?.hidden ? state?.openKeys || [] : currentRoute.meta.name;
};
</script>

<style lang="less" scoped>
.menu-container {
  overflow: auto;
  &::-webkit-scrollbar {
    width: 0;
    height: 0;
  }
  // &.is-side-menu {
  //   height: calc(100vh - 64px);
  // }
}
</style>
