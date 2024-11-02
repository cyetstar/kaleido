import type { AppRouteConfig } from "@/router/types";
import { PageLayout } from "@/router/constant";

const openApiRoute: AppRouteConfig = {
  path: "/backstage",
  name: "Backstage",
  component: PageLayout,
  redirect: "/backstage/home",
  meta: {
    title: "首页",
  },
  children: [
    {
      path: "/backstage/home",
      name: "BackstageHome",
      component: () => import("@/views/backstage/home/homePage.vue"),
      meta: {
        title: "首页",
        requiresAuth: false,
        icon: ["i-home.png", "i-home-active.png"],
      },
    }
  ]
}

export default openApiRoute