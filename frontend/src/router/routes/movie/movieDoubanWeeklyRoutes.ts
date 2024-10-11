import type { AppRouteConfig } from "@/router/types";

const movieDoubanWeeklyRoute: AppRouteConfig[] = [
  {
    path: "/movie/movieDoubanWeekly/page",
    name: "movieDoubanWeeklyPage",
    component: () =>
      import("@/views/movie/movieDoubanWeekly/movieDoubanWeeklyPage.vue"),
    meta: {
      title: "豆瓣口碑榜",
    },
  },
  {
    path: "/movie/movieDoubanWeekly/view",
    name: "movieDoubanWeeklyView",
    component: () =>
      import("@/views/movie/movieDoubanWeekly/movieDoubanWeeklyView.vue"),
    meta: {
      title: "豆瓣口碑榜详情",
    },
  },
];

export default movieDoubanWeeklyRoute;
