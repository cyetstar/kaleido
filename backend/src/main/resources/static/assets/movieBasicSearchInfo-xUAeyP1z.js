import{a as u,b as i,o as p,c as M,f as a,w as l,a5 as G,g as m,e as c,t as k,a2 as b,k as $,z as g,l as z,F as H,P as F}from"./index-8V0LDlr9.js";import{e as J,f as L,g as O}from"./movieBasicApi-t3o0u2-x.js";import{_ as Q}from"./movieBasicFileManage-wTrkUyzx.js";const W={class:"flex gap-2"},X=["href"],Y=["src"],Z=["href"],ee={key:0,class:"text-muted"},te={class:"text-muted"},ne={__name:"movieBasicSearchInfo",emits:["match-success"],setup(ae,{expose:S,emit:T}){const D=T,y=u(),v=u(),_=u(),r=u(),f=u([]),s=u({source:"douban",keyword:"",matchType:"path"}),N=[{text:"豆瓣",value:"douban"},{text:"TMDB",value:"tmdb"}];let n={};const U=(e,o)=>{n=e,f.value=[],r.value=!0,s.value.matchType=o,o==="path"?(v.value=n.name,s.value.keyword=n.name.replaceAll("."," ")):(v.value=n.title,s.value.keyword=n.title)},w=()=>{_.value=!0,J(s.value).then(e=>{f.value=e,_.value=!1})},V=e=>{y.value.show(e)},x=e=>{L({...n,...e,...s.value}).then(()=>{F.success("匹配成功"),r.value=!1,D("match-success")})},P=e=>{_.value=!0;let o=e.poster.replace("s_ratio_poster","m_ratio_poster");O({id:n.id,url:o}).then(()=>{F.success("下载成功"),r.value=!1})},I=e=>b(e.doubanId)?`https://movie.douban.com/subject/${e.doubanId}/`:`https://www.themoviedb.org/movie/${e.tmdbId}/`,C=e=>{if(e.doubanId&&e.doubanId===n.doubanId||e.tmdbId&&e.tmdbId===n.tmdbId)return"bg-highlight"};return S({show:U,addRowColor:C}),(e,o)=>{const j=i("h-radio"),K=i("h-input"),d=i("h-button"),h=i("a-table-column"),R=i("k-logo-link"),A=i("a-space"),E=i("a-table"),q=i("a-modal");return p(),M(H,null,[a(q,{ref:"formRef",visible:r.value,"onUpdate:visible":o[2]||(o[2]=t=>r.value=t),title:`匹配【${v.value}】信息`,width:"960px",footer:null},{default:l(()=>[c("div",W,[a(j,{value:s.value.source,"onUpdate:value":o[0]||(o[0]=t=>s.value.source=t),button:"",name:"source",columns:N},null,8,["value"]),a(K,{class:"flex-1",placeholder:"",value:s.value.keyword,"onUpdate:value":o[1]||(o[1]=t=>s.value.keyword=t),name:"keyword",onKeyup:G(w,["enter"])},null,8,["value"]),a(d,{onClick:w},{default:l(()=>[m("搜索")]),_:1}),a(d,{onClick:x},{default:l(()=>[m("无法匹配")]),_:1})]),a(E,{size:"small","data-source":f.value,loading:_.value,"row-class-name":C,class:"mt-3"},{default:l(()=>[a(h,{title:"海报",align:"center",width:"150px"},{default:l(({record:t})=>[c("a",{href:I(t),target:"_blank",class:"flex justify-center"},[c("img",{src:t.poster,width:80,referrerpolicy:"no-referrer"},null,8,Y)],8,X)]),_:1}),a(h,{title:"影片信息"},{default:l(({record:t})=>[c("p",null,[c("a",{href:I(t),target:"_blank"},k(t.title),9,Z),a(R,{type:"plex",width:20,id:t.existId,class:"ml-3"},null,8,["id"])]),$(b)(t.originalTitle)?(p(),M("p",ee,k(t.originalTitle),1)):g("",!0),c("div",te,k(t.year),1)]),_:1}),a(h,{title:"操作",align:"center",width:"150px"},{default:l(({record:t})=>[a(A,{size:0},{default:l(()=>[$(b)(t.existId)?(p(),z(d,{key:0,type:"primary",size:"small",link:"",onClick:B=>V(t.existId)},{default:l(()=>[m("查看文件 ")]),_:2},1032,["onClick"])):g("",!0),a(d,{type:"primary",size:"small",link:"",onClick:B=>x(t)},{default:l(()=>[m("匹配 ")]),_:2},1032,["onClick"]),e.type!=="path"?(p(),z(d,{key:1,type:"primary",size:"small",link:"",onClick:B=>P(t)},{default:l(()=>[m("下载海报 ")]),_:2},1032,["onClick"])):g("",!0)]),_:2},1024)]),_:1})]),_:1},8,["data-source","loading"])]),_:1},8,["visible","title"]),a(Q,{ref_key:"refMovieBasicFileManage",ref:y},null,512)],64)}}};export{ne as _};