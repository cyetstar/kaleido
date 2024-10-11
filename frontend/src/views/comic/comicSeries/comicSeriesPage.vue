<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画系列列表页面
-->
<template>
  <section class="k-page-section">
    <div class="k-search-form">
      <div class="flex justify-between items-center">
        <a-form :model="searchForm">
          <h-input
            v-model:value="searchForm.keyword"
            placeholder="关键字"
            search
            enter-button
            @search="onSearch"
          />
        </a-form>
        <a-space>
          <k-action-button
            action="comicUpdateSource"
            ok-text="更新文件源"
            cancel-text="取消更新"
          />
          <k-action-button
            action="comicSync"
            ok-text="同步Komga"
            cancel-text="取消同步"
            :form="searchForm"
          />
          <k-action-button
            action="comicMatchInfo"
            ok-text="自动抓取"
            cancel-text="取消抓取"
            :form="searchForm"
          />
          <h-button @click="onOpenDownloadFolder">文件源目录</h-button>
        </a-space>
      </div>
      <div class="flex justify-between items-center mt-4 mb-2">
        <div class="flex items-center">
          <a-cascader
            v-model:value="filter.value"
            :options="cascaderOptions"
            @change="onChangeCascader"
          >
            <a href="#" v-if="isEmpty(filter.value)">筛选</a>
            <a href="#" v-else> {{ filter.text }} </a>
          </a-cascader>
          <close-circle-outlined
            v-if="isNotEmpty(filter.value)"
            class="ml-2 cursor-pointer"
            @click="onClearCascader"
          />
        </div>
        <div>总记录数: {{ pageResult.total }}</div>
      </div>
    </div>
    <section ref="refScrollGrid" @scroll="onScrollGrid">
      <div v-if="pageResult.records.length === 0">
        <a-empty :image="Empty.PRESENTED_IMAGE_SIMPLE" />
      </div>
      <div v-else class="grid grid-cols-24 gap-6">
        <template :key="record.id" v-for="record in pageResult.records">
          <a-card class="k-card col-span-3" :bordered="false">
            <template #cover>
              <k-thumb-image
                class="h-poster"
                type="ComicSeries"
                :preview="false"
                :id="record.id"
                @click="onViewRecord(record.id)"
              />
              <div class="absolute top-0 left-0 m-2px">
                <k-logo-link
                  type="komga"
                  sub="series"
                  :id="record.id"
                  :width="20"
                  class="mr-1"
                />
                <k-logo-link type="bgm" :id="record.bgmId" :width="20" />
              </div>
            </template>
            <a-card-meta
              :title="record.title"
              :description="record.year"
            ></a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>
  <comic-series-download-folder ref="refComicSeriesDownloadFolder" />
</template>

<script setup>
import { onActivated, onMounted, ref } from "vue";
import { apiComicSeriesPage } from "@/api/comic/comicSeriesApi.ts";

import { Empty, message } from "ant-design-vue";
import { CloseCircleOutlined } from "@ant-design/icons-vue";
import { onBeforeRouteLeave, useRoute, useRouter } from "vue-router";
import { useAppStore } from "@/store/modules/app";
import { apiSysDictListByDictType } from "@/api/sysadmin/sysDictApi";
import { isEmpty, isNotEmpty } from "@ht/util";
import ComicSeriesDownloadFolder from "@/views/comic/comicSeries/comicSeriesDownloadFolder.vue";
import KActionButton from "@c/ActionButton/ActionButton.vue";

const route = useRoute();
const router = useRouter();
const appStore = useAppStore();
const actions = ref(appStore.actions);
const cascaderOptions = ref([
  {
    label: "类型",
    value: "genreId",
    type: "comicGenre",
  },
  {
    label: "国家地区",
    value: "countryId",
    type: "comicCountry",
  },
  {
    label: "分级",
    value: "contentRating",
    type: "comicContentRating",
  },
  {
    label: "年代",
    value: "decade",
    type: "comicDecade",
  },
  {
    label: "年份",
    value: "year",
    type: "comicYear",
  },
  {
    label: "多文件",
    value: "multipleFiles",
    type: "sfbz",
  },
  {
    label: "低质量",
    value: "lowQuality",
    type: "sfbz",
  },
  {
    label: "国语配音",
    value: "mandarin",
    type: "sfbz",
  },
  {
    label: "无字幕",
    value: "noSubtitle",
    type: "sfbz",
  },
]);
const refScrollGrid = ref();
const refComicSeriesDownloadFolder = ref();
const loading = ref(true);
const searchForm = ref({});
const pageResult = ref({
  records: [],
  pageNumber: 0,
  pageSize: 50,
});

const filter = ref({
  key: "",
  value: "",
  text: "",
});

const setFilter = (key, value, text) => {
  searchForm.value[key] = value;
  filter.value.key = key;
  filter.value.value = value;
  filter.value.text = text;
};

const clearFilter = () => {
  searchForm.value[filter.value.key] = "";
  filter.value.key = "";
  filter.value.value = "";
  filter.value.text = "";
};

const initCascader = () => {
  cascaderOptions.value.forEach((s) => {
    apiSysDictListByDictType(s.type).then((r) => {
      s.children = r.map((m) => ({ label: m.text, value: m.value }));
    });
  });
};

onMounted(() => {
  loadData();
  initCascader();
});

onActivated(() => {
  refScrollGrid.value.scrollTop = appStore.getScrollTop("comic");
  if (route.params.load) {
    searchForm.value.keyword = route.params.keyword;
    onSearch();
  }
});

onBeforeRouteLeave((to, from, next) => {
  appStore.setScrollTop(refScrollGrid.value.scrollTop, "comic");
  next();
});

const loadData = () => {
  loading.value = true;
  const data = {
    pageNumber: pageResult.value.pageNumber + 1,
    pageSize: pageResult.value.pageSize,
    searchCount: true,
    ...searchForm.value,
  };
  apiComicSeriesPage(data).then((res) => {
    loading.value = false;
    pageResult.value.records.push(...res.records);
    pageResult.value.pageNumber = res.pageNumber;
    pageResult.value.pageSize = res.pageSize;
    pageResult.value.total = res.total;
  });
};

const onSearch = () => {
  pageResult.value.pageNumber = 0;
  pageResult.value.records = [];
  loadData();
};

const onViewRecord = (id) => {
  router.push({ path: "/comic/comicSeries/view", query: { id } });
};

const onOpenDownloadFolder = () => {
  refComicSeriesDownloadFolder.value.show();
};

const onChangeCascader = (value, selectedOptions) => {
  setFilter(value[0], value[1], selectedOptions.map((s) => s.label).join(": "));
  onSearch();
};

const onClearCascader = () => {
  clearFilter();
  onSearch();
};

const onScrollGrid = () => {
  const containerHeight = refScrollGrid.value.clientHeight;
  // 获取滚动容器滚动的高度
  const scrollHeight = refScrollGrid.value.scrollHeight;
  // 获取滚动容器滚动的距离
  const scrollTop = refScrollGrid.value.scrollTop;
  // 判断是否滑倒底部
  if (
    containerHeight + scrollTop >= scrollHeight - 50 &&
    loading.value !== true
  ) {
    loadData();
  }
};
</script>

<style lang="less" scoped></style>
