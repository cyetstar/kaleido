<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 剧集表单页面
-->
<template>
  <h-form-modal
    ref="refForm"
    mask
    :label-col="{ span: 4 }"
    width="1000px"
    v-model:form="form"
    title="剧集"
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
              label="原标题"
              v-model:value="form.originalTitle"
              name="originalTitle"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="排序名"
              v-model:value="form.sortTitle"
              name="sortTitle"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="简介"
              text-area
              rows="4"
              v-model:value="form.summary"
              name="summary"
            />
          </h-col>
          <h-col :span="24">
            <h-input label="年份" v-model:value="form.year" name="year" />
          </h-col>
          <h-col :span="24">
            <h-input label="评分" v-model:value="form.rating" name="rating" />
          </h-col>
          <h-col :span="24">
            <h-select
              label="评级"
              v-model:value="form.contentRating"
              dict-type="tvshowContentRating"
              name="contentRating"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="IMDb编号"
              v-model:value="form.imdbId"
              name="imdbId"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="豆瓣编号"
              v-model:value="form.doubanId"
              name="doubanId"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="TMDB编号"
              v-model:value="form.tmdbId"
              name="tmdbId"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="tags" tab="标签">
          <h-col :span="24">
            <h-select
              mode="multiple"
              label="国家地区"
              v-model:value="form.countryList"
              dictType="Country"
              name="countryList"
            />
          </h-col>
          <h-col :span="24">
            <h-select
              mode="multiple"
              label="语言"
              v-model:value="form.languageList"
              dictType="Language"
              name="languageList"
            />
          </h-col>
          <h-col :span="24">
            <h-select
              mode="multiple"
              label="类型"
              v-model:value="form.genreList"
              dictType="Genre"
              name="genreList"
            />
          </h-col>
          <h-col :span="24">
            <h-select
              mode="tags"
              label="标签"
              v-model:value="form.tagList"
              name="tagList"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="akas" tab="别名">
          <h-col :span="24">
            <template :key="i" v-for="(aka, i) in form.akaList">
              <h-input
                label="别名"
                v-model:value="form.akaList[i]"
                :name="'aka' + i"
                search
                @search="onAddAka(i)"
                :allowClear="false"
              >
                <template #enterButton>
                  <PlusOutlined v-if="i === 0" />
                  <MinusOutlined v-else />
                </template>
              </h-input>
            </template>
          </h-col>
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </h-form-modal>
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { PlusOutlined, MinusOutlined } from "@ant-design/icons-vue";
import {
  apiTvshowShowCreate,
  apiTvshowShowUpdate,
  apiTvshowShowView,
} from "@/api/tvshow/tvshowShowApi";

const emits = defineEmits(["save-complete"]);
let formAction = ref("create");
let refForm = ref();

let form = ref({
  id: "",
  title: "",
  originalTitle: "",
  studio: "",
  contentRating: "",
  summary: "",
  year: "",
  originallyAvailableAt: "",
  rating: "",
  thumb: "",
  art: "",
  totalSeasons: "",
  addedAt: "",
  updatedAt: "",
});

const create = () => {
  formAction.value = "create";
  refForm.value.reset();
  refForm.value.show();
};

const update = async (id) => {
  formAction.value = "update";
  refForm.value.reset();
  form.value = await apiTvshowShowView({ id });
  refForm.value.show();
};

const onSubmit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await apiTvshowShowCreate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        refForm.value.hide();
      }
    } else if (formAction.value === "update") {
      form.value.akaList = form.value.akaList.filter((s) => s !== "");
      let res = await apiTvshowShowUpdate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        refForm.value.hide();
      }
    }
  } catch (e) {}
};

const onAddAka = (i) => {
  if (i === 0) {
    form.value.akaList.push("");
  } else {
    form.value.akaList.splice(i, 1);
  }
};

defineExpose({
  create,
  update,
});
</script>

<style lang="less" scoped>
/deep/ .ant-input-search-button {
  background: #fff;
  border: 1px solid #d9d9d9;
  color: #00000072;
  padding: 4px 10px;
}
</style>
