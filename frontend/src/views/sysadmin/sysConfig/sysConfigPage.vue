<!--
 * @Author: xiadawei
 * @Date: 2023-11-12 15:19:06
 * @Description: 系统配置表列表页面
-->
<template>
  <section class="h-page-section" ref="appManagePage">
    <h-form-search v-model:form="searchForm" @search="onSearch" @reset="onReset">
        <h-col :span="6">
          <h-input label="配置名称"
                     v-model:value="searchForm.configName" name="configName"/>
        </h-col>
        <h-col :span="6">
          <h-input label="配置键名"
                     v-model:value="searchForm.configKey" name="configKey"/>
        </h-col>
        <h-col :span="6">
          <h-input label="配置键值"
                     v-model:value="searchForm.configValue" name="configValue"/>
        </h-col>
        <h-col :span="6">
          <h-select label="是否已删除1：已删除，0：未删除" dict-type="isDeleted"
                    v-model:value="searchForm.isDeleted" name="isDeleted"/>
        </h-col>
    </h-form-search>

    <a-space class="h-btn-space">
      <h-button v-permissKey="'sysConfig:create'" type="primary" @click="onCreateRecord">新增</h-button>
      <h-button-delete v-permissKey="'sysConfig:delete'" plain :disabled="selectedRowKeys.length === 0"
                       @delete="onDeleteRecord(selectedRowKeys)"/>
      <h-button v-permissKey="'sysConfig:export'" @click="onShowExportRecord">导出</h-button>
    </a-space>

    <h-table-data ref="tableRef" row-key="id" @loadData="loadData" @selection-change="selectionChange">
        <a-table-column title="id" data-index="id" align="center"></a-table-column>
        <a-table-column title="配置名称" data-index="configName" align="center"></a-table-column>
        <a-table-column title="配置键名" data-index="configKey" align="center"></a-table-column>
        <a-table-column title="配置键值" data-index="configValue" align="center"></a-table-column>
        <a-table-column title="是否已删除1：已删除，0：未删除" data-index="isDeleted" align="center"></a-table-column>
        <a-table-column title="创建时间" data-index="createTime" align="center"></a-table-column>
        <a-table-column title="更新时间" data-index="updateTime" align="center"></a-table-column>
      <a-table-column title="操作" align="center" width="300px">
        <template #="{ record }">
          <a-space :size="0">
            <h-button type="primary" size="small" link v-permissKey="'sysConfig:view'"
                      @click="onViewRecord(record.id)">详情</h-button>
            <h-button type="primary" size="small" link v-permissKey="'sysConfig:update'"
                      @click="onUpdateRecord(record.id)">编辑</h-button>
            <h-button-delete size="small" link v-permissKey="'sysConfig:delete'"
                             @delete="onDeleteRecord(record.id)">删除</h-button-delete>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>

    <sysConfigForm ref="formRef" @save-complete="saveComplete"></sysConfigForm>

    <h-modal-export ref="exportRef" @loadColumn="loadColumn" @export="onExport"></h-modal-export>
  </section>
</template>

<script setup>
import sysConfigForm from './sysConfigForm.vue';
import { ref, getCurrentInstance } from 'vue';
import {sysConfigPageApi, sysConfigDeleteApi, sysConfigColumnApi, sysConfigExportApi} from "@/api/sysadmin/sysConfigApi.ts"

import { message } from "ant-design-vue";
import { useRouter } from 'vue-router';

const searchForm = ref({
  id: "",
  configName: "",
  configKey: "",
  configValue: "",
  isDeleted: "",
  createTime: "",
  updateTime: "",
});

const tableRef = ref();
const onSearch = () => {
  tableRef.value.load(1);
};

const onReset = () => {
  tableRef.value.load(1);
};

const loadData = (params, callbacks) => {
  callbacks(sysConfigPageApi({ ...params, ...searchForm.value, searchCount: true }));
};

const selectionChange = () => { };

const formRef = ref();
const onCreateRecord = () => {
  formRef.value.create();
}
const router = useRouter()
const onViewRecord = (id) => {
  router.push({ path: "/sysConfig/view", query: { id } })
}
const onUpdateRecord = (id) => {
  formRef.value.update(id);
};
const saveComplete = () => {
  tableRef.value.load(1);
};

const selectedRowKeys = ref([]);
const onDeleteRecord = (recordId) => {
  const id = Array.isArray(recordId) ? recordId : [recordId];
  sysConfigDeleteApi(id).then(res => {
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

const loadColumn = (callbacks) => {
  callbacks(sysConfigColumnApi);
};

const onExport = async (params) => {
  const data = await sysConfigExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统配置表");
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