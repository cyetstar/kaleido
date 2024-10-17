<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 剧集详情页面
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
              action="tvshowSync"
              ok-text="同步Plex"
              cancel-text="取消同步"
              :form="searchForm"
          />
          <k-action-button
              action="tvshowMatchInfo"
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
            type="TvshowSeason"
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
          <a-tag v-for="item in record.countryList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.genreList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.languageList" :key="item">{{ item }}</a-tag>
          <a-tag v-for="item in record.tagList" :key="item">{{ item }}</a-tag>
        </p>
        <p v-if="isNotEmpty(record.summaryList)">
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
        <p class="flex" v-if="isNotEmpty(record.studio)">
          <span class="mr-2">制片方:</span>
          <span class="flex-1">{{ record.studio }}</span>
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
          <k-logo-link type="tmdbtv" :id="record.tmdbId" class="mr-3"/>
          <k-logo-link type="douban" :id="record.doubanId" class="mr-3"/>
        </p>
      </div>
    </section>
    <section v-if="isNotEmpty(record.actorList)">
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
    <section v-if="isNotEmpty(episodeRecords)">
      <h-module-title title="单集"/>
      <div class="flex mb-6" v-for="episodeRecord in episodeRecords" :key="episodeRecord.id">
        <div>
          <k-thumb-image
              style="object-fit: cover; aspect-ratio: 16 / 9; width: 200px"
              type="TvshowEpisode"
              :id="episodeRecord.id"
          />
        </div>
        <div class="flex-1 ml-6">
          <p>第 {{ episodeRecord.episodeIndex }} 集 {{ episodeRecord.title }}</p>
          <p>{{ episodeRecord.summary }}</p>
        </div>
      </div>
    </section>
  </section>
  <tvshow-show-search-info ref="refTvshowShowSearchInfo"/>
  <tvshow-season-file-manage ref="refTvshowSeasonFileManage"/>
  <tvshow-season-form ref="refTvshowSeasonForm"/>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {FileTextOutlined, LeftOutlined} from "@ant-design/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {isNotEmpty} from "@ht/util";
import {apiTvshowEpisodePage} from "@/api/tvshow/tvshowEpisodeApi";
import {apiTvshowSeasonView} from "@/api/tvshow/tvshowSeasonApi";
import TvshowSeasonFileManage from "@/views/tvshow/tvshowSeason/tvshowSeasonFileManage.vue";
import TvshowSeasonForm from "@/views/tvshow/tvshowSeason/tvshowSeasonForm.vue";
import TvshowShowSearchInfo from "@/views/tvshow/tvshowShow/tvshowShowSearchInfo.vue";
import {formatUnixTimestamp} from "@/utils/utils";

const route = useRoute()
const router = useRouter()
const record = ref({})
const episodeRecords = ref([]);
const refTvshowShowSearchInfo = ref();
const refTvshowSeasonForm = ref();
const refTvshowSeasonFileManage = ref();
const id = route.query.id;
const searchForm = ref({
  seasonId: id,
  force: true
})
const title = route.query.title
const rating = computed(() => record.value.rating / 2);
const actorList = computed(() => {
  if (isNotEmpty(record.value.actorList) && record.value.actorList.length > 8) {
    return record.value.actorList.slice(0, 8)
  }
  return record.value.actorList;
});

const initData = async () => {
  apiTvshowSeasonView({id}).then((res) => {
    record.value = res
  });
  apiTvshowEpisodePage({pageSize: 1000, seasonId: id, orderBy: "ASC:episode_index",}).then(({records}) => {
    episodeRecords.value = records
  })
};

const onSearchInfo = () => {
  refTvshowShowSearchInfo.value.show(record.value)
}
const onUpdate = () => {
  refTvshowSeasonForm.value.update(id);
}
const onFileManage = () => {
  refTvshowSeasonFileManage.value.show(id);
};

onMounted(() => {
  initData()
})
</script>