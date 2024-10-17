<!--
 * @Author: cyetstar
 * @Date: 2023-11-20 22:35:49
 * @Description: 艺术家列表页面
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
          <a-card class="k-card col-span-3" :bordered="false">
            <template #cover>
              <k-plex-image
                class="h-cover cursor-pointer"
                type="music"
                :preview="false"
                :plex-thumb="record.thumb"
                @click="onViewRecord(record.id)"
              />
            </template>
            <a-card-meta :title="record.title"> </a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>
</template>

<script setup>
import { ref, onMounted, onActivated } from "vue";
import { apiArtistPage } from "@/api/artistApi.ts";

import { Empty, message } from "ant-design-vue";
import { onBeforeRouteLeave, useRouter } from "vue-router";
import { useAppStore } from "@/store/modules/app";

const router = useRouter();
const appStore = useAppStore();
const refScrollGrid = ref();
const searchForm = ref({});
const loading = ref(false);
const pageResult = ref({
  records: [],
  pageNumber: 0,
  pageSize: 100,
});

onMounted(() => {
  loadData();
});

onActivated(() => {
  refScrollGrid.value.scrollTop = appStore.getScrollTop("artist");
});

onBeforeRouteLeave((to, from, next) => {
  appStore.setScrollTop(refScrollGrid.value.scrollTop, "artist");
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
  apiArtistPage(data).then((res) => {
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
  router.push({ path: "/artist/view", query: { id } });
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
