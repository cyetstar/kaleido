<template>
  <h-button @click="onClick">{{ text }}</h-button>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
import { isActionRunning, triggerAction } from "@/utils/action";

export default defineComponent({
  name: "KActionButton",
  props: {
    action: null,
    okText: null,
    cancelText: null,
    form: null,
  },
  setup(props, { emit }) {
    const text = computed(() => {
      return isActionRunning(props.action) ? props.cancelText : props.okText;
    });

    const onClick = () => {
      triggerAction(props.action, props.form);
    };
    return {
      text,
      onClick,
    };
  },
});
</script>
