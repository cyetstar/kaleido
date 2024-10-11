import type { AppRouteConfig } from "@/router/types";

const sysLogRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysLog/page",
    name: "sysLogPage",
    component: () => import("@/views/sysadmin/sysLog/sysLogPage.vue"),
    meta: {
      title: "系统日志",
      requiresAuth: false,
    },
  },
];

export default sysLogRoute;
