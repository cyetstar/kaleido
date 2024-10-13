<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    :title="`匹配【${title}】信息`"
    width="960px"
  >
    <template v-if="viewMatchInfo">
      <a-table
        size="small"
        :data-source="matchInfoDataSource"
        :pagination="false"
        :loading="loading"
        class="mt-3"
      >
        <a-table-column
          title="曲号"
          align="center"
          data-index="trackIndex"
          width="10%"
        />
        <a-table-column title="歌名" data-index="title" width="70%" />
        <a-table-column title="曲长" data-index="durationLabel" width="10%" />
        <a-table-column title="匹配度" width="10%">
          <template #="{ record }">
            <div class="flex">
              <a-popover placement="top">
                <template #content>
                  <div>{{ record.title }}</div>
                  <div>{{ record.songTitle }}</div>
                </template>
                <div
                  :class="[
                    'k-block',
                    compareTitles(record.title, record.songTitle),
                  ]"
                ></div>
              </a-popover>
              <a-popover placement="top">
                <template #content>
                  <div>{{ record.artist }}</div>
                  <div>{{ record.songArtist }}</div>
                </template>
                <div
                  :class="[
                    'k-block',
                    compareTitles(record.artist, record.songArtist),
                  ]"
                ></div>
              </a-popover>
              <a-popover placement="top">
                <template #content>
                  <div>{{ record.durationLabel }}</div>
                  <div>{{ record.songDurationLabel }}</div>
                </template>
                <div
                  :class="[
                    'k-block',
                    compareNumbers(record.duration, record.songDuration),
                  ]"
                ></div>
              </a-popover>
              <a-popover placement="top">
                <template #content>
                  <div>{{ record.publishTime }}</div>
                  <div>{{ record.songPublishTime }}</div>
                </template>
                <div
                  :class="[
                    'k-block',
                    compareTitles(record.publishTime, record.songPublishTime),
                  ]"
                ></div>
              </a-popover>
            </div>
          </template>
        </a-table-column>
      </a-table>
    </template>
    <template v-else>
      <div class="flex gap-2">
        <h-radio
          v-model:value="searchForm.source"
          button
          name="source"
          :columns="sourceColumns"
        />
        <h-input
          class="flex-1"
          placeholder=""
          v-model:value="searchForm.keyword"
          name="keyword"
          @keyup.enter="onSearch"
        />
        <h-button @click="onSearch">搜索</h-button>
        <h-button @click="onMatch">无法匹配</h-button>
      </div>

      <a-table
        size="small"
        :data-source="dataSource"
        :loading="loading"
        :row-class-name="addRowColor"
        class="mt-3"
      >
        <a-table-column title="封面" align="center" :width="150">
          <template #="{ record }">
            <a
              :href="`https://music.163.com/#/album?id=${record.neteaseId}`"
              target="_blank"
              class="flex justify-center"
            >
              <img
                :src="record.picUrl"
                :width="100"
                referrerpolicy="no-referrer"
              />
            </a>
          </template>
        </a-table-column>
        <a-table-column title="专辑名">
          <template #="{ record }">
            <p>
              {{ record.title }}
            </p>
            <p class="text-muted">
              {{ record.artist }}
            </p>
            <div class="text-muted">{{ record.publishTime }}</div>
          </template>
        </a-table-column>
        <a-table-column title="操作" align="center" width="150px">
          <template #="{ record }">
            <a-space :size="0">
              <h-button
                type="primary"
                size="small"
                link
                @click="onViewMatchInfo(record)"
                >匹配信息
              </h-button>
              <h-button
                v-if="searchForm.matchType !== 'path'"
                type="primary"
                size="small"
                link
                @click="onDownloadCover(record)"
                >下载封面
              </h-button>
            </a-space>
          </template>
        </a-table-column>
      </a-table>
    </template>
    <template #footer>
      <template v-if="viewMatchInfo">
        <h-button @click="viewMatchInfo = false">返回</h-button>
        <h-button @click="onMatch(infoRecord)">确定匹配</h-button>
      </template>
      <template v-else>
        <h-button @click="visible = false">关闭</h-button>
      </template>
    </template>
  </a-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import {
  apiMusicAlbumSearchInfo,
  apiMusicAlbumMatchInfo,
  apiMusicAlbumDownloadCover,
  apiMusicAlbumViewMatchInfo,
} from "@/api/music/musicAlbumApi.ts";

