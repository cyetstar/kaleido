import { useFetch } from "@/utils/http";
import { RequestMethod } from "@ht/http";

export const apiMovieBasicPage = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasic/page",
    params,
  });
};

export const apiMovieBasicView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasic/view",
    params,
  });
};

export const apiMovieBasicCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieBasic/create",
    data,
  });
};

export const apiMovieBasicUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieBasic/update",
    data,
  });
};

export const apiMovieBasicDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieBasic/delete",
    data,
  });
};

export const apiMovieBasicColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasic/column",
    params,
  });
};

export const apiMovieBasicExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasic/export",
    params,
  });
};

export const apiMovieBasicSearchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/movieBasic/searchInfo",
    data,
  });
};

export const apiMovieBasicMatchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/movieBasic/matchInfo",
    data,
  });
};

export const apiMovieBasicViewPath = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasic/viewPath",
    params,
  });
};

export const apiMovieBasicUploadPoster = (data: any) => {
  return useFetch.upload<any>({
    url: "/movieBasic/uploadPoster",
    method: RequestMethod.POST,
    data,
  });
};

export const apiMovieBasicDownloadPoster = (data: any) => {
  return useFetch.post<any>({
    url: "/movieBasic/downloadPoster",
    data,
  });
};
