import type { AppRouteConfig } from "@/router/types";

const sysRoleRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysRole/page",
    name: "sysRolePage",
    component: () => import("@/views/sysadmin/sysRole/sysRolePage.vue"),
    meta: {
      title: "用户管理",
      requiresAuth: false,
    },
  },
];

export default sysRoleRoute;
