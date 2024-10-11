<!--
 * @Author: cyetstar
 * @Date: 2023-11-26 01:20:15
 * @Description: 电影集合列表页面
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
            enterButton
            @search="onSearch"
          />
        </a-form>
        <a-space>
          <h-button @click="onCreate">新增合集</h-button>
          <h-button @click="onSyncDouban">同步豆列</h-button>
        </a-space>
      </div>
      <div class="flex justify-end items-center mt-4 mb-2">
        <div>总记录数: {{ pageResult.total }}</div>
      </div>
    </div>
    <section ref="refScrollGrid" @scroll="onScrollGrid">
      <div v-if="pageResult.records.length === 0">
        <a-empty :image="Empty.PRESENTED_IMAGE_SIMPLE" />
      </div>
      <div v-else class="grid grid-cols-24 gap-6">
        <template :key="record.id" v-for="record in pageResult.records">
          <a-card class="k-card col-span-4" :bordered="false">
            <template #cover>
              <k-plex-image
                class="cursor-pointer"
                :preview="false"
                :plex-thumb="record.thumb"
                @click="onViewRecord(record.id)"
              />
            </template>
            <a-card-meta :title="record.title"></a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>
  <movie-collection-form ref="refMovieCollectionForm"></movie-collection-form>
</template>

<script setup>
import { ref, onMounted, onActivated } from "vue";
import {
  apiMovieCollectionPage,
  apiMovieCollectionSyncPlex,
} from "@/api/movie/movieCollectionApi.ts";

import { Empty, message } from "ant-design-vue";
import { onBeforeRouteLeave, useRouter } from "vue-router";
import { isEmpty, isNotEmpty } from "@ht/util";
import { useAppStore } from "@/store/modules/app";
import MovieCollectionForm from "@/views/movie/movieCollection/movieCollectionForm.vue";
import { triggerAction } from "@/utils/action";

const refMovieCollectionForm = ref();
const router = useRouter();
const appStore = useAppStore();
const refScrollGrid = ref();
const loading = ref(true);
const searchForm = ref({});
const pageResult = ref({
  records: [],
  pageNumber: 0,
  pageSize: 100,
});

onMounted(() => {
  loadData();
});

onActivated(() => {
  refScrollGrid.value.scrollTop = appStore.getScrollTop("movieCollection");
});

onBeforeRouteLeave((to, from, next) => {
  appStore.setScrollTop(refScrollGrid.value.scrollTop, "movieCollection");
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
  apiMovieCollectionPage(data).then((res) => {
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
  router.push({ path: "/movie/movieCollection/view", query: { id } });
};

const onCreate = () => {
  refMovieCollectionForm.value.create();
};

const onSyncDouban = () => {
  triggerAction("movieCollectionSyncDoubanAll");
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
