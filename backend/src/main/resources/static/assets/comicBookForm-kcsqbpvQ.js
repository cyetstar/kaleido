import{_ as z,a as c,x as E,ag as F,v as J,b as u,o as N,l as M,w as n,k as a,N as k,f as l,e as f,c as O,z as W,g as q,P as d}from"./index-8V0LDlr9.js";import{i as G,R as H,f as Q,h as X,S as i,b as Y,j as Z}from"./image-compressor-mN1qqc4Y.js";const $={class:"flex"},ee={class:"ml-3"},oe=["src"],ae={class:"mt-4"},te={__name:"comicBookForm",emits:["save-complete"],setup(le,{expose:h,emit:y}){const g=y;let B=c("create"),p=c(),v=c("basic"),_=c([]),e=c({id:"",title:"",originalTitle:"",bookNumber:"",sortNumber:"",coverPageNumber:1,bgmId:""}),b=c(),U=E(()=>{let s=F("imageUrl");return s=s+"page?id="+e.value.id+"&number="+(e.value.coverPageNumber||1),s});const R=async s=>{B.value="update",p.value.reset(),Q({id:s}).then(o=>{e.value=o,e.value.coverPageNumber=e.value.coverPageNumber||1,_.value=[];for(let r=1;r<=e.value.pageCount;r++)_.value.push({text:r,value:r});p.value.show()})};let C=J();const w=()=>{if(!i)return;let s=JSON.parse(e.value.coverBoxData)||C.$state.cropBoxData;s&&i.setCropBoxData(s),b.value=i.getDataURL()},D=()=>{i&&(b.value=i.getDataURL())},S=()=>{X({id:e.value.id}).then(s=>{s?(d.success("清除成功"),p.value.hide()):d.error("清除失败")})},P={toWidth:300,mimeType:"image/jpg",mode:"strict",speed:"low"},I=new G.ImageCompressor,T=async()=>{try{if(v.value==="cover"){if(!i)return;let s=i.getDataURL(),o=i.getCropBoxData();I.run(s,P,r=>{Y({...e.value,data:r,coverBoxData:JSON.stringify(o)}).then(m=>{m?(C.setCropBoxData(o),d.success("设置成功"),g("save-complete"),p.value.hide()):d.error("设置失败")})})}else Z(e.value).then(s=>{s?(d.success("保存成功"),g("save-complete"),p.value.hide()):d.error("保存失败")})}catch{}};return h({update:R}),(s,o)=>{const r=u("h-input"),m=u("h-col"),x=u("a-tab-pane"),V=u("h-radio"),j=u("h-button"),K=u("a-tabs"),L=u("a-col"),A=u("h-form-modal");return N(),M(A,{ref_key:"formRef",ref:p,"label-col":{span:4},width:"1200px",form:a(e),"onUpdate:form":o[7]||(o[7]=t=>k(e)?e.value=t:e=t),title:"编辑信息",onSubmit:T},{default:n(()=>[l(L,{span:24},{default:n(()=>[l(K,{activeKey:a(v),"onUpdate:activeKey":o[6]||(o[6]=t=>k(v)?v.value=t:v=t),"tab-position":"left"},{default:n(()=>[l(x,{key:"basic",tab:"基本"},{default:n(()=>[l(m,{span:24},{default:n(()=>[l(r,{label:"标题",value:a(e).title,"onUpdate:value":o[0]||(o[0]=t=>a(e).title=t),name:"title"},null,8,["value"])]),_:1}),l(m,{span:24},{default:n(()=>[l(r,{label:"原标题",value:a(e).originalTitle,"onUpdate:value":o[1]||(o[1]=t=>a(e).originalTitle=t),name:"originalTitle"},null,8,["value"])]),_:1}),l(m,{span:24},{default:n(()=>[l(r,{label:"卷号",value:a(e).bookNumber,"onUpdate:value":o[2]||(o[2]=t=>a(e).bookNumber=t),name:"bookNumber"},null,8,["value"])]),_:1}),l(m,{span:24},{default:n(()=>[l(r,{label:"排序",value:a(e).sortNumber,"onUpdate:value":o[3]||(o[3]=t=>a(e).sortNumber=t),name:"sortNumber"},null,8,["value"])]),_:1}),l(m,{span:24},{default:n(()=>[l(r,{label:"番组计划编号",value:a(e).bgmId,"onUpdate:value":o[4]||(o[4]=t=>a(e).bgmId=t),name:"bgmId"},null,8,["value"])]),_:1})]),_:1}),l(x,{key:"cover",tab:"封面"},{default:n(()=>[f("div",$,[f("div",null,[l(a(H),{boxStyle:{width:"800px",height:"300px",backgroundColor:"#f8f8f8",margin:"auto"},img:a(U),options:{viewMode:1,zoomable:!1,dragMode:"crop",initialAspectRatio:21/29.7},onReady:w,onCropend:D},null,8,["img"])]),f("div",ee,[a(b)?(N(),O("img",{key:0,src:a(b),class:"",style:{"object-fit":"contain",width:"200px",height:"300px"}},null,8,oe)):W("",!0)])]),f("div",ae,[l(V,{button:"",columns:a(_),value:a(e).coverPageNumber,"onUpdate:value":o[5]||(o[5]=t=>a(e).coverPageNumber=t)},null,8,["columns","value"]),l(j,{type:"danger",onClick:S},{default:n(()=>[q("清除封面")]),_:1})])]),_:1})]),_:1},8,["activeKey"])]),_:1})]),_:1},8,["form"])}}},ne=z(te,[["__scopeId","data-v-6b22da6d"]]);export{ne as C};
