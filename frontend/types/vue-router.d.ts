/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-09-25 11:09:04
 * @Description:
 */
export {};

declare module "vue-router" {
  interface RouteMeta extends Record<string | number | symbol, unknown> {
    title?: string;
    icon?: string[];
    requiresAuth?: boolean;
    permission?: string[];
  }
}
