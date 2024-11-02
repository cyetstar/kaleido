/*
 * @Author: gongxiaofei
 * @Date: 2022-08-09 09:16:53
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-09 11:35:18
 * @Description: 
 */
type optionItem = {
  name: string,
  value: string | number
}

export interface OptionsConfig {
  [propName: string]: optionItem[]
  // AppType: optionItem[],
  // AppState: optionItem[],
  // AuditState: optionItem[],
  // AppRole: optionItem[],
  // AppOpenType: optionItem[],
  // AppField: optionItem[]
}