import { useFetch } from "@/utils/http";

export const apiTvshowShowPage = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShow/page",
    params,
  });
};

export const apiTvshowShowView = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShow/view",
    params,
  });
};

export const apiTvshowShowCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShow/create",
    data,
  });
};

export const apiTvshowShowUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShow/update",
    data,
  });
};

export const apiTvshowShowSearchInfo = (data: any) => {
  return useFetch.post<any>({
    url: "/tvshowShow/searchInfo",
    data,
  });
};

export const apiTvshowShowViewPath = (params: any) => {
  return useFetch.get<any>({
    url: "/tvshowShow/viewPath",
    params,
  });
};
