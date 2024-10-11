/*
 * @Author: gongxiaofei
 * @Date: 2023-04-19 16:07:26
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-20 10:31:08
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjBjgJbPageReq extends tableSearch {
  mc?: string;
  yslx?: string;
  yyid?: string;
  fid?: string;
  searchCount?: boolean;
}

export interface bjgItem {
  id: string;
  mc: string;
  yslx: string;
  ygsjl: number;
  cjrid: string;
}
export interface tycjBjgJbPageRes extends tableListRes {
  records: bjgItem[];
}

export interface tycjBjgJbCreateSjxs {
  sjxBsf: string;
  sjxCd?: string;
  sjxJd?: string;
  sjxDmzm?: string;
  sjxFbbz?: string;
  sjxId: string;
  sjxLx: string;
  sjxMc: string;
  sjxZjlx: string;
}
export interface tycjBjgJbCreateReq {
  czlx: string;
  dsbz: string;
  mc: string;
  ygsjl: string;
  yslx: string;
  yyid: string;
  id?: string;
  sjxs: tycjBjgJbCreateSjxs[];
}
