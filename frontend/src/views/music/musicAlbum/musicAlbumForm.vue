<!--
 * @Author: cyetstar
 * @Date: 2023-11-20 22:35:49
 * @Description: 专辑表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    mask
    :label-col="{ span: 4 }"
    width="1000px"
    v-model:form="form"
    title="专辑"
    @submit="onSubmit"
  >
    <a-col :span="24">
      <a-tabs v-model:activeKey="activeKey" tab-position="left">
        <a-tab-pane key="basic" tab="基本">
          <h-col :span="24">
            <h-input label="标题" v-model:value="form.title" name="title" />
          </h-col>
          <h-col :span="24">
            <h-input
              text-area
              rows="5"
              label="简介"
              v-model:value="form.summary"
              name="summary"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="发行日期"
              v-model:value="form.originallyAvailableAt"
              name="originallyAvailableAt"
            />
          </h-col>
          <h-col :span="24">
            <k-select-artist
              v-model:value="form.artistList"
              mode="multiple"
              name="artistList"
              label="艺术家"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="碟数"
              v-model:value="form.totalDiscs"
              name="totalDiscs"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="音轨数"
              v-model:value="form.totalTracks"
              name="totalTracks"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="MusicBrainz编号"
              v-model:value="form.musicbrainzId"
              name="musicbrainzId"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="网易云音乐编号"
              v-model:value="form.neteaseId"
              name="neteaseId"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="tags" tab="标签">
          <h-col :span="24">
            <h-input
              label="发行国家"
              v-model:value="form.releaseCountry"
              name="releaseCountry"
            />
          </h-col>
          <h-col :span="24">
            <h-input label="唱片公司" v-model:value="form.label" name="label" />
          </h-col>
          <h-col :span="24">
            <h-input label="专辑类型" v-model:value="form.type" name="type" />
          </h-col>
          <h-col :span="24">
            <h-input label="音乐流派" v-model:value="form.genre" name="genre" />
          </h-col>
          <h-col :span="24">
            <h-input label="媒体" v-model:value="form.media" name="media" />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="track" tab="曲目">
          <template v-for="(track, index) in form.trackList">
            <a-row :gutter="6">
              <h-col :span="2">
                <h-input
                  :allow-clear="false"
                  v-model:value="form.trackList[index].discIndex"
                  :name="`trackList[${{ index }}].discIndex`"
                />
              </h-col>
              <h-col :span="2">
                <h-input
                  :allow-clear="false"
                  v-model:value="form.trackList[index].trackIndex"
                  :name="`trackList[${{ index }}].trackIndex`"
                />
              </h-col>
              <h-col :span="10">
                <h-input
                  v-model:value="form.trackList[index].title"
                  :name="`trackList[${{ index }}].title`"
                />
              </h-col>
              <h-col :span="8">
                <k-select-artist
                  mode="multiple"
                  v-model:value="form.trackList[index].artistList"
                  :name="`trackList[${{ index }}].artistList`"
                />
              </h-col>
              <h-col :span="2">
                <a-button @click="onCopy(index)">
                  <format-painter-outlined />
                </a-button>
              </h-col>
            </a-row>
          </template>
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { FormatPainterOutlined } from "@ant-design/icons-vue";
import {
  apiMusicAlbumCreate,
  apiMusicAlbumUpdate,
  apiMusicAlbumView,
} from "@/api/music/musicAlbumApi";
import { apiMusicTrackListByAlbumId } from "@/api/music/musicTrackApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");
const formRef = ref();
const activeKey = ref("basic");
const form = ref({
  id: "",
  musicbrainzId: "",
  plexId: "",
  plexThumb: "",
  neteaseId: "",
  title: "",
  artists: "",
  summary: "",
  type: "",
  genre: "",
  releaseCountry: "",
  date: "",
  label: "",
  releaseDate: "",
  totalDiscs: "",
  totalTracks: "",
  media: "",
  path: "",
  trackList: [],
});

const create = () => {
  formAction.value = "create";
  formRef.value.reset();
  formRef.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  Promise.all([
    apiMusicAlbumView({ id }),
    apiMusicTrackListByAlbumId({ albumId: id }),
  ]).then(([res1, res2]) => {
    form.value = res1;
    if (res1.artistList.length > 0) {
      form.value.artistList = res1.artistList.map((s) => s.id);
    }
    form.value.trackList = res2.flat().map((s) => {
      if (s.artistList.length > 0) {
        s.artistList = s.artistList.map((a) => a.id);
      }
      return s;
    });
    formRef.value.show();
  });
};

const onCopy = (i) => {
  form.value.trackList[i].artistList = form.value.artistList;
};

const onSubmit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await apiMusicAlbumCreate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await apiMusicAlbumUpdate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        formRef.value.hide();
      }
    }
  } catch (e) {}
};

defineExpose({
  create,
  update,
});
</script>

<style lang="less" scoped></style>
