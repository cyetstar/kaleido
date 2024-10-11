<!--
 * @Author: cyetstar
 * @Date: 2023-12-29 16:56:31
 * @Description: 豆瓣电影口碑榜列表页面
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
          <h-button @click="onSyncWeekly">同步口碑榜</h-button>
        </a-space>
      </div>
      <div class="flex justify-end items-center mt-4 mb-2">
        <div>总记录数: {{ pageResult.total }}</div>
      </div>
    </div>
    <section ref="refScrollGrid" @scroll="onScrollGrid">
      <div v-if="isEmpty(pageResult.records)">
        <a-empty :image="Empty.PRESENTED_IMAGE_SIMPLE" />
      </div>
      <div v-else class="grid grid-cols-24 gap-6">
        <template :key="record.id" v-for="record in pageResult.records">
          <div class="col-span-12">
            <a-badge-ribbon
              :text="'#' + record.top"
              :color="record.status === '1' ? 'red' : ''"
            >
              <a-card class="k-card k-card-inline" :bordered="true">
                <template #cover>
                  <k-plex-image
                    :preview="false"
                    :plex-thumb="record.thumb"
                    class="h-poster"
                    :class="record.movieId ? 'cursor-pointer' : null"
                    @click="
                      record.movieId ? onViewRecord(record.movieId) : null
                    "
                  />
                  <div class="absolute top-0 left-0 m-2px">
                    <k-logo-link
                      type="plex"
                      :id="record.movieId"
                      :width="20"
                      class="mr-1"
                    />
                    <k-logo-link
                      type="douban"
                      :id="record.doubanId"
                      :width="20"
                    />
                  </div>
                </template>
                <a-card-meta :title="record.title + ' (' + record.year + ')'">
                  <template #description>
                    <div>
                      {{ record.originalTitle }}
                    </div>
                    <div class="grid grid-rows-4 grid-flow-col gap-x-6">
                      <div
                        class="row-span-1"
                        v-for="(value, key) in record.listingDetailMap"
                      >
                        {{ key }} ～ #{{ value }}
                      </div>
                    </div>
                  </template>
                </a-card-meta>
              </a-card>
            </a-badge-ribbon>
          </div>
        </template>
      </div>
    </section>
  </section>
</template>

<script setup>
import { ref, onMounted, onActivated } from "vue";
import {
  apiMovieDoubanWeeklyPage,
  apiMovieDoubanWeeklySync,
} from "@/api/movie/movieDoubanWeeklyApi";
import { Empty, message } from "ant-design-vue";
import { onBeforeRouteLeave, useRouter } from "vue-router";
import { isEmpty, isNotEmpty } from "@ht/util";
import { useAppStore } from "@/store/modules/app";

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

const onSyncWeekly = () => {
  apiMovieDoubanWeeklySync().then((res) => {
    if (res) {
      message.success("同步成功");
    } else {
      message.error("同步发生错误");
    }
  });
};

const loadData = () => {
  loading.value = true;
  const data = {
    pageNumber: pageResult.value.pageNumber + 1,
    pageSize: pageResult.value.pageSize,
    searchCount: true,
    ...searchForm.value,
  };
  apiMovieDoubanWeeklyPage(data).then((res) => {
    loading.value = false;
    pageResult.value.records.push(...res.records);
    pageResult.value.pageNumber = res.pageNumber;
    pageResult.value.pageSize = res.pageSize;
    pageResult.value.total = res.total;
  });
};
const onViewRecord = (id) => {
  router.push({ path: "/movie/movieBasic/view", query: { id } });
};
</script>
<style lang="less" scoped>
:deep(.k-card-inline) {
  .ant-image {
    display: block;

    & > img {
      width: 120px;
    }
  }

  .ant-card-meta-description {
    div {
      margin-bottom: 6px;
    }
  }
}
</style>
