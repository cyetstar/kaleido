import { useFetch } from "@/utils/http";

export const apiMovieGenrePage = (params: any) => {
return useFetch.get<any>({
  url: "/movieGenre/page",
  params,
  });
};

export const apiMovieGenreView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieGenre/view",
    params,
  });
};

export const apiMovieGenreCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieGenre/create",
    data,
  });
};

export const apiMovieGenreUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieGenre/update",
    data,
  });
};

export const apiMovieGenreDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieGenre/delete",
    data,
  });
};

export const apiMovieGenreColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieGenre/column",
    params,
  });
};

export const apiMovieGenreExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieGenre/export",
    params,
  });
};