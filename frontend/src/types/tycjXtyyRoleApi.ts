/*
 * @Author: gongxiaofei
 * @Date: 2023-05-17 14:46:23
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-17 16:03:07
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjXtyyRolePageReq extends tableSearch {
  dm?: string;
  mc?: string;
  xtyyId?: string;
  searchCount?: boolean;
}

export interface Role {
  dm: string;
  mc: string;
  id?: string;
  xtyyId: string;
  xtyyMc: string;
  bz: string;
  xh: number;
}
export interface tycjXtyyRolePageRes extends tableListRes {
  records: Omit<Role, "xh">[];
}

export interface tycjXtyyRoleCreateRes extends Omit<Role, "id"> {}
export interface tycjXtyyRoleUpdateRes
  extends Omit<Role, "dm" | "xtyyMc" | "xtyyId"> {}
