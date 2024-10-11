import{s as ue}from"./dict-NdCH1Osc.js";import{J as i,R as de,d as pe,E as ie,a as c,y as me,b as n,L as ce,o as m,c as z,f as s,w as t,k as f,N as fe,g as _,l as y,O as C,t as ve,z as B,P as D}from"./index-8V0LDlr9.js";import{s as _e}from"./sysRoleApi-NpXJjwtS.js";const ye=r=>i.get({url:"/sysUser/page",params:r}),be=r=>i.delete({url:"/sysUser/delete",data:r}),Ue=r=>i.post({url:"/sysUser/updateEnable",data:r}),Ce=r=>i.get({url:"/sysUser/checkUnique",data:{username:r}}),we=()=>i.get({url:"/sysUser/column"}),he=r=>i.get({url:"/sysUser/export",data:r,isTransformResponse:!1,responseType:de.BLOB}),ge=r=>i.post({url:"/sysUser/create",data:r}),ke=r=>i.post({url:"/sysUser/update",data:r}),xe=r=>i.get({url:"/sysUser/view",data:{id:r}}),Re={class:"h-page-section"},qe={key:0},Ne=pe({__name:"sysUserPage",setup(r){const{proxy:O}=ie();let d=c({username:"",name:"",mobile:"",enabled:"",deptCode:""});const F=(o,a)=>{a(ye({...o,...d.value})),w.value=[]};let w=c([]);const M=o=>{w.value=o},v=c(),P=()=>{v.value.load(1)},T=()=>{v.value.load(1)},E=c([]),V=async()=>{const{records:o}=await _e({pageSize:999,pageNumber:1}),a=o.reduce((p,u)=>{const{id:h,...R}=u;return p.push({id:h+"",...R}),p},[]);E.value=a};me(()=>{V()});const K=async()=>{e.value.type="0",e.value.visible=!0},N=async o=>{let a=o?[o]:w.value;await be(a)&&(v.value.load(),D.success("操作成功！"))},L=c(),$=o=>{o(we)},j=()=>{L.value.show()},J=async o=>{const a=await he({...o,...d.value});O.$dowloadExcel(a,"系统用户")},G=o=>o.map(a=>a.name).join("/"),H=async(o,a)=>{await Ue({userId:a,enable:o})&&(D.success("操作成功"),v.value.load())},Q=async o=>{const a=await xe(o),{deptCode:p,filterCode:u}=a;e.value.form=a,e.value.deptCode=p,e.value.filterCode=u,e.value.type="1",e.value.visible=!0},e=c({visible:!1,type:"0",deptCode:"",filterCode:"",form:{username:"",name:"",mobile:"",deptCode:"",filterCode:"",roleIds:[],password:"",password2:""}}),W=o=>o?Ce(o):!0,X=c(),Y=()=>{e.value.visible=!1,e.value.form={username:"",name:"",mobile:"",deptCode:"",filterCode:"",roleIds:[],password:"",password2:""}},Z=async()=>{try{(e.value.type==="0"?await ge(e.value.form):await ke(e.value.form))&&(D.success(e.value.type==="0"?"新增成功":"编辑成功"),e.value.visible=!1,v.value.load(1))}catch{}};return(o,a)=>{const p=n("h-input"),u=n("a-col"),h=n("h-select"),R=n("h-form-search"),g=n("h-button"),ee=n("a-popconfirm"),I=n("a-space"),k=n("a-table-column"),ae=n("a-switch"),se=n("h-button-delete"),le=n("h-table-data"),te=n("h-modal-export"),q=n("h-col"),A=n("h-input-password"),x=n("a-row"),oe=n("h-input-tel"),S=n("h-select-ssxq"),ne=n("a-form"),re=n("a-modal"),b=ce("permissKey");return m(),z("section",Re,[s(R,{form:f(d),"onUpdate:form":a[2]||(a[2]=l=>fe(d)?d.value=l:d=l),onSearch:P,onReset:T,class:"h-form"},{default:t(()=>[s(u,{span:5},{default:t(()=>[s(p,{value:f(d).username,"onUpdate:value":a[0]||(a[0]=l=>f(d).username=l),label:"用户名",name:"username"},null,8,["value"])]),_:1}),s(u,{span:5},{default:t(()=>[s(h,{value:f(d).enabled,"onUpdate:value":a[1]||(a[1]=l=>f(d).enabled=l),label:"状态",name:"enabled",columns:f(ue)},null,8,["value","columns"])]),_:1})]),_:1},8,["form"]),s(I,{class:"h-btn-space"},{default:t(()=>[C((m(),y(g,{type:"primary",onClick:K},{default:t(()=>[_("新增")]),_:1})),[[b,"sysUser:create"]]),s(ee,{title:"确定删除选中的用户吗？","ok-text":"确定","cancel-text":"取消",onConfirm:N},{default:t(()=>[C((m(),y(g,{type:"danger",plain:"",disabled:f(w).length===0},{default:t(()=>[_("删除 ")]),_:1},8,["disabled"])),[[b,"sysUser:delete"]])]),_:1}),C((m(),y(g,{onClick:j},{default:t(()=>[_("导出")]),_:1})),[[b,"sysUser:export"]])]),_:1}),s(le,{ref_key:"tableRef",ref:v,"row-key":"id",onLoadData:F,onSelectionChange:M},{default:t(()=>[s(k,{title:"用户名","data-index":"username"}),s(k,{title:"角色","data-index":"sysRoleList"},{default:t(({text:l})=>[_(ve(G(l)),1)]),_:1}),s(k,{title:"启用状态","data-index":"isAccountNonLocked"},{default:t(({record:l})=>[s(ae,{checked:l.enabled,"onUpdate:checked":U=>l.enabled=U,onChange:U=>H(U,l.id)},null,8,["checked","onUpdate:checked","onChange"])]),_:1}),s(k,{title:"操作","data-index":"action"},{default:t(({record:l})=>[s(I,{size:0},{default:t(()=>[C((m(),y(g,{type:"primary",size:"small",link:"",onClick:U=>Q(l.id)},{default:t(()=>[_("编辑")]),_:2},1032,["onClick"])),[[b,"sysUser:update"]]),C((m(),y(se,{size:"small",link:"",onDelete:U=>N(l.id)},{default:t(()=>[_("删除")]),_:2},1032,["onDelete"])),[[b,"sysUser:delete"]])]),_:2},1024)]),_:1})]),_:1},512),s(te,{ref_key:"exportRef",ref:L,onLoadColumn:$,onExport:J},null,512),s(re,{visible:e.value.visible,"onUpdate:visible":a[12]||(a[12]=l=>e.value.visible=l),title:e.value.type==="0"?"新增用户":"编辑用户",width:"800px",onCancel:Y,onOk:Z,destroyOnClose:!0},{default:t(()=>[s(ne,{model:e.value.form,"label-col":{span:8},ref_key:"userFormRef",ref:X},{default:t(()=>[e.value.type==="0"?(m(),z("div",qe,[s(q,{span:12,"right-offset":12},{default:t(()=>[s(p,{"read-only":"",label:"用户名",placeholder:"请输入用户名",name:"username",value:e.value.form.username,"onUpdate:value":a[3]||(a[3]=l=>e.value.form.username=l),unique:W,required:""},null,8,["value"])]),_:1}),s(x,null,{default:t(()=>[s(q,{span:12},{default:t(()=>[s(A,{required:"",label:"密码",name:"password",value:e.value.form.password,"onUpdate:value":a[4]||(a[4]=l=>e.value.form.password=l)},null,8,["value"])]),_:1}),s(q,{span:12},{default:t(()=>[s(A,{required:"",label:"重复密码",name:"password2",password:e.value.form.password,value:e.value.form.password2,"onUpdate:value":a[5]||(a[5]=l=>e.value.form.password2=l)},null,8,["password","value"])]),_:1})]),_:1})])):B("",!0),s(x,null,{default:t(()=>[s(u,{span:12},{default:t(()=>[s(p,{required:"",label:"姓名",name:"name",value:e.value.form.name,"onUpdate:value":a[6]||(a[6]=l=>e.value.form.name=l)},null,8,["value"])]),_:1}),s(u,{span:12},{default:t(()=>[s(oe,{label:"联系电话",name:"mobile",value:e.value.form.mobile,"onUpdate:value":a[7]||(a[7]=l=>e.value.form.mobile=l)},null,8,["value"])]),_:1})]),_:1}),s(x,null,{default:t(()=>[s(u,{span:12},{default:t(()=>[s(S,{name:"deptCode",required:"","data-type":"ga",label:"所属单位","min-level":12,value:e.value.form.deptCode,"onUpdate:value":a[8]||(a[8]=l=>e.value.form.deptCode=l),"default-value":e.value.deptCode},null,8,["value","default-value"])]),_:1}),s(u,{span:12},{default:t(()=>[s(S,{name:"filterCode",required:"","data-type":"ga","min-level":12,label:"查询范围",value:e.value.form.filterCode,"onUpdate:value":a[9]||(a[9]=l=>e.value.form.filterCode=l),"default-value":e.value.filterCode},null,8,["value","default-value"])]),_:1})]),_:1}),s(x,null,{default:t(()=>[s(u,{span:12},{default:t(()=>[s(h,{required:"",mode:"multiple",label:"角色",name:"roleIds",value:e.value.form.roleIds,"onUpdate:value":a[10]||(a[10]=l=>e.value.form.roleIds=l),columns:E.value,fieldNames:{label:"name",value:"id"}},null,8,["value","columns"])]),_:1}),e.value.type==="1"?(m(),y(u,{key:0,span:12},{default:t(()=>[s(A,{label:"密码修改",name:"password",placeholder:"输入将修改密码",value:e.value.form.password,"onUpdate:value":a[11]||(a[11]=l=>e.value.form.password=l)},null,8,["value"])]),_:1})):B("",!0)]),_:1})]),_:1},8,["model"])]),_:1},8,["visible","title"])])}}});export{Ne as default};
