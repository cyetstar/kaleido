<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <k-file-modal ref="refFileModal" />
</template>

<script setup>
import { ref } from "vue";

import { apiMovieBasicViewPath } from "@/api/movie/movieBasicApi";

const emits = defineEmits(["match-success"]);
const refFileModal = ref();

const show = (recordId) => {
  apiMovieBasicViewPath({ id: recordId }).then((res) => {
    refFileModal.value.show(res);
  });
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
