import { useFetch } from "@/utils/http";

export const apiTvshowEpisodeActorPage = (params: any) => {
return useFetch.get<any>({
  url: "/tvshowEpisodeActor/page",
  params,
  });
};

export const apiTvshowEpisodeActorView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisodeActor/view",
    params,
  });
};

export const apiTvshowEpisodeActorCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowEpisodeActor/create",
    data,
  });
};

export const apiTvshowEpisodeActorUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowEpisodeActor/update",
    data,
  });
};

export const apiTvshowEpisodeActorDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowEpisodeActor/delete",
    data,
  });
};

export const apiTvshowEpisodeActorColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisodeActor/column",
    params,
  });
};

export const apiTvshowEpisodeActorExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowEpisodeActor/export",
    params,
  });
};