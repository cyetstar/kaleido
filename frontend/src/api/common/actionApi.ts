import { useFetch } from "@/utils/http";

export const apiActionStart = (data: any) => {
  return useFetch.post<any>({
    url: "/action/start",
    data,
  });
};

export const apiActionStop = (data: any) => {
  return useFetch.post<any>({
    url: "/action/stop",
    data,
  });
};
