<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 剧集详情页面
-->
<template>
  <section class="k-view-section">
    <a-page-header :title="record.title" @back="() => $router.go(-1)">
      <template v-if="record.status==='ENDED'" #subTitle>
        <a-tag color="green"
        >已完结
        </a-tag>
      </template>
      <template #backIcon>
        <LeftOutlined/>
      </template>
      <template #extra>
        <a-space>
          <h-button @click="onSetCover">设置封面</h-button>
          <k-action-button
              action="comicSync"
              ok-text="同步Komga"
              cancel-text="取消同步"
              :form="searchForm"
          />
          <k-action-button
              action="comicMatchInfo"
              ok-text="自动抓取"
              cancel-text="取消抓取"
              :form="searchForm"
          />
          <h-button @click="onSearchInfo">匹配抓取</h-button>
          <h-button @click="onUpdate">编辑</h-button>
          <h-button @click="onFileManage">文件管理</h-button>
        </a-space>
      </template>
    </a-page-header>
    <section class="flex">
      <div>
        <k-thumb-image
            class="h-poster"
            style="width: 250px"
            type="ComicSeries"
            :id="record.id"
        />
      </div>
      <div class="flex-1 ml-8">
        <p class="flex items-center">
          <span v-if="isNotEmpty(record.rating)" class="mr-2">{{ record.rating }} 分</span>
          <a-rate v-model:value="rating" disabled allow-half/>
        </p>
        <p>
          <a-tag v-for="item in record.tagList" :key="item"
          >{{ item }}
          </a-tag>
        </p>
        <p v-if="isNotEmpty(summaryList)">
          <p v-for="(item, index) in summaryList" :key="index">
            {{ item }}
          </p>
        </p>
        <p class="flex" v-if="isNotEmpty(record.authorList)">
          <span class="mr-2">作者:</span>
          <span class="flex-1">
          <template v-for="(item, index) in record.authorList">
            <span v-if="index !== 0" class="px-2">/</span>
            <a @click="onViewAuthor(item)">{{ item.name }}</a>
          </template>
        </span>
        </p>
        <p class="flex" v-if="isNotEmpty(record.publisher)">
          <span class="mr-2">出版社:</span>
          <span class="flex-1">{{ record.publisher }}</span>
        </p>
        <p class="flex" v-if="isNotEmpty(record.alternateTitleList)">
          <span class="mr-2">又名:</span>
          <span class="flex-1">
            <template v-for="(item, index) in record.alternateTitleList">
              <span v-if="index !== 0" class="px-2">/</span>
              {{ item }}
            </template>
          </span>
        </p>
        <p class="flex">
          <span class="mr-2">总卷数:</span>
          <span class="flex-1">
            {{ record.bookCount }}
            <span v-if="isNotEmpty(bookRecords) && bookRecords.length!==record.bookCount">
              ({{ bookRecords.length }})
            </span>
          </span>
        </p>
        <p class="flex">
          <span class="mr-2">更新于:</span>
          <span class="flex-1">
            {{ formatUnixTimestamp(record.updatedAt) }}
          </span>
        </p>
        <p>
          <k-logo-link type="komga" sub="series" :id="record.id" class="mr-3"/>
          <k-logo-link type="bgm" :id="record.bgmId" class="mr-3"/>
        </p>
      </div>
    </section>
    <section v-if="isNotEmpty(bookRecords)">
      <h-module-title title="单卷"/>
      <div class="grid grid-cols-24 gap-6">
        <template :key="bookRecord.id" v-for="bookRecord in bookRecords">
          <a-card class="k-card col-span-3" :bordered="false">
            <template #cover>
              <k-thumb-image
                  class="h-poster"
                  type="ComicBook"
                  :preview="false"
                  :id="bookRecord.id"
                  @click="onViewBook(bookRecord.id)"
              />
              <div class="absolute top-0 left-0 m-2px">
                <k-logo-link
                    type="komga"
                    sub="book"
                    :id="bookRecord.id"
                    :width="20"
                    class="mr-1"
                />
                <k-logo-link type="bgm" :id="bookRecord.bgmId" :width="20"/>
              </div>
            </template>
            <a-card-meta :title="bookRecord.title">
            </a-card-meta>
          </a-card>
        </template>
      </div>
    </section>
  </section>
  <comic-series-file-manage ref="refComicSeriesFileManage"/>
  <comic-series-set-cover ref="refComicSeriesSetCover"/>
  <comic-series-form ref="refComicSeriesForm" @save-complete="initData"/>
  <comic-series-search-info ref="refComicSeriesSearchInfo"/>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import {LeftOutlined} from "@ant-design/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {isNotEmpty} from "@ht/util";
import {formatUnixTimestamp} from "@/utils/utils";
import {apiComicSeriesView} from "@/api/comic/comicSeriesApi";
import {apiComicBookPage} from "@/api/comic/comicBookApi";
import ComicSeriesFileManage from "@/views/comic/comicSeries/comicSeriesFileManage.vue";
import ComicSeriesSetCover from "@/views/comic/comicSeries/comicSeriesSetCover.vue";
import ComicSeriesForm from "@/views/comic/comicSeries/comicSeriesForm.vue";
import ComicSeriesSearchInfo from "@/views/comic/comicSeries/comicSeriesSearchInfo.vue";

const route = useRoute()
const router = useRouter()
const record = ref({})
const bookRecords = ref([]);
const refComicSeriesSetCover = ref();
const refComicSeriesFileManage = ref();
const refComicSeriesSearchInfo = ref();
const refComicSeriesForm = ref();
const id = route.query.id;
const rating = computed(() => record.value.rating / 2);
const summaryList = computed(() => {
  if (isNotEmpty(record.value.summary)) {
    return record.value.summary.split("\n")
  }
  return null;
});

const searchForm = ref({
  id: id,
  seriesId: id,
  force: true
})

const initData = async () => {
  apiComicSeriesView({id}).then((res) => {
    record.value = res
    apiComicBookPage({pageSize: 1000, orderBy: "ASC:sort_number;ASC:book_number", seriesId: id}).then(({records}) => {
      bookRecords.value = records
    })
  });
};

const onViewBook = (id) => {
  router.push({path: "/comic/comicBook/view", query: {id}})
};

const onViewAuthor = (author) => {
  router.push({name: "comicSeriesPage", params: {keyword: author.name, load: "true"}});
}


const onFileManage = () => {
  refComicSeriesFileManage.value.show(id);
};

const onSetCover = () => {
  let bookRecord = bookRecords.value[0]
  refComicSeriesSetCover.value.show(bookRecord)
}

const onSearchInfo = () => {
  refComicSeriesSearchInfo.value.show(record.value);
}

const onUpdate = () => {
  refComicSeriesForm.value.update(id);
}


onMounted(() => {
  initData()
})
</script>