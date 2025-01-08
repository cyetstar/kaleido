/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-29 10:25:21
 * @Description:
 */
import type { Router } from "vue-router";
import { useUserStore } from "@/store/modules/user";
import { usePermissionStore } from "@/store/modules/permission";
import { alertErrMsg } from "@/utils/message";
import { ErrorCodeEnum } from "@/enums/httpEnum";
import {
  addPermissions,
  checkAccessToken,
  isRequiresAuthRoute,
} from "@/router/helper";

export const createPermissionGuard = (router: Router) => {
  router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const permissionStore = usePermissionStore();
    if (to.path === "/login") {
      next();
    } else if (!checkAccessToken()) {
      try {
        await userStore.reLogin();
        await addPermissions();
        next(to.fullPath);
      } catch (e) {
        next("/login");
      }
    } else {
      if (!permissionStore.hasPermissions) {
        await addPermissions();
        next(to.fullPath);
      } else {
        const permissionKeys = permissionStore.permissionKeys;
        if (to.meta.permissionKey) {
          if (permissionKeys.includes(to.meta.permissionKey)) {
            next();
          } else {
            alertErrMsg(ErrorCodeEnum.C100, "没有权限");
            next("");
          }
        } else {
          next();
        }
      }
    }
  });
};
