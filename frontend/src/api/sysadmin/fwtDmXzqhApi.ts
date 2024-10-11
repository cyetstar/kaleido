import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";

export const fwtDmXzqhPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/fwtDmXzqh/page",
        params,
    });
};
export const fwtDmXzqhCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmXzqh/create",
        data,
    });
};
export const fwtDmXzqhUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmXzqh/update",
        data,
    });
};
export const fwtDmXzqhDeleteApi = (ids: string[]) => {
    return useFetch.delete<any>({
        url: "/fwtDmXzqh/delete",
        params: {
            id: ids.join(","),
        },
    });
};
export const fwtDmXzqhViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/fwtDmXzqh/view",
        data: {
            id,
        },
    });
};
export const fwtDmXzqhColumnApi = () => {
    return useFetch.get<any>({
        url: "/fwtDmXzqh/column",
    });
};
export const fwtDmXzqhExportApi = (data: any) => {
    return useFetch.get<any>({
        url: "/fwtDmXzqh/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};