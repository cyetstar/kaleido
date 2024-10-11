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
      :pagination="false"
      class="mt-3"
    >
      <a-table-column title="封面" align="center" width="150px">
        <template #="{ record }">
          <a
            :href="`https://bgm.tv/subject/${record.bgmId}`"
            target="_blank"
            class="flex justify-center"
          >
            <img :src="record.cover" :width="80" referrerpolicy="no-referrer" />
          </a>
        </template>
      </a-table-column>
      <a-table-column title="漫画信息">
        <template #="{ record }">
          <p>
            <a :href="`https://bgm.tv/subject/${record.bgmId}`" target="_blank">
              {{ record.series }}
            </a>
            <k-logo-link
              type="komga"
              :width="20"
              :id="record.existId"
              class="ml-3"
            />
          </p>
          <p class="text-muted" v-if="isNotEmpty(record.originalSeries)">
            {{ record.originalSeries }}
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
          </a-space>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
  <comic-series-file-manage ref="refComicSeriesFileManage" />
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { isNotEmpty } from "@ht/util";
import {
  apiComicSeriesMatchInfo,
  apiComicSeriesSearchInfo,
} from "@/api/comic/comicSeriesApi";
import ComicSeriesFileManage from "@/views/comic/comicSeries/comicSeriesFileManage.vue";

const emits = defineEmits(["match-success"]);
const refComicSeriesFileManage = ref();
const title = ref();
const loading = ref();
const visible = ref();
const dataSource = ref([]);
const searchForm = ref({
  source: "bgm",
  keyword: "",
  matchType: "path",
});
const sourceColumns = [
  {
    text: "bgm",
    value: "bgm",
  },
  {
    text: "bgm(v0)",
    value: "bgm_v0",
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
    searchForm.value.keyword = record.name
      .replaceAll("[", "")
      .replaceAll("]", "")
      .trim();
  } else {
    title.value = record.title;
    searchForm.value.keyword = record.title;
  }
};

const onSearch = () => {
  loading.value = true;
  apiComicSeriesSearchInfo(searchForm.value).then((res) => {
    dataSource.value = res;
    loading.value = false;
  });
};

const onFileManage = (id) => {
  refComicSeriesFileManage.value.show(id);
};

const onMatch = (data) => {
  apiComicSeriesMatchInfo({ ...record, ...data, ...searchForm.value }).then(
    () => {
      message.success("匹配成功");
      visible.value = false;
      emits("match-success");
    }
  );
};

const addRowColor = (data) => {
  if (data.bgmId && data.bgmId === record.bgmId) {
    return "bg-highlight";
  }
};
defineExpose({
  show,
  addRowColor,
});
</script>

<style lang="less" scoped></style>
