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
        :disabled="refFileModal.selectedRows.length !== 1"
        type="primary"
        @click="onMatch"
        >匹配搜索</h-button
      >
    </template>
  </k-file-modal>
  <tvshow-show-search-info
    ref="refTvshowShowSearchInfo"
    @match-success="onMatchSuccess"
  />
</template>

<script setup>
import { ref } from "vue";
import { useAppStore } from "@/store/modules/app";
import TvshowShowSearchInfo from "@/views/tvshow/tvshowShow/tvshowShowSearchInfo.vue";

const emits = defineEmits(["match-success"]);

const appStore = useAppStore();
const refFileModal = ref();
const refTvshowShowSearchInfo = ref();

const show = () => {
  const importPath = appStore.$state.config["tvshowImportPath"];
  refFileModal.value.show(importPath);
};

const onMatch = () => {
  let path = refFileModal.value.selectedRows[0];
  refTvshowShowSearchInfo.value.show(path, "path");
};

const onMatchSuccess = () => {
  refFileModal.value.load();
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
