/*
 * @Author: gongxiaofei
 * @Date: 2022-08-09 18:28:27
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-18 11:30:31
 * @Description:
 */
import { useFetch } from "@/utils/http";

enum Api {
  BATCH_UPLOAD = "/sysadmin/fwtFj/batchUpload",
  EXPORT_FILE = "/tycjBzSjy/excelImport",
}

export const batchUpload = (files: any) => {
  const formData = new FormData();
  // formData.append("files", files);
  // formData.append("options", "DEFAULT");
  files.forEach((file: any) => {
    formData.append("files", file);
    formData.append("options", "DEFAULT");
  });
  return useFetch.post<any>({
    url: Api.BATCH_UPLOAD,
    data: formData,
  });
};

export const exportFile = (files: any, flId: any) => {
  const formData = new FormData();
  files.forEach((file: any) => {
    formData.append("file", file);
    formData.append("flId", flId);
  });
  console.log(formData);
  return useFetch.post<any>({
    url: Api.EXPORT_FILE,
    data: formData,
  });
};
