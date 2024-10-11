import type { AppRouteConfig } from "@/router/types";

const movieBasicRoute: AppRouteConfig[] = [
  {
    path: "/movie/movieBasic/page",
    name: "movieBasicPage",
    component: () => import("@/views/movie/movieBasic/movieBasicPage.vue"),
    meta: {
      title: "电影",
      keepAlive: true,
    },
  },
  {
    path: "/movie/movieBasic/view",
    name: "movieBasicView",
    component: () => import("@/views/movie/movieBasic/movieBasicView.vue"),
    meta: {
      title: "电影详情",
    },
  },
];

export default movieBasicRoute;
