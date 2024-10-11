/*
 * @Author: gongxiaofei
 * @Date: 2023-04-14 09:51:31
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-15 10:18:36
 * @Description: 标准字典-类型
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjBzDmPageReq extends tableSearch {
  dmbsf?: string;
  dmly?: string;
  dmmc?: string;
  lybzh?: string;
  lyxtmc?: string;
  searchCount?: boolean;
}

export interface bzDict {
  id: string;
  dmbb: string;
  dmbsf: string;
  dmly: string;
  dmmc: string;
  dmzt: string;
  fbrq: string;
  fsbz: string;
  lybzh: string;
  lyxtmc: string;
  sflsbb: string;
  sjxbsf: string;
  spzt: string;
  yyId: string;
}
export interface tycjBzDmPageRes extends tableListRes {
  records: bzDict[];
}

export interface tycjBzDmTreeReq {
  dmzm: string;
}

export interface dictViewTree {
  dmxmc: string;
  dmxz: string;
  fid: number;
  id: number;
  childs: dictViewTree[];
}
export interface tycjBzDmTreeRes {
  dmbsf: string;
  id: number;
  dmmc: string;
  dmly?: string;
  lybzh?: string;
  lyxtmc?: string;
  tree: dictViewTree[];
}
