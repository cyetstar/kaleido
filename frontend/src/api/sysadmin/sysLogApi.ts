import {useFetch} from "@/utils/http";

export const sysLogListApi = () => {
    return useFetch.get<any>({
        url: "/sysLog/list",
    });
};

export const sysLogReadApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysLog/read",
        params,
    });
};