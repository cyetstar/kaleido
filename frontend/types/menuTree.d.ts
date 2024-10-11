/*
 * @Author: gongxiaofei
 * @Date: 2022-08-17 10:44:38
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-17 10:48:43
 * @Description: 
 */
export interface MenuTree {
  dm: string,
  jc: string,
  children?: menuTree[],
  end?: boolean,
  isLeaf?: boolean,
  // [propName: string]: any
}