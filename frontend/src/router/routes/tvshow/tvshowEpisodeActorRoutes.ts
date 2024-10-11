import type { AppRouteConfig } from "@/router/types";

const tvshowEpisodeActorRoute: AppRouteConfig[] = [
  {
    path: "/tvshowEpisodeActor/page",
    name: "TvshowEpisodeActorPage",
    component: () => import("@/views/tvshow/tvshowEpisodeActor/tvshowEpisodeActorPage.vue"),
    meta: {
      title: "单集演职员关联表",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshowEpisodeActor/view",
    name: "TvshowEpisodeActorView",
    component: () => import("@/views/tvshow/tvshowEpisodeActor/tvshowEpisodeActorView.vue"),
    meta: {
      title: "单集演职员关联表详情",
      requiresAuth: false,
    },
  },
]

export default tvshowEpisodeActorRoute