<!--
 * @Author: gongxiaofei
 * @Date: 2022-06-23 09:42:25
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 14:24:30
 * @Description: 
-->
<template>
  <section id="header-bar">
    <div class="flex items-center">
      <img src="@/assets/images/logo.png" alt="" style="width: 30px" />
      <div class="font-350 cursor-pointer title" @click="router.replace('/')">
        {{ title }}
      </div>
      <div class="menu-box">
        <div
          :class="[
            'menu-item',
            activeMenu === i.name ? 'menu-item-active' : '',
          ]"
          v-for="i in menuRoute"
          :key="i.name"
          @click="onChangeMenu(i)"
        >
          {{ i.title }}
        </div>
      </div>
    </div>

    <aside class="flex items-center">
      <header-action />
      <header-user class="ml-12px"></header-user>
    </aside>
  </section>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useMenuStore } from "@/store/modules/menu";
import headerUser from "./HeaderUser.vue";
import HeaderAction from "@/layouts/backstage/section/HeaderAction.vue";

const props = defineProps({
  title: {
    type: String,
    default: "",
  },
});

const menuStore = useMenuStore();
const router = useRouter();
const menuRoute = computed(() => menuStore.$state.menus);
const activeMenu = computed(() => menuStore.$state.activeMenu);

const onChangeMenu = (route: any) => {
  const { name, children } = route;
  menuStore.setMenu(name, children);
  router.push(getPath(route));
};

const getPath = (route: any): string => {
  const { path, children } = route;
  if (path) {
    return path;
  } else {
    return getPath(children[0]);
  }
};
</script>

<style lang="less" scoped></style>
