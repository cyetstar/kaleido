/*
 * @Author: gongxiaofei
 * @Date: 2022-08-22 09:16:24
 * @LastEditors: songtao
 * @LastEditTime: 2023-10-18 15:38:02
 * @Description:
 */
import type { AppRouteConfig } from "../types";
import { PageLayout } from "@/router/constant";

import sysUserRoutes from "@/router/routes/sysadmin/sysUserRoutes";
import sysRoleRoutes from "@/router/routes/sysadmin/sysRoleRoutes";
import sysResourceRoutes from "@/router/routes/sysadmin/sysResourceRoutes";
import sysMenuRoutes from "@/router/routes/sysadmin/sysMenuRoutes";
import webLogRoutes from "@/router/routes/sysadmin/webLogRoutes";
import sysDictTypeRoutes from "@/router/routes/sysadmin/sysDictTypeRoutes";
import sysLogRoutes from "@/router/routes/sysadmin/sysLogRoutes";
import musicAlbumRoutes from "@/router/routes/music/musicAlbumRoutes";
import artistRoutes from "@/router/routes/artistRoutes";
import sysConfigRoutes from "@/router/routes/sysadmin/sysConfigRoutes";
import movieBasicRoutes from "@/router/routes/movie/movieBasicRoutes";
import movieCollectionRoutes from "@/router/routes/movie/movieCollectionRoutes";
import tvshowShowRoutes from "@/router/routes/tvshow/tvshowShowRoutes";
import tvshowSeasonRoutes from "@/router/routes/tvshow/tvshowSeasonRoutes";
import movieDoubanWeeklyRoutes from "@/router/routes/movie/movieDoubanWeeklyRoutes";
import comicSeriesRoutes from "@/router/routes/comic/comicSeriesRoutes";
import comicBookRoutes from "@/router/routes/comic/comicBookRoutes";
import threadRoutes from "@/router/routes/threadRoutes";

const LoginRoute: AppRouteConfig = {
  path: "/login",
  name: "login",
  component: () => import("@/views/login/index.vue"),
  hidden: true,
  meta: {
    title: "登录",
    requiresAuth: false,
  },
};

const BackstageRoute: AppRouteConfig = {
  path: "/",
  name: "backstage",
  component: PageLayout,
  redirect: movieBasicRoutes[0].path,
  children: [
    {
      path: "/:path(.*)*",
      name: "PageNotFound",
      component: () => import("@/views/error/PageNotFound.vue"),
      hidden: true,
      meta: {
        title: "404 - Page Not Found",
        requiresAuth: false,
      },
    },
    ...sysUserRoutes,
    ...sysRoleRoutes,
    ...sysMenuRoutes,
    ...sysResourceRoutes,
    ...sysDictTypeRoutes,
    ...sysLogRoutes,
    ...sysConfigRoutes,
    ...webLogRoutes,
    ...musicAlbumRoutes,
    ...artistRoutes,
    ...movieBasicRoutes,
    ...movieCollectionRoutes,
    ...movieDoubanWeeklyRoutes,
    ...tvshowShowRoutes,
    ...tvshowSeasonRoutes,
    ...comicSeriesRoutes,
    ...comicBookRoutes,
    ...threadRoutes,
  ],
};
export const allRoutes = [LoginRoute, BackstageRoute];
export const menuRoute = [BackstageRoute];
export const basicRoutes = [BackstageRoute];
