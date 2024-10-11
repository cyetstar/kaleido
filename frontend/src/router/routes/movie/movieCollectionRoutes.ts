import type { AppRouteConfig } from "@/router/types";

const movieCollectionRoute: AppRouteConfig[] = [
  {
    path: "/movie/movieCollection/page",
    name: "MovieCollectionPage",
    component: () =>
      import("@/views/movie/movieCollection/movieCollectionPage.vue"),
    meta: {
      title: "电影合集",
      keepAlive: true,
    },
  },
  {
    path: "/movie/movieCollection/view",
    name: "MovieCollectionView",
    component: () =>
      import("@/views/movie/movieCollection/movieCollectionView.vue"),
    meta: {
      title: "电影合集详情",
    },
  },
];

export default movieCollectionRoute;
