/*
 * @Author: gongxiaofei
 * @Date: 2023-06-28 09:08:39
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-28 09:08:40
 * @Description: 
 */
/*
 * @Author: gongxiaofei
 * @Date: 2023-05-10 09:38:27
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-05-30 15:55:37
 * @Description:
 */
export interface yypbInfo {
  sjbId: number;
  sjbMc: string;
  sjbTb: string;
  xh: number;
  xtyyId: number;
  yylx: string;
  sjbbsf: string;
  zsjbbsf: string;
  zsjbId: string;
}
export interface tycjSjbBpPhoneHomeRes {
  xtyy: yypbInfo[];
  ywyy: yypbInfo[];
}
export interface tycjSjbBpDbDetailsRes {
  wbp: yypbInfo[];
  ybp: yypbInfo[];
}

export interface tycjSjbBpBpReq {
  list: yypbInfo[];
}
