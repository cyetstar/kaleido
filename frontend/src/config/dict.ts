/*
 * @Author: gongxiaofei
 * @Date: 2023-04-13 15:25:39
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-13 10:14:47
 * @Description: 本地维护的字典
 */

// 字典--来源
export interface dict {
  text: string;
  value: string;
}
// 标签位置
export const labelPosition: dict[] = [
  {
    text: "左侧",
    value: "left",
  },
  {
    text: "顶部",
    value: "top",
  },
];
export const source: dict[] = [
  {
    text: "标准-国标",
    value: "BZ-G",
  },
  {
    text: "标准-部标",
    value: "BZ-B",
  },
  {
    text: "标准-省标",
    value: "BZ-S",
  },
  {
    text: "标准-市标",
    value: "BZ-SHI",
  },
  {
    text: "业务-部级",
    value: "YW-B",
  },
  {
    text: "业务-省级",
    value: "YW-S",
  },
  {
    text: "业务-市级",
    value: "YW-SHI",
  },
  {
    text: "业务-区级",
    value: "YW-Q",
  },
];
// 数据类型
export const sjlxDict: dict[] = [
  {
    text: "字符型",
    value: "C",
  },
  {
    text: "数值型",
    value: "N",
  },
  {
    text: "货币型",
    value: "Y",
  },
  {
    text: "日期型",
    value: "D",
  },
  {
    text: "日期时间型",
    value: "T",
  },
  {
    text: "逻辑型",
    value: "L",
  },
  {
    text: "备注型",
    value: "M",
  },
  {
    text: "通用型",
    value: "G",
  },
  {
    text: "双精度型",
    value: "B",
  },
  {
    text: "整型",
    value: "I",
  },
  {
    text: "浮点型",
    value: "F",
  },
];
// 组件类型
export const zjlxDict: dict[] = [
  {
    text: "单行输入",
    value: "input",
  },
  {
    text: "多行输入",
    value: "textarea",
  },
  {
    text: "数字输入",
    value: "number",
  },
  {
    text: "单选项",
    value: "radio",
  },
  {
    text: "多选项",
    value: "checkbox",
  },
  {
    text: "下拉选项",
    value: "select",
  },
  {
    text: "时间",
    value: "time",
  },
  {
    text: "时间范围",
    value: "timeRange",
  },
  {
    text: "日期",
    value: "date",
  },
  {
    text: "日期范围",
    value: "dateRange",
  },
  {
    text: "文字",
    value: "staticText",
  },
  {
    text: "分隔线",
    value: "divider",
  },
  {
    text: "图片",
    value: "pictureUpload",
  },
  {
    text: "文件",
    value: "fileUpload",
  },
  {
    text: "级连选择",
    value: "cascader",
  },
  {
    text: "级连选择(辖区)",
    value: "cascader-ssxq",
  },
];
// 状态
export const spztDict: dict[] = [
  {
    text: "未提交",
    value: "-1",
  },
  {
    text: "待审核",
    value: "0",
  },
  {
    text: "审核通过",
    value: "1",
  },
  {
    text: "已驳回",
    value: "2",
  },
];
// 运行状态
export const yyztDict: dict[] = [
  {
    text: "上架",
    value: "1",
  },
  {
    text: "下架",
    value: "0",
  },
];
// 要素类型
export const yslxDict: dict[] = [
  {
    text: "人员",
    value: "ry",
  },
  {
    text: "地址",
    value: "dz",
  },
  {
    text: "事件",
    value: "sj",
  },
  {
    text: "物品",
    value: "wp",
  },
  {
    text: "行业",
    value: "hy",
  },
  {
    text: "单位",
    value: "dw",
  },
  {
    text: "网络",
    value: "wl",
  },
];
// 是否含有底数
export const dsDict: dict[] = [
  {
    text: "是",
    value: "1",
  },
  {
    text: "否",
    value: "0",
  },
];
// 是否进行读/写操作
export const dxczDict: dict[] = [
  {
    text: "只读",
    value: "1",
  },
  {
    text: "读写",
    value: "2",
  },
  {
    text: "否",
    value: "0",
  },
];
// 多次采录
export const dcclDict: dict[] = [
  {
    text: "是",
    value: "1",
  },
  {
    text: "否",
    value: "0",
  },
];
// 数据量单位
export const sjdwDict: dict[] = [
  {
    text: "条",
    value: "1",
  },
  {
    text: "万条",
    value: "2",
  },
];
// 查询方式
export const queryType: dict[] = [
  {
    text: "等于",
    value: "EQ",
  },
  {
    text: "模糊右(**%)",
    value: "LIKE_RIGHT",
  },
  {
    text: "模糊左(%**)",
    value: "LIKE_LEFT",
  },
  {
    text: "模糊(%**%)",
    value: "LIKE",
  },
  {
    text: "大于等于",
    value: "GE",
  },
  {
    text: "大于",
    value: "GT",
  },
  {
    text: "小于等于",
    value: "LE",
  },
  {
    text: "小于",
    value: "LT",
  },
  {
    text: "正序",
    value: "ASC",
  },
  {
    text: "倒序",
    value: "DESC",
  },
];
// 应用审核状态
export const yyshztType: dict[] = [
  {
    text: "待审核",
    value: "0",
  },
  {
    text: "审核通过",
    value: "1",
  },
  {
    text: "已驳回",
    value: "2",
  },
];
// 优先级
export const yxjType: dict[] = [
  {
    text: "低",
    value: "1",
  },
  {
    text: "中",
    value: "2",
  },
  {
    text: "高",
    value: "3",
  },
];
// 应用场景
export const yycjType: dict[] = [
  // {
  //   text: "检查",
  //   value: "1",
  // },
  {
    text: "预警",
    value: "2",
  },
  {
    text: "周期任务",
    value: "3",
  },
];
// 指令反馈类型
export const zlfklxDict: dict[] = [
  {
    text: "无需反馈",
    value: "1",
  },
  {
    text: "需要反馈",
    value: "2",
  },
];
// 系统用户状态
export const sysUserStatusDict: dict[] = [
  {
    text: "启用",
    value: "1",
  },
  {
    text: "禁用",
    value: "0",
  },
];
// 业务中心-任务状态
export const taskStatusDict: dict[] = [
  {
    text: "待处理",
    value: "0",
  },
  {
    text: "已完成",
    value: "1",
  },
  {
    text: "已转发",
    value: "2",
  },
];

// 是否隐藏
export const isHideDict: dict[] = [
  {
    text: "是",
    value: "true",
  },
  {
    text: "否",
    value: "false",
  },
];

export const yxbzDict: dict[] = [
  {
    text: "是",
    value: "1",
  },
  {
    text: "否",
    value: "0",
  },
];

export const forwardTypeDict: dict[] = [
  {
    text: "个人",
    value: "1",
  },
  {
    text: "单位",
    value: "2",
  },
];
