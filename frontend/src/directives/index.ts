/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-12-02 09:17:19
 * @Description: 防抖、节流指令
 */
import { App, DirectiveBinding } from "vue";

let debounceTimer: NodeJS.Timeout | null , throttleTimer: NodeJS.Timeout | null

export default (app: App<Element>) => {
  // 防抖
  app.directive("debounce", {
    mounted(el: HTMLElement, binding: DirectiveBinding) {
    
      const eventType: string =  Object.keys(binding.modifiers)[0] || 'click'
          
      el.addEventListener(eventType, () => {
        
        const dealy: number = binding.arg ? parseInt(binding.arg) : 300;
        const fn: unknown = binding.value;
  
        if (isNaN(dealy)) { 
          throw Error("v-debounce:arg必须为数字!");
        }
        
        if (typeof fn !== "function") {
          throw Error("v-debounce绑定值必须为函数!");
        }

        if (debounceTimer) {
          clearTimeout(debounceTimer);
        }

        debounceTimer = setTimeout(() => {
          fn();
        }, dealy);

      });
    },
  });
  // 节流
  app.directive("throttle", {
    mounted(el: HTMLElement, binding: DirectiveBinding) {
      
      const eventType: string =  Object.keys(binding.modifiers)[0] || 'click'
      
      el.addEventListener(eventType, () => {
        
        const dealy: number = binding.arg ? parseInt(binding.arg) : 300;
        const fn: unknown = binding.value;
  
        if (isNaN(dealy)) { 
          throw Error("v-throttle:arg必须为数字!");
        }
        
        if (typeof fn !== "function") {
          throw Error("v-throttle绑定值必须为函数!");
        }

        if (throttleTimer) {
          return;
        }

        throttleTimer = setTimeout(() => {
          fn();
          throttleTimer = null
        }, dealy);

      });
    },
  });
};
