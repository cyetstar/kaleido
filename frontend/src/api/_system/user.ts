/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-10-25 15:19:51
 * @Description:
 */
import type { UserInfoModel } from './model/userModel'
import { useFetch } from '@/utils/http'

enum Api {
  ACCOUNT_INFO = '/sysUser/info',
  USER_LIST = '/sysadmin/fwtQxYh/page',
  ADD_UPDATA_USER = '/sysadmin/fwtQxYh/saveOrUpdate',
  GET_USER_DETAIL = '/sysadmin/fwtQxYh/detail',
  BATCH_DETELE_USER = '/sysadmin/fwtQxYh/deleteYhs',
  UPDATE_USER_STATE = '/sysadmin/fwtQxYh/updateYxbz',
  RESET_PSW = '/sysadmin/fwtQxYh/resetPassword',

  USER_PRE = '/sysadmin/user',
  USER_ROLE_PRE = '/sysadmin/user/role',
  UPDATE_USER_PASSWORD = '/sysadmin/user/changePassword'
}

export const getAccountInfo = () => {
  return useFetch.get<any>({
    url: Api.ACCOUNT_INFO,
    isEncrypt: true,
    isDecrypt: false
  })
}

// 获取用户列表
export const getUserList = (params: any) => {
  return useFetch.get<any>({
    url: Api.USER_LIST,
    params
  })
}

// 新增编辑用户
export const addUpdataUser = (data: any) => {
  return useFetch.post<any>({
    url: Api.ADD_UPDATA_USER,
    data
  })
}
// 获取用户详情
export const getUserDetail = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_USER_DETAIL,
    params
  })
}

// 批量删除用户
export const batchDeleteUser = (params: any) => {
  return useFetch.get<any>({
    url: Api.BATCH_DETELE_USER,
    params
  })
}

// 启用禁用
export const updateUserState = (params: any) => {
  return useFetch.get<any>({
    url: Api.UPDATE_USER_STATE,
    params
  })

}

// 重置密码
export const resetPsw = (params: any) => {
  return useFetch.get<any>({
    url: Api.RESET_PSW,
    params
  })

}