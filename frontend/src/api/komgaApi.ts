import { useFetch } from "@/utils/http";

export const apiKomgaListLibrary = () => {
  return useFetch.post<any>({
    url: "/komga/listLibrary",
  });
};
