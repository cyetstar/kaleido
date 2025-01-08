import type { RouteLocationNormalized } from "vue-router";
import { useMenuStore } from "@/store/modules/menu";
import { usePermissionStore } from "@/store/modules/permission";
import { TokenTypeEnum } from "@/enums/authEnum";
import { useCookie } from "@/hooks/web/useCookie";
import { basicRoutes } from "@/router/routes";
import { router } from "@/router";

export function checkAccessToken() {
  return useCookie(TokenTypeEnum.ACCESS_TOKEN);
}

export function isBasicRoute(route: RouteLocationNormalized) {
  if (route.matched?.length > 0) {
    return basicRoutes.map((r) => r.path).includes(route.matched[0]?.path);
  } else {
    return false;
  }
}

export function isRequiresAuthRoute(route: RouteLocationNormalized) {
  return !route.matched.some((r) => r.meta?.requiresAuth === false);
}

export async function addPermissions() {
  const permissionStore = usePermissionStore();
  if (
    !permissionStore.permissionKeys ||
    permissionStore.permissionKeys.length === 0
  ) {
    await permissionStore.generatePermissions();
  }
}
