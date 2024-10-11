import {useFetch} from "@/utils/http";

export const sysDictTypePageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysDictType/page",
        params,
    });
};
export const sysDictTypeViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/sysDictType/view",
        data: {
            id,
        },
    });
};
export const sysDictTypeCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysDictType/create",
        data,
    });
};
export const sysDictTypeUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysDictType/update",
        data,
    });
};
export const sysDictTypeDeleteApi = (data: any) => {
    return useFetch.delete<any>({
        url: "/sysDictType/delete",
        data,
    });
};

export const sysDictTypeReloadApi = () => {
    return useFetch.get<any>({
        url: "/sysDictType/reload",
    });
};

