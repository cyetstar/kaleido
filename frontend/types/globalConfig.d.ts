/*
 * @Author: gongxiaofei
 * @Date: 2022-08-11 14:19:53
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-15 09:24:24
 * @Description: 
 */
interface Common {
  componentLib: string,
  formLib: string,
  dragAction: string,
  basicFieldTitle: string,
  standardFieldTitle: string,
  noWidgetHint: string,
}
interface Base {
  [propName: string]: string
}
export interface globalConfig {
  WidgetTabs: Base,
  WidgetCollapse: Base,
  WidgetLabel: Base,
  ToolbarPanel: Base,
  FormWidget: Base,
  SettingTabs: Base,
  SettingForm: Base,
  FieldSetting: Base,
}