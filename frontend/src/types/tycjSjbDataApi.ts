/*
 * @Author: gongxiaofei
 * @Date: 2023-07-04 13:51:31
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-04 13:51:32
 * @Description:
 */
/*
 * @Author: gongxiaofei
 * @Date: 2023-05-15 09:31:27
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-15 16:47:04
 * @Description:
 */
export interface tycjSjbDataSelectReq {
  page: number;
  size: number;
  sjbbsf: string;
  sjbId: string;
  searchCount: boolean;
  param: any;
}

export interface tycjSjbDataInsertReq {
  data: any;
  dataId?: number;
  sjbbsf: string;
}
export interface tycjSjbDataViewReq {
  dataId: number;
  sjbbsf: string;
  sjbId: string;
}
