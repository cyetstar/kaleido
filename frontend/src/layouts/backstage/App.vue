<!--
 * @Author: gongxiaofei
 * @Date: 2022-08-05 13:56:57
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-09-26 16:27:20
 * @Description: 主视图
-->
<template>
  <app-header :title="title"></app-header>
  <section class="flex h-[calc(100vh-48px)] overflow-y-auto">
    <app-side></app-side>
    <app-main class="py-11px pl-11px flex-1"></app-main>
  </section>
</template>

<script setup lang="ts">
import AppHeader from "@/layouts/backstage/section/AppHeader.vue";
import AppMain from "@/layouts/backstage/section/AppMain.vue";
import AppSide from "./section/AppSide.vue";
import { ref, reactive, computed, onMounted } from "vue";
import config from "@/config";

const title = ref<string>(config.APP.title);

// let collapsed = ref<boolean>(false)
</script>

<style lang="less" scoped></style>
