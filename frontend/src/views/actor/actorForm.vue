<!--
 * @Author: cyetstar
 * @Date: 2023-11-26 01:20:15
 * @Description: 演职员表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 4 }"
    width="600px"
    v-model:form="form"
    title="演职员"
    @submit="onSubmit"
  >
    <h-col :span="24">
      <h-input label="姓名" v-model:value="form.name" name="name" />
    </h-col>
    <h-col :span="24">
      <h-input
        label="原名"
        v-model:value="form.originalName"
        name="originalName"
      />
    </h-col>
    <h-col :span="24">
      <h-input label="豆瓣编号" v-model:value="form.doubanId" name="doubanId" />
    </h-col>
    <h-col :span="24">
      <h-input label="头像地址" v-model:value="form.thumb" name="thumb" />
    </h-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { apiActorCreate, apiActorUpdate, apiActorView } from "@/api/actorApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let formRef = ref();

let form = ref({
  id: "",
  doubanId: "",
  name: "",
  originalName: "",
});

const create = () => {
  formAction.value = "create";
  formRef.value.reset();
  formRef.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  form.value = await apiActorView({ id });
  formRef.value.show();
};

const onSubmit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await apiActorCreate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await apiActorUpdate(form.value);
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
