<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 15:19:06
 * @Description: 系统配置表表单页面
-->
<template>
  <section class="h-page-section" ref="appManagePage">
    <a-form ref="refForm" :label-col="{ span: 9 }" v-model:form="form">
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="third" tab="第三方">
          <h-col :span="12">
            <h-input
              label="Plex访问地址"
              name="plexUrl"
              v-model:value="form.plexUrl"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="Plex Token"
              name="plexToken"
              v-model:value="form.plexToken"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="Komga地址"
              name="komgaUrl"
              v-model:value="form.komgaUrl"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="Komga账号"
              name="komgaUsername"
              v-model:value="form.komgaUsername"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="Komga密码"
              name="komgaPassword"
              v-model:value="form.komgaPassword"
            />
          </h-col>

          <h-col :span="12">
            <h-input
              label="TMM地址"
              name="tmmUrl"
              v-model:value="form.tmmUrl"
            />
          </h-col>
          <h-col :span="12">
            <h-select
              label="Plex电影资料库"
              :columns="columns"
              name="plexMovieLibraryId"
              @change="onChange($event, 'movie')"
              v-model:value="form.plexMovieLibraryId"
            />
          </h-col>
          <h-col :span="12">
            <h-select
              label="Plex剧集资料库"
              :columns="columns"
              name="plexTvshowLibraryId"
              @change="onChange($event, 'tvshow')"
              v-model:value="form.plexTvshowLibraryId"
            />
          </h-col>
          <h-col :span="12">
            <h-select
              label="Plex音乐资料库"
              :columns="columns"
              name="plexMusicLibraryId"
              @change="onChange($event, 'music')"
              v-model:value="form.plexMusicLibraryId"
            />
          </h-col>
          <h-col :span="12">
            <h-select
              label="Komga漫画资料库"
              :columns="columns"
              name="komgaComicLibraryId"
              @change="onChange($event, 'comic')"
              v-model:value="form.komgaComicLibraryId"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="dir" tab="目录">
          <h-col :span="12">
            <h-input
              label="电影资料库目录"
              name="movieLibraryPath"
              v-model:value="form.movieLibraryPath"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="剧集资料库目录"
              name="tvshowLibraryPath"
              v-model:value="form.tvshowLibraryPath"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="音乐资料库目录"
              name="musicLibraryPath"
              v-model:value="form.musicLibraryPath"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="漫画资料库目录"
              name="comicLibraryPath"
              v-model:value="form.comicLibraryPath"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="other" tab="其他">
          <h-col :span="12">
            <h-input
              label="Plex重试次数"
              name="plexRetries"
              v-model:value="form.plexRetries"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="单次抓取信息延时（秒）"
              name="matchInfoSleepSecond"
              v-model:value="form.matchInfoSleepSecond"
            />
          </h-col>
          <h-col :span="12">
            <h-input
              label="单次下载歌词延时（秒）"
              name="downloadLyricSleepSecond"
              v-model:value="form.downloadLyricSleepSecond"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否定时同步电影"
              name="syncMovie"
              v-model:value="form.syncMovie"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否定时同步剧集"
              name="syncTvshow"
              v-model:value="form.syncTvshow"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否定时同步音乐"
              name="syncMusic"
              v-model:value="form.syncMusic"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否定时同步漫画"
              name="syncMusic"
              v-model:value="form.syncComic"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否自动刷新Plex"
              name="refreshMetadata"
              v-model:value="form.refreshMetadata"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否重写电影NFO文件"
              name="writeMovieNFO"
              v-model:value="form.writeMovieNFO"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否重写剧集NFO文件"
              name="writeTvshowNFO"
              v-model:value="form.writeTvshowNFO"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否重写音频文件标签"
              name="writeAudioTag"
              v-model:value="form.writeAudioTag"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <h-radio
              label="是否重写ComicInfo文件"
              name="writeComicInfo"
              v-model:value="form.writeComicInfo"
              dict-type="sfbz"
            />
          </h-col>
          <h-col :span="12">
            <a-form-item label="重置电影分析进程">
              {{ form.lastMovieAnalyzeTime }}
              <h-button small @click="onSave('lastMovieAnalyzeTime', '0')"
                >重置
              </h-button>
            </a-form-item>
          </h-col>
          <h-col :span="12">
            <a-form-item label="重置电影抓取进程">
              {{ form.lastMovieMatchInfo }}
              <h-button small @click="onSave('lastMovieMatchInfo', '0')"
                >重置
              </h-button>
            </a-form-item>
          </h-col>
          <h-col :span="12">
            <a-form-item label="重置剧集抓取进程">
              {{ form.lastTvshowMatchInfo }}
              <h-button small @click="onSave('lastTvshowMatchInfo', '0')"
                >重置
              </h-button>
            </a-form-item>
          </h-col>
          <h-col :span="12">
            <a-form-item label="重置音乐抓取进程">
              {{ form.lastMusicMatchInfo }}
              <h-button small @click="onSave('lastMusicMatchInfo', '0')"
                >重置
              </h-button>
            </a-form-item>
          </h-col>
          <h-col :span="12">
            <a-form-item label="重置漫画抓取进程">
              {{ form.lastComicMatchInfo }}
              <h-button small @click="onSave('lastComicMatchInfo', '0')"
                >重置
              </h-button>
            </a-form-item>
          </h-col>
          <h-col :span="12">
            <a-form-item label="优化数据库">
              <k-action-button
                action="tableOptimize"
                ok-text="执行"
                cancel-text="取消"
              />
            </a-form-item>
          </h-col>
        </a-tab-pane>
      </a-tabs>
      <h-col :span="12" :offset="3" class="mt-3">
        <h-button type="primary" @click="onSave">保存</h-button>
      </h-col>
    </a-form>
  </section>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { message } from "ant-design-vue";
