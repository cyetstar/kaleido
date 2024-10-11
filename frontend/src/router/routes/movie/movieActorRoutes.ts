import type { AppRouteConfig } from "@/router/types";

const movieActorRoute: AppRouteConfig[] = [
  {
    path: "/movieActor/page",
    name: "MovieActorPage",
    component: () => import("@/views/movie/movieActor/movieActorPage.vue"),
    meta: {
      title: "演职员",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieActor/view",
    name: "MovieActorView",
    component: () => import("@/views/movie/movieActor/movieActorView.vue"),
    meta: {
      title: "演职员详情",
      requiresAuth: false,
    },
  },
]

export default movieActorRoute