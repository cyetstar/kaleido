/*
 * @Author: gongxiaofei
 * @Date: 2022-10-19 14:36:49
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-06-29 10:23:44
 * @Description:
 */
import { defineStore } from "pinia";
import { useUserStore } from "@/store/modules/user";

const { VITE_BASE_WS } = import.meta.env;

import { createPinia } from "pinia";
const pinia = createPinia();
export default pinia;

const userStore = useUserStore(pinia);
export const useWebSocketStore = defineStore("websocket", {
  state: (): any => ({
    websocket: null,
    message: null,
    connected: false,
  }),
  actions: {
    connect(callback: Function) {
      if (this.connected) {
        return;
      }
      this.websocket = new WebSocket(VITE_BASE_WS + userStore.userId);
      this.websocket.onopen = () => {
        console.log("websocket opened");
        this.connected = true;
        if (callback) {
          callback();
        }
      };
      this.websocket.onerror = () => {
        console.log("websocket occurs error");
      };
      this.websocket.onmessage = (message: string) => {
        this.message = message;
      };
      this.websocket.onclose = () => {
        console.log("websocket closed");
        this.connected = false;
      };
    },
    send(message: string) {
      this.websocket.send(message);
    },
    close() {
      this.websocket.close();
    },
  },
});
