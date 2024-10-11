import type { AppRouteConfig } from "@/router/types";

const tvshowShowRoute: AppRouteConfig[] = [
  {
    path: "/tvshow/tvshowShow/page",
    name: "TvshowShowPage",
    component: () => import("@/views/tvshow/tvshowShow/tvshowShowPage.vue"),
    meta: {
      title: "剧集",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshow/tvshowShow/view",
    name: "TvshowShowView",
    component: () => import("@/views/tvshow/tvshowShow/tvshowShowView.vue"),
    meta: {
      title: "剧集详情",
      requiresAuth: false,
    },
  },
];

export default tvshowShowRoute;
