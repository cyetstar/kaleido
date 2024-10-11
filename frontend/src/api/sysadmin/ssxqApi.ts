import {useFetch} from "@/utils/http";

export const listByParentApi = (params: any) => {
    return useFetch.get<any>({
        url: "/ssxq/listByParent",
        params,
    });
};