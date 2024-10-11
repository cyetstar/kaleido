import { useFetch } from "@/utils/http";

export const apiActorPage = (params: any) => {
  return useFetch.get<any>({
    url: "/actor/page",
    params,
  });
};

export const apiActorView = (params: any) => {
  return useFetch.get<any>({
    url: "/actor/view",
    params,
  });
};

export const apiActorCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/actor/create",
    data,
  });
};

export const apiActorUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/actor/update",
    data,
  });
};

export const apiActorDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/actor/delete",
    data,
  });
};

export const apiActorColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/actor/column",
    params,
  });
};

export const apiActorExport = (params: any) => {
  return useFetch.get<any>({
    url: "/actor/export",
    params,
  });
};
