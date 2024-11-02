<!--
 * @Author: cyetstar
 * @Date: 2023-11-26 01:20:15
 * @Description: 电影详情页面
-->
<template>
  <section class="k-view-section">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template #backIcon>
        <LeftOutlined/>
      </template>
      <template #extra>
        <a-space>
          <k-action-button
              action="movieAnalyze"
              ok-text="分析文件"
              cancel-text="取消分析"
              :form="searchForm"
          />
          <k-action-button
              action="movieSync"
              ok-text="同步Plex"
              cancel-text="取消同步"
              :form="searchForm"
          />
          <k-action-button
              action="movieMatchInfo"
              ok-text="自动抓取"
              cancel-text="取消抓取"
              :form="searchForm"
          />
          <h-button @click="onSearchInfo">匹配抓取</h-button>
          <h-button @click="onUpdate">编辑</h-button>
          <h-button @click="onFileManage">文件管理</h-button>
        </a-space>
      </template>
    </a-page-header>
    <section class="flex">
      <div>
        <k-thumb-image
            v-if="record.id"
            class="h-poster"
            type="MovieBasic"
            style="width: 250px"
            :id="id"
        />
      </div>
      <div class="flex-1 ml-8">
        <p>{{ record.originalTitle }} <span v-if="isNotEmpty(record.year)">({{ record.year }})</span></p>
        <p class="flex items-center">
          <span v-if="isNotEmpty(record.rating)" class="mr-2">{{ record.rating }} 分</span>
          <a-rate v-model:value="rating" disabled allow-half/>
        </p>
        <p>
          <a-tag v-if="record.contentRating">{{ record.contentRating }}</a-tag>
          <a-tag v-if="record.multipleFiles==='1'">多文件</a-tag>
          <a-tag v-if="record.noSubtitle==='1'">无字幕</a-tag>
          <a-tag v-if="record.lowQuality==='1'">低质量</a-tag>
          <a-tag v-if="record.mandarin==='1'">国语配音</a-tag>
          <a-tag v-for="item in record.countryList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.genreList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.languageList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.tagList" :key="item">{{ item }}</a-tag>
        </p>
        <p>
          <p v-for="(item, index) in record.summaryList" :key="index">
            {{ item }}
          </p>
        </p>
        <p class="flex" v-if="isNotEmpty(record.directorList)">
          <span class="mr-2">导演:</span>
          <span class="flex-1">
            <template v-for="(item, index) in record.directorList">
              <span v-if="index !== 0" class="px-2">/</span>
              <a @click="onViewArtist(item)">{{ item.name }}</a>
            </template>
          </span>
        </p>
        <p class="flex" v-if="isNotEmpty(record.writerList)">
          <span class="mr-2">编剧:</span>
          <span class="flex-1">
            <template v-for="(item, index) in record.writerList">
              <span v-if="index !== 0" class="px-2">/</span>
              <a @click="onViewArtist(item)">{{ item.name }}</a>
            </template>
          </span>
        </p>
        <p class="flex" v-if="isNotEmpty(record.akaList)">
          <span class="mr-2">又名:</span>
          <span class="flex-1">
            <template v-for="(item, index) in record.akaList">
              <span v-if="index !== 0" class="px-2">/</span>
              {{ item }}
            </template>
          </span>
        </p>
        <p class="flex">
          <span class="mr-2">文件名:</span>
          <span class="flex-1">
            {{ record.filename }}
          </span>
        </p>
        <p class="flex">
          <span class="mr-2">更新于:</span>
          <span class="flex-1">
            {{ formatUnixTimestamp(record.updatedAt) }}
          </span>
        </p>
        <p>
          <k-logo-link type="plex" :id="record.id" class="mr-3"/>
          <k-logo-link type="imdb" :id="record.imdbId" class="mr-3"/>
          <k-logo-link type="tmdb" :id="record.tmdbId" class="mr-3"/>
          <k-logo-link type="douban" :id="record.doubanId" class="mr-3"/>
        </p>
      </div>
    </section>
    <section v-if="isNotEmpty(actorList)">
      <h-module-title title="主演"/>
      <div class="grid grid-cols-24 gap-6">
        <template :key="item.id" v-for="item in actorList">
          <a-card class="k-card col-span-3" :bordered="false">
            <template #cover>
              <k-thumb-image
                  class="h-thumb"
                  :preview="false"
                  :url="item.thumb"
                  @click="onViewArtist(item)"
              />
            </template>
            <a-card-meta
                :title="item.name"
                :description="item.playRole"
            ></a-card-meta>
          </a-card>
        </template>
      </div>
    </section>

    <section v-if="isNotEmpty(collectionRecordList)">
      <h-module-title title="合集"/>
      <div class="grid grid-cols-24 gap-6">
        <template :key="item.id" v-for="item in collectionRecordList">
          <a-card class="k-card col-span-4" :bordered="false">
            <template #cover>
              <k-plex-image
                  class="cursor-pointer"
                  :preview="false"
                  :plex-thumb="item.thumb"
                  @click="onViewCollection(item.id)"
              />
            </template>
            <a-card-meta
                :title="item.title"
            ></a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>
  <movie-basic-form ref="refMovieBasicForm" @save-complete="initData"/>
  <movie-basic-search-info ref="refMovieBasicSearchInfo" @match-success="initData"/>
  <movie-basic-file-manage ref="refMovieBasicFileManage"/>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {
  apiMovieBasicView,
} from "@/api/movie/movieBasicApi";
import {apiMovieCollectionListByMovieId} from "@/api/movie/movieCollectionApi";
import {LeftOutlined} from "@ant-design/icons-vue";
import {isNotEmpty} from "@ht/util";
import MovieBasicSearchInfo from "@/views/movie/movieBasic/movieBasicSearchInfo.vue";
import MovieBasicFileManage from "@/views/movie/movieBasic/movieBasicFileManage.vue";
import MovieBasicForm from "@/views/movie/movieBasic/movieBasicForm.vue";
import {formatUnixTimestamp} from "@/utils/utils";

