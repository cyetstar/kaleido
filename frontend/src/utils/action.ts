import { apiActionStart, apiActionStop } from "@/api/common/actionApi";
import { ExclamationCircleOutlined } from "@ant-design/icons-vue";
import { Modal } from "ant-design-vue";
import { createVNode } from "vue";
import { useAppStore } from "@/store/modules/app";
import { useWebSocketStore } from "@/store/modules/websocket";

import { createPinia } from "pinia";

export const triggerAction = (action: string, params?: any) => {
  const appStore = useAppStore();
  if (appStore.actions[action]) {
    return apiActionStop({ action });
  } else {
    Modal.confirm({
      content: "该操作需花费较长时间，是否继续执行？",
      icon: createVNode(ExclamationCircleOutlined),
      onOk() {
        apiActionStart({
          action,
          params,
        }).then(() => {
          useWebSocketStore().connect();
        });
      },
      onCancel() {},
    });
  }
};

export const isActionRunning = (action: string) => {
  return !!useAppStore().$state.actions[action];
};
