<!--
 * @Author: cyetstar
 * @Date: 2023-11-26 01:20:15
 * @Description: 电影集合详情页面
-->
<template>
  <section class="k-view-section" ref="refScrollGrid" @scroll="onScrollGrid">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template #backIcon>
        <LeftOutlined/>
      </template>
      <template #extra>
        <a-space>
          <h-button-delete @delete="onDelete">删除</h-button-delete>
          <h-button @click="onSyncDouban"
          >{{ appStore.actions["movieCollectionSyncDouban"] ? "取消同步" : "同步豆列" }}
          </h-button>
        </a-space>
      </template>
    </a-page-header>
    <section class="flex">
      <div>
        <k-plex-image style="width: 250px" :plex-thumb="record.thumb"/>
      </div>
      <div class="flex-1 ml-8">
        <p>
          <p v-for="(item, index) in record.summaryList" :key="index">
            {{ item }}
          </p>
        </p>
        <p class="flex" v-if="isNotEmpty(record.updateTime)">
          <span class="mr-2">更新于:</span>
          <span class="flex-1">{{ record.updateTime }}</span>
        </p>
        <p>
          <k-logo-link type="doulist" :id="record.doubanId" class="mr-3"/>
        </p>
      </div>
    </section>
    <section>
      <h-module-title title="电影">
        <template #right>
          共{{ record.childCount }}部
        </template>
      </h-module-title>
      <div class="grid grid-cols-24 gap-6">
        <template :key="item.id" v-for="item in pageResult.records">
          <a-tooltip :title="item.comment">
            <a-card class="k-card col-span-3" :bordered="false">
              <template #cover>
                <k-plex-image
                    class="h-thumb"
                    :class="isEmpty(item.movieId)?null:'cursor-pointer'"
                    :preview="false"
                    :plex-thumb="item.thumb"
                    @click="isEmpty(item.movieId)?null:onViewMovieRecord(item.movieId)"
                />
                <div class="absolute top-0 left-0 m-2px">
                  <k-logo-link
                      type="plex"
                      :id="item.movieId"
                      :width="20"
                      class="mr-1"
                  />
                  <k-logo-link
                      type="douban"
                      :id="item.doubanId"
                      :width="20"
                  />
                </div>
              </template>
              <a-card-meta
                  :title="item.title"
              >
                <template #description>
                  <div>{{ item.year }}</div>

                </template>
              </a-card-meta>
            </a-card>
          </a-tooltip>
        </template>
      </div>
    </section>
  </section>
</template>

<script setup>
import {onActivated, onMounted, ref} from "vue";
import {onBeforeRouteLeave, useRoute, useRouter} from "vue-router";
import {apiMovieCollectionDelete, apiMovieCollectionView,} from "@/api/movie/movieCollectionApi.ts";
import {isEmpty, isNotEmpty} from "@ht/util";
import {message} from "ant-design-vue";
import {useAppStore} from "@/store/modules/app";
import {apiMovieBasicCollectionPage} from "@/api/movie/movieBasicCollectionApi";
import {triggerAction} from "@/utils/action";

const route = useRoute();
const router = useRouter();
const appStore = useAppStore();

const id = route.query.id;

const refScrollGrid = ref();
const loading = ref(true);
const record = ref({});
const searchForm = ref({
  collectionId: id
});
const pageResult = ref({
  records: [],
  pageNumber: 0,
  pageSize: 100,
});


const initData = async () => {
  apiMovieCollectionView({id}).then((res) => {
    record.value = res;
  });
};


const onViewMovieRecord = (id) => {
  router.push({path: "/movie/movieBasic/view", query: {id}});
};

const onSyncDouban = () => {
  triggerAction('movieCollectionSyncDouban', {id})
}

const onDelete = () => {
  apiMovieCollectionDelete([id]).then((res) => {
    if (res) {
      message.success("删除成功");
      router.push("/movie/movieCollection/page");
    } else {
      message.error("删除失败");
    }
  });
};

onMounted(() => {
  initData();
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
  apiMovieBasicCollectionPage(data).then((res) => {
    loading.value = false;
    pageResult.value.records.push(...res.records);
    pageResult.value.pageNumber = res.pageNumber;
    pageResult.value.pageSize = res.pageSize;
    pageResult.value.total = res.total;
  });
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
