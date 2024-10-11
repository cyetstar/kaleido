import { useFetch } from "@/utils/http";

export const apiTvshowGenrePage = (params: any) => {
return useFetch.get<any>({
  url: "/tvshowGenre/page",
  params,
  });
};

export const apiTvshowGenreView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowGenre/view",
    params,
  });
};

export const apiTvshowGenreCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowGenre/create",
    data,
  });
};

export const apiTvshowGenreUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowGenre/update",
    data,
  });
};

export const apiTvshowGenreDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowGenre/delete",
    data,
  });
};

export const apiTvshowGenreColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowGenre/column",
    params,
  });
};

export const apiTvshowGenreExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowGenre/export",
    params,
  });
};