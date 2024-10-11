import type { AppRouteConfig } from "@/router/types";

const tvshowShowActorRoute: AppRouteConfig[] = [
  {
    path: "/tvshowShowActor/page",
    name: "TvshowShowActorPage",
    component: () => import("@/views/tvshow/tvshowShowActor/tvshowShowActorPage.vue"),
    meta: {
      title: "剧集演职员关联表",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "/tvshowShowActor/view",
    name: "TvshowShowActorView",
    component: () => import("@/views/tvshow/tvshowShowActor/tvshowShowActorView.vue"),
    meta: {
      title: "剧集演职员关联表详情",
      requiresAuth: false,
    },
  },
]

export default tvshowShowActorRoute