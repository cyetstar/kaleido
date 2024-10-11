<!--
 * @Author: gongxiaofei
 * @Date: 2023-07-03 17:29:00
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-03 17:29:01
 * @Description: 
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 6 }"
    v-model:form="form"
    :title="formAction === 'create' ? '新增字典项' : '编辑字典项'"
    @submit="submit"
  >
    <h-col :span="24">
      <h-input
        required
        label="字典项名"
        name="label"
        v-model:value="form.label"
      ></h-input>
    </h-col>
    <h-col :span="24">
      <h-input
        required
        label="字典项值"
        name="value"
        v-model:value="form.value"
      ></h-input>
    </h-col>
    <h-col :span="24">
      <h-input
        required
        label="顺序号"
        name="sort"
        v-model:value="form.sort"
      ></h-input>
    </h-col>
  </h-form-modal>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { message } from "ant-design-vue";
import {
  sysDictCreateApi,
  sysDictUpdateApi,
  sysDictViewApi,
} from "@/api/sysadmin/sysDictApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let formRef = ref();

let form = ref({
  id: "",
  label: "",
  value: "",
  sort: "",
  dictType: "",
});

const create = (dictType: string) => {
  formAction.value = "create";
  form.value = {
    id: "",
    label: "",
    value: "",
    sort: "",
    dictType: dictType,
  };
  formRef.value.reset();
  formRef.value.show();
};
const update = async (id: string) => {
  formAction.value = "update";
  formRef.value.reset();
  form.value = await sysDictViewApi(id);
  formRef.value.show();
};
const submit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await sysDictCreateApi(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await sysDictUpdateApi(form.value);
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
