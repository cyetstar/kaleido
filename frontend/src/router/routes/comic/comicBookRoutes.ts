import type { AppRouteConfig } from "@/router/types";

const comicBookRoute: AppRouteConfig[] = [
  {
    path: "comic/comicBook/page",
    name: "comicBookPage",
    component: () => import("@/views/comic/comicBook/comicBookPage.vue"),
    meta: {
      title: "漫画书籍",
      requiresAuth: false,
      icon: ["i-home.png", "i-home-active.png"],
    },
  },
  {
    path: "comic/comicBook/view",
    name: "comicBookView",
    component: () => import("@/views/comic/comicBook/comicBookView.vue"),
    meta: {
      title: "漫画书籍详情",
      requiresAuth: false,
    },
  },
]

export default comicBookRoute