/*
 * @Author: gongxiaofei
 * @Date: 2022-06-22 13:32:07
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-28 15:17:37
 * @Description:
 */
import type { App } from "vue";
import { getAssetsFile, dowloadExcel } from "@/utils/utils";

export const mountFunction = (app: App) => {
  app.config.globalProperties.$getAssetsFile = getAssetsFile;
  app.config.globalProperties.$dowloadExcel = dowloadExcel;
};

export function debounce(fn: Function, wait: number) {
  let timeout: any = null;
  return function () {
    if (timeout !== null) clearTimeout(timeout);
    timeout = setTimeout(fn, wait);
  };
}
