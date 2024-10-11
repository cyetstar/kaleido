/*
 * @Author: gongxiaofei
 * @Date: 2022-08-18 09:48:34
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-11-22 09:59:36
 * @Description: 数据元
 */
import { useFetch } from '@/utils/http'
import { BzSjyCategoryParams, BzSjyListParams, DataList, ModuleParams } from '@/api/_system/model/dataElementModel'

enum Api {
  BZ_SJY_CATEGORY = '/tycjSjxLx/getData',
  BZ_SJY_LIST = '/tycjBzSjy/getListByFlId',
  DATA_CATA_LIST = '/tycjSjbJb/pageData',
  DATACATADEYUANLIST = '/tycjSjbSjx/sjyPage',
  DATA_DETAILS = '/tycjSjbJb/listData',
  GET_MODULE_PARAMS = '/tycjSjbJb/detail',
  GET_DATA_DETAILS = '/tycjSjbJb/getDataDetails',
  GET_LIST_BY_SJYMC = '/tycjBzSjy/getListBySjymc'
}

export const getBzSjyCategory = (params: BzSjyCategoryParams) => {
  return useFetch.get<any>({
    url: Api.BZ_SJY_CATEGORY,
    params: params
  })
}

export const getBzSjyList = (params: BzSjyListParams) => {
  return useFetch.get<any>({
    url: Api.BZ_SJY_LIST,
    params: params
  })
}

export const getListBySjymc = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_LIST_BY_SJYMC,
    params: params
  })
}
// 数据管理-数据目录列表
export const getDataCataList = (params: any) => {
  return useFetch.get<any>({
    url: Api.DATA_CATA_LIST,
    params
  })
}
// 数据管理-数据详情
export const getDataCataDeYuanList = (params: any) => {
  return useFetch.get<any>({
    url: Api.DATACATADEYUANLIST,
    params
  })
}

export const getDataDetails = (data: DataList) => {
  return useFetch.post<any>({
    url: Api.DATA_DETAILS,
    data
  })
}

export const getDatas = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_DATA_DETAILS,
    params
  })
}

// 根据模块id获取模块表单信息
export const getModuleParams = (params: ModuleParams) => {
  return useFetch.get<any>({
    url: Api.GET_MODULE_PARAMS,
    params
  })
}