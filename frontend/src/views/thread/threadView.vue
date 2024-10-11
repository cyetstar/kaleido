<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 15:19:06
 * @Description: 系统配置表详情页面
-->
<template>
  <section class="h-form-wrapper">
    <div class="h-form-body">
      <h-module-title title="发布信息"></h-module-title>
      <a-row class="mt-3">
        <h-col :span="24">
          <h3>
            <a-tag :color="color[record.status]">{{ record.status }}</a-tag>
            {{ record.title }}
            <a v-if="record.url" :href="record.url" target="_blank">
              <link-outlined />
            </a>
          </h3>
        </h-col>
        <h-col :span="8">
          <a-form-item label="豆瓣编号">
            {{ record.doubanId }}
          </a-form-item>
        </h-col>
        <h-col :span="8">
          <a-form-item label="IMDb编号">
            {{ record.imdbId }}
          </a-form-item>
        </h-col>
        <h-col :span="8">
          <a-form-item label="番组计划编号">
            {{ record.bgmId }}
          </a-form-item>
        </h-col>
      </a-row>
      <div v-if="isNotEmpty(record.threadList)" class="mt-5">
        <h-module-title title="其他发布"></h-module-title>
        <a-row class="m-3" :gutter="[16, 16]">
          <h-col v-for="item in record.threadList" :span="24">
            <a-tag :color="color[record.status]">{{ record.status }}</a-tag>
            {{ item.title }}
            <a v-if="item.url" :href="item.url" target="_blank">
              <link-outlined />
            </a>
            <a-space class="ml-3">
              <h-button
                small
                type="success"
                @click="onUpdateThreadStatus(item.id, 'like')"
                >like
              </h-button>
              <h-button
                small
                type="danger"
                @click="onUpdateThreadStatus(item.id, 'unlike')"
                >unlike
              </h-button>
              <h-button
                small
                type="primary"
                @click="onUpdateThreadStatus(item.id, 'achieve')"
                >achieve
              </h-button>
            </a-space>
          </h-col>
        </a-row>
      </div>
      <div v-if="record.movieBasicId" class="mt-5">
        <h-module-title clas="mt-10" title="电影信息"></h-module-title>
        <a-row :gutter="[16, 16]">
          <h-col :span="24">
            {{ record.movieBasicTitle }} / {{ record.movieBasicOriginalTitle }}
            <a-space class="ml-3">
              <h-button @click="onMovieFileManage" small>查看文件</h-button>
            </a-space>
          </h-col>
        </a-row>
      </div>
      <div v-if="record.comicSeriesId" class="mt-5">
        <h-module-title clas="mt-10" title="漫画信息"></h-module-title>
        <a-row :gutter="[16, 16]">
          <h-col :span="24">
            {{ record.comicSeriesTitle }} /
            {{ record.comicSeriesOriginalTitle }}
            <h-button @click="onComicFileManage" small>查看文件</h-button>
          </h-col>
        </a-row>
      </div>
      <a-space class="mt-5">
        <h-button type="success" @click="onUpdateThreadStatus(id, 'like')"
          >like
        </h-button>
        <h-button type="danger" @click="onUpdateThreadStatus(id, 'unlike')"
          >unlike
        </h-button>
        <h-button type="primary" @click="onUpdateThreadStatus(id, 'achieve')"
          >achieve
        </h-button>
        <h-button @click="router.back()">返回</h-button>
      </a-space>
    </div>
  </section>
  <movie-basic-file-manage ref="refMovieBasicFileManage" />
  <comic-series-file-manage ref="refComicSeriesFileManage" />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { LinkOutlined } from "@ant-design/icons-vue";
import { apiThreadUpdateStatus, apiThreadView } from "@/api/threadApi.ts";
import MovieBasicFileManage from "@/views/movie/movieBasic/movieBasicFileManage.vue";
import ComicSeriesFileManage from "@/views/comic/comicSeries/comicSeriesFileManage.vue";
import { isNotEmpty } from "@ht/util";
import { message } from "ant-design-vue";

const route = useRoute();
const router = useRouter();
const refMovieBasicFileManage = ref();
const refComicSeriesFileManage = ref();
const id = route.query.id;
const color = ref({
  like: "green",
  unlike: "red",
  achieve: "blue",
});

const record = ref({});
const getView = async () => {
  await apiThreadView({ id }).then((res) => {
    record.value = res;
  });
};

const onMovieFileManage = () => {
  refMovieBasicFileManage.value.show(record.value.movieBasicId);
};

const onComicFileManage = () => {
  refComicSeriesFileManage.value.show(record.value.comicSeriesId);
};

const onUpdateThreadStatus = (id, status) => {
  apiThreadUpdateStatus({ id, status }).then((res) => {
    if (res) {
      message.success("操作成功");
    }
  });
};

onMounted(() => {
  getView();
});
</script>

<style lang="less" scoped>
.view-box {
  display: flex;
  flex-wrap: wrap;
  gap: 20;
}

.view-item {
  width: 50%;
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  font-size: 14px;

  &-label {
    min-width: 90px;
  }

  &-value {
    flex: 1;
  }
}
</style>
