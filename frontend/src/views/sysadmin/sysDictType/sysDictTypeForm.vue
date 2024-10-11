<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 6 }"
    v-model:form="form"
    :title="formAction === 'create' ? '新增字典' : '编辑字典'"
    @submit="submit"
  >
    <h-col :span="24">
      <h-input
        required
        label="字典名称"
        name="name"
        v-model:value="form.name"
      ></h-input>
    </h-col>
    <h-col :span="24">
      <h-input
        required
        label="字典类型"
        name="type"
        v-model:value="form.type"
      ></h-input>
    </h-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import {
  sysDictTypeViewApi,
  sysDictTypeCreateApi,
  sysDictTypeUpdateApi,
} from "@/api/sysadmin/sysDictTypeApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let formRef = ref();

let form = ref({
  id: "",
  name: "",
  type: "",
});

const create = () => {
  formAction.value = "create";
  formRef.value.reset();
  formRef.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  form.value = await sysDictTypeViewApi(id);
  formRef.value.show();
};

const submit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await sysDictTypeCreateApi(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await sysDictTypeUpdateApi(form.value);
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
