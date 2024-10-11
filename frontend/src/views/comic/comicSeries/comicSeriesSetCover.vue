<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画书籍详情页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    title="设置封面页"
    width="1200px"
    style="top: 20px"
  >
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
          :src="newUrl"
          class=""
          style="object-fit: contain; height: 300px"
        />
      </div>
    </div>
    <h-module-title title="单卷" class="mt-4" />
    <div class="mt-4">
      <h-radio
        button
        :columns="bookColumns"
        v-model:value="bookId"
        @change="onChangeBook"
      />
    </div>
    <h-module-title title="页码" class="mt-4" />
    <div class="mt-4">
      <h-radio button :columns="pageColumns" v-model:value="coverPageNumber" />
    </div>
    <template #footer>
      <h-button type="default" @click="onRotate(90)">顺时针旋转</h-button>
      <h-button type="default" @click="onRotate(-90)">逆时针旋转</h-button>
      <h-button type="default" @click="onSetCropBoxData"
        >{{ local ? "上次比例" : "原先比例" }}
      </h-button>
      <h-button type="primary" @click="onSubmit">确认</h-button>
      <h-button
        :type="interval > 0 ? 'default' : 'primary'"
        class="ml-3"
        @click="onSubmitAuto"
        v-if="bookCount > bookIndex + 1"
        >{{ interval > 0 ? "关闭处理" : "自动处理" }}
      </h-button>
      <h-button
        type="primary"
        class="ml-3"
        @click="onSubmitNextBook"
        v-if="bookCount > bookIndex + 1"
        >确认并下卷
      </h-button>
      <h-button type="primary" class="ml-3" @click="onSubmitClose" v-else
        >确认并关闭
      </h-button>
    </template>
  </a-modal>
</template>

<script setup>
import { ref, computed, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import VuePictureCropper, { cropper } from "vue-picture-cropper";
import { ImageCompressor } from "image-compressor";
import {
  apiComicBookPage,
  apiComicBookUploadCover,
} from "@/api/comic/comicBookApi";
import { message } from "ant-design-vue";
import { useAppStore } from "@/store/modules/app";

const route = useRoute();
const router = useRouter();
let visible = ref();

let coverPageNumber = ref(1);
let pageColumns = ref([]);
let bookColumns = ref([]);

let id = null;
let newUrl = ref();
let bookCount = ref(0);
let bookId = ref();
let bookIndex = ref(0);
let record = ref({});

let url = computed(() => {
  let url = inject("imageUrl");
  url = url + "page?id=" + record.value.id + "&number=" + coverPageNumber.value;
  return url;
});

let local = ref(true);
let appStore = useAppStore();
let readied = false;
let interval = ref(0);

const genPageColumns = () => {
  pageColumns.value = [];
  for (let i = 1; i <= record.value.pageCount; i++) {
    pageColumns.value.push({ text: i, value: i });
  }
};

const initData = () => {
  apiComicBookPage({
    pageSize: 2000,
    orderBy: "ASC:book_number",
    seriesId: record.value.seriesId,
  }).then((res) => {
    bookColumns.value = res.records.map((s) => ({
      text: s.bookNumber,
      value: s.id,
      className: s.coverPageNumber != null ? "cover" : "",
      ...s,
    }));
    bookCount.value = res.records.length;
    bookIndex.value = res.records.findIndex((s) => s.id === bookId.value);
    genPageColumns();
  });
};

const show = (bookRecord) => {
  record.value = bookRecord;
  bookId.value = bookRecord.id;
  coverPageNumber.value = 1;
  visible.value = true;
  initData();
};

const onChangeBook = (e) => {
  bookIndex.value = bookColumns.value.findIndex(
    (s) => s.value === bookId.value
  );
  record.value = bookColumns.value.filter((s) => s.value === e.id)[0];
  if (record.value.pageCount < coverPageNumber.value) {
    coverPageNumber.value = record.value.pageCount;
  }
  genPageColumns();
};

const onRotate = (degree) => {
  if (!cropper) {
    return;
  }
  cropper.rotate(degree);
};

const onReady = () => {
  if (!cropper) {
    return;
  }
  let cropBoxData = local.value
    ? appStore.$state.cropBoxData
    : JSON.parse(record.value.coverBoxData) || appStore.$state.cropBoxData;
  if (cropBoxData) {
    cropper.setCropBoxData(cropBoxData);
  }
  newUrl.value = cropper.getDataURL();
  readied = true;
};
const onCropend = () => {
  if (!cropper) return;
  newUrl.value = cropper.getDataURL();
};

const onSetCropBoxData = () => {
  local.value = !local.value;
  if (!cropper) return;
  let cropBoxData = local.value
    ? appStore.$state.cropBoxData
    : JSON.parse(record.value.coverBoxData) || appStore.$state.cropBoxData;
  if (cropBoxData) {
    cropper.setCropBoxData(cropBoxData);
  }
};

const onSubmitNextBook = () => {
  onSubmit().then((res) => {
    if (res) {
      bookId.value = bookColumns.value[bookIndex.value + 1].value;
      onChangeBook({ id: bookId.value });
    }
  });
};

const onSubmitAuto = () => {
  if (interval.value > 0) {
    clearInterval(interval.value);
    interval.value = 0;
  } else {
    interval.value = setInterval(() => {
      if (!readied) {
        return;
      }
      if (bookCount.value > bookIndex.value + 1) {
        onSubmitNextBook();
      } else {
        onSubmitClose();
        clearInterval(interval.value);
        interval.value = 0;
      }
      readied = false;
    }, 500);
  }
};

const onSubmitClose = () => {
  onSubmit().then((res) => {
    visible.value = !res;
  });
};

const onSubmit = () => {
  if (!cropper) return;
  let dataURL = cropper.getDataURL();
  let cropBoxData = cropper.getCropBoxData();
  const imageCompressor = new ImageCompressor();
  const compressorSettings = {
    toWidth: 300,
    mimeType: "image/jpg",
    mode: "strict",
    speed: "low",
  };

  return new Promise((resolve) => {
    imageCompressor.run(dataURL, compressorSettings, (data) => {
      apiComicBookUploadCover({
        ...record.value,
        data: data,
        coverPageNumber: coverPageNumber.value,
        coverBoxData: JSON.stringify(cropBoxData),
      }).then((res) => {
        if (res) {
          let data = cropper.getCropBoxData();
          appStore.setCropBoxData(data);
          message.success("设置成功");
        } else {
          message.error("设置失败");
        }
        return resolve(res);
      });
    });
  });
};

defineExpose({
  show,
});
</script>
<style lang="less" scoped>
/deep/ .ant-radio-button-wrapper {
  width: 32px;
  text-align: center;
  padding: 0;
  margin: 1px;
}

/deep/ .cover {
  background: rgba(66, 152, 255, 0.4);
}
</style>
