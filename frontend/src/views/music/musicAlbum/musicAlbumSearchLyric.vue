<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    :title="`查询“${trackRecord.title}”歌词`"
    width="660px"
    :footer="null"
  >
    <a-table
      size="small"
      :data-source="dataSource"
      :loading="loading"
      :row-class-name="addRowColor"
      class="mt-3"
    >
      <a-table-column
        title="曲号"
        data-index="no"
        width="10%"
        align="center"
      ></a-table-column>
      <a-table-column title="歌名" data-index="name"></a-table-column>
      <a-table-column title="操作" align="center" width="150px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              type="primary"
              size="small"
              link
              @click="onDownloadLyric(record)"
              >下载歌词
            </h-button>
          </a-space>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { apiMusicAlbumViewNetease } from "@/api/music/musicAlbumApi.ts";
import { apiMusicTrackDownloadLyric } from "@/api/music/musicTrackApi";

const emits = defineEmits(["match-success"]);
const loading = ref();
const trackRecord = ref({
  title: "",
});
let visible = ref();
let dataSource = ref([]);
let form = ref({
  keywords: "",
});

const show = (record) => {
  trackRecord.value = record;
  visible.value = true;
  apiMusicAlbumViewNetease({ id: trackRecord.value.albumId }).then((res) => {
    dataSource.value = res.songs;
    loading.value = false;
  });
};

const onDownloadLyric = (record) => {
  loading.value = true;
  apiMusicTrackDownloadLyric({
    id: trackRecord.value.id,
    neteaseId: record.id,
  }).then((res) => {
    visible.value = false;
    loading.value = false;
    emits("match-success");
  });
};

const addRowColor = (record) => {
  if (record.no === trackRecord.value.trackIndex) {
    return "bg-highlight";
  }
};

defineExpose({
  show,
  addRowColor,
});
</script>

<style lang="less" scoped></style>
