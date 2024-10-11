import { useFetch } from "@/utils/http";

export const apiTvshowEpisodePage = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisode/page",
    params,
  });
};

export const apiTvshowEpisodeView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisode/view",
    params,
  });
};

export const apiTvshowEpisodeCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowEpisode/create",
    data,
  });
};

export const apiTvshowEpisodeUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowEpisode/update",
    data,
  });
};

export const apiTvshowEpisodeDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowEpisode/delete",
    data,
  });
};

export const apiTvshowEpisodeColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisode/column",
    params,
  });
};

export const apiTvshowEpisodeExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisode/export",
    params,
  });
};

export const apiTvshowEpisodeSyncPlex = () => {
  return useFetch.post<any>({
    url: "/tvshowEpisode/syncPlex",
  });
};
