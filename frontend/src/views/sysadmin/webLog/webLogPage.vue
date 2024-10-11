<!--
 * @Author: gongxiaofei
 * @Date: 2023-06-29 13:55:48
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-09-11 10:01:23
 * @Description: 日志管理
-->
<template>
  <section class="h-page-section">
    <h-form-search
      v-model:form="searchForm"
      @search="onSearch"
      @reset="onReset"
    >
      <a-col :span="5">
        <h-input
          label="操作名称"
          name="description"
          v-model:value="searchForm.description"
        />
      </a-col>
      <a-col :span="5">
        <h-input
          label="操作人"
          name="username"
          v-model:value="searchForm.username"
        />
      </a-col>
      <a-col :span="6">
        <a-form-item label="操作时间:">
          <a-range-picker
            style="width: 100%"
            format="YYYY-MM-DD"
            @change="onRangeChange"
          ></a-range-picker>
        </a-form-item>
      </a-col>
    </h-form-search>
    <a-space class="py-12px">
      <h-button v-permissKey="'webLog:export'" @click="showExport"
        >导出</h-button
      >
    </a-space>
    <h-table-data ref="tableRef" row-key="id" @loadData="loadData">
      <a-table-column
        title="操作名称"
        data-index="description"
      ></a-table-column>
      <a-table-column title="操作人">
        <template v-slot="{ record }">
          {{ record.cjr }}({{ record.cjrzh }})
        </template>
      </a-table-column>
      <a-table-column title="操作人ip" data-index="ip"></a-table-column>
      <a-table-column title="操作时间" data-index="cjsj"></a-table-column>
      <a-table-column title="耗时" data-index="spendTime"></a-table-column>
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
  webLogPageApi,
  webLogColumnApi,
  webLogExportApi,
} from "@/api/sysadmin/webLogApi";
import { Dayjs } from "dayjs";

const { proxy } = getCurrentInstance() as any;

let searchForm = ref<{
  description: string;
  username: string;
  startCjsj: string;
  endCjsj: string;
}>({
  description: "",
  username: "",
  startCjsj: "",
  endCjsj: "",
});

const onRangeChange = (value: [Dayjs, Dayjs], dateString: [string, string]) => {
  searchForm.value.startCjsj = dateString[0];
  searchForm.value.endCjsj = dateString[1];
};
let tableRef = ref();
const loadData = (params: any, callbacks: any) => {
  callbacks(webLogPageApi({ ...params, ...searchForm.value }));
};
const onSearch = () => {
  tableRef.value.reload(1);
};
const onReset = () => {
  tableRef.value.reload(1);
};

const onAdd = async () => {};

const exportRef = ref();
const showExport = () => {
  exportRef.value.show();
};

const loadColumn = (callbacks: any) => {
  callbacks(webLogColumnApi);
};

const onExport = async (params: any) => {
  const data = await webLogExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统日志");
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
