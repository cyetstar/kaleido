import { useFetch } from "@/utils/http";

export const apiMovieCollectionPage = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCollection/page",
    params,
  });
};

export const apiMovieCollectionView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCollection/view",
    params,
  });
};

export const apiMovieCollectionCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieCollection/create",
    data,
  });
};

export const apiMovieCollectionUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieCollection/update",
    data,
  });
};

export const apiMovieCollectionDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieCollection/delete",
    data,
  });
};

export const apiMovieCollectionColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCollection/column",
    params,
  });
};

export const apiMovieCollectionExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCollection/export",
    params,
  });
};

export const apiMovieCollectionSyncPlex = () => {
  return useFetch.post<any>({
    url: "/movieCollection/syncPlex",
  });
};

export const apiMovieCollectionListByMovieId = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCollection/listByMovieId",
    params,
  });
};
