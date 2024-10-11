import { useFetch } from "@/utils/http";

export const apiMovieCountryPage = (params: any) => {
return useFetch.get<any>({
  url: "/movieCountry/page",
  params,
  });
};

export const apiMovieCountryView = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCountry/view",
    params,
  });
};

export const apiMovieCountryCreate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieCountry/create",
    data,
  });
};

export const apiMovieCountryUpdate = (data: any) => {
  return useFetch.post<any>({
    url: "/movieCountry/update",
    data,
  });
};

export const apiMovieCountryDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/movieCountry/delete",
    data,
  });
};

export const apiMovieCountryColumn = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCountry/column",
    params,
  });
};

export const apiMovieCountryExport = (params: any) => {
  return useFetch.get<any>({
    url: "/movieCountry/export",
    params,
  });
};