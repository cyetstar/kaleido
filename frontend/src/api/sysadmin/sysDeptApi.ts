import {useFetch} from "@/utils/http";

export const sysDeptPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysDept/page",
        params,
    });
};

export const sysDeptViewApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysDept/view",
        params,
    });
};

export const sysDeptCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysDept/create",
        data,
    });
};

export const sysDeptUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysDept/update",
        data,
    });
};

export const sysDeptDeleteApi = (data: any) => {
    return useFetch.delete<any>({
        url: "/sysDept/delete",
        data,
    });
};

export const sysDeptColumnApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysDept/column",
        params,
    });
};

export const sysDeptExportApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysDept/export",
        params,
    });
};