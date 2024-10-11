/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-30 17:21:50
 * @Description: 数据字典
 */
import { useFetch } from '@/utils/http'
import { DictListParam, ConditionDictParam } from '@/api/_system/model/dictModel'

enum Api {
  DICT_LIST = '/tycjBzDmx/getDmx',
  DICT_SELECT = '/tycjBzDm/findAll'
}

export const getDict = (params: DictListParam) => {
  return useFetch.get<any>({
    url: Api.DICT_LIST,
    params: params
  })
}

export const getConditionDict = (params: ConditionDictParam) => {
  return useFetch.get<any>({
    url: Api.DICT_SELECT,
    params: params
  })
}
