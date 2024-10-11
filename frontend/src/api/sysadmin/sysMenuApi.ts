import { useFetch } from "@/utils/http";

export const sysMenuPageApi = (params: any) => {
  return useFetch.get<any>({
    url: "/sysMenu/page",
    params,
  });
};
export const sysMenuColumnApi = () => {
  return useFetch.get<any>({
    url: "/sysMenu/column",
  });
};
export const sysMenuExportApi = (data: any) => {
  return useFetch.get<any>({
    url: "/sysMenu/export",
    data,
  });
};
// 初始化菜单
export const sysMenuInitApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysMenu/init",
    data,
  });
};
export const sysMenuViewApi = (id: string) => {
  return useFetch.get<any>({
    url: "/sysMenu/view",
    data: {
      id,
    },
  });
};
export const sysMenuUpdateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysMenu/update",
    data,
  });
};
export const sysMenuDeleteApi = (data: any) => {
  return useFetch.delete<any>({
    url: "/sysMenu/delete",
    data,
  });
};
export const sysMenuTreeApi = (apps?: string) => {
  return useFetch.get({
    url: "/sysMenu/tree2",
    data: {
      apps,
    },
  });
};

export const sysMenuUpdateHiddenApi = (data: any) => {
  return useFetch.post({
    url: "/sysMenu/updateHidden",
    data,
  });
};
