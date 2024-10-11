import type { AppRouteConfig } from "@/router/types";

const tvshowSeasonRoute: AppRouteConfig[] = [
  {
    path: "/tvshow/tvshowSeason/page",
    name: "TvshowSeasonPage",
    component: () => import("@/views/tvshow/tvshowSeason/tvshowSeasonPage.vue"),
    meta: {
      title: "单季",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshow/tvshowSeason/view",
    name: "TvshowSeasonView",
    component: () => import("@/views/tvshow/tvshowSeason/tvshowSeasonView.vue"),
    meta: {
      title: "单季详情",
      requiresAuth: false,
    },
  },
];

export default tvshowSeasonRoute;
