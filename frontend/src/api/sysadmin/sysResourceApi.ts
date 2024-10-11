import { useFetch } from "@/utils/http";

export const sysResourcePageApi = (params: any) => {
  return useFetch.get<any>({
    url: "sysResource/page",
    params,
  });
};
export const sysResourceColumnApi = () => {
  return useFetch.get<any>({
    url: "sysResource/column",
  });
};
export const sysResourceExportApi = (data: any) => {
  return useFetch.get<any>({
    url: "sysResource/export",
    data,
  });
};
// 初始化菜单
export const sysResourceInitApi = (data: any) => {
  return useFetch.post<any>({
    url: "sysResource/init",
    data,
  });
};

export const apiSysResourceListType = () => {
  return useFetch.get({
    url: "/sysResource/listType",
  });
};

export const apiSysResourceListTypeByRoleId = (roleId: string) => {
  return useFetch.get({
    url: "/sysResource/listTypeByRoleId",
    params: {
      roleId,
    },
  });
};
export const sysRoleResourceBindApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysRoleResource/bind",
    data,
  });
};
