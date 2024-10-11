import { useFetch } from "@/utils/http";

export const apiSysDictListByDictType = (dictType: string) => {
  return useFetch.get<any>({
    url: "/sysDict/listByDictType",
    params: {
      dictType,
    },
  });
};

export const apiSysDictPage = (params: any) => {
  return useFetch.get<any>({
    url: "/sysDict/page",
    params,
  });
};

export const sysDictViewApi = (id: string) => {
  return useFetch.get<any>({
    url: "/sysDict/view",
    params: {
      id,
    },
  });
};

export const sysDictCreateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysDict/create",
    data,
  });
};

export const sysDictCreateBatchApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysDict/createBatch",
    data,
  });
};
export const sysDictUpdateApi = (data: any) => {
  return useFetch.post<any>({
    url: "/sysDict/update",
    data,
  });
};
export const sysDictDeleteApi = (data: any) => {
  return useFetch.delete<any>({
    url: "/sysDict/delete",
    data,
  });
};
