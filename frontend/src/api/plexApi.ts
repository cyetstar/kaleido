import { useFetch } from "@/utils/http";

export const apiPlexListLibrary = () => {
  return useFetch.post<any>({
    url: "/plex/listLibrary",
  });
};
