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
    title="批量新增"
    @submit="submit"
  >
    <h-col :span="24">
      <h-input
        text-area
        required
        name="batchContent"
        v-model:value="form.batchContent"
        :rows="5"
        placeholder="每行一对项值、项名，项值、项名之间用空格间隔"
      ></h-input>
    </h-col>
  </h-form-modal>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { message } from "ant-design-vue";
import {
  sysDictCreateApi,
  sysDictCreateBatchApi,
  sysDictUpdateApi,
  sysDictViewApi,
} from "@/api/sysadmin/sysDictApi";

const emits = defineEmits(["save-complete"]);

let formRef = ref();

let form = ref({
  batchContent: "",
  dictType: "",
});

let dictType;

const create = (type: string) => {
  dictType = type;
  formRef.value.reset();
  formRef.value.show();
};
const submit = async () => {
  try {
    const results = [];
    let lines = form.value.batchContent.split(/\n/);
    lines.forEach((line) => {
      const [value, label] = line.split(/\s+/);
      if (value && label) {
        results.push({
          value,
          label,
          dictType,
        });
      }
    });
    let res = await sysDictCreateBatchApi(results);
    if (res) {
      message.success("操作成功");
      emits("save-complete");
      formRef.value.hide();
    }
  } catch (e) {}
};

defineExpose({
  create,
});
</script>

<style lang="less" scoped></style>
