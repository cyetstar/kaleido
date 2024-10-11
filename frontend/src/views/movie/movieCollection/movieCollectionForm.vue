<!--
 * @Author: cyetstar
 * @Date: 2023-11-26 01:20:15
 * @Description: 电影集合表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 4 }"
    width="600px"
    v-model:form="form"
    title="电影合集"
    @submit="onSubmit"
  >
    <h-col :span="24">
      <h-input label="豆瓣编号" v-model:value="form.doubanId" name="doubanId" />
    </h-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { apiMovieCollectionCreate } from "@/api/movie/movieCollectionApi";

const emits = defineEmits(["save-complete"]);

let formRef = ref();
let form = ref({});

const create = () => {
  formRef.value.reset();
  formRef.value.show();
};

const onSubmit = async () => {
  apiMovieCollectionCreate(form.value).then((res) => {
    if (res) {
      message.success("操作成功");
      emits("save-complete");
      formRef.value.hide();
    }
  });
};

defineExpose({
  create,
});
</script>

<style lang="less" scoped></style>
