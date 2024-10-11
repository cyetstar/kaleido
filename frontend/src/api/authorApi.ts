import { useFetch } from "@/utils/http";

export const apiAuthorPage = (params: any) => {
  return useFetch.get<any>({
    url: "/author/page",
    params,
  });
};

export const apiAuthorView = (params: any) => {
  return useFetch.get<any>({
    url: "/author/view",
    params,
  });
};

export const apiAuthorCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/author/create",
    data,
  });
};

export const apiAuthorUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/author/update",
    data,
  });
};

export const apiAuthorDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/author/delete",
    data,
  });
};

export const apiAuthorColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/author/column",
    params,
  });
};

export const apiAuthorExport = (params: any) => {
  return useFetch.get<any>({
    url: "/author/export",
    params,
  });
};
