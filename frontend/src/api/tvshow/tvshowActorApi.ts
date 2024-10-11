import { useFetch } from "@/utils/http";

export const apiTvshowActorPage = (params: any) => {
return useFetch.get<any>({
  url: "/tvshowActor/page",
  params,
  });
};

export const apiTvshowActorView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowActor/view",
    params,
  });
};

export const apiTvshowActorCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowActor/create",
    data,
  });
};

export const apiTvshowActorUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowActor/update",
    data,
  });
};

export const apiTvshowActorDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowActor/delete",
    data,
  });
};

export const apiTvshowActorColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowActor/column",
    params,
  });
};

export const apiTvshowActorExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowActor/export",
    params,
  });
};