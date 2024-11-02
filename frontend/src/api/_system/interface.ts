/*
 * @Author: gongxiaofei
 * @Date: 2022-08-31 10:44:01
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-31 15:07:32
 * @Description: 
 */
import { useFetch } from '@/utils/http'
import { InterfaceById } from '@/api/_system/model/interfaceModel'

enum Api {
  INTERFACE_LIST = '/tycjBzJk/findList',
  GET_INTERFACE_BY_ID= '/tycjBzJk/getDataByChooseId',
  SJBJK_CREATE_BATCH = '/tycjSjbJk/createBatch'
}

export const getInterfaceList = () => {
  return useFetch.get<any>({
    url: Api.INTERFACE_LIST
  })
}

export const getInterfaceById = (params: InterfaceById) => {
  return useFetch.get<any>({
    url: Api.GET_INTERFACE_BY_ID,
    params
  })
}

export const sjbjkSaveOrUpdata = (data: any) => {
  return useFetch.post<any>({
    url: Api.SJBJK_CREATE_BATCH,
    data
  })
}