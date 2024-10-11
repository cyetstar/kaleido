import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";


export const fwtDmXzjdPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/fwtDmXzjd/page",
        params,
    });
};
export const fwtDmXzjdDeleteApi = (ids: string[]) => {
    return useFetch.delete<any>({
        url: "/fwtDmXzjd/delete",
        params: {
            id: ids.join(","),
        },
    });
};
export const fwtDmXzjdColumnApi = () => {
    return useFetch.get<any>({
        url: "/fwtDmXzjd/column",
    });
};
export const fwtDmXzjdExportApi = (data: any) => {
    return useFetch.get<any>({
        url: "/fwtDmXzjd/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};
export const fwtDmXzjdCreate = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmXzjd/create",
        data,
    });
};
export const fwtDmXzjdUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmXzjd/update",
        data,
    });
};
export const fwtDmXzjdViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/fwtDmXzjd/view",
        data: {
            id,
        },
    });
};