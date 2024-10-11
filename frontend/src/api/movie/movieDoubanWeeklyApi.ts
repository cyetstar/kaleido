import { useFetch } from "@/utils/http";

export const apiMovieDoubanWeeklyPage = (params: any) => {
  return useFetch.get<any>({
    url: "/movieDoubanWeekly/page",
    params,
  });
};

export const apiMovieDoubanWeeklyView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieDoubanWeekly/view",
    params,
  });
};

export const apiMovieDoubanWeeklyCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieDoubanWeekly/create",
    data,
  });
};

export const apiMovieDoubanWeeklyUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieDoubanWeekly/update",
    data,
  });
};

export const apiMovieDoubanWeeklyDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieDoubanWeekly/delete",
    data,
  });
};

export const apiMovieDoubanWeeklyColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieDoubanWeekly/column",
    params,
  });
};

export const apiMovieDoubanWeeklyExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieDoubanWeekly/export",
    params,
  });
};

export const apiMovieDoubanWeeklySync = () => {
  return useFetch.post<any>({
    url: "/movieDoubanWeekly/sync",
  });
};
