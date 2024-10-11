<script setup lang="ts" name="App">
import { ConfigProvider } from "ant-design-vue";
import zhCN from "ant-design-vue/es/locale/zh_CN";
</script>

<template>
  <config-provider :locale="zhCN">
    <router-view />
  </config-provider>
</template>
