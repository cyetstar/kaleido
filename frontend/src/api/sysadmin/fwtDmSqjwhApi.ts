import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";


export const fwtDmSqjwhPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/fwtDmSqjwh/page",
        params,
    });
};
export const fwtDmSqjwhDeleteApi = (ids: string[]) => {
    return useFetch.delete<any>({
        url: "/fwtDmSqjwh/delete",
        params: {
            id: ids.join(","),
        },
    });
};
export const fwtDmSqjwhColumnApi = () => {
    return useFetch.get<any>({
        url: "/fwtDmSqjwh/column",
    });
};
export const fwtDmSqjwhExportApi = (data: any) => {
    return useFetch.get<any>({
        url: "/fwtDmSqjwh/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};
export const fwtDmSqjwhCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmSqjwh/create",
        data,
    });
};
export const fwtDmSqjwhUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmSqjwh/update",
        data,
    });
};
export const fwtDmSqjwhViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/fwtDmSqjwh/view",
        data: {
            id,
        },
    });
};