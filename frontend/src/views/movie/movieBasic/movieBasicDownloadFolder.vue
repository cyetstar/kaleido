<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <k-file-modal ref="refFileModal">
    <template #footer>
      <h-button
        plain
        type="primary"
        :disabled="refFileModal.selectedRows.length !== 1"
        @click="onMatch"
        >搜索匹配
      </h-button>
    </template>
  </k-file-modal>
  <movie-basic-search-info
    ref="refMovieBasicSearchInfo"
    @match-success="onMatchSuccess"
  />
</template>

<script setup>
import { ref } from "vue";
import { useAppStore } from "@/store/modules/app";
import { message } from "ant-design-vue";
import MovieBasicSearchInfo from "@/views/movie/movieBasic/movieBasicSearchInfo.vue";

const emits = defineEmits(["match-success"]);

const appStore = useAppStore();
const refFileModal = ref();
const refMovieBasicSearchInfo = ref();

const show = () => {
  const libraryPath = appStore.$state.config["movieImportPath"];
  refFileModal.value.show(libraryPath);
};

const onMatch = () => {
  if (refFileModal.value.selectedRows.length !== 1) {
    message.error("请选择1条记录操作");
    return;
  }
  let path = refFileModal.value.selectedRows[0];
  refMovieBasicSearchInfo.value.show(path, "path");
};

const onMatchSuccess = () => {
  refFileModal.value.load();
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
