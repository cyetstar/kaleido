import { PostModel } from "./postModel";
import { RoleModel } from "./roleModel";

export interface UserInfoModel {
  name: string;
  userId: number;
  username: string;
  mobile: Nullable<string>;
  roles: RoleModel[];
  deptCode: string;
  deptName: string;
  ssxq: string;
  permissions: string[];
}