const router = useRouter();
const record = ref({});
const collectionRecordList = ref([]);
const refMovieBasicSearchInfo = ref();
const refMovieBasicFileManage = ref();
const refMovieBasicForm = ref();
const route = useRoute();
const id = route.query.id;
const rating = computed(() => record.value.rating / 2);
const actorList = computed(() => {
  if (isNotEmpty(record.value.actorList) && record.value.actorList.length > 8) {
    return record.value.actorList.slice(0, 8)
  }
  return record.value.actorList;
});

const searchForm = ref({
  id: id,
  force: true
})


const initData = async () => {
  apiMovieBasicView({id}).then((res) => {
    res.tagList = res.tagList.filter(s =>
        // 过滤已经存在的tag记录
        !res.countryList.some(item => item.idLabel === s)
        && !res.languageList.some(item => item.idLabel === s)
        && !res.genreList.some(item => item.idLabel === s)
    );
    record.value = res
  });
  apiMovieCollectionListByMovieId({movieId: id}).then(res => {
    collectionRecordList.value = res.splice(0, 8)
  })
};

const onFileManage = () => {
  refMovieBasicFileManage.value.show(id);
};


const onSearchInfo = () => {
  refMovieBasicSearchInfo.value.show(record.value);
}

const onViewArtist = (actor) => {
  router.push({name: "movieBasicPage", params: {actorId: actor.id, actorName: actor.name, load: true}});
}

const onViewCollection = (id) => {
  router.push({path: "/movie/movieCollection/view", query: {id}});
}

const onUpdate = () => {
  refMovieBasicForm.value.update(id);
}


onMounted(() => {
  initData();
});
</script>

<style lang="less" scoped></style>
