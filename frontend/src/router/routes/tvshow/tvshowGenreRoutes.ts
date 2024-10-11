import type { AppRouteConfig } from "@/router/types";

const tvshowGenreRoute: AppRouteConfig[] = [
  {
    path: "/tvshowGenre/page",
    name: "TvshowGenrePage",
    component: () => import("@/views/tvshow/tvshowGenre/tvshowGenrePage.vue"),
    meta: {
      title: "剧集类型",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshowGenre/view",
    name: "TvshowGenreView",
    component: () => import("@/views/tvshow/tvshowGenre/tvshowGenreView.vue"),
    meta: {
      title: "剧集类型详情",
      requiresAuth: false,
    },
  },
]

export default tvshowGenreRoute