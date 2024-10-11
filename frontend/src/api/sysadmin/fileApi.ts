import { useFetch } from "@/utils/http";

export const apiFilePage = (params: any) => {
  return useFetch.get<any>({
    url: "/file/page",
    params,
  });
};

export const apiFileDelete = (data: any) => {
  return useFetch.delete<any>({
    url: "/file/delete",
    data,
  });
};

export const apiFileRename = (data: any) => {
  return useFetch.post<any>({
    url: "/file/rename",
    data,
  });
};

export const apiFileCopyOrCut = (data: any) => {
  return useFetch.post<any>({
    url: "/file/copyOrCut",
    data,
  });
};

export const apiFileNewDirectory = (data: any) => {
  return useFetch.post<any>({
    url: "/file/newDirectory",
    data,
  });
};

export const apiFileOpen = (params: any) => {
  return useFetch.open<any>({
    url: "/file/open",
    params,
  });
};
