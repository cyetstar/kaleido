/*
 * @Author: gongxiaofei
 * @Date: 2022-08-08 10:09:05
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-11-15 17:12:52
 * @Description:
 */
import { useFetch } from "@/utils/http";
import {
  AreaTreeParam,
  DeptChildren,
  SsxqCascade,
  SsxqCascadeBack,
} from "@/api/_common/model/area";

enum Api {
  GET_TEREE_BY_DMZM = "/tycjDept/getTreeByDmzm",
  GET_DEPT_CHILDREN = "/tycjDept/children",
  GET_SSXQ_CASCADE = "/widget/ssxqCascade",
  GET_SSXQ_CASCADE_BACK = "/widget/ssxqCascadeNb",
  GET_XQ_TREE = "/fwtDmSsxq/getChildren",
  GET_XQ_LIST = "/fwtDmSsxq/getChildrenPage",
  ADD_XQ = "/fwtDmSsxq/createSsxq",
  EDIT_XQ = "/fwtDmSsxq/updateSsxq",
  DELETE_XQ = "/fwtDmSsxq/deleteSsxq",
  UPDATE_YXZT = "/fwtDmSsxq/updateYxbz",
}
// 回显获取所在部门树
export const getTreeByDmzm = (params: AreaTreeParam) => {
  return useFetch.get<any>({
    url: Api.GET_TEREE_BY_DMZM,
    params,
  });
};

// 获取部门
export const getDeptChildren = (params: DeptChildren) => {
  return useFetch.get<any>({
    url: Api.GET_DEPT_CHILDREN,
    params,
  });
};
// 获取单位
export const getSsxqCascade = (params: SsxqCascade) => {
  return useFetch.get<any>({
    url: Api.GET_SSXQ_CASCADE,
    params,
  });
};

export const getSsxqCascadeBack = (params: SsxqCascadeBack) => {
  return useFetch.get<any>({
    url: Api.GET_SSXQ_CASCADE_BACK,
    params,
  });
};

// 获取辖区树
export const getXqTree = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_XQ_TREE,
    params,
  });
};
// 获取辖区列表
export const getXqList = (params: any) => {
  return useFetch.get<any>({
    url: Api.GET_XQ_LIST,
    params,
  });
};
// 新增辖区
export const addXq = (data: any) => {
  return useFetch.post<any>({
    url: Api.ADD_XQ,
    data,
  });
};
// 编辑辖区
export const editXq = (data: any) => {
  return useFetch.post<any>({
    url: Api.EDIT_XQ,
    data,
  });
};
// 删除辖区
export const deleteXq = (params: any) => {
  return useFetch.get<any>({
    url: Api.DELETE_XQ,
    params,
  });
};

// 启用禁用
export const updateYxbz = (data: any) => {
  return useFetch.post<any>({
    url: Api.UPDATE_YXZT,
    data,
  });
};
