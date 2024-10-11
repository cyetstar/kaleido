import {useFetch} from "@/utils/http";
import {ResponseTypeEnum} from "@/enums/httpEnum";


export const sysUserPageApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysUser/page",
        params,
    });
};
export const sysUserDeleteApi = (data: string | string[]) => {
    return useFetch.delete<any>({
        url: "/sysUser/delete",
        data,
    });
};
export const sysUserUpdateEnableApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysUser/updateEnable",
        data,
    });
};
export const sysUserCheckUniqueApi = (username: string) => {
    return useFetch.get<any>({
        url: "/sysUser/checkUnique",
        data: {
            username,
        },
    });
};
export const sysUserColumnApi = () => {
    return useFetch.get<any>({
        url: "/sysUser/column",
    });
};
export const sysUserExportApi = (data: any) => {
    return useFetch.get<any>({
        url: "/sysUser/export",
        data,
        isTransformResponse: false,
        responseType: ResponseTypeEnum.BLOB,
    });
};
export const sysUserCreateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysUser/create",
        data,
    });
};
export const sysUserUpdateApi = (data: any) => {
    return useFetch.post<any>({
        url: "/sysUser/update",
        data,
    });
};
export const sysUserViewApi = (id: string) => {
    return useFetch.get<any>({
        url: "/sysUser/view",
        data: {
            id,
        },
    });
};

export const sysUserListByDeptCodeApi = (params: any) => {
    return useFetch.get<any>({
        url: "/sysUser/listByDeptCode",
        params
    });
};