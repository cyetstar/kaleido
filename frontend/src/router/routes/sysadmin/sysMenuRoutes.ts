import type { AppRouteConfig } from "@/router/types";

const sysMenuRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysMenu/page",
    name: "sysMenuPage",
    component: () => import("@/views/sysadmin/sysMenu/sysMenuPage.vue"),
    meta: {
      title: "用户管理",
      requiresAuth: false,
    },
  },
];

export default sysMenuRoute;
