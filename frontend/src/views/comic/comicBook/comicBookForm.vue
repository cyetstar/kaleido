<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画书籍表单页面
-->
<template>
  <h-form-modal
    ref="formRef"
    :label-col="{ span: 4 }"
    width="1200px"
    v-model:form="form"
    title="编辑信息"
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
              label="卷号"
              v-model:value="form.bookNumber"
              name="bookNumber"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="排序"
              v-model:value="form.sortNumber"
              name="sortNumber"
            />
          </h-col>
          <h-col :span="24">
            <h-input
              label="番组计划编号"
              v-model:value="form.bgmId"
              name="bgmId"
            />
          </h-col>
        </a-tab-pane>
        <a-tab-pane key="cover" tab="封面">
          <div class="flex">
            <div>
              <vue-picture-cropper
                :boxStyle="{
                  width: '800px',
                  height: '300px',
                  backgroundColor: '#f8f8f8',
                  margin: 'auto',
                }"
                :img="url"
                :options="{
                  viewMode: 1,
                  zoomable: false,
                  dragMode: 'crop',
                  initialAspectRatio: 21 / 29.7,
                }"
                @ready="onReady"
                @cropend="onCropend"
              />
            </div>
            <div class="ml-3">
              <img
                v-if="newUrl"
                :src="newUrl"
                class=""
                style="object-fit: contain; width: 200px; height: 300px"
              />
            </div>
          </div>
          <div class="mt-4">
            <h-radio
              button
              :columns="pageNumberColumns"
              v-model:value="form.coverPageNumber"
            />
            <h-button type="danger" @click="onClear">清除封面</h-button>
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </h-form-modal>
</template>

<script setup>
import { computed, inject, ref } from "vue";
import { message } from "ant-design-vue";
import {
  apiComicBookClearCover,
  apiComicBookUpdate,
  apiComicBookUploadCover,
  apiComicBookView,
} from "@/api/comic/comicBookApi";
import VuePictureCropper, { cropper } from "vue-picture-cropper";
import { ImageCompressor } from "image-compressor";
import { useAppStore } from "@/store/modules/app";

const emits = defineEmits(["save-complete"]);

let formAction = ref("create");
let formRef = ref();
let activeKey = ref("basic");
let pageNumberColumns = ref([]);

let form = ref({
  id: "",
  title: "",
  originalTitle: "",
  bookNumber: "",
  sortNumber: "",
  coverPageNumber: 1,
  bgmId: "",
});

let newUrl = ref();
let url = computed(() => {
  let url = inject("imageUrl");
  url =
    url +
    "page?id=" +
    form.value.id +
    "&number=" +
    (form.value.coverPageNumber || 1);
  return url;
});

const update = async (id) => {
  formAction.value = "update";
  formRef.value.reset();
  apiComicBookView({ id }).then((res) => {
    form.value = res;
    form.value.coverPageNumber = form.value.coverPageNumber || 1;
    pageNumberColumns.value = [];
    for (let i = 1; i <= form.value.pageCount; i++) {
      pageNumberColumns.value.push({ text: i, value: i });
    }
    formRef.value.show();
  });
};

let appStore = useAppStore();
const onReady = () => {
  if (!cropper) {
    return;
  }
  let cropBoxData =
    JSON.parse(form.value.coverBoxData) || appStore.$state.cropBoxData;
  if (cropBoxData) {
    cropper.setCropBoxData(cropBoxData);
  }
  newUrl.value = cropper.getDataURL();
};
const onCropend = () => {
  if (!cropper) {
    return;
  }
  newUrl.value = cropper.getDataURL();
};

const onClear = () => {
  apiComicBookClearCover({ id: form.value.id }).then((res) => {
    if (res) {
      message.success("清除成功");
      formRef.value.hide();
    } else {
      message.error("清除失败");
    }
  });
};

const compressorSettings = {
  toWidth: 300,
  mimeType: "image/jpg",
  mode: "strict",
  speed: "low",
};
const imageCompressor = new ImageCompressor();
const onSubmit = async () => {
  try {
    if (activeKey.value === "cover") {
      if (!cropper) {
        return;
      }
      let dataURL = cropper.getDataURL();
      let cropBoxData = cropper.getCropBoxData();
      imageCompressor.run(dataURL, compressorSettings, (data) => {
        apiComicBookUploadCover({
          ...form.value,
          data: data,
          coverBoxData: JSON.stringify(cropBoxData),
        }).then((res) => {
          if (res) {
            appStore.setCropBoxData(cropBoxData);
            message.success("设置成功");
            emits("save-complete");
            formRef.value.hide();
          } else {
            message.error("设置失败");
          }
        });
      });
    } else {
      apiComicBookUpdate(form.value).then((res) => {
        if (res) {
          message.success("保存成功");
          emits("save-complete");
          formRef.value.hide();
        } else {
          message.error("保存失败");
        }
      });
    }
  } catch (e) {}
};

defineExpose({
  update,
});
</script>

<style lang="less" scoped>
/deep/ .ant-radio-button-wrapper {
  width: 32px;
  text-align: center;
  padding: 0;
  margin: 1px;
}
</style>
