import type { AppRouteConfig } from "@/router/types";

const webLogRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/webLog/page",
    name: "webLogPage",
    component: () => import("@/views/sysadmin/webLog/webLogPage.vue"),
    meta: {
      title: "操作日志",
      requiresAuth: false,
    },
  },
];

export default webLogRoute;
