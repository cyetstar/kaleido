import{a as m,b as i,o as K,l as P,w as l,k as c,f as a,a5 as T,g as v,e as d,t as w,N as $,P as y}from"./index-8V0LDlr9.js";import{i as F,j as q,k as E,l as G}from"./musicAlbumApi-CiX1Qw26.js";const H={class:"flex gap-2"},J=["href"],L=["src"],O={class:"text-muted"},Q={class:"text-muted"},Z={__name:"musicAlbumSearchInfo",emits:["match-success"],setup(W,{expose:z,emit:A}){const g=A,o=m(),p=m(),f=m();let r={},h={},n=m(),b=m([]),u=m({source:"musicbrainz",keyword:""}),I=[{text:"MusicBrainz",value:"musicbrainz"},{text:"网易",value:"netease"}];const S=(t,s)=>{n.value=!0,f.value=s,b.value=[],f.value==="path"?(h=t,p.value=h.name,u.value.keyword=p.value.replaceAll("."," ")):(r=t,p.value=r.title+" "+r.artists,u.value.keyword=p.value)},x=()=>{o.value=!0,F(u.value).then(t=>{b.value=t,o.value=!1})},C=t=>{o.value=!0,f.value==="path"?q({...h,...t}).then(()=>{y.success("匹配成功"),n.value=!1,o.value=!1,g("match-success")}):E({...r,...t,...u.value}).then(()=>{y.success("匹配成功"),n.value=!1,g("match-success")}).catch(()=>{o.value=!1})},U=t=>{o.value=!0,G({id:r.id,url:t.picUrl}).then(s=>{y.success("下载成功"),n.value=!1,o.value=!1})},M=t=>{if(t&&t.neteaseId===r.neteaseId)return"bg-highlight"};return z({show:S,addRowColor:M}),(t,s)=>{const B=i("h-radio"),N=i("h-input"),_=i("h-button"),k=i("a-table-column"),D=i("a-space"),R=i("a-table"),V=i("a-modal");return K(),P(V,{ref:"formRef",visible:c(n),"onUpdate:visible":s[2]||(s[2]=e=>$(n)?n.value=e:n=e),title:"匹配网易云",width:"960px",footer:null},{default:l(()=>[d("div",H,[a(B,{value:c(u).source,"onUpdate:value":s[0]||(s[0]=e=>c(u).source=e),button:"",name:"source",columns:c(I)},null,8,["value","columns"]),a(N,{class:"flex-1",placeholder:"",value:c(u).keyword,"onUpdate:value":s[1]||(s[1]=e=>c(u).keyword=e),name:"keyword",onKeyup:T(x,["enter"])},null,8,["value"]),a(_,{onClick:x},{default:l(()=>[v("搜索")]),_:1}),a(_,{onClick:C},{default:l(()=>[v("无法匹配")]),_:1})]),a(R,{size:"small","data-source":c(b),loading:o.value,"row-class-name":M,class:"mt-3"},{default:l(()=>[a(k,{title:"封面",align:"center",width:150},{default:l(({record:e})=>[d("a",{href:`https://music.163.com/#/album?id=${e.neteaseId}`,target:"_blank",class:"flex justify-center"},[d("img",{src:e.picUrl,width:100,referrerpolicy:"no-referrer"},null,8,L)],8,J)]),_:1}),a(k,{title:"专辑名"},{default:l(({record:e})=>[d("p",null,w(e.title),1),d("p",O,w(e.artist),1),d("div",Q,w(e.publishTime),1)]),_:1}),a(k,{title:"操作",align:"center",width:"150px"},{default:l(({record:e})=>[a(D,{size:0},{default:l(()=>[a(_,{type:"primary",size:"small",link:"",onClick:j=>C(e)},{default:l(()=>[v("匹配信息 ")]),_:2},1032,["onClick"]),a(_,{type:"primary",size:"small",link:"",onClick:j=>U(e)},{default:l(()=>[v("下载封面 ")]),_:2},1032,["onClick"])]),_:2},1024)]),_:1})]),_:1},8,["data-source","loading"])]),_:1},8,["visible"])}}};export{Z as _};