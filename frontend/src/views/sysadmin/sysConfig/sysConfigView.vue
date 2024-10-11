<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 15:19:06
 * @Description: 系统配置表详情页面
-->
<template>
  <section class="page-section">
    <div class="view-box">
      <div class="view-item">
        <span class="view-item-label">id: </span>
        <span class="view-item-value">{{ viewData.id }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">配置名称: </span>
        <span class="view-item-value">{{ viewData.configName }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">配置键名: </span>
        <span class="view-item-value">{{ viewData.configKey }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">配置键值: </span>
        <span class="view-item-value">{{ viewData.configValue }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">是否已删除1：已删除，0：未删除: </span>
        <span class="view-item-value">{{ viewData.isDeleted }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">创建时间: </span>
        <span class="view-item-value">{{ viewData.createTime }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">更新时间: </span>
        <span class="view-item-value">{{ viewData.updateTime }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">创建人: </span>
        <span class="view-item-value">{{ viewData.createdBy }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">更新人: </span>
        <span class="view-item-value">{{ viewData.updatedBy }}</span>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import {sysConfigViewApi} from "@/api/sysadmin/sysConfigApi.ts";

const route = useRoute()
const id = route.query.id

const viewData = ref({})
const getView = async () => {
  const res = await sysConfigViewApi({ id })
  viewData.value = res
}
onMounted(() => {
  getView()
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