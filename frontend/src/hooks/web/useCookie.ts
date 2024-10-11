/*
 * @Author: gongxiaofei
 * @Date: 2022-06-22 13:32:07
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-08-12 10:29:18
 * @Description:
 */
import type { TokenTypeEnum } from "@/enums/authEnum";
import type { CookieAttributes } from "js-cookie";
import Cookie from "js-cookie";
import { isJsonString } from "@ht/util";

// 可自行扩展更多 enmu类型
type CookieKey = TokenTypeEnum;

export function createCookie(
  key: CookieKey,
  value: string,
  options?: CookieAttributes
) {
  Cookie.set(key, value, options);
}

export function removeCookies(keys: CookieKey[] = []) {
  keys.forEach((key) => {
    Cookie.remove(key);
  });
}

export function useCookie<T = string>(key: CookieKey): UnDefable<T> {
  const value = Cookie.get(key);
  if (typeof value === "undefined") {
    return undefined;
  } else if (isJsonString(value)) {
    return JSON.parse(value as string) as T;
  } else {
    return value as unknown as T;
  }
}
