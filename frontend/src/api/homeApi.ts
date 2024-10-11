/*
 * @Author: songtao
 * @Date: 2023-09-25 15:36:40
 * @LastEditors: songtao
 * @LastEditTime: 2023-09-25 16:27:19
 * @Description:
 */
import { useFetch } from "@/utils/http";

const enum Api {
  YCTP_HOME_YWSJ = "/dp/ywsj",
  YCTP_HOME_ZPKRL = "/dp/zpkrl",
  YCTP_HOME_RKSJFX = "/dp/rksjfx",
}

export const yctpHomeYwsjApi = () => {
  return useFetch.get<any>({
    url: Api.YCTP_HOME_YWSJ,
  });
};

export const yctpHomeZpkrlApi = () => {
  return useFetch.get<any>({
    url: Api.YCTP_HOME_ZPKRL,
  });
};

export const yctpHomeRksjfxApi = () => {
  return useFetch.get<any>({
    url: Api.YCTP_HOME_RKSJFX,
  });
};