import { apiPlexListLibrary } from "@/api/plexApi";
import {
  apiSysConfigFindByKeys,
  apiSysConfigSave,
} from "@/api/sysadmin/sysConfigApi";
import { useAppStore } from "@/store/modules/app";
import { apiKomgaListLibrary } from "@/api/komgaApi";
import KActionButton from "@c/ActionButton/ActionButton.vue";

let appStore = useAppStore();
let activeKey = ref();
let columns = ref([]);

let form = ref({
  movieLibraryPath: "",
  tvshowLibraryPath: "",
  musicLibraryPath: "",
  comicLibraryPath: "",

  plexUrl: "",
  plexToken: "",
  komgaUrl: "",
  komgaUsername: "",
  komgaPassword: "",
  tmmUrl: "",
  plexMovieLibraryId: "",
  plexMovieLibraryPath: "",
  plexTvshowLibraryId: "",
  plexTvshowLibraryPath: "",
  plexMusicLibraryId: "",
  plexMusicLibraryPath: "",
  komgaComicLibraryId: "",
  komgaComicLibraryPath: "",

  plexRetries: "",
  matchInfoSleepSecond: "",
  downloadLyricSleepSecond: "",

  lastMovieAnalyzeTime: "",
  lastMovieMatchInfo: "",
  lastTvshowMatchInfo: "",
  lastMusicMatchInfo: "",
  lastComicMatchInfo: "",

  writeComicInfo: "",
  writeMovieNFO: "",
  writeAudioTag: "",
  writeTvshowNFO: "",
  refreshMetadata: "",
  syncMovie: "",
  syncTvshow: "",
  syncMusic: "",
  syncComic: "",
  checkThreadStatus: "",
  checkMovieStatus: "",
  analyzeMovie: "",

  videoExtension: "",
  comicZipExtension: "",
  audioExtension: "",
});

onMounted(() => {
  initData();
});

const initData = () => {
  apiSysConfigFindByKeys(Object.keys(form.value)).then((res) => {
    form.value = res;
    columns.value = [];
    Promise.all([apiPlexListLibrary(), apiKomgaListLibrary()]).then(
      ([res1, res2]) => {
        res1.forEach((s) => {
          columns.value.push({ text: s.name, value: s.key, ...s });
        });
        res2.forEach((s) => {
          columns.value.push({ text: s.name, value: s.key, ...s });
        });
      }
    );
  });
};

const onChange = (e, type) => {
  if (type === "movie") {
    form.value.plexMovieLibraryPath = e.path;
  } else if (type === "tvshow") {
    form.value.plexTvshowLibraryPath = e.path;
  } else if (type === "music") {
    form.value.plexMusicLibraryPath = e.path;
  } else if (type === "comic") {
    form.value.komgaComicLibraryPath = e.path;
  }
};

const onSave = (key, value) => {
  let data = form.value;
  if (key && value) {
    data = { [key]: value };
  }
  apiSysConfigSave(data).then((res) => {
    if (res) {
      Promise.all([apiPlexListLibrary(), apiKomgaListLibrary()]).then(
        ([res1, res2]) => {
          columns.value = [];
          res1.forEach((s) => {
            columns.value.push({ text: s.name, value: s.key, ...s });
          });
          res2.forEach((s) => {
            columns.value.push({ text: s.name, value: s.key, ...s });
          });
        }
      );
      appStore.initAppConfig();
      message.success("操作成功");
    }
  });
};
</script>

<style lang="less" scoped></style>
