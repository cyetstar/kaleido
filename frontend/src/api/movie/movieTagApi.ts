import { useFetch } from "@/utils/http";

export const apiMovieTagPage = (params: any) => {
return useFetch.get<any>({
  url: "/movieTag/page",
  params,
  });
};

export const apiMovieTagView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieTag/view",
    params,
  });
};

export const apiMovieTagCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieTag/create",
    data,
  });
};

export const apiMovieTagUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieTag/update",
    data,
  });
};

export const apiMovieTagDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieTag/delete",
    data,
  });
};

export const apiMovieTagColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieTag/column",
    params,
  });
};

export const apiMovieTagExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieTag/export",
    params,
  });
};