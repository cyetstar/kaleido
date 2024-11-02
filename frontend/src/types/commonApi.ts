/*
 * @Author: gongxiaofei
 * @Date: 2023-04-13 14:11:10
 * @LastEditors: songtao
 * @LastEditTime: 2023-04-19 15:11:21
 * @Description:
 */
export interface tableSearch {
  pageSize: number;
  pageNumber: number;
}

export interface tableListRes {
  total: number;
  totalPage: number;
  first: boolean;
  last: boolean;
  current: number;
  size: number;
  empty: boolean;
}
