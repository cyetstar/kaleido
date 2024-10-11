/*
 * @Author: gongxiaofei
 * @Date: 2023-05-26 10:40:46
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-26 10:42:27
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface sysadminFwtQxYhPageReq extends tableSearch {
  jh?: string;
  jz?: string;
  lxdh?: string;
  qxzbh?: string;
  szbm?: string;
  yhmc?: string;
  yhbh?: string;
  yxbz?: string;
  zxtbh?: string;
  searchCount?: boolean;
}

export interface sysadminFwtQxYhPageRes extends tableListRes {
  records: any[];
}
