/*
 * @Author: gongxiaofei
 * @Date: 2022-08-18 09:50:27
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-23 22:02:51
 * @Description: 
 */
export interface BzSjyCategoryParams {
  id: number
}
export interface BzSjyListParams {
  shzt: string
  sjybsf?: string
  flId: string
  pageSize: number
  pageNumber: number
  searchCount: boolean
}
export interface DataList {
  page: number,
  size: number,
  sjbbsf: string,
  sjbbh: string,
  param: any
}

export interface ModuleParams {
  id: string | number
}