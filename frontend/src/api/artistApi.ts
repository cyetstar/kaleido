import { useFetch } from "@/utils/http";

export const apiArtistPage = (params: any) => {
  return useFetch.get<any>({
    url: "/artist/page",
    params,
  });
};

export const apiArtistView = (params: any) => {
  return useFetch.get<any>({
    url: "/artist/view",
    params,
  });
};

export const apiArtistCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/artist/create",
    data,
  });
};

export const apiArtistUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/artist/update",
    data,
  });
};

export const apiArtistDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/artist/delete",
    data,
  });
};

export const apiArtistColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/artist/column",
    params,
  });
};

export const apiArtistExport = (params: any) => {
  return useFetch.get<any>({
    url: "/artist/export",
    params,
  });
};

export const apiArtistSyncPlexById = (data: any) => {
  return useFetch.get<any>({
    url: "/artist/syncPlexById",
    data,
  });
};

export const apiArtistSearchNetease = (data: any) => {
  return useFetch.get<any>({
    url: "/artist/searchNetease",
    data,
  });
};

export const apiArtistMatchNetease = (data: any) => {
  return useFetch.post<any>({
    url: "/artist/matchNetease",
    data,
  });
};
