<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    title="匹配网易云"
    width="960px"
    :footer="null"
  >
    <div class="flex gap-2">
      <h-radio
        v-model:value="searchForm.source"
        button
        name="source"
        :columns="sourceColumns"
      />
      <h-input
        class="flex-1"
        placeholder=""
        v-model:value="searchForm.keyword"
        name="keyword"
        @keyup.enter="onSearch"
      />
      <h-button @click="onSearch">搜索</h-button>
      <h-button @click="onMatch">无法匹配</h-button>
    </div>

    <a-table
      size="small"
      :data-source="dataSource"
      :loading="loading"
      :row-class-name="addRowColor"
      class="mt-3"
    >
      <a-table-column title="封面" align="center" :width="150">
        <template #="{ record }">
          <a
            :href="`https://music.163.com/#/album?id=${record.neteaseId}`"
            target="_blank"
            class="flex justify-center"
          >
            <img
              :src="record.picUrl"
              :width="100"
              referrerpolicy="no-referrer"
            />
          </a>
        </template>
      </a-table-column>
      <a-table-column title="专辑名">
        <template #="{ record }">
          <p>
            {{ record.title }}
          </p>
          <p class="text-muted">
            {{ record.artist }}
          </p>
          <div class="text-muted">{{ record.publishTime }}</div>
        </template>
      </a-table-column>
      <a-table-column title="操作" align="center" width="150px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button type="primary" size="small" link @click="onMatch(record)"
              >匹配信息
            </h-button>
            <h-button
              type="primary"
              size="small"
              link
              @click="onDownloadCover(record)"
              >下载封面
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
import {
  apiMusicAlbumSearchInfo,
  apiMusicAlbumMatchInfo,
  apiMusicAlbumMatchPath,
  apiMusicAlbumDownloadCover,
} from "@/api/music/musicAlbumApi.ts";

const emits = defineEmits(["match-success"]);
const loading = ref();
const title = ref();
const type = ref();
let record = {};
let path = {};
let visible = ref();
let dataSource = ref([]);
let searchForm = ref({
  source: "musicbrainz",
  keyword: "",
});
let sourceColumns = [
  {
    text: "MusicBrainz",
    value: "musicbrainz",
  },
  {
    text: "网易",
    value: "netease",
  },
];

const show = (source, recordType) => {
  visible.value = true;
  type.value = recordType;
  dataSource.value = [];
  if (type.value === "path") {
    path = source;
    title.value = path.name;
    searchForm.value.keyword = title.value.replaceAll("\.", " ");
  } else {
    record = source;
    title.value = record.title + " " + record.artists;
    searchForm.value.keyword = title.value;
  }
};

const onSearch = () => {
  loading.value = true;
  apiMusicAlbumSearchInfo(searchForm.value).then((res) => {
    dataSource.value = res;
    loading.value = false;
  });
};

const onMatch = (source) => {
  loading.value = true;
  if (type.value === "path") {
    apiMusicAlbumMatchPath({ ...path, ...source }).then(() => {
      message.success("匹配成功");
      visible.value = false;
      loading.value = false;
      emits("match-success");
    });
  } else {
    apiMusicAlbumMatchInfo({ ...record, ...source, ...searchForm.value })
      .then(() => {
        message.success("匹配成功");
        visible.value = false;
        emits("match-success");
      })
      .catch(() => {
        loading.value = false;
      });
  }
};

const onDownloadCover = (source) => {
  loading.value = true;
  apiMusicAlbumDownloadCover({ id: record.id, url: source.picUrl }).then(
    (res) => {
      message.success("下载成功");
      visible.value = false;
      loading.value = false;
    }
  );
};

const addRowColor = (source) => {
  if (source && source.neteaseId === record.neteaseId) {
    return "bg-highlight";
  }
};

defineExpose({
  show,
  addRowColor,
});
</script>

<style lang="less" scoped></style>
