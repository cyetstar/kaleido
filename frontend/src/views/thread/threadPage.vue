<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 15:19:06
 * @Description: 系统配置表列表页面
-->
<template>
  <section class="h-page-section">
    <h-form-search
      v-model:form="searchForm"
      @search="onSearch"
      @reset="onReset"
      class="h-form"
    >
      <h-col :span="6">
        <h-select
          label="站点"
          v-model:value="searchForm.website"
          dict-type="threadWebsite"
          name="website"
        />
      </h-col>
      <h-col :span="6">
        <h-input label="标题" v-model:value="searchForm.title" name="title" />
      </h-col>
      <h-col :span="6">
        <h-input
          label="豆瓣编号"
          v-model:value="searchForm.doubanId"
          name="doubanId"
        />
      </h-col>
      <h-col :span="6">
        <h-input
          label="IMDb编号"
          v-model:value="searchForm.imdbId"
          name="imdbId"
        />
      </h-col>
      <h-col :span="6">
        <h-input
          label="番组计划编号"
          v-model:value="searchForm.bgmId"
          name="bgmId"
        />
      </h-col>
      <h-col :span="6">
        <h-select
          label="状态"
          v-model:value="searchForm.status"
          dict-type="threadStatus"
          name="status"
        />
      </h-col>
    </h-form-search>
    <a-space class="h-btn-space"></a-space>
    <h-table-data
      ref="tableRef"
      row-key="id"
      @loadData="loadData"
      @selection-change="selectionChange"
    >
      <a-table-column title="标题">
        <template #="{ record }">
          <a-tag :color="color[record.status]">{{ record.status }}</a-tag>
          {{ record.title }}
          <a v-if="record.url" :href="record.url" target="_blank">
            <link-outlined />
          </a>
        </template>
      </a-table-column>
      <a-table-column title="操作" align="center" width="80px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              type="primary"
              size="small"
              link
              @click="onViewRecord(record.id)"
              >详情
            </h-button>
            <h-button-delete
              size="small"
              link
              @delete="onDeleteRecord(record.id)"
              >删除
            </h-button-delete>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>
  </section>
</template>

<script setup>
import { ref, getCurrentInstance } from "vue";
import {
  apiThreadPage,
  apiThreadDelete,
  apiThreadColumn,
  apiThreadExport,
} from "@/api/threadApi.ts";

import { message } from "ant-design-vue";
import { useRouter } from "vue-router";
import { LinkOutlined } from "@ant-design/icons-vue";

const color = ref({
  like: "green",
  unlike: "red",
  achieve: "blue",
});
const searchForm = ref({});
const tableRef = ref();
const onSearch = () => {
  tableRef.value.load(1);
};

const onReset = () => {
  tableRef.value.load(1);
};

const loadData = (params, callbacks) => {
  callbacks(apiThreadPage({ ...params, ...searchForm.value }));
};

const selectionChange = () => {};

const formRef = ref();
const onCreateRecord = () => {
  formRef.value.create();
};
const router = useRouter();
const onViewRecord = (id) => {
  router.push({ path: "/thread/view", query: { id } });
};
const onUpdateRecord = (id) => {
  formRef.value.update(id);
};
const saveComplete = () => {
  tableRef.value.load(1);
};

const selectedRowKeys = ref([]);
const onDeleteRecord = (recordId) => {
  const id = Array.isArray(recordId) ? recordId : [recordId];
  apiThreadDelete(id).then((res) => {
    if (res) {
      tableRef.value.load();
      message.success("删除成功！");
    }
  });
};

const { proxy } = getCurrentInstance();
const exportRef = ref();
const onShowExport = () => {
  exportRef.value.show();
};

const loadColumn = (callbacks) => {
  callbacks(apiThreadColumn);
};

const onExport = async (params) => {
  const data = await apiThreadExport({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统配置表");
};
</script>

<style lang="less" scoped>
:deep(.ant-modal-body) {
  padding: 0 24px;
}

:deep(.ant-modal-footer) {
  border-top: none;
}

.table-box {
  margin-top: 20px;
}

.view-line {
  display: flex;
  justify-content: space-between;
  height: 36px;
  line-height: 36px;

  &:not(:last-child) {
    border-bottom: 1px solid #f2f2f2;
  }

  .line-name {
    color: #333333;
    font-size: 14px;
  }

  .line-value {
    color: #666666;
    font-size: 14px;
  }
}

.table-line {
  display: flex;
  justify-content: space-between;
  height: 36px;
  line-height: 36px;

  &:not(:last-child) {
    border-bottom: 1px solid #f2f2f2;
  }

  .table-item {
    width: calc(100% / 3);
    text-align: center;

    &:first-child {
      text-align: left;
    }

    &:last-child {
      text-align: right;
    }
  }
}

.table-header {
  font-weight: 700;
}

.edit-icon {
  font-size: 16px;
  margin-left: 12px;
  color: #666;
  cursor: pointer;
}
</style>
