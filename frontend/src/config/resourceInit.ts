/*
 * @Author: gongxiaofei
 * @Date: 2023-06-29 09:19:01
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-09-11 10:19:31
 * @Description:
 */
export interface resourceItem {
  type: string;
  name: string;
  action?: string[];
  children?: resourceItem[];
}

export const ALL_RESOURCE: resourceItem[] = [
  {
    type: "thread",
    name: "发布记录",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysUser",
    name: "用户管理",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysMenu",
    name: "菜单管理",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysRole",
    name: "角色管理",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysResource",
    name: "资源管理",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysDictType",
    name: "字典类型",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysDict",
    name: "字典管理",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysConfig",
    name: "系统配置表",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "sysLog",
    name: "系统日志",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "actor",
    name: "演职员",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "artist",
    name: "艺术家",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "author",
    name: "作者",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "movieBasic",
    name: "电影",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "movieCollection",
    name: "电影集合",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "movieDoubanWeekly",
    name: "豆瓣电影口碑榜",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "tvshowShow",
    name: "剧集",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "musicAlbum",
    name: "专辑",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
  {
    type: "comicSeries",
    name: "漫画系列",
    action: ["page", "view", "create", "update", "delete", "export", "other"],
  },
];
