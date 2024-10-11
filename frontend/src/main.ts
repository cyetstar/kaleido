/*
 * @Author: gongxiaofei
 * @Date: 2022-06-22 13:32:07
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-09-26 10:31:11
 * @Description:
 */
import "virtual:windi-base.css";
import "virtual:windi-components.css";
import "virtual:windi-utilities.css";
// import "virtual:svg-icons-register";
import "echarts";
import ECharts from "vue-echarts";
import App from "./App.vue";
import { createApp } from "vue";
import { registerGlobComp } from "@c/register";
import { setupStore } from "@/store";
import { router, setupRouter } from "@/router";
import { setupRouterGuard } from "@/router/guard";
import { mountFunction } from "@/utils/index";
import "ant-design-vue/dist/antd.less";
import "hta-ui/dist/style/index.less";
import "@/styles/index.less";
import directives from "./utils/directives";
import directive from "./directives";

const app = createApp(App);
directives(app);
app.use(directive);

app.component("v-chart", ECharts);

// 挂载全局方法
mountFunction(app);

// 配置 store
setupStore(app);

// 注册全局组件
registerGlobComp(app);

// 配置路由
setupRouter(app);

// 配置路由守卫
setupRouterGuard(router);

app.mount("#app");
