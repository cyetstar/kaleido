<!--
 * @Author: gongxiaofei
 * @Date: 2022-08-04 19:23:58
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 13:36:44
 * @Description: 
-->
<template>
  <section class="flex items-center py-9px">
    <a-dropdown>
      <span
        class="text-14px text-[var(--font-color)] cursor-pointer flex items-center"
      >
        <span class="ml-8px mr-6px text-[#666666] text-14px">{{ name }}</span>
        <!-- <span class="mr-7px">{{ username }}</span> -->
        <img src="@/assets/icons/thing-down.png" class="w-14px h-14px" />
      </span>
      <template #overlay>
        <a-menu>
          <a-menu-item
            v-for="{ name, value, action } in menuList"
            :key="value"
            @click="action"
          >
            <div>{{ name }}</div>
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </section>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/modules/user";

const router = useRouter();
const userStore = useUserStore();
const name = userStore.name;
console.log("name", name);

const menuList = ref([
  {
    name: "退出登录",
    value: 4,
    action: () => {
      userStore.logout();
    },
  },
]);
</script>

<style lang="less" scoped></style>
