<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <k-file-modal ref="refFileModal">
    <template #footer>
      <a-space>
        <h-button
            @click="refFileModal.close()"
        >关闭
        </h-button>
        <a-upload
            v-model:file-list="fileList"
            name="file"
            :showUploadList="false"
            :custom-request="uploadFile"
        >
          <h-button>上传封面</h-button>
        </a-upload>
      </a-space>
    </template>
  </k-file-modal>
</template>

<script setup>
import { ref } from "vue";
import {
  apiMusicAlbumUploadCover,
  apiMusicAlbumViewPath,
} from "@/api/music/musicAlbumApi";
import { message } from "ant-design-vue";

const emits = defineEmits(["match-success"]);

const fileList = ref([]);
const refFileModal = ref();
let id = null;
let path = null;

const show = (recordId) => {
  id = recordId;
  apiMusicAlbumViewPath({ id }).then((res) => {
    path = res;
    refFileModal.value.show(path);
  });
};

const uploadFile = (options) => {
  apiMusicAlbumUploadCover({
    id,
    path,
    file: options.file,
  }).then((res) => {
    if (res) {
      refFileModal.value.load();
    } else {
      message.error("上传失败");
    }
  });
};

const onUploadCover = () => {};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
