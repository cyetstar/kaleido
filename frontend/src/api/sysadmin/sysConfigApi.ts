import { useFetch } from "@/utils/http";

export const sysConfigPageApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysConfig/page",
    params,
  });
};

export const sysConfigViewApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysConfig/view",
    params,
  });
};

export const sysConfigCreateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysConfig/create",
    data,
  });
};

export const sysConfigUpdateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysConfig/update",
    data,
  });
};

export const sysConfigDeleteApi = (data: any) => {
  return useFetch.delete<any>({
    url: "/sysConfig/delete",
    data,
  });
};

export const sysConfigColumnApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysConfig/column",
    params,
  });
};

export const sysConfigExportApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysConfig/export",
    params,
  });
};

export const apiSysConfigFindByKeys = (data: any) => {
  return useFetch.post<any>({
    url: "/sysConfig/findByKeys",
    data,
  });
};

export const apiSysConfigSave = (data: any) => {
  return useFetch.post<any>({
    url: "/sysConfig/save",
    data,
  });
};
