<!--
 * @Author: cyetstar
 * @Date: 2024-03-12 17:49:02
 * @Description: 漫画作者详情页面
-->
<template>
  <section class="page-section">
    <div class="view-box">
      <div class="view-item">
        <span class="view-item-label">主键: </span>
        <span class="view-item-value">{{ record.id }}</span>
      </div>
      <div class="view-item">
        <span class="view-item-label">姓名: </span>
        <span class="view-item-value">{{ record.name }}</span>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { apiAuthorView } from "@/api/authorApi.ts";

const route = useRoute();
const id = route.query.id;

const record = ref({});
const initData = async () => {
  const res = await apiAuthorView({ id });
  record.value = res;
};
onMounted(() => {
  initData();
});
</script>

<style lang="less" scoped>
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
