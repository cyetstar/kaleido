/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-11-10 13:37:55
 * @Description: 
 */
import { useFetch } from '@/utils/http'
enum Api {
  GET_DEPT_LIST = '/tycjDept/page',
}
export const getDeptList = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_DEPT_LIST,
    params: params
  })
}
