<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 漫画下载文件夹管理页面
-->
<template>
  <k-file-modal ref="refFileModal">
    <template #footer>
      <h-button
        :disabled="refFileModal.selectedRows.length !== 1"
        plain
        type="primary"
        @click="onMatch"
        >匹配搜索
      </h-button>
    </template>
  </k-file-modal>
  <comic-series-search-info
    ref="refComicSeriesSearchInfo"
    @match-success="onMatchSuccess"
  />
</template>

<script setup>
import { ref } from "vue";
import { useAppStore } from "@/store/modules/app";
import ComicSeriesSearchInfo from "@/views/comic/comicSeries/comicSeriesSearchInfo.vue";

const emits = defineEmits(["match-success"]);

const appStore = useAppStore();
const refFileModal = ref();
const refComicSeriesSearchInfo = ref();

const show = () => {
  const importPath = appStore.$state.config["comicImportPath"];
  refFileModal.value.show(importPath);
};

const onMatch = () => {
  let paths = refFileModal.value.selectedRows;
  refComicSeriesSearchInfo.value.show(paths[0], "path");
};

const onMatchSuccess = () => {
  refFileModal.value.load();
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
