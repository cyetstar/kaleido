import type { AppRouteConfig } from "@/router/types";

const tvshowEpisodeRoute: AppRouteConfig[] = [
  {
    path: "/tvshowEpisode/page",
    name: "TvshowEpisodePage",
    component: () => import("@/views/tvshow/tvshowEpisode/tvshowEpisodePage.vue"),
    meta: {
      title: "单集",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshowEpisode/view",
    name: "TvshowEpisodeView",
    component: () => import("@/views/tvshow/tvshowEpisode/tvshowEpisodeView.vue"),
    meta: {
      title: "单集详情",
      requiresAuth: false,
    },
  },
]

export default tvshowEpisodeRoute