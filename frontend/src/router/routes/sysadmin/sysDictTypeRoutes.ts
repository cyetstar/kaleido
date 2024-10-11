import type { AppRouteConfig } from "@/router/types";

const sysDictTypeRoute: AppRouteConfig[] = [
  {
    path: "/sysadmin/sysDictType/page",
    name: "sysDictTypePage",
    component: () => import("@/views/sysadmin/sysDictType/sysDictTypePage.vue"),
    meta: {
      title: "字典管理",
      requiresAuth: false,
    },
  },
  {
    path: "/sysadmin/sysDictType/view",
    name: "sysDictTypeView",
    component: () => import("@/views/sysadmin/sysDictType/sysDictTypeView.vue"),
    meta: {
      title: "字典详情",
      requiresAuth: false,
    },
  },
];

export default sysDictTypeRoute;
