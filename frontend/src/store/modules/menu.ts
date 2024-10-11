/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 14:30:32
 * @Description:
 */
import type { AppRouteConfig } from "@/router/types";
import { ErrorCodeEnum } from "@/enums/httpEnum";
import { defineStore } from "pinia";
import { menuRoute } from "@/router/routes";
import { sysMenuTreeApi } from "@/api/sysadmin/sysMenuApi";
interface MenuState {
  menus: any[];
  activeMenu: string;
  subMenu: AppRouteConfig[];
}
export const useMenuStore = defineStore("menu", {
  state: (): MenuState => ({
    menus: [],
    activeMenu: (menuRoute[0]?.name as string) || "",
    subMenu: menuRoute[0]?.children || [],
  }),
  getters: {
    hasRoutes: (state) => state.menus.length > 0,
  },
  actions: {
    setMenu(active: string, subs: AppRouteConfig[]) {
      this.activeMenu = active;
      this.subMenu = subs;
    },
    getSysMenus(app: string) {
      return new Map(Object.entries(this.menus)).get(app);
    },
    async generateRoutes() {
      const serverRoutes = await sysMenuTreeApi();
      console.log(serverRoutes);
      this.menus = serverRoutes;
      const activedItem = serverRoutes[0];
      this.setMenu(activedItem.name, activedItem.children);
    },
    clearRoutes() {
      this.$reset();
    },
  },
  persist: {
    key: "MENU_STORE",
    storage: window.localStorage,
  },
});
