import { CustomAxios } from "@ht/http";
import { ErrorMsgMap } from "@ht/http";
import { ContentType, ErrorCode, RequestMethod, ResponseType } from "@ht/http";
import config from "@/config";
import { useMessage } from "@/hooks/web/useMessage";

const { VITE_BASE_API } = import.meta.env;
export const useFetch = new CustomAxios({
  // 请求前缀
  baseURL: VITE_BASE_API,

  // 限制超时时长
  timeout: config.HTTP.timeout,

  // 超时错误提示
  timeoutErrorMessage: ErrorMsgMap.get(ErrorCode.A200),

  // 请求类型
  method: RequestMethod.GET,

  // 请求头
  headers: {
    "content-type": ContentType.JSON,
  },

  // 是否携带token
  withToken: true,

  // 是否sm2加密
  SM2Encrypt: false,

  // 是否进行响应数据转换
  isTransformResponse: true,

  // 响应体类型
  responseType: ResponseType.JSON,

  // 忽略 CancelToken
  ignoreCancelToken: false,

  message: {
    error(message: string) {
      useMessage().createMessage.error(message);
    },
  },
});
