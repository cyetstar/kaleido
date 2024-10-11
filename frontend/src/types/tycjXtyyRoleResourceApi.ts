/*
 * @Author: gongxiaofei
 * @Date: 2023-05-17 16:17:13
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-17 17:03:29
 * @Description:
 */

export interface tycjXtyyRoleGetResoucesConfReq {
  xtyyId: string;
  roleId: string;
}

export interface roleYyList {
  resources: string;
  sjbId: string;
  sjbMc: string;
  sjbbsf: string;
}
export interface tycjXtyyRoleGetResoucesConfRes {
  all: roleYyList[];
  ypz: roleYyList[];
}
