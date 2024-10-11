import type { AppRouteConfig } from "@/router/types";

const sysUserRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysUser/page",
    name: "sysUserPage",
    component: () => import("@/views/sysadmin/sysUser/sysUserPage.vue"),
    meta: {
      title: "用户管理",
      requiresAuth: false,
    },
  },
];

export default sysUserRoute;
