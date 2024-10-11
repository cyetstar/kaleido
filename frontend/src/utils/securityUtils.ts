import { useUserStore } from "@/store/modules/user";

const securityUtils: any = {
  //返回当前用户姓名
  getUserId: () => {
    return useUserStore().$state.userId;
  },
  getName: () => {
    return useUserStore().$state.name;
  },
  getUserName: () => {
    return useUserStore().$state.username;
  },
  getMobile: () => {
    return useUserStore().$state.mobile;
  },
  getDeptCode: () => {
    return useUserStore().$state.deptCode;
  },
  getSsxq: () => {
    return useUserStore().$state.ssxq;
  },
};

export default securityUtils;
