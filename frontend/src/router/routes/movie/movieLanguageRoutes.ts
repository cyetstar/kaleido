import type { AppRouteConfig } from "@/router/types";

const movieLanguageRoute: AppRouteConfig[] = [
  {
    path: "/movieLanguage/page",
    name: "MovieLanguagePage",
    component: () => import("@/views/movie/movieLanguage/movieLanguagePage.vue"),
    meta: {
      title: "语言",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieLanguage/view",
    name: "MovieLanguageView",
    component: () => import("@/views/movie/movieLanguage/movieLanguageView.vue"),
    meta: {
      title: "语言详情",
      requiresAuth: false,
    },
  },
]

export default movieLanguageRoute