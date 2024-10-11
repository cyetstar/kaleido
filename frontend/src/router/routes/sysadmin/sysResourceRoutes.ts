import type { AppRouteConfig } from "@/router/types";

const sysResourceRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysResource/page",
    name: "sysResourcePage",
    component: () => import("@/views/sysadmin/sysResource/sysResourcePage.vue"),
    meta: {
      title: "用户管理",
      requiresAuth: false,
    },
  },
];

export default sysResourceRoute;
