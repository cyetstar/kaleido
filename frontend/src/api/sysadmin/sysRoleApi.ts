import { useFetch } from "@/utils/http";
import { ResponseTypeEnum } from "@/enums/httpEnum";

export const sysRolePageApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysRole/page",
    params,
  });
};
export const sysRoleDeleteApi = (data: any) => {
  return useFetch.delete<any>({
    url: "/sysRole/delete",
    data,
  });
};
export const sysRoleCreateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysRole/create",
    data,
  });
};
export const sysRoleUpdateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysRole/update",
    data,
  });
};
export const sysRoleViewApi = (id: string) => {
  return useFetch.get<any>({
    url: "/sysRole/view",
    data: {
      id,
    },
  });
};

export const sysRoleColumnApi = () => {
  return useFetch.get<any>({
    url: "/sysRole/column",
  });
};
export const sysRoleExportApi = (data: any) => {
  return useFetch.get<any>({
    url: "/sysRole/export",
    data,
    isTransformResponse: false,
    responseType: ResponseTypeEnum.BLOB,
  });
};

export const apiSysRoleAuthorize = (data: any) => {
  return useFetch.post({
    url: "/sysRole/authorize",
    data,
  });
};
