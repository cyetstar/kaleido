import type { AppRouteConfig } from "@/router/types";

const sysConfigRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysConfig/form",
    name: "SysConfigForm",
    component: () => import("@/views/sysadmin/sysConfig/sysConfigForm.vue"),
    meta: {
      title: "系统配置表",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
];

export default sysConfigRoute;
