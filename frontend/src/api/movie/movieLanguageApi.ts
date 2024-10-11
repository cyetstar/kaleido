import { useFetch } from "@/utils/http";

export const apiMovieLanguagePage = (params: any) => {
return useFetch.get<any>({
  url: "/movieLanguage/page",
  params,
  });
};

export const apiMovieLanguageView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieLanguage/view",
    params,
  });
};

export const apiMovieLanguageCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieLanguage/create",
    data,
  });
};

export const apiMovieLanguageUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieLanguage/update",
    data,
  });
};

export const apiMovieLanguageDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieLanguage/delete",
    data,
  });
};

export const apiMovieLanguageColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieLanguage/column",
    params,
  });
};

export const apiMovieLanguageExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieLanguage/export",
    params,
  });
};