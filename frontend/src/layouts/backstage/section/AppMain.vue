<!--
 * @Author: gongxiaofei
 * @Date: 2022-08-05 14:05:19
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-09-26 14:44:20
 * @Description: 
-->
<template>
  <section id="main-section">
    <k-global-config
      :image-url="VITE_IMAGE_PATH"
      :list-dict-by-type="listDictByType"
    >
      <router-view v-slot="{ Component, route }">
        <keep-alive>
          <component
            v-if="route.meta.keepAlive"
            :is="Component"
            :key="route.name"
          />
        </keep-alive>
        <component
          v-if="!route.meta.keepAlive"
          :is="Component"
          :key="route.name"
        />
      </router-view>
    </k-global-config>
  </section>
</template>

<script setup>
import { useAppStore } from "@/store/modules/app";
import { apiSysDictListByDictType } from "@/api/sysadmin/sysDictApi";

const { VITE_IMAGE_PATH } = import.meta.env;
const appStore = useAppStore();

const listDictByType = (type) => {
  return apiSysDictListByDictType(type);
};
</script>

<style lang="less" scoped>
#main-section {
  background: rgba(238, 238, 238, 0.93);
}
</style>
