import{J as s,R as o}from"./index-8V0LDlr9.js";const r=e=>s.get({url:"/sysRole/page",params:e}),l=e=>s.delete({url:"/sysRole/delete",data:e}),a=e=>s.post({url:"/sysRole/create",data:e}),n=e=>s.post({url:"/sysRole/update",data:e}),p=e=>s.get({url:"/sysRole/view",data:{id:e}}),u=()=>s.get({url:"/sysRole/column"}),R=e=>s.get({url:"/sysRole/export",data:e,isTransformResponse:!1,responseType:o.BLOB}),y=e=>s.post({url:"/sysRole/authorize",data:e});export{y as a,p as b,a as c,n as d,l as e,u as f,R as g,r as s};