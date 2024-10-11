import { useFetch } from "@/utils/http";
import { RequestMethod } from "@ht/http";

export const apiComicBookPage = (params: any) => {
  return useFetch.get<any>({
    url: "/comicBook/page",
    params,
  });
};

export const apiComicBookView = (params: any) => {
  return useFetch.get<any>({
    url: "/comicBook/view",
    params,
  });
};

export const apiComicBookCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/comicBook/create",
    data,
  });
};

export const apiComicBookUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/comicBook/update",
    data,
  });
};

export const apiComicBookDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/comicBook/delete",
    data,
  });
};

export const apiComicBookColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/comicBook/column",
    params,
  });
};

export const apiComicBookExport = (params: any) => {
  return useFetch.get<any>({
    url: "/comicBook/export",
    params,
  });
};

export const apiComicBookUploadCover = (data: any) => {
  return useFetch.post<any>({
    url: "/comicBook/uploadCover",
    data,
  });
};

export const apiComicBookClearCover = (data: any) => {
  return useFetch.post<any>({
    url: "/comicBook/clearCover",
    data,
  });
};

export const apiComicBookOpenComicInfo = (params: any) => {
  return useFetch.open<any>({
    url: "/comicBook/openComicInfo",
    params,
  });
};

export const apiComicBookWriteComicInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/comicBook/writeComicInfo",
    data,
  });
};
