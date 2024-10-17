<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 单季表单页面
-->
<template>
  <h-form-modal
    ref="refForm"
    :label-col="{ span: 4 }"
    width="1000px"
    v-model:form="form"
    title="单季"
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
        <a-tab-pane key="authors" tab="演职表">
          <h-col :span="24">
            <k-select-actor
              v-model:value="form.directorList"
              mode="multiple"
              name="directorList"
              label="导演"
            />
          </h-col>
          <h-col :span="24">
            <k-select-actor
              v-model:value="form.writerList"
              mode="multiple"
              name="writerList"
              label="编剧"
              :maxTagCount="1"
            />
          </h-col>
          <h-col :span="24">
            <template :key="i" v-for="(actor, i) in form.actorList">
              <a-row>
                <h-col :span="12">
                  <k-select-actor
                    :labelCol="{ span: 8 }"
                    v-model:value="form.actorList[i].id"
                    :name="'form.actorList[' + i + '].id'"
                    label="主演"
                  />
                </h-col>
                <h-col :span="12">
                  <h-input
                    v-model:value="form.actorList[i].playRole"
                    :name="'form.actorList[' + i + '].playRole'"
                    label="饰"
                    search
                    @search="onAddActor(i)"
                  >
                    <template #enterButton>
                      <PlusOutlined v-if="i === 0" />
                      <MinusOutlined v-else />
                    </template>
                  </h-input>
                </h-col>
              </a-row>
            </template>
          </h-col>
          <h-col :span="24" offset="4"
            >找不到？<a @click="onCreateActor">新增演职员</a></h-col
          >
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </h-form-modal>
  <actor-form ref="refActorForm" />
</template>

<script setup>
import { ref } from "vue";
import { message } from "ant-design-vue";
import { MinusOutlined, PlusOutlined } from "@ant-design/icons-vue";
import {
  apiTvshowSeasonCreate,
  apiTvshowSeasonUpdate,
  apiTvshowSeasonView,
} from "@/api/tvshow/tvshowSeasonApi";
import ActorForm from "@/views/actor/actorForm.vue";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");

let refForm = ref();
let refActorForm = ref();

let form = ref({
  id: "",
  showId: "",
  title: "",
  summary: "",
  seasonIndex: "",
  thumb: "",
  art: "",
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
  form.value = await apiTvshowSeasonView({ id });
  if (form.value.directorList.length > 0) {
    form.value.directorList = form.value.directorList.map((s) => s.id);
  }
  if (form.value.writerList.length > 0) {
    form.value.writerList = form.value.writerList.map((s) => s.id);
  }
  refForm.value.show();
};

const onAddActor = (i) => {
  if (i === 0) {
    form.value.actorList.push({
      id: "",
      playRole: "",
    });
  } else {
    form.value.actorList.splice(i, 1);
  }
};

const onCreateActor = () => {
  refActorForm.value.create();
};

const onSubmit = async () => {
  try {
    if (formAction.value === "create") {
      let res = await apiTvshowSeasonCreate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        refForm.value.hide();
      }
    } else if (formAction.value === "update") {
      let res = await apiTvshowSeasonUpdate(form.value);
      if (res) {
        message.success("操作成功");
        emits("save-complete");
        refForm.value.hide();
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