const emits = defineEmits(["match-success"]);
const loading = ref();
const title = ref();
const visible = ref();
const viewMatchInfo = ref(false);
const dataSource = ref([]);
const matchInfoDataSource = ref([]);
const infoRecord = ref({});
let record = {};
const searchForm = ref({
  source: "netease",
  keyword: "",
  matchType: "path",
});
const sourceColumns = [
  {
    text: "MusicBrainz",
    value: "musicbrainz",
  },
  {
    text: "网易",
    value: "netease",
  },
];

const show = (data, dataType) => {
  dataSource.value = [];
  viewMatchInfo.value = false;
  record = data;
  searchForm.value.matchType = dataType;
  if (dataType === "path") {
    title.value = record.name;
    searchForm.value.keyword = record.name.replaceAll("\.", " ");
  } else {
    title.value = record.title + " " + record.artists;
    searchForm.value.keyword = record.title;
  }
  visible.value = true;
};

const onSearch = () => {
  loading.value = true;
  apiMusicAlbumSearchInfo(searchForm.value).then((res) => {
    dataSource.value = res;
    loading.value = false;
  });
};

const onViewMatchInfo = (data) => {
  infoRecord.value = data;
  apiMusicAlbumViewMatchInfo({ ...record, ...data }).then((res) => {
    matchInfoDataSource.value = res;
    viewMatchInfo.value = true;
  });
};

const onMatch = (data) => {
  apiMusicAlbumMatchInfo({ ...record, ...data, ...searchForm.value }).then(
    () => {
      message.success("匹配成功");
      visible.value = false;
      emits("match-success");
    }
  );
};

const onDownloadCover = (source) => {
  loading.value = true;
  apiMusicAlbumDownloadCover({ id: record.id, url: source.picUrl }).then(
    (res) => {
      message.success("下载成功");
      visible.value = false;
      loading.value = false;
    }
  );
};

const addRowColor = (source) => {
  if (source && source.neteaseId === record.neteaseId) {
    return "bg-highlight";
  }
};

const compareNumbers = (num1, num2) => {
  if (num1 === num2) {
    return "high";
  }
  // 计算两个数字的差异百分比
  const difference = Math.abs(num1 - num2) / Math.max(num1, num2);
  // 如果差异小于等于 1%，返回相似，否则返回不同
  if (difference <= 0.01) {
    return "medium";
  } else {
    return "low";
  }
};

const compareTitles = (title1, title2) => {
  // 移除标点符号并转换为小写
  function normalizeTitle(title) {
    return title
      .toLowerCase() // 转换为小写
      .replace(/[.,/#!$%^&*;:{}=_`~\]\[\-()]/g, "") // 去除标点符号
      .replace(/\s{2,}/g, " ") // 替换多余的空格
      .trim(); // 去除首尾空格
  }

  // 如果两个字符串完全一致
  if (title1 === title2) {
    return "high";
  }
  // 标准化标题
  const normalizedTitle1 = normalizeTitle(title1);
  const normalizedTitle2 = normalizeTitle(title2);
  // 如果标准化后的标题一致
  if (normalizedTitle1 === normalizedTitle2) {
    return "medium";
  }
  // 如果不一致
  return "low";
};

defineExpose({
  show,
  addRowColor,
  compareTitles,
});
</script>

<style lang="less" scoped>
.k-block {
  width: 10px;
  height: 10px;
  margin: 0 2px;

  &.low {
    background: #ff4d4f;
  }

  &.medium {
    background: #ffb152;
  }

  &.high {
    background: #3eb660;
  }
}
</style>
