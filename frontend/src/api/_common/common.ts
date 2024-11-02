/*
 * @Author: gongxiaofei
 * @Date: 2022-09-27 16:01:21
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-12-13 10:26:30
 * @Description: 
 */
import { useFetch } from '@/utils/http'

enum Api {
  GET_ALL_APP = '/tycjXtyyJb/getUnlimited',
  SAVE_UPDATA_GROUP = '/tycjSjbJbZb/saveOrUpdate',
  DELETE_GROUP = '/tycjSjbJbZb/delete',
  GET_GROUP_DETAIL = '/tycjSjbJbZb/detail',
  GET_INTERFACE_BY_GZD = '/tycjBzJk/findDsList',
  SAVE_UPDATE_DS_INTERFACE = '/tycjDsjk/saveOrUpdate',
  GET_DS_INTERFACE_DETAIL = '/tycjDsjk/detail',
  SEARCH_MATCH = '/es/searchMatch',
  GET_RY_BY_BM = '/tyYyJb/getXmAndSfzhByBm'
}

// 获取所有应用
export const getAllApp = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_ALL_APP,
    params: params
  })
}

// 创建组（关联表单）
export const saveUpdataGroup = (data: any) => {
  return useFetch.post<any>({
    url: Api.SAVE_UPDATA_GROUP,
    data
  })
}

// 取消关联表
export const deleteGroup = (params: any) => {
  return useFetch.get<any>({
    url: Api.DELETE_GROUP,
    params
  })
}
// 取消关联表
export const getFroupDetail = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_GROUP_DETAIL,
    params
  })
}

// 根据表单根字段获取接口
export const getInterfaceByGzd = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_INTERFACE_BY_GZD,
    params
  })
}

// 新增、编辑底数接口配置
export const saveDsInterface = (data: any) => {
  return useFetch.post<any>({
    url: Api.SAVE_UPDATE_DS_INTERFACE,
    data
  })
}

// 获取底数接口配置详情
export const getDsInterfaceDetail = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_DS_INTERFACE_DETAIL,
    params
  })
}

// 搜索
export const searchMatch = (params: any) => {
  return useFetch.get<any>({
    url: Api.SEARCH_MATCH,
    params
  })
}

// 获取部门下的人员信息列表
export const getRyByBm = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_RY_BY_BM,
    params
  })
}