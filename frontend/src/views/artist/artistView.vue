<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 00:14:19
 * @Description: 艺术家详情页面
-->
<template>
  <section class="k-view-section">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template #backIcon>
        <LeftOutlined />
      </template>
      <template #extra>
        <a-space>
          <h-button @click="onSyncPlexById">同步Plex</h-button>
          <h-button @click="onSearchNetease">匹配网易云</h-button>
        </a-space>
      </template>
    </a-page-header>
    <section>
      <a-row>
        <h-col :span="6">
          <k-thumb-image
              :preview="false"
              style="width: 280px"
              :url="record.thumb"/>
        </h-col>
        <h-col :span="18">
          <p>{{ record.area }}</p>
          <p>{{ record.summary }}</p>
          <p>
            <k-logo-link type="plex" :id="record.id" class="mr-3" />
            <k-logo-link
              type="musicbrainz"
              :id="record.musicbrainzId"
              class="mr-3"
            />
            <k-logo-link type="netease" :id="record.neteaseId" class="mr-3" />
          </p>
        </h-col>
      </a-row>
    </section>

    <section>
      <h-module-title title="专辑" />
      <div class="grid grid-cols-24 gap-x-4 gap-y-4">
        <template :key="albumRecord.id" v-for="albumRecord in albumRecords">
          <a-card class="col-span-3 k-card" :bordered="false">
            <template #cover>
              <k-thumb-image
                class="h-cover cursor-pointer"
                type="MusicAlbum"
                :preview="false"
                :id="albumRecord.id"
                @click="onViewAlbumRecord(albumRecord.id)"
              />
              <div class="absolute top-0 left-0 m-2px">
                <k-logo-link
                  type="plex"
                  :id="albumRecord.id"
                  :width="20"
                  class="mr-1"
                />
                <k-logo-link
                  type="netease"
                  :id="albumRecord.neteaseId"
                  :width="20"
                />
              </div>
            </template>
            <a-card-meta
              :title="albumRecord.title"
              :description="albumRecord.year"
            >
            </a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>

<!--  <artist-search-netease-->
<!--    ref="refArtistSearchNetease"-->
<!--    @match-success="onMatchSuccess"-->
<!--  />-->
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { message } from "ant-design-vue";
import { LeftOutlined } from "@ant-design/icons-vue";
import {
  apiArtistSyncPlexById,
  apiArtistView,
} from "@/api/artistApi.ts";
import { apiMusicAlbumListByArtistId } from "@/api/music/musicAlbumApi";

const route = useRoute();
const router = useRouter();
const id = route.query.id;

const record = ref({});
const albumRecords = ref([]);
const refArtistSearchNetease = ref();
const initData = () => {
  apiArtistView({ id }).then((res) => (record.value = res));
  apiMusicAlbumListByArtistId({ artistId: id }).then(
    (res) => (albumRecords.value = res)
  );
};

const onViewAlbumRecord = (id) => {
  router.push({ path: "/music/musicAlbum/view", query: { id } });
};

const onSyncPlexById = () => {
  apiArtistSyncPlexById({ id }).then(() => {
    message.success("同步成功");
  });
};

const onSearchNetease = () => {
  refArtistSearchNetease.value.show(record.value);
};

const onMatchSuccess = ({ neteaseId }) => {
  record.value.neteaseId = neteaseId;
};

onMounted(() => {
  initData();
});
</script>

<style lang="less" scoped></style>
