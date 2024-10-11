import type { AppRouteConfig } from "@/router/types";

const comicAuthorRoute: AppRouteConfig[] = [
  {
    path: "comic/comicAuthor/page",
    name: "comicAuthorPage",
    component: () => import("@/views/comic/comicAuthor/comicAuthorPage.vue"),
    meta: {
      title: "漫画作者",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "comic/comicAuthor/view",
    name: "comicAuthorView",
    component: () => import("@/views/comic/comicAuthor/comicAuthorView.vue"),
    meta: {
      title: "漫画作者详情",
      requiresAuth: false,
    },
  },
]

export default comicAuthorRoute