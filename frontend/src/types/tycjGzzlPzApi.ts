/*
 * @Author: gongxiaofei
 * @Date: 2023-05-22 10:11:52
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-05 15:06:44
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjGzzlPzPagReq extends tableSearch {
  zlmc?: string;
  yycj?: string;
  searchCount?: boolean;
}

export interface tycjGzzlJbPagReq extends tableSearch {
  zllx?: string;
  fsrxm?: string;
  jsrxm?: string;
  searchCount?: boolean;
}

export interface tycjGzzlJbCreateReq {
  jsDw?: string;
  jsrGmsfhm?: string;
  sfbmkj?: string;
  sfyxzf: string;
  wcqx: string;
  yxj: string;
  zlbt: string;
  zllx: string;
  zlnr: string;
}
