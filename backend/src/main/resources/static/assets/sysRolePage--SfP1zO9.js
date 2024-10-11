import{a as ue,s as ie,b as de,c as pe,d as _e,e as me,f as fe,g as ve}from"./sysRoleApi-NpXJjwtS.js";import{a as he,b as ye,A as be}from"./sysResourceApi-CDzKfU6-.js";import{a as d,b as c,o as u,l as p,w as l,F as N,f as o,e as O,n as V,c as L,P as I,d as Re,E as ke,L as xe,k as n,N as Ce,g as f,O as w,t as ge,_ as we}from"./index-8V0LDlr9.js";const Ae={__name:"sysRoleFormAuthorize",setup(J,{expose:$}){const r=d(""),v=d({}),A=d({}),m=d({}),h=_=>{v.value.id=_,Promise.all([he(),ye(_)]).then(([k,s])=>{A.value=k;for(let x in s)m.value[x]=s[x].map(U=>U.id);console.log(A.value),console.log(m.value),r.value.show()})},M=()=>{let _=[];for(let k in m.value)_=_.concat(m.value[k]);v.value.resourceIdList=_,ue(v.value).then(()=>{I.success("操作成功"),r.value.hide()})};return $({show:h}),(_,k)=>{const s=c("h-checkbox"),x=c("a-collapse-panel"),U=c("a-collapse"),P=c("h-col"),B=c("h-form-modal");return u(),p(B,{ref_key:"refFormModal",ref:r,form:v.value,title:"角色授权",width:"650px",onSubmit:M},{default:l(()=>[o(P,{span:24},{default:l(()=>[o(U,null,{default:l(()=>[(u(!0),L(N,null,V(A.value,(C,y)=>(u(),p(x,{key:y,header:y},{default:l(()=>[O("p",null,[o(s,{columns:C,"field-names":{label:"name",value:"id"},value:m.value[y],"onUpdate:value":D=>m.value[y]=D},null,8,["columns","value","onUpdate:value"])])]),_:2},1032,["header"]))),128))]),_:1})]),_:1})]),_:1},8,["form"])}}},Ue=Ae,Se={class:"h-page-section"},De={class:"mt-10px"},Ee=Re({__name:"sysRolePage",setup(J){const{proxy:$}=ke();let r=d({name:"",code:""});const v=d(""),A=(t,e)=>{e(ie({...t,...r.value})),C.value=[]},m=t=>{C.value=t},h=d(),M=()=>{h.value.load(1)},_=()=>{h.value.load(1)},k=d();let s=d({visible:!1,type:"0",form:{}});const x=()=>{s.value.type="0",s.value.visible=!0},U=async t=>{const e=await de(t);s.value.type="1",s.value.form=e,s.value.visible=!0},P=t=>{v.value.show(t)},B=async()=>{try{(s.value.type==="0"?await pe(s.value.form):await _e(s.value.form))&&(I.success(s.value.type==="0"?"新增成功":"编辑成功"),h.value.load(1))}catch{}};let C=d([]);const y=async t=>{let e=Array.isArray(t)?t:[t];await me(e)&&(h.value.load(),I.success("删除成功！"))},D=d(),Q=()=>{D.value.show()},W=t=>{t(fe)},X=async t=>{const e=await ve({...t,...r.value});$.$dowloadExcel(e,"系统角色")};let b=d({visible:!1,roleId:"",roleResources:[]});const Y=t=>{let e=[...be].find(i=>i.type===t);return e?"-"+e.name:""},Z=(t,e)=>{e.forEach(i=>{i.status=t.target.checked})},ee=t=>{t.checkAll=t.actions.every(e=>e.status)},oe=()=>{b.value.visible=!1},ae=async()=>{let t=[];b.value.roleResources.forEach(e=>{e.actions.forEach(i=>{i.status&&t.push(i.id)})});try{await sysRoleResourceBindApi({roleId:b.value.roleId,resourceIds:t})&&(I.success("授权成功"),b.value.visible=!1)}catch{}};return(t,e)=>{const i=c("h-input"),S=c("a-col"),le=c("h-form-search"),E=c("h-button"),q=c("h-button-delete"),K=c("a-space"),F=c("a-table-column"),te=c("h-table-data"),T=c("a-row"),se=c("a-form"),j=c("a-modal"),G=c("a-checkbox"),ne=c("a-collapse-panel"),ce=c("a-collapse"),re=c("h-modal-export"),g=xe("permissKey");return u(),L("section",Se,[o(le,{form:n(r),"onUpdate:form":e[2]||(e[2]=a=>Ce(r)?r.value=a:r=a),onSearch:M,onReset:_,class:"h-form"},{default:l(()=>[o(S,{span:5},{default:l(()=>[o(i,{value:n(r).name,"onUpdate:value":e[0]||(e[0]=a=>n(r).name=a),label:"角色名称",name:"name"},null,8,["value"])]),_:1}),o(S,{span:5},{default:l(()=>[o(i,{value:n(r).code,"onUpdate:value":e[1]||(e[1]=a=>n(r).code=a),label:"角色代码",name:"code"},null,8,["value"])]),_:1})]),_:1},8,["form"]),o(K,{class:"h-btn-space"},{default:l(()=>[w((u(),p(E,{type:"primary",onClick:x},{default:l(()=>[f("新增 ")]),_:1})),[[g,"sysRole:create"]]),w((u(),p(q,{type:"danger",plain:"",disabled:n(C).length===0,onDelete:e[3]||(e[3]=a=>y(n(C)))},{default:l(()=>[f(" 删除 ")]),_:1},8,["disabled"])),[[g,"sysRole:delete"]]),w((u(),p(E,{onClick:Q},{default:l(()=>[f("导出 ")]),_:1})),[[g,"sysRole:export"]])]),_:1}),o(te,{ref_key:"tableRef",ref:h,"row-key":"id",onLoadData:A,onSelectionChange:m},{default:l(()=>[o(F,{title:"角色名称","data-index":"name"}),o(F,{title:"角色代码","data-index":"code"}),o(F,{title:"简介","data-index":"description"}),o(F,{title:"操作",key:"action"},{default:l(({record:a})=>[o(K,{size:0},{default:l(()=>[w((u(),p(E,{type:"primary",link:"",plain:"",size:"small",onClick:z=>U(a.id)},{default:l(()=>[f(" 编辑 ")]),_:2},1032,["onClick"])),[[g,"sysRole:update"]]),w((u(),p(q,{link:"",plain:"",size:"small",onDelete:z=>y(a.id)},{default:l(()=>[f(" 删除 ")]),_:2},1032,["onDelete"])),[[g,"sysRole:delete"]]),w((u(),p(E,{type:"primary",link:"",plain:"",size:"small",onClick:z=>P(a.id)},{default:l(()=>[f(" 权限 ")]),_:2},1032,["onClick"])),[[g,"sysRole:other"]])]),_:2},1024)]),_:1})]),_:1},512),o(j,{visible:n(s).visible,"onUpdate:visible":e[7]||(e[7]=a=>n(s).visible=a),title:n(s).type==="0"?"新增角色":"编辑角色",width:"800px",onOk:B},{default:l(()=>[o(se,{model:n(s).form,"label-col":{span:8},ref_key:"roleFormRef",ref:k},{default:l(()=>[o(T,null,{default:l(()=>[o(S,{span:12},{default:l(()=>[o(i,{disabled:n(s).type==="1",required:"",label:"角色代码",name:"code",value:n(s).form.code,"onUpdate:value":e[4]||(e[4]=a=>n(s).form.code=a)},null,8,["disabled","value"])]),_:1}),o(S,{span:12},{default:l(()=>[o(i,{required:"",label:"角色名称",name:"name",value:n(s).form.name,"onUpdate:value":e[5]||(e[5]=a=>n(s).form.name=a)},null,8,["value"])]),_:1})]),_:1}),o(T,null,{default:l(()=>[o(S,{span:12},{default:l(()=>[o(i,{required:"",label:"简介",name:"description",value:n(s).form.description,"onUpdate:value":e[6]||(e[6]=a=>n(s).form.description=a)},null,8,["value"])]),_:1})]),_:1})]),_:1},8,["model"])]),_:1},8,["visible","title"]),o(j,{visible:n(b).visible,"onUpdate:visible":e[8]||(e[8]=a=>n(b).visible=a),title:"角色授权",width:"800px",onCancel:oe,onOk:ae},{default:l(()=>[o(ce,null,{default:l(()=>[(u(!0),L(N,null,V(n(b).roleResources,(a,z)=>(u(),p(ne,{key:z,header:`${a.type}${Y(a.type)}`},{default:l(()=>[O("div",null,[o(G,{checked:a.checkAll,"onUpdate:checked":R=>a.checkAll=R,onChange:R=>Z(R,a.actions)},{default:l(()=>[f(" 选择全部 ")]),_:2},1032,["checked","onUpdate:checked","onChange"]),O("div",De,[(u(!0),L(N,null,V(a.actions,(R,Fe)=>(u(),p(G,{onChange:H=>ee(a),checked:R.status,"onUpdate:checked":H=>R.status=H},{default:l(()=>[f(ge(R.action),1)]),_:2},1032,["onChange","checked","onUpdate:checked"]))),256))])])]),_:2},1032,["header"]))),128))]),_:1})]),_:1},8,["visible"]),o(re,{ref_key:"exportRef",ref:D,onLoadColumn:W,onExport:X},null,512),o(Ue,{ref_key:"refSysRoleFormAuthorize",ref:v},null,512)])}}}),$e=we(Ee,[["__scopeId","data-v-eea0f664"]]);export{$e as default};
