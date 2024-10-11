<!--
 * @Author: xiadawei
 * @Date: 2023-11-11 21:13:09
 * @Description: 发行品表单页面
-->
<template>
  <a-modal
    ref="formRef"
    v-model:visible="visible"
    title="匹配网易云"
    width="800px"
    :footer="null"
  >
    <a-form layout="inline">
      <h-input
        class="w-300px"
        placeholder=""
        v-model:value="form.keywords"
        name="keywords"
        @blur="onSearch"
        @keyup.enter.native="$event.target.blur()"
      />
      <h-button @click="onSearch">搜索</h-button>
    </a-form>

    <a-table size="small" :data-source="dataSource" class="mt-3">
      <a-table-column align="center">
        <template #="{ record }">
          <check-circle-two-tone
            two-tone-color="#52c41a"
            v-if="record.neteaseId === artistRecord.neteaseId"
          />
        </template>
      </a-table-column>
      <a-table-column title="照片" align="center">
        <template #="{ record }">
          <a-image :src="record.picUrl" :width="100" />
        </template>
      </a-table-column>
      <a-table-column
        title="名称"
        data-index="name"
        align="center"
      ></a-table-column>
      <a-table-column
        title="中文名"
        data-index="trans"
        align="center"
      ></a-table-column>
      <a-table-column title="操作" align="center">
        <template #="{ record }">
          <a-space :size="0">
            <h-button type="primary" size="small" link @click="onMatch(record)"
              >匹配
            </h-button>
          </a-space>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
</template>

<script setup>
import { ref } from "vue";
import { CheckCircleTwoTone } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import {
  apiMusicArtistSearchNetease,
  apiMusicArtistMatchNetease,
} from "@/api/music/musicArtistApi";

const emits = defineEmits(["match-success"]);

let artistRecord = ref({});
let visible = ref();
let dataSource = ref([]);
let form = ref({
  keywords: "",
});

const show = (record) => {
  visible.value = true;
  artistRecord.value = record;
  form.value.keywords = record.title;
  onSearch();
};

const onSearch = () => {
  apiMusicArtistSearchNetease(form.value).then((res) => {
    dataSource.value = res;
  });
};

const onMatch = (record) => {
  apiMusicArtistMatchNetease({
    ...artistRecord.value,
    ...record,
  }).then(() => {
    message.success("匹配成功");
    visible.value = false;
    emits("match-success", record);
  });
};

defineExpose({
  show,
});
</script>

<style lang="less" scoped></style>
