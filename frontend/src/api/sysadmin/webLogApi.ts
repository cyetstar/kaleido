import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";

export const webLogPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/webLog/page",
        params,
    });
};
export const webLogColumnApi = () => {
    return useFetch.get<any>({
        url: "/webLog/column",
    });
};
export const webLogExportApi = (data: any) => {
    return useFetch.get<any>({
        url: "/webLog/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};
