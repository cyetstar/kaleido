<template>
  <div class="bg flex h-screen p-3/25 justify-center items-center">
    <div class="bg-black">
      <div class="mx-12 my-10">
        <h1 class="text-42px font-bold text-center">Kaleido</h1>
        <a-form :model="loginForm" @finish="doLogin">
          <h-input
            class="mt-5"
            required
            placeholder="请输入账号"
            v-model:value="loginForm.username"
            name="username"
          />

          <h-input-password
            class="mt-3"
            required
            placeholder="请输入密码"
            v-model:value="loginForm.password"
            name="password"
          />

          <a-button
            style="background: #1e2d40; border-color: #1e2d40; color: #fff"
            class="mt-3"
            block
            :loading="loginStatus"
            html-type="submit"
            >登录</a-button
          >
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="Login">
import { reactive } from "vue";
import { LoginParams } from "@/api/_auth/model";
import { useMessage, MessageKey } from "@/hooks/web/useMessage";
import { useUserStore } from "@/store/modules/user";
import { useRouter } from "vue-router";
import config from "@/config";
import { useMenuStore } from "@/store/modules/menu";

const router = useRouter();
const { createMessage } = useMessage();

const loginForm = reactive<LoginParams>({
  username: "",
  password: "",
  rememberMe: false,
});
let loginStatus = $ref<boolean>(false);

async function doLogin(value: LoginParams) {
  const userStore = useUserStore();
  try {
    loginStatus = true;
    createMessage.loading({
      duration: 0,
      content: "正在登录",
      key: MessageKey.LOADING_KEY,
    });
    await userStore.login(value);
    createMessage.success({
      content: "登录成功",
      key: MessageKey.LOADING_KEY,
    });
    router.replace("/movie/movieBasic/page");
  } finally {
    loginStatus = false;
  }
}
</script>
<style lang="less" scoped>
.bg-black {
  background: rgba(255, 255, 255, 0.4);
}

.bg {
  background-image: url("@/assets/images/mesh-970.png");
  background-size: cover;
}
</style>
