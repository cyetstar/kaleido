/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-03 10:19:27
 * @Description:
 */
import type { LoginParams, LoginResultModel } from "@/api/_auth/model";
import type { UserInfoModel } from "@/api/_system/model/userModel";
import { loginApi, tokenRefresh } from "@/api/_auth";
import { checkPassword } from "@/utils/regex";
import { createCookie, removeCookies } from "@/hooks/web/useCookie";
import { TokenTypeEnum } from "@/enums/authEnum";
import { defineStore } from "pinia";
import { getAccountInfo } from "@/api/_system/user";
import { usePermissionStore } from "@/store/modules/permission";
import { useMenuStore } from "@/store/modules/menu";
import { useAppStore } from "@/store/modules/app";
import { router } from "@/router";

interface UserState extends UserInfoModel {
  security: boolean;
}

function setTokenHelper({
  access_token,
  refresh_token,
  expires_in,
}: LoginResultModel) {
  createCookie(TokenTypeEnum.ACCESS_TOKEN, access_token, {
    expires: expires_in / (24 * 60 * 60 - 10),
  });
  createCookie(TokenTypeEnum.REFRESH_TOKEN, refresh_token);
}

const initialUserState: UserState = {
  name: "",
  userId: -1,
  username: "",
  deptCode: "",
  deptName: "",
  ssxq: "",
  mobile: "",
  roles: [],
  permissions: [],
  security: true, //密码安全性
};

export const useUserStore = defineStore("user", {
  state: (): UserState => initialUserState,
  getters: {
    invalid: (state) => state.userId === -1,
  },
  actions: {
    async login({
      username,
      password,
    }: LoginParams): Promise<LoginResultModel> {
      const data = await loginApi({ username, password });
      setTokenHelper(data);
      await this.setUserInfo();
      await useMenuStore().generateRoutes();
      await useAppStore().initAppConfig();
      return data;
    },

    async reLogin(): Promise<LoginResultModel> {
      const data = await tokenRefresh();
      setTokenHelper(data);
      await this.setUserInfo();
      return data;
    },

    async setUserInfo() {
      const data = await getAccountInfo();
      this.setUserPermission(data.permissions);
      this.$patch(data);
    },

    setUserPermission(data: any) {
      // const data = await getUserPermissions();
      const permissionStore = usePermissionStore();
      permissionStore.setPermission(data);
    },

    logout(redirectUrl?: string) {
      this.$reset();
      removeCookies([TokenTypeEnum.ACCESS_TOKEN, TokenTypeEnum.REFRESH_TOKEN]);
      sessionStorage.clear();
      router.replace(`/login?redirect=${redirectUrl}`);
    },
  },
  persist: {
    key: "USER_STORE",
    storage: window.localStorage,
  },
});
