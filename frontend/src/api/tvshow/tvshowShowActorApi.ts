import { useFetch } from "@/utils/http";

export const apiTvshowShowActorPage = (params: any) => {
return useFetch.get<any>({
  url: "/tvshowShowActor/page",
  params,
  });
};

export const apiTvshowShowActorView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowActor/view",
    params,
  });
};

export const apiTvshowShowActorCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShowActor/create",
    data,
  });
};

export const apiTvshowShowActorUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShowActor/update",
    data,
  });
};

export const apiTvshowShowActorDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/tvshowShowActor/delete",
    data,
  });
};

export const apiTvshowShowActorColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowActor/column",
    params,
  });
};

export const apiTvshowShowActorExport = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShowActor/export",
    params,
  });
};