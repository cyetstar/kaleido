/*
 * @Author: gongxiaofei
 * @Date: 2023-06-09 13:46:50
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-10-17 15:27:32
 * @Description:
 */
import { useFetch } from "@/utils/http";

enum Api {
  COMMON_SJGSDW_CASCADE = "/common/sjgsdwCascade",
  COMMOM_SJGSDW_CASCADEBACK = "/common/sjgsdwCascadeBack",
  FILEINFO_UPLOAD = "/fileInfo/upload",
  FILEINFO_BATCH_UPLOAD = "/fileInfo/batchUpload",
  DICT_DICTTYPE = "/sysDict/listByType",
}

export const commonSjgsdwCascade = (params: any) => {
  return useFetch.get<any>({
    url: Api.COMMON_SJGSDW_CASCADE,
    params,
  });
};

export const commonSjgsdwCascadeBack = (params: any) => {
  return useFetch.get<any>({
    url: Api.COMMOM_SJGSDW_CASCADEBACK,
    params,
  });
};

function getFile(file: any) {
  let formData = new FormData();
  formData.append("file", file);
  return formData;
}

export const fileInfoUpload = (file: any) => {
  return useFetch.post<any>({
    url: Api.FILEINFO_UPLOAD,
    data: getFile(file),
  });
};

export const fileInfoBatchUpload = (files: any) => {
  let formData = new FormData();
  files.forEach((file: any) => {
    console.log(file);
    formData.append("files", file);
  });
  return useFetch.post<any>({
    url: Api.FILEINFO_BATCH_UPLOAD,
    data: formData,
  });
};

export const getDict = (type: string) => {
  return useFetch.get<any>({
    url: Api.DICT_DICTTYPE,
    params: {
      type,
    },
  });
};
