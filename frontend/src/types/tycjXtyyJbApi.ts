/*
 * @Author: gongxiaofei
 * @Date: 2023-04-17 16:30:09
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-25 10:28:40
 * @Description:
 */
import { tableSearch, tableListRes } from "./commonApi";

export interface tycjXtyyJbPageReq extends tableSearch {
  fzrlxbh?: string;
  fzrxm?: string;
  spzt?: string;
  yyzt?: string;
  yymc?: string;
  searchCount?: boolean;
}

export interface xtyy {
  id: string;
  djxtmc: string;
  yymc: string;
  fzrxm: string;
  fzrlxdh: string;
  spzt: string;
  yyzt: string;
  ywryLxdh: string;
  ywryXm: string;
}
export interface tycjXtyyJbPageRes extends tableListRes {
  records: xtyy[];
}

export interface tycjXtyyJbYyztRes {
  yyId: string;
}

export interface tycjXtyyJbCreateReq {
  yymc: string;
  fzrszbm: string;
  djxtmc: string;
  fzrxm: string;
  fzrlxdh: string;
  yyjj: string;
  // tbdz: string;
  id?: string;
  ywryXm: string;
  ywryLxdh: string;
}

export interface tycjXtyyJbViewReq {
  id: string;
}

export interface tycjXtyyJbViewRes {
  id: string;
  yymc: string;
  fzrszbm: string;
  djxtmc: string;
  fzrxm: string;
  fzrlxdh: string;
  yyjj: string;
  tbdz: string;
  ywryXm: string;
  ywryLxdh: string;
  appKey?: string;
  appSecret?: string;
}

export interface tycjXtyyJbShReq {
  btgyy?: string;
  spzt: string;
  xtyyId: number;
}
