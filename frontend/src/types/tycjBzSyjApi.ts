/*
 * @Author: gongxiaofei
 * @Date: 2023-04-13 13:54:18
 * @LastEditors: songtao
 * @LastEditTime: 2023-04-19 14:20:27
 * @Description: 标准数据元-类型
 */
import { tableSearch, tableListRes } from "./commonApi";
export interface tycjBzSjyPageReq extends tableSearch {
  mc: string;
  nbbsf: string;
  dmzm?: string;
  searchCount?: boolean;
}

export interface bzSjy {
  bsf: string;
  bsgs: string;
  cjsj: string;
  dmzm: string;
  id: string;
  mc: string;
  nbbsf: string;
  sm: string;
}
export interface tycjBzSjyPageRes extends tableListRes {
  records: bzSjy[];
}

export interface tycjBzSjyViewReq {
  id: string;
}

export interface tycjBzSjyCreateReq {
  bsc: string;
  bsf: string;
  bsgs: string;
  bz?: string;
  dmzm?: string;
  dxlc: string;
  ly: string;
  lybzh?: string;
  lyxtmc?: string;
  mc: string;
  nbbsf: string;
  sjcd?: number;
  sjjd?: number;
  sjlx: string;
  sm: string;
  txc: string;
  tymc: string;
}

export interface tycjBzSjyViewRes extends tycjBzSjyCreateReq {
  id: number;
}

export interface tycjBzSjyUpdateReq extends tycjBzSjyCreateReq {
  id: number;
}

export interface tycjBzSjyDeleteReq {
  id: string;
}
