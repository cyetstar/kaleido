<!--
 * @Author: cyetstar
 * @Date: 2023-12-29 16:56:31
 * @Description: 豆瓣电影口碑榜详情页面
-->
<template>
  <section class="page-section">
    <div class="view-box">
      <div class="view-item">
        <span class="view-item-label">主键: </span>
        <span class="view-item-value">{{ record.id }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">电影名: </span>
        <span class="view-item-value">{{ record.title }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">原片名: </span>
        <span class="view-item-value">{{ record.originalTitle }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">首映年份: </span>
        <span class="view-item-value">{{ record.year }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">海报: </span>
        <span class="view-item-value">{{ record.thumb }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">上榜日期: </span>
        <span class="view-item-value">{{ record.listingDate }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">下榜日期: </span>
        <span class="view-item-value">{{ record.delistingDate }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">最高名次: </span>
        <span class="view-item-value">{{ record.top }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">备注: </span>
        <span class="view-item-value">{{ record.memo }}</span>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import {apiMovieDoubanWeeklyView} from "@/api/movie/movieDoubanWeeklyApi.ts";

const route = useRoute()
const id = route.query.id

const record = ref({})
const initData = async () => {
  const res = await apiMovieDoubanWeeklyView({ id })
  record.value = res
}
onMounted(() => {
  initData()
})
</script>

<style lang='less' scoped>
  .view-box {
    display: flex;
    flex-wrap: wrap;
    gap: 20;
  }

  .view-item {
    width: 50%;
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    font-size: 14px;

  &-label {
     min-width: 90px;
   }

  &-value {
     flex: 1;
   }
  }
</style>