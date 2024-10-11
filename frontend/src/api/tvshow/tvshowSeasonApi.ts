import { useFetch } from "@/utils/http";

export const apiTvshowSeasonPage = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowSeason/page",
    params,
  });
};

export const apiTvshowSeasonView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowSeason/view",
    params,
  });
};

export const apiTvshowSeasonCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowSeason/create",
    data,
  });
};

export const apiTvshowSeasonUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowSeason/update",
    data,
  });
};

export const apiTvshowSeasonDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowSeason/delete",
    data,
  });
};

export const apiTvshowSeasonColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowSeason/column",
    params,
  });
};

export const apiTvshowSeasonExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowSeason/export",
    params,
  });
};

export const apiTvshowSeasonMatchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowSeason/matchInfo",
    data,
  });
};

export const apiTvshowSeasonDownloadPoster = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowSeason/downloadPoster",
    data,
  });
};

export const apiTvshowSeasonViewPath = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowSeason/viewPath",
    params,
  });
};
