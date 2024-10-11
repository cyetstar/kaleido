<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 单集详情页面
-->
<template>
  <section class="page-section">
    <div class="view-box">
      <div class="view-item">
        <span class="view-item-label">主键: </span>
        <span class="view-item-value">{{ record.id }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">单季id: </span>
        <span class="view-item-value">{{ record.seasonId }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">标题: </span>
        <span class="view-item-value">{{ record.title }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">制片公司: </span>
        <span class="view-item-value">{{ record.studio }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">剧集评级: </span>
        <span class="view-item-value">{{ record.contentRating }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">简介: </span>
        <span class="view-item-value">{{ record.summary }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">首播年份: </span>
        <span class="view-item-value">{{ record.year }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">首播日期: </span>
        <span class="view-item-value">{{ record.originallyAvailableAt }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">集号: </span>
        <span class="view-item-value">{{ record.episodeIndex }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">评分: </span>
        <span class="view-item-value">{{ record.rating }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">片长(秒): </span>
        <span class="view-item-value">{{ record.duration }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">海报: </span>
        <span class="view-item-value">{{ record.thumb }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">艺术图: </span>
        <span class="view-item-value">{{ record.art }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">加入时间: </span>
        <span class="view-item-value">{{ record.addedAt }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">更新时间: </span>
        <span class="view-item-value">{{ record.updatedAt }}</span>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import {apiTvshowEpisodeView} from "@/api/tvshow/tvshowEpisodeApi.ts";

const route = useRoute()
const id = route.query.id

const record = ref({})
const initData = async () => {
  const res = await apiTvshowEpisodeView({ id })
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