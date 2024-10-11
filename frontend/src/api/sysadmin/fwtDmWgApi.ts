import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";


export const fwtDmWgPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/fwtDmWg/page",
        params,
    });
};
export const fwtDmWgDeleteApi = (ids: string[]) => {
    return useFetch.delete<any>({
        url: "/fwtDmWg/delete",
        params: {
            id: ids.join(","),
        },
    });
};
export const fwtDmWgColumnApi = () => {
    return useFetch.get<any>({
        url: "/fwtDmWg/column",
    });
};
export const fwtDmWgExport = (data: any) => {
    return useFetch.get<any>({
        url: "/fwtDmWg/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};
export const fwtDmWgCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmWg/create",
        data,
    });
};
export const fwtDmWgUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/fwtDmWg/update",
        data,
    });
};
export const fwtDmWgViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/fwtDmWg/view",
        data: {
            id,
        },
    });
};