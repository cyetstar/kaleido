import{d as p,u as b,r as x,a as w,b as t,o as y,c as k,e as c,f as a,w as u,g as I,h as S,M as i,p as M,i as N,j as B,_ as K}from"./index-8V0LDlr9.js";const L=o=>(M("data-v-138d2392"),o=o(),N(),o),C={class:"bg flex h-screen p-3/25 justify-center items-center"},E={class:"bg-black"},U={class:"mx-12 my-10"},V=L(()=>c("h1",{class:"text-42px font-bold text-center"},"Kaleido",-1)),j=p({name:"Login"}),q=p({...j,setup(o){const _=b(),{createMessage:l}=B(),s=x({username:"",password:"",rememberMe:!1});let n=w(!1);async function m(d){const e=S();try{n.value=!0,l.loading({duration:0,content:"正在登录",key:i.LOADING_KEY}),await e.login(d),l.success({content:"登录成功",key:i.LOADING_KEY}),_.replace("/movie/movieBasic/page")}finally{n.value=!1}}return(d,e)=>{const f=t("h-input"),v=t("h-input-password"),g=t("a-button"),h=t("a-form");return y(),k("div",C,[c("div",E,[c("div",U,[V,a(h,{model:s,onFinish:m},{default:u(()=>[a(f,{class:"mt-5",required:"",placeholder:"请输入账号",value:s.username,"onUpdate:value":e[0]||(e[0]=r=>s.username=r),name:"username"},null,8,["value"]),a(v,{class:"mt-3",required:"",placeholder:"请输入密码",value:s.password,"onUpdate:value":e[1]||(e[1]=r=>s.password=r),name:"password"},null,8,["value"]),a(g,{style:{background:"#1e2d40","border-color":"#1e2d40",color:"#fff"},class:"mt-3",block:"",loading:n.value,"html-type":"submit"},{default:u(()=>[I("登录")]),_:1},8,["loading"])]),_:1},8,["model"])])])])}}}),D=K(q,[["__scopeId","data-v-138d2392"]]);export{D as default};
