import type { AppRouteConfig } from "@/router/types";

const musicAlbumRoutes: AppRouteConfig[] = [
  {
    path: "/music/musicAlbum/page",
    name: "musicAlbumPage",
    component: () => import("@/views/music/musicAlbum/musicAlbumPage.vue"),
    meta: {
      title: "专辑",
      keepAlive: true,
    },
  },
  {
    path: "/music/musicAlbum/view",
    name: "musicAlbumView",
    component: () => import("@/views/music/musicAlbum/musicAlbumView.vue"),
    meta: {
      title: "专辑详情",
    },
  },
];

export default musicAlbumRoutes;
