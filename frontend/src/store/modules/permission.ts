/*
 * @Author: gongxiaofei
 * @Date: 2022-10-19 14:36:49
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-29 10:23:44
 * @Description:
 */
import { defineStore } from "pinia";
import { alertErrMsg } from "@/utils/message";
import { ErrorCodeEnum } from "@/enums/httpEnum";
import { getUserPermissions } from "@/api/_system/role";
export const usePermissionStore = defineStore("permission", {
  state: (): any => ({
    permissions: [],
    permissionKeys: [],
  }),
  getters: {
    hasPermissions: (state) => state.permissionKeys.length > 0,
  },
  actions: {
    async generatePermissions() {
      try {
        // const data = await getUserPermissions();
        // this.permissions = data;
        // if (data.length > 0) {
        //   let list: any = [];
        //   data.forEach((i: any) => {
        //     if (i) {
        //       list.push(i.id);
        //     }
        //   });
        //   this.permissionKeys = list;
        // } else {
        //   alertErrMsg(
        //     ErrorCodeEnum.C100,
        //     "您还没有权限，请联系管理员配置相应权限后再登陆！"
        //   );
        // }
      } catch (error) {
        alertErrMsg(ErrorCodeEnum.C100, "获取权限失败");
      }
    },
    setPermission(data: any) {
      this.permissions = data;
      if (data.length > 0) {
        let list: any = [];
        data.forEach((i: any) => {
          if (i) {
            list.push(i);
          }
        });
        this.permissionKeys = list;
      }
    },
    clearPermissions() {
      this.$reset();
    },
  },
  persist: {
    key: "PERMISSION_STORE",
    storage: window.localStorage,
  },
});
