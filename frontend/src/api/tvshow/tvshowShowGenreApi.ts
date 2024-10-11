import { useFetch } from "@/utils/http";

export const apiTvshowShowGenrePage = (params: any) => {
return useFetch.get<any>({
  url: "/tvshowShowGenre/page",
  params,
  });
};

export const apiTvshowShowGenreView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowGenre/view",
    params,
  });
};

export const apiTvshowShowGenreCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShowGenre/create",
    data,
  });
};

export const apiTvshowShowGenreUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShowGenre/update",
    data,
  });
};

export const apiTvshowShowGenreDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowShowGenre/delete",
    data,
  });
};

export const apiTvshowShowGenreColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowGenre/column",
    params,
  });
};

export const apiTvshowShowGenreExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowGenre/export",
    params,
  });
};