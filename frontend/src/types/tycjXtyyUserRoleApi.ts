/*
 * @Author: gongxiaofei
 * @Date: 2023-05-19 09:57:07
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-26 10:42:58
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjXtyyUserRolePageReq extends tableSearch {
  yhmc?: string;
  lxdh?: string;
  roleId?: string;
  szbm?: string;
  searchCount?: boolean;
}

export interface tycjXtyyUserRoleUser {
  lxdh: string;
  yhmc: string;
  szbm: string;
  szbmLabel: string;
  sfzh: string;
  yhbh: string;
  szdwLabel: string;
  role: userRoleItem[];
}
export interface tycjXtyyUserRolePageRes extends tableListRes {
  records: tycjXtyyUserRoleUser[];
}

export interface userRoleItem {
  id: string;
  mc: string;
  xtyyId: string;
  xtyyMc: string;
}

export interface tycjXtyyUserRoleSettingReq {
  addRoles: userRoleItem[];
  removeRoles: userRoleItem[];
  yhbh: string;
}

export interface tycjXtyyUserRoleBatchSettingReq {
  yhbhs: string[];
  addRoles: userRoleItem[];
}
