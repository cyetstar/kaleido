import{f as e,m as W,A as X,J as R,a as f,b as p,o as _,l as w,w as s,g as x,P as B,k as a,N as Y,G as re,u as ce,y as de,c as O,e as g,a4 as me,F as C,n as $,t as I,z,a2 as pe}from"./index-8V0LDlr9.js";import{b as ve,c as fe,d as _e,e as Z,f as be,g as ye,h as he}from"./musicAlbumApi-CiX1Qw26.js";import{_ as ge}from"./musicAlbumSearchInfo-KuB437q0.js";var ke={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M688 312v-48c0-4.4-3.6-8-8-8H296c-4.4 0-8 3.6-8 8v48c0 4.4 3.6 8 8 8h384c4.4 0 8-3.6 8-8zm-392 88c-4.4 0-8 3.6-8 8v48c0 4.4 3.6 8 8 8h184c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8H296zm144 452H208V148h560v344c0 4.4 3.6 8 8 8h56c4.4 0 8-3.6 8-8V108c0-17.7-14.3-32-32-32H168c-17.7 0-32 14.3-32 32v784c0 17.7 14.3 32 32 32h272c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm445.7 51.5l-93.3-93.3C814.7 780.7 828 743.9 828 704c0-97.2-78.8-176-176-176s-176 78.8-176 176 78.8 176 176 176c35.8 0 69-10.7 96.8-29l94.7 94.7c1.6 1.6 3.6 2.3 5.6 2.3s4.1-.8 5.6-2.3l31-31a7.9 7.9 0 000-11.2zM652 816c-61.9 0-112-50.1-112-112s50.1-112 112-112 112 50.1 112 112-50.1 112-112 112z"}}]},name:"file-search",theme:"outlined"};const xe=ke;function we(r){for(var o=1;o<arguments.length;o++){var c=arguments[o]!=null?Object(arguments[o]):{},d=Object.keys(c);typeof Object.getOwnPropertySymbols=="function"&&(d=d.concat(Object.getOwnPropertySymbols(c).filter(function(u){return Object.getOwnPropertyDescriptor(c,u).enumerable}))),d.forEach(function(u){Me(r,u,c[u])})}return r}function Me(r,o,c){return o in r?Object.defineProperty(r,o,{value:c,enumerable:!0,configurable:!0,writable:!0}):r[o]=c,r}var q=function(o,c){var d=we({},o,c.attrs);return e(X,W(d,{icon:xe}),null)};q.displayName="FileSearchOutlined";q.inheritAttrs=!1;const Ae=q;var Oe={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M854.6 288.6L639.4 73.4c-6-6-14.1-9.4-22.6-9.4H192c-17.7 0-32 14.3-32 32v832c0 17.7 14.3 32 32 32h640c17.7 0 32-14.3 32-32V311.3c0-8.5-3.4-16.7-9.4-22.7zM790.2 326H602V137.8L790.2 326zm1.8 562H232V136h302v216a42 42 0 0042 42h216v494zM504 618H320c-4.4 0-8 3.6-8 8v48c0 4.4 3.6 8 8 8h184c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8zM312 490v48c0 4.4 3.6 8 8 8h384c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8H320c-4.4 0-8 3.6-8 8z"}}]},name:"file-text",theme:"outlined"};const Ie=Oe;function Se(r){for(var o=1;o<arguments.length;o++){var c=arguments[o]!=null?Object(arguments[o]):{},d=Object.keys(c);typeof Object.getOwnPropertySymbols=="function"&&(d=d.concat(Object.getOwnPropertySymbols(c).filter(function(u){return Object.getOwnPropertyDescriptor(c,u).enumerable}))),d.forEach(function(u){Le(r,u,c[u])})}return r}function Le(r,o,c){return o in r?Object.defineProperty(r,o,{value:c,enumerable:!0,configurable:!0,writable:!0}):r[o]=c,r}var E=function(o,c){var d=Se({},o,c.attrs);return e(X,W(d,{icon:Ie}),null)};E.displayName="FileTextOutlined";E.inheritAttrs=!1;const Ue=E,Fe=r=>R.get({url:"/musicTrack/listByAlbumId",params:r}),Te=r=>R.get({url:"/musicTrack/viewLyric",params:r}),Ce=r=>R.post({url:"/musicTrack/downloadLyric",data:r}),ze={__name:"musicAlbumFileManage",emits:["match-success"],setup(r,{expose:o,emit:c}){const d=f([]),u=f();let v=null,t=null;const M=h=>{v=h,ve({id:v}).then(b=>{t=b,u.value.show(t)})},S=h=>{fe({id:v,path:t,file:h.file}).then(b=>{b?u.value.load():B.error("上传失败")})};return o({show:M}),(h,b)=>{const l=p("h-button"),m=p("a-upload"),i=p("k-file-modal");return _(),w(i,{ref_key:"refFileModal",ref:u},{footer:s(()=>[e(m,{"file-list":d.value,"onUpdate:fileList":b[0]||(b[0]=A=>d.value=A),name:"file",showUploadList:!1,"custom-request":S},{default:s(()=>[e(l,{plain:"",type:"primary"},{default:s(()=>[x("上传封面")]),_:1})]),_:1},8,["file-list"])]),_:1},512)}}},De={__name:"musicAlbumSearchLyric",emits:["match-success"],setup(r,{expose:o,emit:c}){const d=c,u=f(),v=f({title:""});let t=f(),M=f([]);f({keywords:""});const S=l=>{v.value=l,t.value=!0,_e({id:v.value.albumId}).then(m=>{M.value=m.songs,u.value=!1})},h=l=>{u.value=!0,Ce({id:v.value.id,neteaseId:l.id}).then(m=>{t.value=!1,u.value=!1,d("match-success")})},b=l=>{if(l.no===v.value.trackIndex)return"bg-highlight"};return o({show:S,addRowColor:b}),(l,m)=>{const i=p("a-table-column"),A=p("h-button"),L=p("a-space"),U=p("a-table"),n=p("a-modal");return _(),w(n,{ref:"formRef",visible:a(t),"onUpdate:visible":m[0]||(m[0]=F=>Y(t)?t.value=F:t=F),title:`查询“${v.value.title}”歌词`,width:"660px",footer:null},{default:s(()=>[e(U,{size:"small","data-source":a(M),loading:u.value,"row-class-name":b,class:"mt-3"},{default:s(()=>[e(i,{title:"曲号","data-index":"no",width:"10%",align:"center"}),e(i,{title:"歌名","data-index":"name"}),e(i,{title:"操作",align:"center",width:"150px"},{default:s(({record:F})=>[e(L,{size:0},{default:s(()=>[e(A,{type:"primary",size:"small",link:"",onClick:G=>h(F)},{default:s(()=>[x("下载歌词 ")]),_:2},1032,["onClick"])]),_:2},1024)]),_:1})]),_:1},8,["data-source","loading"])]),_:1},8,["visible","title"])}}},je={__name:"musicAlbumForm",emits:["save-complete"],setup(r,{expose:o,emit:c}){const d=c;let u=f("create"),v=f(),t=f({id:"",musicbrainzId:"",plexId:"",plexThumb:"",neteaseId:"",title:"",artists:"",summary:"",type:"",genre:"",releaseCountry:"",date:"",label:"",releaseDate:"",totalDiscs:"",totalTracks:"",media:"",path:"",cjsj:"",xgsj:""});const M=()=>{u.value="create",v.value.reset(),v.value.show()},S=async b=>{u.value="update",v.value.reset(),t.value=await Z({id:b}),v.value.show()},h=async()=>{try{u.value==="create"?await be(t.value)&&(B.success("操作成功"),d("save-complete"),v.value.hide()):u.value==="update"&&await ye(t.value)&&(B.success("操作成功"),d("save-complete"),v.value.hide())}catch{}};return o({create:M,update:S}),(b,l)=>{const m=p("h-input"),i=p("h-col"),A=p("h-select"),L=p("h-select-datetime"),U=p("h-form-modal");return _(),w(U,{ref_key:"formRef",ref:v,"label-col":{span:4},width:"600px",form:a(t),"onUpdate:form":l[19]||(l[19]=n=>Y(t)?t.value=n:t=n),title:"专辑",onSubmit:h},{default:s(()=>[e(i,{span:24},{default:s(()=>[e(m,{label:"MusicBrainz编号",value:a(t).musicbrainzId,"onUpdate:value":l[0]||(l[0]=n=>a(t).musicbrainzId=n),name:"musicbrainzId"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"Plex编号",value:a(t).plexId,"onUpdate:value":l[1]||(l[1]=n=>a(t).plexId=n),name:"plexId"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"Plex缩略图",value:a(t).plexThumb,"onUpdate:value":l[2]||(l[2]=n=>a(t).plexThumb=n),name:"plexThumb"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"网易云音乐编号",value:a(t).neteaseId,"onUpdate:value":l[3]||(l[3]=n=>a(t).neteaseId=n),name:"neteaseId"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"标题",value:a(t).title,"onUpdate:value":l[4]||(l[4]=n=>a(t).title=n),name:"title"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"艺术家",value:a(t).artists,"onUpdate:value":l[5]||(l[5]=n=>a(t).artists=n),name:"artists"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"简介",value:a(t).summary,"onUpdate:value":l[6]||(l[6]=n=>a(t).summary=n),name:"summary"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"专辑类型",value:a(t).type,"onUpdate:value":l[7]||(l[7]=n=>a(t).type=n),name:"type"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"音乐流派",value:a(t).genre,"onUpdate:value":l[8]||(l[8]=n=>a(t).genre=n),name:"genre"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"发行国家",value:a(t).releaseCountry,"onUpdate:value":l[9]||(l[9]=n=>a(t).releaseCountry=n),name:"releaseCountry"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"日期",value:a(t).date,"onUpdate:value":l[10]||(l[10]=n=>a(t).date=n),name:"date"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"唱片公司",value:a(t).label,"onUpdate:value":l[11]||(l[11]=n=>a(t).label=n),name:"label"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"发行日期",value:a(t).releaseDate,"onUpdate:value":l[12]||(l[12]=n=>a(t).releaseDate=n),name:"releaseDate"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(A,{label:"碟数","dict-type":"totalDiscs",value:a(t).totalDiscs,"onUpdate:value":l[13]||(l[13]=n=>a(t).totalDiscs=n),name:"totalDiscs"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(A,{label:"音轨数","dict-type":"totalTracks",value:a(t).totalTracks,"onUpdate:value":l[14]||(l[14]=n=>a(t).totalTracks=n),name:"totalTracks"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"媒体",value:a(t).media,"onUpdate:value":l[15]||(l[15]=n=>a(t).media=n),name:"media"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(m,{label:"文件路径",value:a(t).path,"onUpdate:value":l[16]||(l[16]=n=>a(t).path=n),name:"path"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(L,{label:"创建时间",type:"datetime",value:a(t).cjsj,"onUpdate:value":l[17]||(l[17]=n=>a(t).cjsj=n),name:"cjsj"},null,8,["value"])]),_:1}),e(i,{span:24},{default:s(()=>[e(L,{label:"修改时间",type:"datetime",value:a(t).xgsj,"onUpdate:value":l[18]||(l[18]=n=>a(t).xgsj=n),name:"xgsj"},null,8,["value"])]),_:1})]),_:1},8,["form"])}}},Ve={class:"k-view-section"},Pe={class:"flex"},$e={class:"flex-1 ml-8"},Be=["onClick"],qe={__name:"musicAlbumView",setup(r){const o=re(),c=ce(),d=o.query.id,u=f({}),v=f([]),t=f(!1),M=f(),S=f(),h=f({albumId:d,force:!0}),b=f(),l=f(),m=f(),i=f(),A=()=>{L(),U()},L=()=>{Z({id:d}).then(y=>u.value=y)},U=()=>{Fe({albumId:d}).then(y=>v.value=y)},n=()=>{b.value.update(d)},F=()=>{m.value.show(d)},G=y=>{c.push({path:"/music/musicArtist/view",query:{id:y}})},K=()=>{l.value.show(u.value)},ee=()=>{he({id:u.value.id}).then(()=>{B.success("下载成功"),A()})},te=y=>{Te({id:y.id}).then(T=>{S.value=T,M.value=y.title,t.value=!0})},le=y=>{i.value.show(y)};return de(()=>{A()}),(y,T)=>{const D=p("h-button"),J=p("k-action-button"),ae=p("a-space"),ne=p("a-page-header"),ue=p("k-thumb-image"),H=p("a-tag"),N=p("k-logo-link"),Q=p("h-module-title"),j=p("a-table-column"),se=p("a-table"),oe=p("a-modal");return _(),O(C,null,[g("section",Ve,[e(ne,{title:u.value.title,onBack:T[0]||(T[0]=()=>y.$router.go(-1))},{backIcon:s(()=>[e(a(me))]),extra:s(()=>[e(ae,null,{default:s(()=>[e(D,{disabled:!u.value.neteaseId,onClick:ee},{default:s(()=>[x("下载歌词 ")]),_:1},8,["disabled"]),e(J,{action:"musicSync","ok-text":"同步Plex","cancel-text":"取消同步",form:h.value},null,8,["form"]),e(J,{action:"movieMatchInfo","ok-text":"自动抓取","cancel-text":"取消抓取",form:h.value},null,8,["form"]),e(D,{onClick:K},{default:s(()=>[x("匹配抓取")]),_:1}),e(D,{onClick:n},{default:s(()=>[x("编辑")]),_:1}),e(D,{onClick:F},{default:s(()=>[x("文件管理")]),_:1})]),_:1})]),_:1},8,["title"]),g("section",Pe,[g("div",null,[e(ue,{class:"h-cover",type:"MusicAlbum",style:{width:"250px"},id:u.value.id},null,8,["id"])]),g("div",$e,[g("h1",null,[(_(!0),O(C,null,$(u.value.musicArtistDTOList,k=>(_(),O("a",{onClick:V=>G(k.id)},I(k.title),9,Be))),256))]),g("p",null,I(u.value.originallyAvailableAt),1),g("p",null,I(u.value.label),1),g("p",null,[u.value.type?(_(),w(H,{key:0},{default:s(()=>[x(I(u.value.type),1)]),_:1})):z("",!0),u.value.genre?(_(),w(H,{key:1},{default:s(()=>[x(I(u.value.genre),1)]),_:1})):z("",!0),u.value.media?(_(),w(H,{key:2},{default:s(()=>[x(I(u.value.media),1)]),_:1})):z("",!0)]),g("p",null,[(_(!0),O(C,null,$(u.value.summaryList,(k,V)=>(_(),O("p",{key:V},I(k),1))),128))]),g("p",null,[e(N,{class:"mr-3",id:a(d)},null,8,["id"]),e(N,{class:"mr-3",type:"musicbrianz",id:u.value.musicbrainzId},null,8,["id"]),e(N,{class:"mr-3",type:"netease",id:u.value.neteaseId},null,8,["id"])])])]),g("section",null,[(_(!0),O(C,null,$(v.value,(k,V)=>(_(),O(C,null,[v.value.length>1?(_(),w(Q,{key:0,title:`Disc${V+1}`},null,8,["title"])):(_(),w(Q,{key:1,title:"曲目"})),e(se,{pagination:!1,size:"small","data-source":k,class:"mb-6"},{default:s(()=>[e(j,{title:"曲号",width:"10%","data-index":"trackIndex",align:"center"}),e(j,{title:"歌名",width:"75%","data-index":"title"}),e(j,{title:"歌词",width:"5%",align:"center"},{default:s(({record:P})=>[P.hasLyric==="1"?(_(),w(a(Ue),{key:0,onClick:ie=>te(P)},null,8,["onClick"])):z("",!0),a(pe)(u.value.neteaseId)&&P.hasLyric!=="1"?(_(),w(a(Ae),{key:1,onClick:ie=>le(P)},null,8,["onClick"])):z("",!0)]),_:1}),e(j,{title:"曲长",width:"10%","data-index":"durationLabel",align:"center"})]),_:2},1032,["data-source"])],64))),256))])]),e(oe,{visible:t.value,"onUpdate:visible":T[1]||(T[1]=k=>t.value=k),footer:null},{title:s(()=>[x("“"+I(M.value)+"”歌词",1)]),default:s(()=>[(_(!0),O(C,null,$(S.value,k=>(_(),O("p",null,I(k),1))),256))]),_:1},8,["visible"]),e(je,{ref_key:"refMusicAlbumForm",ref:b},null,512),e(ge,{ref_key:"refMusicAlbumSearchInfo",ref:l,onMatchSuccess:L},null,512),e(ze,{ref_key:"refMusicAlbumFileManage",ref:m},null,512),e(De,{ref_key:"refMusicAlbumSearchLyric",ref:i,onMatchSuccess:U},null,512)],64)}}};export{qe as default};
