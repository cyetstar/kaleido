import type { AppRouteConfig } from "@/router/types";

const artistRoutes: AppRouteConfig[] = [
  {
    path: "/artist/page",
    name: "artistPage",
    component: () => import("@/views/artist/artistPage.vue"),
    meta: {
      title: "艺术家",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/artist/view",
    name: "artistView",
    component: () => import("@/views/artist/artistView.vue"),
    meta: {
      title: "艺术家详情",
      requiresAuth: false,
    },
  },
];

export default artistRoutes;
