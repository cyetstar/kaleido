<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品详情页面
-->
<template>
  <section class="k-view-section">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template #backIcon>
        <LeftOutlined/>
      </template>
      <template #extra>
        <a-space>
          <h-button :disabled="!record.neteaseId" @click="onDownloadLyric"
          >下载歌词
          </h-button>
          <k-action-button
              action="musicSync"
              ok-text="同步Plex"
              cancel-text="取消同步"
              :form="searchForm"
          />
          <k-action-button
              action="musicMatchInfo"
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
            class="h-cover"
            type="MusicAlbum"
            style="width: 250px"
            :id="id"
        />
      </div>
      <div class="flex-1 ml-8">
        <h1>
          <a
              v-for="artist in record.artistList"
              @click="onViewArtist(artist.id)"
          >{{ artist.title }}</a
          >
        </h1>
        <p>{{ record.originallyAvailableAt }}</p>
        <p>{{ record.label }}</p>
        <p>
          <!--          <a-tag v-for="genre in record.genreList">{{ genre }}</a-tag>-->
          <!--          <a-tag v-for="style in record.styleList">{{ style }}</a-tag>-->
          <!--          <a-tag v-for="mood in record.moodList">{{ mood }}</a-tag>-->
          <a-tag v-if="record.genre">{{ record.genre }}</a-tag>
          <a-tag v-if="record.type">{{ record.type }}</a-tag>
          <a-tag v-if="record.media">{{ record.media }}</a-tag>
        </p>
        <p>
          <p v-for="(item, index) in record.summaryList" :key="index">
            {{ item }}
          </p>
        </p>
        <p>
          <k-logo-link class="mr-3" :id="id"/>
          <k-logo-link
              class="mr-3"
              type="musicbrianz"
              :id="record.musicbrainzId"
          />
          <k-logo-link class="mr-3" type="netease" :id="record.neteaseId"/>
        </p>
      </div>
    </section>
    <section>

      <template v-for="(discRecord, index) in discRecords">
        <h-module-title v-if="discRecords.length > 1" :title="`Disc${ index + 1 }`"/>
        <h-module-title v-else title="曲目"/>
        <a-table :pagination="false" size="small" :data-source="discRecord" class="mb-6">
          <a-table-column
              title="曲号"
              width="10%"
              data-index="trackIndex"
              align="center"
          ></a-table-column>
          <a-table-column
              title="歌名"
              width="75%"
              data-index="title"
          ></a-table-column>
          <a-table-column title="歌词" width="5%" align="center">
            <template #="{ record: trackRecord }">
              <file-text-outlined
                  @click="onViewLyric(trackRecord)"
              />
            </template>
          </a-table-column>
          <a-table-column
              title="曲长"
              width="10%"
              data-index="durationLabel"
              align="center"
          >
          </a-table-column>
        </a-table>
      </template>
    </section>
  </section>
  <a-modal v-model:visible="modalLyricsVisible" :footer="null">
    <template #title>“{{ modalLyricsTitle }}”歌词</template>
    <template v-for="lyrics in lyrics">
      <p>{{ lyrics }}</p>
    </template>
  </a-modal>
  <music-album-form ref="refMusicAlbumForm"/>
  <music-album-search-info ref="refMusicAlbumSearchInfo" @match-success="initAlbumData"/>
  <music-album-file-manage ref="refMusicAlbumFileManage"/>
  <music-album-search-lyric ref="refMusicAlbumSearchLyric" @match-success="initTrackData"/>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import {
  apiMusicAlbumDownloadLyric,
  apiMusicAlbumSyncPlexById,
  apiMusicAlbumUpdateAudioTag,
  apiMusicAlbumView,
} from "@/api/music/musicAlbumApi.ts";
import {
  apiMusicTrackViewLyric,
  apiMusicTrackListByAlbumId,
} from "@/api/music/musicTrackApi";
import {message} from "ant-design-vue";
import {FileTextOutlined, LeftOutlined, FileSearchOutlined} from "@ant-design/icons-vue";
import MusicAlbumSearchInfo from "@/views/music/musicAlbum/musicAlbumSearchInfo.vue";
import MusicAlbumFileManage from "@/views/music/musicAlbum/musicAlbumFileManage.vue";
import MusicAlbumSearchLyric from "@/views/music/musicAlbum/musicAlbumSearchLyric.vue";
import {isNotEmpty} from "@ht/util";
import MusicAlbumForm from "@/views/music/musicAlbum/musicAlbumForm.vue";

const route = useRoute();
const router = useRouter();

const id = route.query.id;
const record = ref({});
const discRecords = ref([]);
const modalLyricsVisible = ref(false);
const modalLyricsTitle = ref();
const lyrics = ref();
const searchForm = ref({
  id: id,
  albumId: id,
  force: true
})

const refMusicAlbumForm = ref();
const refMusicAlbumSearchInfo = ref();
const refMusicAlbumFileManage = ref();
const refMusicAlbumSearchLyric = ref();

const initData = () => {
  Promise.all([
    apiMusicAlbumView({id}),
    apiMusicTrackListByAlbumId({albumId: id})
  ]).then(([res1, res2]) => {
    record.value = res1;
    discRecords.value = res2;
  })
};

const onUpdate = () => {
  refMusicAlbumForm.value.update(id);
}

const onFileManage = () => {
  refMusicAlbumFileManage.value.show(id);
};

const onViewArtist = (id) => {
  router.push({path: "/music/musicArtist/view", query: {id}});
};

const onUpdateAudioTag = () => {
  apiMusicAlbumUpdateAudioTag({id}).then((res) => {
    if (res) {
      message.success("读取成功");
      initData();
    } else {
      message.error("读取失败");
    }
  });
};

const onSyncPlexById = () => {
  apiMusicAlbumSyncPlexById({id}).then((res) => {
    if (res) {
      message.success("同步成功");
      initData();
    } else {
      message.error("同步失败");
    }
  });
};

const onSearchInfo = () => {
  refMusicAlbumSearchInfo.value.show(record.value);
}

const onDownloadLyric = () => {
  apiMusicAlbumDownloadLyric({id: record.value.id}).then(() => {
    message.success("下载成功");
    initData();
  });
};

const onViewLyric = (record) => {
  apiMusicTrackViewLyric({id: record.id}).then((res) => {
    lyrics.value = res;
    modalLyricsTitle.value = record.title;
    modalLyricsVisible.value = true;
  });
};

const onSearchLyric = (record) => {
  refMusicAlbumSearchLyric.value.show(record)
}

onMounted(() => {
  initData();
});
</script>

<style lang="less" scoped></style>
