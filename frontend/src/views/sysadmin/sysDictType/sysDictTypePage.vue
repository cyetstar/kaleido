<!--
 * @Author: gongxiaofei
 * @Date: 2023-07-03 16:43:56
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-09-11 10:17:20
 * @Description: 字典管理
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
        <h-input v-model:value="searchForm.name" label="字典名称" name="name" />
      </a-col>
      <a-col :span="5">
        <h-input v-model:value="searchForm.type" label="字典类型" name="type" />
      </a-col>
    </h-form-search>
    <a-space class="h-btn-space">
      <h-button
        v-permissKey="'sysDictType:other'"
        type="primary"
        @click="onReload"
        >重载字典</h-button
      >
      <h-button
        v-permissKey="'sysDictType:create'"
        type="primary"
        @click="onCreateRecord"
        >新增</h-button
      >
      <h-button-delete
        v-permissKey="'sysDictType:delete'"
        plain
        :disabled="selectedRowKeys.length === 0"
        @delete="onDeleteRecord(selectedRowKeys)"
        >删除</h-button-delete
      >
    </a-space>
    <h-table-data
      ref="tableRef"
      row-key="id"
      @loadData="loadData"
      @selection-change="selectionChange"
    >
      <a-table-column title="字典名称" data-index="name"></a-table-column>
      <a-table-column title="字典类型" data-index="type"></a-table-column>
      <a-table-column title="创建时间" data-index="createTime"></a-table-column>
      <a-table-column title="操作" key="action">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              type="primary"
              size="small"
              link
              v-permissKey="'sysDictType:view'"
              @click="onViewRecord(record.id)"
              >详情
            </h-button>
            <h-button
              type="primary"
              size="small"
              link
              v-permissKey="'sysDictType:update'"
              @click="onUpdateRecord(record.id)"
              >编辑
            </h-button>
            <h-button-delete
              size="small"
              link
              v-permissKey="'sysDictType:delete'"
              @delete="onDeleteRecord(record.id)"
              >删除
            </h-button-delete>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>
    <sysDictTypeForm
      ref="formRef"
      @save-complete="saveComplete"
    ></sysDictTypeForm>
  </section>
</template>

<script setup lang="ts">
import { ref } from "vue";
import sysDictTypeForm from "./sysDictTypeForm.vue";

import {
  sysDictTypePageApi,
  sysDictTypeDeleteApi,
  sysDictTypeReloadApi,
} from "@/api/sysadmin/sysDictTypeApi";
import { message } from "ant-design-vue";
import { useRouter } from "vue-router";

const router = useRouter();
let searchForm = ref<{ name: string; type: string }>({
  name: "",
  type: "",
});

const loadData = (params: any, callbacks: any) => {
  callbacks(sysDictTypePageApi({ ...params, ...searchForm.value }));
  selectedRowKeys.value = [];
};
const selectionChange = (keys: string[]) => {
  selectedRowKeys.value = keys;
};
const tableRef = ref();
const onSearch = () => {
  tableRef.value.load(1);
};
const onReset = () => {
  tableRef.value.load(1);
};

const formRef = ref();
let dictModalData = ref<any>({
  visible: false,
  type: "0",
  form: {},
});
const onCreateRecord = () => {
  formRef.value.create();
};

const onUpdateRecord = async (id: string) => {
  formRef.value.update(id);
};

const saveComplete = () => {
  tableRef.value.load(1);
};
const onViewRecord = (id: string) => {
  router.push({ path: "/sysadmin/sysDictType/view", query: { id } });
};

let selectedRowKeys = ref<string[]>([]);
const onDeleteRecord = async (recordId: string | string[]) => {
  let id = typeof recordId === "string" ? [recordId] : recordId;
  sysDictTypeDeleteApi(id).then((res) => {
    if (res) {
      tableRef.value.load();
      message.success("删除成功！");
    }
  });
};

const onReload = () => {
  sysDictTypeReloadApi().then((res) => {
    if (res) {
      message.success("重载成功！");
    }
  });
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
