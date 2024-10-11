/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-10-18 13:36:50
 * @Description: 
 */
import type { RouteMeta, RouteRecordRaw } from 'vue-router'

export type AppRouteConfig = Omit<RouteRecordRaw, 'meta' | 'children'> & {
  id?: number
  title?: string,
  hidden?: boolean
  showRoot?: boolean
  meta?: RouteMeta
  children?: AppRouteConfig[],
  // permissionType?: number,     // 1: 目录/页面 2: 按钮
  permissionKey?: string
}
