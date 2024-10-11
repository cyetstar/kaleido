import { useFetch } from "@/utils/http";

export const apiMovieBasicCollectionPage = (params: any) => {
  return useFetch.get<any>({
    url: "/movieBasicCollection/page",
    params,
  });
};
