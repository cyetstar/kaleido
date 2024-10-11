import { useFetch } from "@/utils/http";

export const apiMovieAkaPage = (params: any) => {
return useFetch.get<any>({
  url: "/movieAka/page",
  params,
  });
};

export const apiMovieAkaView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieAka/view",
    params,
  });
};

export const apiMovieAkaCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieAka/create",
    data,
  });
};

export const apiMovieAkaUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieAka/update",
    data,
  });
};

export const apiMovieAkaDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieAka/delete",
    data,
  });
};

export const apiMovieAkaColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieAka/column",
    params,
  });
};

export const apiMovieAkaExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieAka/export",
    params,
  });
};