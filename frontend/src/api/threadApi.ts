import { useFetch } from "@/utils/http";

export const apiThreadPage = (params: any) => {
  return useFetch.get<any>({
    url: "/thread/page",
    params,
  });
};

export const apiThreadView = (params: any) => {
  return useFetch.get<any>({
    url: "/thread/view",
    params,
  });
};

export const apiThreadCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/thread/create",
    data,
  });
};

export const apiThreadUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/thread/update",
    data,
  });
};

export const apiThreadDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/thread/delete",
    data,
  });
};

export const apiThreadColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/thread/column",
    params,
  });
};

export const apiThreadExport = (params: any) => {
  return useFetch.get<any>({
    url: "/thread/export",
    params,
  });
};

export const apiThreadUpdateStatus = (data: any) => {
  return useFetch.post<any>({
    url: "/thread/updateStatus",
    data,
  });
};
