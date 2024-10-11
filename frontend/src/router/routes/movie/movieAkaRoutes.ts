import type { AppRouteConfig } from "@/router/types";

const movieAkaRoute: AppRouteConfig[] = [
  {
    path: "/movieAka/page",
    name: "MovieAkaPage",
    component: () => import("@/views/movie/movieAka/movieAkaPage.vue"),
    meta: {
      title: "别名",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieAka/view",
    name: "MovieAkaView",
    component: () => import("@/views/movie/movieAka/movieAkaView.vue"),
    meta: {
      title: "别名详情",
      requiresAuth: false,
    },
  },
]

export default movieAkaRoute