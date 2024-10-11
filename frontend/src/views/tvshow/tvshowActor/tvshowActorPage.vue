<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 剧集演职员列表页面
-->
<template>
  <section class="h-page-section" ref="appManagePage">
    <h-form-search v-model:form="searchForm" @search="onSearch" @reset="onReset">
        <h-col :span="6">
          <h-input label="姓名"
                     v-model:value="searchForm.name" name="name"/>
        </h-col>
        <h-col :span="6">
          <h-input label="原名"
                     v-model:value="searchForm.originalName" name="originalName"/>
        </h-col>
        <h-col :span="6">
          <h-input label="豆瓣编号"
                     v-model:value="searchForm.doubanId" name="doubanId"/>
        </h-col>
    </h-form-search>

    <a-space class="h-btn-space">
      <h-button v-permissKey="'tvshowActor:create'" type="primary" @click="onCreateRecord">新增</h-button>
      <h-button-delete v-permissKey="'tvshowActor:delete'" plain :disabled="selectedRowKeys.length === 0"
                       @delete="onDeleteRecord(selectedRowKeys)"/>
      <h-button v-permissKey="'tvshowActor:export'" @click="onShowExportRecord">导出</h-button>
    </a-space>

    <h-table-data ref="tableRef" row-key="id" @loadData="onLoadData" @selection-change="onSelectionChange">
        <a-table-column title="主键" data-index="id" align="center"></a-table-column>
        <a-table-column title="姓名" data-index="name" align="center"></a-table-column>
        <a-table-column title="原名" data-index="originalName" align="center"></a-table-column>
        <a-table-column title="豆瓣编号" data-index="doubanId" align="center"></a-table-column>
      <a-table-column title="操作" align="center" width="300px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button type="primary" size="small" link v-permissKey="'tvshowActor:view'"
                      @click="onViewRecord(record.id)">详情</h-button>
            <h-button type="primary" size="small" link v-permissKey="'tvshowActor:update'"
                      @click="onUpdateRecord(record.id)">编辑</h-button>
            <h-button-delete size="small" link v-permissKey="'tvshowActor:delete'"
                             @delete="onDeleteRecord(record.id)">删除</h-button-delete>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>

    <tvshowActorForm ref="formRef" @save-complete="onSaveComplete"></tvshowActorForm>

    <h-modal-export ref="exportRef" @loadColumn="onLoadColumn" @export="onExport"></h-modal-export>
  </section>
</template>

<script setup>
import tvshowActorForm from './tvshowActorForm.vue';
import { ref, getCurrentInstance } from 'vue';
import {apiTvshowActorPage, apiTvshowActorDelete, apiTvshowActorColumn, apiTvshowActorExport} from "@/api/tvshow/tvshowActorApi.ts"

import { message } from "ant-design-vue";
import { useRouter } from 'vue-router';

const searchForm = ref({
  id: "",
  name: "",
  originalName: "",
  doubanId: "",
});

const tableRef = ref();
const onSearch = () => {
  tableRef.value.load(1);
};

const onReset = () => {
  tableRef.value.load(1);
};

const onLoadData = (params, callbacks) => {
  callbacks(apiTvshowActorPage({ ...params, ...searchForm.value, searchCount: true }));
};

const onSelectionChange = () => { };

const formRef = ref();
const onCreateRecord = () => {
  formRef.value.create();
}
const router = useRouter()
const onViewRecord = (id) => {
  router.push({ path: "tvshow/tvshowActor/view", query: { id } })
}
const onUpdateRecord = (id) => {
  formRef.value.update(id);
};
const onSaveComplete = () => {
  tableRef.value.load(1);
};

const selectedRowKeys = ref([]);
const onDeleteRecord = (recordId) => {
  const id = Array.isArray(recordId) ? recordId : [recordId];
  apiTvshowActorDelete(id).then(res => {
    if (res) {
      tableRef.value.load();
      message.success("删除成功！");
    }
  });
}

const { proxy } = getCurrentInstance();
const exportRef = ref();
const onShowExport = () => {
  exportRef.value.show();
};

const onLoadColumn = (callbacks) => {
  callbacks(apiTvshowActorColumn);
};

const onExport = async (params) => {
  const data = await apiTvshowActorExport({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "剧集演职员");
};
</script>

<style lang='less' scoped>
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