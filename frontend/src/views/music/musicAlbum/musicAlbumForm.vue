<!--
 * @Author: cyetstar
 * @Date: 2023-11-20 22:35:49
 * @Description: 专辑表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 4 }"
    width="600px"
    v-model:form="form"
    title="专辑"
    @submit="onSubmit"
  >
    <h-col :span="24">
      <h-input
        label="MusicBrainz编号"
        v-model:value="form.musicbrainzId"
        name="musicbrainzId"
      />
    </h-col>
    <h-col :span="24">
      <h-input label="Plex编号" v-model:value="form.plexId" name="plexId" />
    </h-col>
    <h-col :span="24">
      <h-input
        label="Plex缩略图"
        v-model:value="form.plexThumb"
        name="plexThumb"
      />
    </h-col>
    <h-col :span="24">
      <h-input
        label="网易云音乐编号"
        v-model:value="form.neteaseId"
        name="neteaseId"
      />
    </h-col>
    <h-col :span="24">
      <h-input label="标题" v-model:value="form.title" name="title" />
    </h-col>
    <h-col :span="24">
      <h-input label="艺术家" v-model:value="form.artists" name="artists" />
    </h-col>
    <h-col :span="24">
      <h-input label="简介" v-model:value="form.summary" name="summary" />
    </h-col>
    <h-col :span="24">
      <h-input label="专辑类型" v-model:value="form.type" name="type" />
    </h-col>
    <h-col :span="24">
      <h-input label="音乐流派" v-model:value="form.genre" name="genre" />
    </h-col>
    <h-col :span="24">
      <h-input
        label="发行国家"
        v-model:value="form.releaseCountry"
        name="releaseCountry"
      />
    </h-col>
    <h-col :span="24">
      <h-input label="日期" v-model:value="form.date" name="date" />
    </h-col>
    <h-col :span="24">
      <h-input label="唱片公司" v-model:value="form.label" name="label" />
    </h-col>
    <h-col :span="24">
      <h-input
        label="发行日期"
        v-model:value="form.releaseDate"
        name="releaseDate"
      />
    </h-col>
    <h-col :span="24">
      <h-select
        label="碟数"
        dict-type="totalDiscs"
        v-model:value="form.totalDiscs"
        name="totalDiscs"
      />
    </h-col>
    <h-col :span="24">
      <h-select
        label="音轨数"
        dict-type="totalTracks"
        v-model:value="form.totalTracks"
        name="totalTracks"
      />
    </h-col>
    <h-col :span="24">
      <h-input label="媒体" v-model:value="form.media" name="media" />
    </h-col>
    <h-col :span="24">
      <h-input label="文件路径" v-model:value="form.path" name="path" />
    </h-col>
    <h-col :span="24">
      <h-select-datetime
        label="创建时间"
        type="datetime"
        v-model:value="form.cjsj"
        name="cjsj"
      />
    </h-col>
    <h-col :span="24">
      <h-select-datetime
        label="修改时间"
        type="datetime"
        v-model:value="form.xgsj"
        name="xgsj"
      />
    </h-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import {
  apiMusicAlbumCreate,
  apiMusicAlbumUpdate,
  apiMusicAlbumView,
} from "@/api/music/musicAlbumApi";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let formRef = ref();

let form = ref({
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
  cjsj: "",
  xgsj: "",
});

const create = () => {
  formAction.value = "create";
  formRef.value.reset();
  formRef.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  form.value = await apiMusicAlbumView({ id });
  formRef.value.show();
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
