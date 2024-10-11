import type { AppRouteConfig } from "@/router/types";

const tvshowShowGenreRoute: AppRouteConfig[] = [
  {
    path: "/tvshowShowGenre/page",
    name: "TvshowShowGenrePage",
    component: () => import("@/views/tvshow/tvshowShowGenre/tvshowShowGenrePage.vue"),
    meta: {
      title: "剧集类型关联表",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshowShowGenre/view",
    name: "TvshowShowGenreView",
    component: () => import("@/views/tvshow/tvshowShowGenre/tvshowShowGenreView.vue"),
    meta: {
      title: "剧集类型关联表详情",
      requiresAuth: false,
    },
  },
]

export default tvshowShowGenreRoute