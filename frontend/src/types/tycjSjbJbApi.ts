/*
 * @Author: gongxiaofei
 * @Date: 2023-04-22 11:25:17
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-06 14:00:13
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjSjbJbPageReq extends tableSearch {
  yyId?: string;
  sjbmc?: string;
  yslx?: string;
  ly?: string;
  searchCount?: boolean;
}

export interface sjbItem {
  id: number;
  bjgId: number;
  bjgMc: string;
  sjbmc: string;
  mkmc: string;
  mkbz: string;
  mktb: string;
  yslx: string;
  cjr: string;
  cjsj: string;
}
export interface tycjSjbJbPageRes extends tableListRes {
  records: sjbItem[];
}

export interface tycjSjbJbCreateReq {
  id?: string;
  bjgId?: string;
  mkbz?: string;
  mkmc?: string;
  mktb?: string;
  sjbmc: string;
  xtyyId: string;
  ly: string;
  yslx?: string;
}

export interface tycjSjbJbViewReq {
  id: string;
}
