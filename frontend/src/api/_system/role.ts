/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-10-19 14:41:50
 * @Description: 
 */

import { useFetch } from '@/utils/http'

enum Api {
  ROLE_LIST = '/sysadmin/fwtQxQxz/page',
  UPDATE_ROLE = '/sysadmin/fwtQxQxz/create',
  GET_ROLE_PERMISSION = '/sysadmin/fwtQxQxzCd/getCdByQxz',
  UPDATE_ROLE_PERMISSION = '/sysadmin/fwtQxQxzCd/update',
  UPDATE_ROLE_STATUS = '/sysadmin/fwtQxQxz/changeStatus',
  GET_USER_PERMISSION = '/sysadmin/fwtQxQxzCd/build'
}

// 获取角色
export const getRole = (params: any) => {
  return useFetch.get<any>({
    url: Api.ROLE_LIST,
    params
  })
}
// 新增/编辑角色
export const updateRole = (data: any) => {
  return useFetch.post<any>({
    url: Api.UPDATE_ROLE,
    data
  })
}
// 获取角色权限
export const getRolePermission = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_ROLE_PERMISSION,
    params
  })
}
// 更新角色权限
export const updateRolePermission = (data: any) => {
  return useFetch.post<any>({
    url: Api.UPDATE_ROLE_PERMISSION,
    data
  })
}

// 更新角色状态
export const updateRoleStatus = (data: any) => {
  return useFetch.post<any>({
    url: Api.UPDATE_ROLE_STATUS,
    data
  })
}

export const getUserPermissions = () => {
  return useFetch.post<any>({
    url: Api.GET_USER_PERMISSION,
  })
}
