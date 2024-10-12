import { defineStore } from "pinia";
import { isEmpty, isNotEmpty } from "@ht/util";
import { apiSysConfigFindByKeys } from "@/api/sysadmin/sysConfigApi";

interface AppState {
  config: any;
  scrollTop: any;
  actions: any;
  cropBoxData: any;
}

export const useAppStore = defineStore("app", {
  state: (): AppState => ({
    config: {},
    scrollTop: {},
    actions: {},
    cropBoxData: { top: 0, left: 0 },
  }),
  actions: {
    async initAppConfig() {
      return apiSysConfigFindByKeys([
        "komgaUrl",
        "plexUrl",
        "movieLibraryPath",
        "tvshowLibraryPath",
        "musicLibraryPath",
        "comicLibraryPath",
      ]).then((res) => {
        this.config = { ...res };
      });
    },

    setScrollTop(scrollTop: number, type: string) {
      this.scrollTop[type] = scrollTop;
    },
    getScrollTop(type: string) {
      return this.scrollTop[type];
    },

    updateAction(actionList: [any]) {
      const actionRunnings: string[] = [];
      if (isNotEmpty(actionList)) {
        actionList.forEach((s) => {
          this.actions[s.action] = s.running;
          if (s.running) {
            actionRunnings.push(s.action);
          }
        });
      }
      Object.keys(this.actions)
        .filter((s: string) => !actionRunnings.includes(s))
        .forEach((s: string) => {
          this.actions[s] = false;
        });
    },

    stopAction(action: string) {
      this.actions[action] = false;
    },

    clearAppState() {
      this.$reset();
    },

    setCropBoxData(data: any) {
      this.cropBoxData = data;
    },
  },
  persist: {
    key: "APP_STORE",
    storage: window.localStorage,
  },
});
