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
import { iconsole } from "@/utils/console";
import { ConsoleTypeEnum } from "@/enums/consoleEnum";

export const createPermissionGuard = (router: Router) => {
  iconsole.SUCCESS(ConsoleTypeEnum.ROUTER, "create permission guard");
  router.beforeEach(async (to, from, next) => {
    console.log("to:", to.meta.keepAlive);
    const userStore = useUserStore();
    const permissionStore = usePermissionStore();
    if (isRequiresAuthRoute(to)) {
      if (!checkAccessToken()) {
        console.error("token expires");
        await userStore.reLogin();
        await addPermissions();
        next(to.fullPath);
      } else {
        // userStore.invalid && (await userStore.setUserInfo())
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
    } else if (to.path === "/home") {
      if (!permissionStore.hasPermissions) {
        await addPermissions();
        next(to.fullPath);
      } else {
        next();
      }
    } else {
      next();
    }
  });
};
