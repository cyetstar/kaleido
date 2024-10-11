import type { AppRouteConfig } from "@/router/types";

const artistRoutes: AppRouteConfig[] = [
  {
    path: "/music/artist/page",
    name: "musicArtistPage",
    component: () => import("@/views/artist/artistPage.vue"),
    meta: {
      title: "艺术家",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/music/artist/view",
    name: "musicArtistView",
    component: () => import("@/views/artist/artistView.vue"),
    meta: {
      title: "艺术家详情",
      requiresAuth: false,
    },
  },
];

export default artistRoutes;
