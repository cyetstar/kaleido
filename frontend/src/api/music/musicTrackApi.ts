import { useFetch } from "@/utils/http";

export const apiMusicTrackPage = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/page",
    params,
  });
};

export const apiMusicTrackView = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/view",
    params,
  });
};

export const apiMusicTrackCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/musicTrack/create",
    data,
  });
};

export const apiMusicTrackUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/musicTrack/update",
    data,
  });
};

export const apiMusicTrackDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/musicTrack/delete",
    data,
  });
};

export const apiMusicTrackColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/column",
    params,
  });
};

export const apiMusicTrackExport = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/export",
    params,
  });
};

export const apiMusicTrackListByAlbumId = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/listByAlbumId",
    params,
  });
};

export const apiMusicTrackViewLyric = (params: any) => {
  return useFetch.get<any>({
    url: "/musicTrack/viewLyric",
    params,
  });
};

export const apiMusicTrackDownloadLyric = (data: any) => {
  return useFetch.post<any>({
    url: "/musicTrack/downloadLyric",
    data,
  });
};
