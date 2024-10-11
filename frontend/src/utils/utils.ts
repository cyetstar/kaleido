/*
 * @Author: gongxiaofei
 * @Date: 2022-08-09 10:49:13
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-08-23 11:02:36
 * @Description:
 */
import { yypbInfo } from "@/types/tycjSjbBpApi";
import dayjs from "dayjs";

/**
 * @description: 格式化数据项
 * @param { 源数据列表 } sourceList
 * @param { 对比源 } source
 * @param { 对比值 } value
 * @param { 输出目标 } target
 * @param { 错误校验类型 } type // 1: ['', null, undefined], 其他: ['', null, undefined, 0]
 */
export const returnData = (
  sourceList: any,
  source: string,
  value: string | number,
  target: string,
  type: number = 1
): string | number | boolean => {
  if (!value || !sourceList || sourceList.length === 0) return "";
  const errorMap =
    type === 1 ? ["", null, undefined] : ["", null, undefined, 0];
  if (errorMap.includes(value)) return "";
  let res = sourceList.find((r: any) => r[source] === value);
  if (res) {
    return res[target];
  } else {
    return "";
  }
  // return sourceList.find((r: any) => r[source] === value)[target]
};

// 生成随机id
export const generateId = () => {
  return Math.floor(
    Math.random() * 100000 + Math.random() * 20000 + Math.random() * 5000
  );
};

// 返回静态文件地址，解决打包后image及icon加载404问题
const filePath: any = {
  images: "images",
  icons: "icons",
  examine: "images/examine",
  work: "images/work",
};
export const getAssetsFile = (name: string, path: string) => {
  return new URL(`../assets/${filePath[path]}/${name}`, import.meta.url).href;
};

// 二进制excel下载
export const dowloadExcel = (data: any, fileName: string) => {
  // debugger;

  const url = window.URL.createObjectURL(new Blob([data]));
  const link = document.createElement("a");
  link.href = url;
  link.target = "_blank";
  link.style.display = "none";
  link.setAttribute(
    "download",
    `${fileName + dayjs().format("YYYY-MM-DD HH:mm:ss")}.xlsx`
  );
  document.body.appendChild(link);
  link.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(link);
};

export const downloadFile = (data: any, fileName: any) => {
  const url = window.URL.createObjectURL(new Blob([data]));
  const link = document.createElement("a");
  link.href = url;
  link.target = "_blank";
  link.style.display = "none";
  let regFileName = fileName.match(/=(.*)$/)[1];
  link.setAttribute("download", decodeURI(regFileName));
  document.body.appendChild(link);
  link.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(link);
};

export const openFile = (data: any, type: string) => {
  const url = window.URL.createObjectURL(new Blob([data], { type }));
  window.open(url);
};

// 深clone
export const deepClone = (origin: any) => {
  if (origin === undefined) {
    return undefined;
  }
  return JSON.parse(JSON.stringify(origin));
};

// 时间转换
export const splitTime = (time: string, type = 1) => {
  if (!time) return;
  if (type == 1) {
    const reg = /(.{4})(.{2})(.{2})(.{2})(.{2})(.{2})/;
    return time.replace(reg, "$1-$2-$3 $4:$5:$6");
  } else if (type == 2) {
    const reg = /(.{4})(.{2})(.{2})(.{2})(.{2})/;
    return time.replace(reg, "$1-$2-$3 $4:$5");
  } else {
    const reg = /(.{4})(.{2})(.{2})(.{2})(.{2})(.{2})/;
    return time.replace(reg, "$1-$2-$3");
  }
};

/**
 *  获取节点到顶部的距离
 */
export const getElementTop = (el: any) => {
  let actualTop = el.offsetTop;
  let current = el.offsetParent;
  while (current !== null) {
    actualTop += current.offsetTop;
    current = current.offsetParent;
  }
  return actualTop;
};

/**
 *  判断对象是否为空
 */
export function objHasKey(obj: any) {
  if (obj == null || obj == undefined) return true;
  return Object.keys(obj).length == 0 && obj.constructor == Object;
}

/**
 *  复制文本
 */
export function copyToClipboard(textToCopy: any) {
  if (navigator.clipboard && window.isSecureContext) {
    return navigator.clipboard.writeText(textToCopy);
  } else {
    // 创建text area
    let textArea: any = document.createElement("textarea");
    textArea.value = textToCopy;
    // 使text area不在viewport，同时设置不可见
    textArea.style.position = "absolute";
    textArea.style.opacity = 0;
    textArea.style.left = "-999999px";
    textArea.style.top = "-999999px";
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();
    return new Promise((res: any, rej: any) => {
      // 执行复制命令并移除文本框
      document.execCommand("copy") ? res() : rej();
      textArea.remove();
    });
  }
}

export const isValidKey = (
  key: string | number | symbol,
  object: object
): key is keyof typeof object => {
  return key in object;
};

export function sortApp(list: yypbInfo[]) {
  return list.sort((a, b) => a?.xh - b?.xh);
}

export function formatUnixTimestamp(unixTimestamp: number): string {
  if (isNaN(unixTimestamp)) {
    return "";
  }
  let date = new Date(unixTimestamp * 1000);
  let year = date.getFullYear();
  let month = String(date.getMonth() + 1).padStart(2, "0"); // 月份从0开始，因此要加1
  let day = String(date.getDate()).padStart(2, "0");
  let hours = String(date.getHours()).padStart(2, "0");
  let minutes = String(date.getMinutes()).padStart(2, "0");
  let seconds = String(date.getSeconds()).padStart(2, "0");

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
