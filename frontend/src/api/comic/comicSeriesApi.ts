import { useFetch } from "@/utils/http";

export const apiComicSeriesPage = (params: any) => {
  return useFetch.get<any>({
    url: "/comicSeries/page",
    params,
  });
};

export const apiComicSeriesView = (params: any) => {
  return useFetch.get<any>({
    url: "/comicSeries/view",
    params,
  });
};

export const apiComicSeriesCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/create",
    data,
  });
};

export const apiComicSeriesUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/update",
    data,
  });
};

export const apiComicSeriesDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/comicSeries/delete",
    data,
  });
};

export const apiComicSeriesColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/comicSeries/column",
    params,
  });
};

export const apiComicSeriesExport = (params: any) => {
  return useFetch.get<any>({
    url: "/comicSeries/export",
    params,
  });
};

export const apiComicSeriesSearchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/searchInfo",
    data,
  });
};

export const apiComicSeriesMatchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/matchInfo",
    data,
  });
};

export const apiComicSeriesSync = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/sync",
    data,
  });
};

export const apiComicSeriesReadComicInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/readComicInfo",
    data,
  });
};

export const apiComicSeriesWriteComicInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/comicSeries/writeComicInfo",
    data,
  });
};

export const apiComicSeriesViewPath = (params: any) => {
  return useFetch.get<any>({
    url: "/comicSeries/viewPath",
    params,
  });
};
