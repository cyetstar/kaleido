<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画书籍详情页面
-->
<template>
  <section class="k-view-section">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template #backIcon>
        <LeftOutlined />
      </template>
      <template #extra>
        <a-space>
          <h-button @click="onOpenComicInfo">查看ComicInfo</h-button>
          <h-button @click="onUpdate">编辑</h-button>
        </a-space>
      </template>
    </a-page-header>
    <section class="flex">
      <div>
        <k-thumb-image
          v-if="record.id"
          class="h-poster"
          type="ComicBook"
          style="width: 250px"
          :id="id"
        />
      </div>
      <div class="flex-1 ml-8">
        <p class="flex" v-if="isNotEmpty(record.pageCount)">
          <span class="mr-2">页数:</span>
          <span class="flex-1">{{ record.pageCount }}</span>
        </p>
        <p class="flex" v-if="isNotEmpty(record.fileSize)">
          <span class="mr-2">文件大小:</span>
          <span class="flex-1">{{ record.fileSizeLabel }}</span>
        </p>
        <p class="flex">
          <span class="mr-2">文件:</span>
          <span class="flex-1">{{ record.filename }}</span>
        </p>
        <p>
          <k-logo-link type="komga" sub="book" :id="record.id" class="mr-3" />
          <k-logo-link type="bgm" :id="record.bgmId" class="mr-3" />
        </p>
      </div>
    </section>
  </section>
  <comic-book-form ref="refComicBookForm"></comic-book-form>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { FileTextOutlined, LeftOutlined } from "@ant-design/icons-vue";
import { useRoute, useRouter } from "vue-router";
import { isNotEmpty } from "@ht/util";
import {
  apiComicBookOpenComicInfo,
  apiComicBookView,
  apiComicBookWriteComicInfo,
} from "@/api/comic/comicBookApi";
import ComicBookForm from "@/views/comic/comicBook/comicBookForm.vue";

const route = useRoute();
const router = useRouter();
const record = ref({});
const refComicBookSetCover = ref();
const refComicBookForm = ref();
const id = route.query.id;

const initData = async () => {
  apiComicBookView({ id }).then((res) => {
    record.value = res;
  });
};

const onOpenComicInfo = () => {
  apiComicBookOpenComicInfo({ id });
};

const onUpdate = () => {
  refComicBookForm.value.update(id);
};

onMounted(() => {
  initData();
});
</script>
