import{J as t}from"./index-8V0LDlr9.js";const o=[{type:"thread",name:"发布记录",action:["page","view","create","update","delete","export","other"]},{type:"sysUser",name:"用户管理",action:["page","view","create","update","delete","export","other"]},{type:"sysMenu",name:"菜单管理",action:["page","view","create","update","delete","export","other"]},{type:"sysRole",name:"角色管理",action:["page","view","create","update","delete","export","other"]},{type:"sysResource",name:"资源管理",action:["page","view","create","update","delete","export","other"]},{type:"sysDictType",name:"字典类型",action:["page","view","create","update","delete","export","other"]},{type:"sysDict",name:"字典管理",action:["page","view","create","update","delete","export","other"]},{type:"sysConfig",name:"系统配置表",action:["page","view","create","update","delete","export","other"]},{type:"sysLog",name:"系统日志",action:["page","view","create","update","delete","export","other"]},{type:"actor",name:"演职员",action:["page","view","create","update","delete","export","other"]},{type:"artist",name:"艺术家",action:["page","view","create","update","delete","export","other"]},{type:"author",name:"作者",action:["page","view","create","update","delete","export","other"]},{type:"movieBasic",name:"电影",action:["page","view","create","update","delete","export","other"]},{type:"movieCollection",name:"电影集合",action:["page","view","create","update","delete","export","other"]},{type:"movieDoubanWeekly",name:"豆瓣电影口碑榜",action:["page","view","create","update","delete","export","other"]},{type:"tvshowShow",name:"剧集",action:["page","view","create","update","delete","export","other"]},{type:"musicAlbum",name:"专辑",action:["page","view","create","update","delete","export","other"]},{type:"comicSeries",name:"漫画系列",action:["page","view","create","update","delete","export","other"]}],r=e=>t.get({url:"sysResource/page",params:e}),p=()=>t.get({url:"sysResource/column"}),s=e=>t.get({url:"sysResource/export",data:e}),c=e=>t.post({url:"sysResource/init",data:e}),i=()=>t.get({url:"/sysResource/listType"}),n=e=>t.get({url:"/sysResource/listTypeByRoleId",params:{roleId:e}});export{o as A,i as a,n as b,c,p as d,s as e,r as s};
