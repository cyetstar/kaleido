<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画作者表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 4 }"
    width="600px"
    v-model:form="form"
    title="漫画作者"
    @submit="onSubmit"
  >
    <h-col :span="24">
      <h-input label="姓名" v-model:value="form.name" name="name" />
    </h-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import {
  apiAuthorCreate,
  apiAuthorUpdate,
  apiAuthorView,
} from "@/api/authorApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let formRef = ref();

let form = ref({
  id: "",
  name: "",
});

const create = () => {
  formAction.value = "create";
  formRef.value.reset();
  formRef.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  form.value = await apiAuthorView({ id });
  formRef.value.show();
};

const onSubmit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await apiAuthorCreate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await apiAuthorUpdate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    }
  } catch (e) {}
};

defineExpose({
  create,
  update,
});
</script>

<style lang="less" scoped></style>
