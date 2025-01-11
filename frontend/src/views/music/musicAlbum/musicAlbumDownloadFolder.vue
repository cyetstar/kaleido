<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <k-file-modal ref="refFileModal">
    <template #footer>
      <a-space>
        <h-button
          :disabled="refFileModal.selectedRows.length !== 1"
          @click="onMatch"
          >搜索匹配
        </h-button>
      </a-space>
    </template>
  </k-file-modal>
  <music-album-search-info
    ref="refMusicAlbumSearchInfo"
    @match-success="onMatchSuccess"
  />
</template>

<script setup>
import { ref } from "vue";
import { useAppStore } from "@/store/modules/app";
import { message } from "ant-design-vue";
import MusicAlbumSearchInfo from "@/views/music/musicAlbum/musicAlbumSearchInfo.vue";

const emits = defineEmits(["match-success"]);

const appStore = useAppStore();
const refFileModal = ref();
const refMusicAlbumSearchInfo = ref();

const show = () => {
  const importPath = appStore.$state.config["musicImportPath"];
  refFileModal.value.show(importPath);
};

const onMatch = () => {
  if (refFileModal.value.selectedRows.length !== 1) {
    message.error("请选择1条记录操作");
    return;
  }
  let path = refFileModal.value.selectedRows[0];
  refMusicAlbumSearchInfo.value.show(path, "path");
};

const onMatchSuccess = () => {
  refFileModal.value.load();
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
