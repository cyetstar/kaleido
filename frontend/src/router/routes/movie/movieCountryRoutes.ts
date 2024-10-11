import type { AppRouteConfig } from "@/router/types";

const movieCountryRoute: AppRouteConfig[] = [
  {
    path: "/movieCountry/page",
    name: "MovieCountryPage",
    component: () => import("@/views/movie/movieCountry/movieCountryPage.vue"),
    meta: {
      title: "国家地区",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/movieCountry/view",
    name: "MovieCountryView",
    component: () => import("@/views/movie/movieCountry/movieCountryView.vue"),
    meta: {
      title: "国家地区详情",
      requiresAuth: false,
    },
  },
]

export default movieCountryRoute