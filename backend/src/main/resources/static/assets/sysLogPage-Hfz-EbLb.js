import{J as q,_ as H,a as p,y as G,b as g,o as v,c as w,f as c,w as d,k as o,g as V,e as L,F as M,n as X}from"./index-8V0LDlr9.js";var k=function(i,e){return Object.defineProperty?Object.defineProperty(i,"raw",{value:e}):i.raw=e,i},n;(function(i){i[i.EOS=0]="EOS",i[i.Text=1]="Text",i[i.Incomplete=2]="Incomplete",i[i.ESC=3]="ESC",i[i.Unknown=4]="Unknown",i[i.SGR=5]="SGR",i[i.OSCURL=6]="OSCURL"})(n||(n={}));class z{constructor(){this.VERSION="6.0.2",this.setup_palettes(),this._use_classes=!1,this.bold=!1,this.faint=!1,this.italic=!1,this.underline=!1,this.fg=this.bg=null,this._buffer="",this._url_allowlist={http:1,https:1},this._escape_html=!0,this.boldStyle="font-weight:bold",this.faintStyle="opacity:0.7",this.italicStyle="font-style:italic",this.underlineStyle="text-decoration:underline"}set use_classes(e){this._use_classes=e}get use_classes(){return this._use_classes}set url_allowlist(e){this._url_allowlist=e}get url_allowlist(){return this._url_allowlist}set escape_html(e){this._escape_html=e}get escape_html(){return this._escape_html}set boldStyle(e){this._boldStyle=e}get boldStyle(){return this._boldStyle}set faintStyle(e){this._faintStyle=e}get faintStyle(){return this._faintStyle}set italicStyle(e){this._italicStyle=e}get italicStyle(){return this._italicStyle}set underlineStyle(e){this._underlineStyle=e}get underlineStyle(){return this._underlineStyle}setup_palettes(){this.ansi_colors=[[{rgb:[0,0,0],class_name:"ansi-black"},{rgb:[187,0,0],class_name:"ansi-red"},{rgb:[0,187,0],class_name:"ansi-green"},{rgb:[187,187,0],class_name:"ansi-yellow"},{rgb:[0,0,187],class_name:"ansi-blue"},{rgb:[187,0,187],class_name:"ansi-magenta"},{rgb:[0,187,187],class_name:"ansi-cyan"},{rgb:[255,255,255],class_name:"ansi-white"}],[{rgb:[85,85,85],class_name:"ansi-bright-black"},{rgb:[255,85,85],class_name:"ansi-bright-red"},{rgb:[0,255,0],class_name:"ansi-bright-green"},{rgb:[255,255,85],class_name:"ansi-bright-yellow"},{rgb:[85,85,255],class_name:"ansi-bright-blue"},{rgb:[255,85,255],class_name:"ansi-bright-magenta"},{rgb:[85,255,255],class_name:"ansi-bright-cyan"},{rgb:[255,255,255],class_name:"ansi-bright-white"}]],this.palette_256=[],this.ansi_colors.forEach(l=>{l.forEach(t=>{this.palette_256.push(t)})});let e=[0,95,135,175,215,255];for(let l=0;l<6;++l)for(let t=0;t<6;++t)for(let a=0;a<6;++a){let r={rgb:[e[l],e[t],e[a]],class_name:"truecolor"};this.palette_256.push(r)}let s=8;for(let l=0;l<24;++l,s+=10){let t={rgb:[s,s,s],class_name:"truecolor"};this.palette_256.push(t)}}escape_txt_for_html(e){return this._escape_html?e.replace(/[&<>"']/gm,s=>{if(s==="&")return"&amp;";if(s==="<")return"&lt;";if(s===">")return"&gt;";if(s==='"')return"&quot;";if(s==="'")return"&#x27;"}):e}append_buffer(e){var s=this._buffer+e;this._buffer=s}get_next_packet(){var e={kind:n.EOS,text:"",url:""},s=this._buffer.length;if(s==0)return e;var l=this._buffer.indexOf("\x1B");if(l==-1)return e.kind=n.Text,e.text=this._buffer,this._buffer="",e;if(l>0)return e.kind=n.Text,e.text=this._buffer.slice(0,l),this._buffer=this._buffer.slice(l),e;if(l==0){if(s<3)return e.kind=n.Incomplete,e;var t=this._buffer.charAt(1);if(t!="["&&t!="]"&&t!="(")return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e;if(t=="["){this._csi_regex||(this._csi_regex=T(O||(O=k([`
                        ^                           # beginning of line
                                                    #
                                                    # First attempt
                        (?:                         # legal sequence
                          \x1B[                      # CSI
                          ([<-?]?)              # private-mode char
                          ([d;]*)                    # any digits or semicolons
                          ([ -/]?               # an intermediate modifier
                          [@-~])                # the command
                        )
                        |                           # alternate (second attempt)
                        (?:                         # illegal sequence
                          \x1B[                      # CSI
                          [ -~]*                # anything legal
                          ([\0-:])              # anything illegal
                        )
                    `],[`
                        ^                           # beginning of line
                                                    #
                                                    # First attempt
                        (?:                         # legal sequence
                          \\x1b\\[                      # CSI
                          ([\\x3c-\\x3f]?)              # private-mode char
                          ([\\d;]*)                    # any digits or semicolons
                          ([\\x20-\\x2f]?               # an intermediate modifier
                          [\\x40-\\x7e])                # the command
                        )
                        |                           # alternate (second attempt)
                        (?:                         # illegal sequence
                          \\x1b\\[                      # CSI
                          [\\x20-\\x7e]*                # anything legal
                          ([\\x00-\\x1f:])              # anything illegal
                        )
                    `]))));let r=this._buffer.match(this._csi_regex);if(r===null)return e.kind=n.Incomplete,e;if(r[4])return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e;r[1]!=""||r[3]!="m"?e.kind=n.Unknown:e.kind=n.SGR,e.text=r[2];var a=r[0].length;return this._buffer=this._buffer.slice(a),e}else if(t=="]"){if(s<4)return e.kind=n.Incomplete,e;if(this._buffer.charAt(2)!="8"||this._buffer.charAt(3)!=";")return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e;this._osc_st||(this._osc_st=D(R||(R=k([`
                        (?:                         # legal sequence
                          (\x1B\\)                    # ESC                           |                           # alternate
                          (\x07)                      # BEL (what xterm did)
                        )
                        |                           # alternate (second attempt)
                        (                           # illegal sequence
                          [\0-]                 # anything illegal
                          |                           # alternate
                          [\b-]                 # anything illegal
                          |                           # alternate
                          [-]                 # anything illegal
                        )
                    `],[`
                        (?:                         # legal sequence
                          (\\x1b\\\\)                    # ESC \\
                          |                           # alternate
                          (\\x07)                      # BEL (what xterm did)
                        )
                        |                           # alternate (second attempt)
                        (                           # illegal sequence
                          [\\x00-\\x06]                 # anything illegal
                          |                           # alternate
                          [\\x08-\\x1a]                 # anything illegal
                          |                           # alternate
                          [\\x1c-\\x1f]                 # anything illegal
                        )
                    `])))),this._osc_st.lastIndex=0;{let f=this._osc_st.exec(this._buffer);if(f===null)return e.kind=n.Incomplete,e;if(f[3])return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e}{let f=this._osc_st.exec(this._buffer);if(f===null)return e.kind=n.Incomplete,e;if(f[3])return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e}this._osc_regex||(this._osc_regex=T(B||(B=k([`
                        ^                           # beginning of line
                                                    #
                        \x1B]8;                    # OSC Hyperlink
                        [ -:<-~]*       # params (excluding ;)
                        ;                           # end of params
                        ([!-~]{0,512})        # URL capture
                        (?:                         # ST
                          (?:\x1B\\)                  # ESC                           |                           # alternate
                          (?:\x07)                    # BEL (what xterm did)
                        )
                        ([ -~]+)              # TEXT capture
                        \x1B]8;;                   # OSC Hyperlink End
                        (?:                         # ST
                          (?:\x1B\\)                  # ESC                           |                           # alternate
                          (?:\x07)                    # BEL (what xterm did)
                        )
                    `],[`
                        ^                           # beginning of line
                                                    #
                        \\x1b\\]8;                    # OSC Hyperlink
                        [\\x20-\\x3a\\x3c-\\x7e]*       # params (excluding ;)
                        ;                           # end of params
                        ([\\x21-\\x7e]{0,512})        # URL capture
                        (?:                         # ST
                          (?:\\x1b\\\\)                  # ESC \\
                          |                           # alternate
                          (?:\\x07)                    # BEL (what xterm did)
                        )
                        ([\\x20-\\x7e]+)              # TEXT capture
                        \\x1b\\]8;;                   # OSC Hyperlink End
                        (?:                         # ST
                          (?:\\x1b\\\\)                  # ESC \\
                          |                           # alternate
                          (?:\\x07)                    # BEL (what xterm did)
                        )
                    `]))));let r=this._buffer.match(this._osc_regex);if(r===null)return e.kind=n.ESC,e.text=this._buffer.slice(0,1),this._buffer=this._buffer.slice(1),e;e.kind=n.OSCURL,e.url=r[1],e.text=r[2];var a=r[0].length;return this._buffer=this._buffer.slice(a),e}else if(t=="(")return e.kind=n.Unknown,this._buffer=this._buffer.slice(3),e}}ansi_to_html(e){this.append_buffer(e);for(var s=[];;){var l=this.get_next_packet();if(l.kind==n.EOS||l.kind==n.Incomplete)break;l.kind==n.ESC||l.kind==n.Unknown||(l.kind==n.Text?s.push(this.transform_to_html(this.with_state(l))):l.kind==n.SGR?this.process_ansi(l):l.kind==n.OSCURL&&s.push(this.process_hyperlink(l)))}return s.join("")}with_state(e){return{bold:this.bold,faint:this.faint,italic:this.italic,underline:this.underline,fg:this.fg,bg:this.bg,text:e.text}}process_ansi(e){let s=e.text.split(";");for(;s.length>0;){let l=s.shift(),t=parseInt(l,10);if(isNaN(t)||t===0)this.fg=null,this.bg=null,this.bold=!1,this.faint=!1,this.italic=!1,this.underline=!1;else if(t===1)this.bold=!0;else if(t===2)this.faint=!0;else if(t===3)this.italic=!0;else if(t===4)this.underline=!0;else if(t===21)this.bold=!1;else if(t===22)this.faint=!1,this.bold=!1;else if(t===23)this.italic=!1;else if(t===24)this.underline=!1;else if(t===39)this.fg=null;else if(t===49)this.bg=null;else if(t>=30&&t<38)this.fg=this.ansi_colors[0][t-30];else if(t>=40&&t<48)this.bg=this.ansi_colors[0][t-40];else if(t>=90&&t<98)this.fg=this.ansi_colors[1][t-90];else if(t>=100&&t<108)this.bg=this.ansi_colors[1][t-100];else if((t===38||t===48)&&s.length>0){let a=t===38,r=s.shift();if(r==="5"&&s.length>0){let u=parseInt(s.shift(),10);u>=0&&u<=255&&(a?this.fg=this.palette_256[u]:this.bg=this.palette_256[u])}if(r==="2"&&s.length>2){let u=parseInt(s.shift(),10),f=parseInt(s.shift(),10),b=parseInt(s.shift(),10);if(u>=0&&u<=255&&f>=0&&f<=255&&b>=0&&b<=255){let m={rgb:[u,f,b],class_name:"truecolor"};a?this.fg=m:this.bg=m}}}}}transform_to_html(e){let s=e.text;if(s.length===0||(s=this.escape_txt_for_html(s),!e.bold&&!e.italic&&!e.underline&&e.fg===null&&e.bg===null))return s;let l=[],t=[],a=e.fg,r=e.bg;e.bold&&l.push(this._boldStyle),e.faint&&l.push(this._faintStyle),e.italic&&l.push(this._italicStyle),e.underline&&l.push(this._underlineStyle),this._use_classes?(a&&(a.class_name!=="truecolor"?t.push(`${a.class_name}-fg`):l.push(`color:rgb(${a.rgb.join(",")})`)),r&&(r.class_name!=="truecolor"?t.push(`${r.class_name}-bg`):l.push(`background-color:rgb(${r.rgb.join(",")})`))):(a&&l.push(`color:rgb(${a.rgb.join(",")})`),r&&l.push(`background-color:rgb(${r.rgb})`));let u="",f="";return t.length&&(u=` class="${t.join(" ")}"`),l.length&&(f=` style="${l.join(";")}"`),`<span${f}${u}>${s}</span>`}process_hyperlink(e){let s=e.url.split(":");return s.length<1||!this._url_allowlist[s[0]]?"":`<a href="${this.escape_txt_for_html(e.url)}">${this.escape_txt_for_html(e.text)}</a>`}}function T(i,...e){let s=i.raw[0],l=/^\s+|\s+\n|\s*#[\s\S]*?\n|\n/gm,t=s.replace(l,"");return new RegExp(t)}function D(i,...e){let s=i.raw[0],l=/^\s+|\s+\n|\s*#[\s\S]*?\n|\n/gm,t=s.replace(l,"");return new RegExp(t,"g")}var O,R,B;const J=()=>q.get({url:"/sysLog/list"}),Q=i=>q.get({url:"/sysLog/read",params:i}),W={class:"h-page-section"},Y=["innerHTML"],Z={__name:"sysLogPage",setup(i){let e=p({frequency:1,auto:!0,scroll:!0}),s=p(),l=p(),t=p([]),a=p([]),r=p([{text:"1秒",value:1},{text:"5秒",value:5},{text:"10秒",value:10}]),u,f;const b=()=>{u>0&&clearInterval(u),e.value.auto&&(u=setInterval(m,e.value.frequency*1e3))},m=()=>{s.value.validate().then(()=>{e.value.fileName!==f&&(t.value.splice(0,t.value.length),e.value.lineNumber=0),f=e.value.fileName;const x=new z;Q(e.value).then(h=>{e.value.lineNumber=h.lineNumber;for(const y in h.logs){const S=x.ansi_to_html(h.logs[y]);t.value.push(S)}e.value.scroll&&h.logs.length>0&&E()})})},E=()=>{l.value.scrollTop=l.value.scrollHeight},U=()=>{J().then(x=>{a.value=x,E()})};return G(()=>{U()}),(x,h)=>{const y=g("h-select"),S=g("a-col"),C=g("h-col"),I=g("a-switch"),N=g("h-button"),j=g("a-space"),$=g("a-row"),F=g("a-form");return v(),w("div",W,[c(F,{model:o(e),ref_key:"formRef",ref:s,class:"h-form"},{default:d(()=>[c($,{gutter:32},{default:d(()=>[c(S,{span:6},{default:d(()=>[c(y,{required:"",label:"日志文件",name:"fileName",value:o(e).fileName,"onUpdate:value":h[0]||(h[0]=_=>o(e).fileName=_),columns:o(a),onChange:b},null,8,["value","columns"])]),_:1}),c(C,{span:4},{default:d(()=>[c(y,{required:"",label:"刷新频率",name:"frequency",disabled:!o(e).auto,value:o(e).frequency,"onUpdate:value":h[1]||(h[1]=_=>o(e).frequency=_),columns:o(r),onChange:b},null,8,["disabled","value","columns"])]),_:1}),c(C,{span:6},{default:d(()=>[c(j,null,{default:d(()=>[c(I,{checked:o(e).scroll,"onUpdate:checked":h[2]||(h[2]=_=>o(e).scroll=_),name:"scroll","un-checked-children":"固定","checked-children":"滚动"},null,8,["checked"]),c(I,{checked:o(e).auto,"onUpdate:checked":h[3]||(h[3]=_=>o(e).auto=_),name:"auto","un-checked-children":"手动刷新","checked-children":"自动刷新",onChange:b},null,8,["checked"]),c(N,{type:"primary",disabled:o(e).auto,onClick:m},{default:d(()=>[V("刷新 ")]),_:1},8,["disabled"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model"]),L("div",{ref_key:"consoleRef",ref:l,class:"console h-[calc(100vh-170px)]"},[(v(!0),w(M,null,X(o(t),(_,A)=>(v(),w("div",{key:A},[L("div",{innerHTML:_,style:{"font-size":"14px"}},null,8,Y)]))),128))],512)])}}},K=H(Z,[["__scopeId","data-v-50d98ef0"]]);export{K as default};
