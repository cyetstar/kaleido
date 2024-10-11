import type { AppRouteConfig } from "@/router/types";

const movieGenreRoute: AppRouteConfig[] = [
  {
    path: "/movieGenre/page",
    name: "MovieGenrePage",
    component: () => import("@/views/movie/movieGenre/movieGenrePage.vue"),
    meta: {
      title: "电影类型",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieGenre/view",
    name: "MovieGenreView",
    component: () => import("@/views/movie/movieGenre/movieGenreView.vue"),
    meta: {
      title: "电影类型详情",
      requiresAuth: false,
    },
  },
]

export default movieGenreRoute