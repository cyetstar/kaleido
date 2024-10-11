<!--
 * @Author: gongxiaofei
 * @Date: 2023-06-29 11:53:18
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 15:32:24
 * @Description: 资源管理
-->
<template>
  <section class="h-page-section">
    <h-form-search
      v-model:form="searchForm"
      @search="onSearch"
      @reset="onReset"
      class="h-form"
    >
      <a-col :span="5">
        <h-input label="资源名称" name="name" v-model:value="searchForm.name" />
      </a-col>
      <a-col :span="5">
        <h-input label="资源代码" name="code" v-model:value="searchForm.code" />
      </a-col>
    </h-form-search>
    <a-space class="h-btn-space">
      <h-button
        v-permissKey="'sysResource:other'"
        type="primary"
        @click="onInit"
        >初始化</h-button
      >
      <h-button v-permissKey="'sysResource:export'" @click="showExport"
        >导出</h-button
      >
    </a-space>
    <h-table-data ref="tableRef" row-key="id" @loadData="loadData">
      <a-table-column title="资源类别" data-index="type"></a-table-column>
      <a-table-column title="资源名称" data-index="name"></a-table-column>
      <a-table-column title="资源代码" data-index="code"></a-table-column>
    </h-table-data>

    <h-modal-export
      ref="exportRef"
      @loadColumn="loadColumn"
      @export="onExport"
    ></h-modal-export>
  </section>
</template>

<script setup lang="ts">
import { ref, getCurrentInstance } from "vue";
import {
  sysResourcePageApi,
  sysResourceColumnApi,
  sysResourceExportApi,
  sysResourceInitApi,
} from "@/api/sysadmin/sysResourceApi";
import { message } from "ant-design-vue";
import { ALL_RESOURCE } from "@/config/resourceInit";

const { proxy } = getCurrentInstance() as any;

let searchForm = ref<{ name: string; code: string }>({
  name: "",
  code: "",
});

let tableRef = ref();
const loadData = (params: any, callbacks: any) => {
  callbacks(sysResourcePageApi({ ...params, ...searchForm.value }));
};
const onSearch = () => {
  tableRef.value.load(1);
};
const onReset = () => {
  tableRef.value.load(1);
};

const onInit = async () => {
  const res = await sysResourceInitApi([...ALL_RESOURCE]);
  if (res) {
    message.success("初始化成功!");
    tableRef.value.load(1);
  }
};

const exportRef = ref();
const showExport = () => {
  exportRef.value.show();
};

const loadColumn = (callbacks: any) => {
  callbacks(sysResourceColumnApi);
};

const onExport = async (params: any) => {
  const data = await sysResourceExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统资源");
};
</script>

<style lang="less" scoped>
.box {
  border: 1px solid var(--border-color1);
  border-radius: var(--border-radius-box) var(--border-radius-box) 0 0;
  border-bottom: none;
}

.borders {
  border: 1px solid var(--border-color1);
  border-bottom: none;
}

:deep(.ant-form-item) {
  margin-bottom: 16px;
}
</style>
