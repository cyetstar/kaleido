<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    :title="`匹配【${title}】信息`"
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
      <a-table-column title="海报" align="center" width="150px">
        <template #="{ record }">
          <a
            :href="`https://movie.douban.com/subject/${record.doubanId}/`"
            target="_blank"
            class="flex justify-center"
          >
            <img
              :src="record.poster"
              :width="80"
              referrerpolicy="no-referrer"
            />
          </a>
        </template>
      </a-table-column>
      <a-table-column title="剧集信息">
        <template #="{ record }">
          <p>
            <a
              :href="`https://movie.douban.com/subject/${record.doubanId}/`"
              target="_blank"
            >
              {{ record.title }}
            </a>
            <k-logo-link
              type="plex"
              :width="20"
              :id="record.existId"
              class="ml-3"
            />
          </p>
          <p class="text-muted" v-if="isNotEmpty(record.originalTitle)">
            {{ record.originalTitle }}
          </p>
          <div class="text-muted">{{ record.year }}</div>
        </template>
      </a-table-column>
      <a-table-column title="操作" align="center" width="150px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              v-if="isNotEmpty(record.existId)"
              type="primary"
              size="small"
              link
              @click="onFileManage(record.existId)"
              >查看文件
            </h-button>
            <h-button type="primary" size="small" link @click="onMatch(record)"
              >匹配
            </h-button>
            <h-button
              v-if="type !== 'path'"
              type="primary"
              size="small"
              link
              @click="onDownloadPoster(record)"
              >下载海报
            </h-button>
          </a-space>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
  <tvshow-season-file-manage ref="refTvshowSeasonFileManage" />
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { isNotEmpty } from "@ht/util";
import { apiTvshowShowSearchInfo } from "@/api/tvshow/tvshowShowApi";
import {
  apiTvshowSeasonDownloadPoster,
  apiTvshowSeasonMatchInfo,
} from "@/api/tvshow/tvshowSeasonApi";
import TvshowSeasonFileManage from "@/views/tvshow/tvshowSeason/tvshowSeasonFileManage.vue";

const emits = defineEmits(["match-success"]);
const refTvshowSeasonFileManage = ref();
const title = ref();
const loading = ref();
const visible = ref();
const dataSource = ref([]);
const searchForm = ref({
  source: "douban",
  keyword: "",
  matchType: "path",
});
const sourceColumns = [
  {
    text: "豆瓣",
    value: "douban",
  },
  {
    text: "TMDB",
    value: "tmdb",
  },
];
let record = {};

const show = (data, dataType) => {
  record = data;
  dataSource.value = [];
  visible.value = true;
  searchForm.value.matchType = dataType;
  if (dataType === "path") {
    title.value = record.name;
    searchForm.value.keyword = record.name.replaceAll("\.", " ");
  } else {
    title.value = record.title;
    searchForm.value.keyword = record.showTitle + " " + record.title;
  }
};

const onSearch = () => {
  loading.value = true;
  apiTvshowShowSearchInfo(searchForm.value).then((res) => {
    dataSource.value = res;
    loading.value = false;
  });
};

const onFileManage = (id) => {
  refTvshowSeasonFileManage.value.show(id);
};

const onMatch = (data) => {
  apiTvshowSeasonMatchInfo({ ...record, ...data, ...searchForm.value }).then(
    () => {
      message.success("匹配成功");
      visible.value = false;
      emits("match-success");
    }
  );
};

const onDownloadPoster = (data) => {
  loading.value = true;
  let url = data.poster.replace("s_ratio_poster", "m_ratio_poster");
  apiTvshowSeasonDownloadPoster({ id: record.id, url: url }).then(() => {
    message.success("下载成功");
    visible.value = false;
  });
};

const addRowColor = (data) => {
  if (
    (data.doubanId && data.doubanId === record.doubanId) ||
    (data.tmdbId && data.tmdbId === record.tmdbId)
  ) {
    return "bg-highlight";
  }
};
defineExpose({
  show,
  addRowColor,
});
</script>

<style lang="less" scoped></style>
