/*
 * @Author: gongxiaofei
 * @Date: 2022-08-08 10:14:16
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-21 09:43:00
 * @Description:
 */

export interface AreaTreeParam {
  dmzm: number | string;
}

export interface DeptChildren {
  dm: number;
}

export interface SsxqCascade {
  value: string | number;
  root: string | number;
  init: boolean;
  zrqEnable: boolean;
  jwhEnable: boolean;
}
export interface SsxqCascadeBack {
  ssxq: string;
}
