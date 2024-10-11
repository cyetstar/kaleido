import type { AppRouteConfig } from "@/router/types";

const movieTagRoute: AppRouteConfig[] = [
  {
    path: "/movieTag/page",
    name: "MovieTagPage",
    component: () => import("@/views/movie/movieTag/movieTagPage.vue"),
    meta: {
      title: "电影标签",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieTag/view",
    name: "MovieTagView",
    component: () => import("@/views/movie/movieTag/movieTagView.vue"),
    meta: {
      title: "电影标签详情",
      requiresAuth: false,
    },
  },
]

export default movieTagRoute