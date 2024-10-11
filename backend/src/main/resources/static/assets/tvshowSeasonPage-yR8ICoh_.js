import{_ as N}from"./tvshowSeasonForm-DaiJihnc.js";import{a as q,b as F,c as M,d as O}from"./tvshowSeasonApi-yL39YQBd.js";import{_ as j,a as m,u as G,E as H,b as s,L as J,o as u,c as Q,f as e,w as n,g as v,l as _,O as c,P as W}from"./index-8V0LDlr9.js";import"./actorForm-JIEKGh_E.js";import"./MinusOutlined-ga10JiA-.js";const X={class:"h-page-section",ref:"appManagePage"},Y={__name:"tvshowSeasonPage",setup(Z){const l=m({id:"",showId:"",title:"",summary:"",seasonIndex:"",thumb:"",art:""}),p=m(),C=()=>{p.value.load(1)},k=()=>{p.value.load(1)},R=(a,t)=>{t(q({...a,...l.value,searchCount:!0}))},I=()=>{},w=m(),D=()=>{w.value.create()},E=G(),U=a=>{E.push({path:"tvshow/tvshowSeason/view",query:{id:a}})},$=a=>{w.value.update(a)},L=()=>{p.value.load(1)},x=m([]),y=a=>{const t=Array.isArray(a)?a:[a];F(t).then(r=>{r&&(p.value.load(),W.success("删除成功！"))})},{proxy:P}=H(),T=m(),z=a=>{a(M)},B=async a=>{const t=await O({...a,...l.value});P.$dowloadExcel(t,"单季")};return(a,t)=>{const r=s("h-input"),f=s("h-col"),K=s("h-form-search"),h=s("h-button"),b=s("h-button-delete"),g=s("a-space"),i=s("a-table-column"),V=s("h-table-data"),A=s("h-modal-export"),d=J("permissKey");return u(),Q("section",X,[e(K,{form:l.value,"onUpdate:form":t[4]||(t[4]=o=>l.value=o),onSearch:C,onReset:k},{default:n(()=>[e(f,{span:6},{default:n(()=>[e(r,{label:"剧集id",value:l.value.showId,"onUpdate:value":t[0]||(t[0]=o=>l.value.showId=o),name:"showId"},null,8,["value"])]),_:1}),e(f,{span:6},{default:n(()=>[e(r,{label:"标题",value:l.value.title,"onUpdate:value":t[1]||(t[1]=o=>l.value.title=o),name:"title"},null,8,["value"])]),_:1}),e(f,{span:6},{default:n(()=>[e(r,{label:"简介",value:l.value.summary,"onUpdate:value":t[2]||(t[2]=o=>l.value.summary=o),name:"summary"},null,8,["value"])]),_:1}),e(f,{span:6},{default:n(()=>[e(r,{label:"季号",value:l.value.seasonIndex,"onUpdate:value":t[3]||(t[3]=o=>l.value.seasonIndex=o),name:"seasonIndex"},null,8,["value"])]),_:1})]),_:1},8,["form"]),e(g,{class:"h-btn-space"},{default:n(()=>[c((u(),_(h,{type:"primary",onClick:D},{default:n(()=>[v("新增")]),_:1})),[[d,"tvshowSeason:create"]]),c(e(b,{plain:"",disabled:x.value.length===0,onDelete:t[5]||(t[5]=o=>y(x.value))},null,8,["disabled"]),[[d,"tvshowSeason:delete"]]),c((u(),_(h,{onClick:a.onShowExportRecord},{default:n(()=>[v("导出")]),_:1},8,["onClick"])),[[d,"tvshowSeason:export"]])]),_:1}),e(V,{ref_key:"tableRef",ref:p,"row-key":"id",onLoadData:R,onSelectionChange:I},{default:n(()=>[e(i,{title:"主键","data-index":"id",align:"center"}),e(i,{title:"剧集id","data-index":"showId",align:"center"}),e(i,{title:"标题","data-index":"title",align:"center"}),e(i,{title:"简介","data-index":"summary",align:"center"}),e(i,{title:"季号","data-index":"seasonIndex",align:"center"}),e(i,{title:"海报","data-index":"thumb",align:"center"}),e(i,{title:"艺术图","data-index":"art",align:"center"}),e(i,{title:"操作",align:"center",width:"300px"},{default:n(({record:o})=>[e(g,{size:0},{default:n(()=>[c((u(),_(h,{type:"primary",size:"small",link:"",onClick:S=>U(o.id)},{default:n(()=>[v("详情")]),_:2},1032,["onClick"])),[[d,"tvshowSeason:view"]]),c((u(),_(h,{type:"primary",size:"small",link:"",onClick:S=>$(o.id)},{default:n(()=>[v("编辑")]),_:2},1032,["onClick"])),[[d,"tvshowSeason:update"]]),c((u(),_(b,{size:"small",link:"",onDelete:S=>y(o.id)},{default:n(()=>[v("删除")]),_:2},1032,["onDelete"])),[[d,"tvshowSeason:delete"]])]),_:2},1024)]),_:1})]),_:1},512),e(N,{ref_key:"formRef",ref:w,onSaveComplete:L},null,512),e(A,{ref_key:"exportRef",ref:T,onLoadColumn:z,onExport:B},null,512)],512)}}},le=j(Y,[["__scopeId","data-v-c6da53d1"]]);export{le as default};
