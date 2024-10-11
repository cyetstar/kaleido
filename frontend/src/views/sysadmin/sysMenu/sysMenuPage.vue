<!--
 * @Author: gongxiaofei
 * @Date: 2023-06-29 10:09:28
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-09-11 09:59:45
 * @Description: 菜单管理
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
        <h-input v-model:value="searchForm.name" label="菜单标识" name="name" />
      </a-col>
      <a-col :span="5">
        <h-input
          v-model:value="searchForm.title"
          label="菜单标题"
          name="title"
        />
      </a-col>
      <a-col :span="5">
        <h-select
          label="是否隐藏"
          name="isHidden"
          v-model:value="searchForm.isHidden"
          dict-type="sfbz"
        ></h-select>
      </a-col>
    </h-form-search>
    <a-space class="py-12px">
      <h-button v-permissKey="'sysMenu:other'" type="primary" @click="onInit"
        >初始化
      </h-button>
      <h-button-delete
        v-permissKey="'sysUser:delete'"
        type="danger"
        plain
        :disabled="selectedRowKeys.length === 0"
        @delete="onDeleteRecord(selectedRowKeys)"
        >删除
      </h-button-delete>
      <h-button v-permissKey="'sysMenu:export'" @click="onShowRecordExport"
        >导出
      </h-button>
    </a-space>
    <h-table-data
      ref="tableRef"
      row-key="id"
      @loadData="loadData"
      @selection-change="selectionChange"
    >
      <a-table-column title="父菜单" key="parentIdLabel">
        <template v-slot="{ record }">
          {{ record.parentId === "0" ? "-" : record.parentIdLabel }}
        </template>
      </a-table-column>
      <a-table-column title="菜单标题" data-index="title"></a-table-column>
      <a-table-column title="菜单标识" data-index="name"></a-table-column>
      <a-table-column title="菜单路径" data-index="path"></a-table-column>
      <a-table-column title="顺序号" data-index="orderNum"></a-table-column>
      <a-table-column title="是否隐藏" data-index="isHidden">
        <template #="{ record }">
          <a-switch
            v-model:checked="record.isHidden"
            @change="(val: any) => onChangeHidden(val, record.id)"
          />
        </template>
      </a-table-column>
      <a-table-column title="操作" key="action">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              link
              size="small"
              type="primary"
              v-permissKey="'sysMenu:update'"
              @click="onUpdateRecord(record.id)"
              >编辑
            </h-button>
            <h-button-delete
              link
              size="small"
              v-permissKey="'sysMenu:delete'"
              @delete="onDeleteRecord(record.id)"
            >
              删除
            </h-button-delete>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>

    <h-modal-export
      ref="exportRef"
      @loadColumn="loadColumn"
      @export="onExport"
    ></h-modal-export>

    <a-modal
      v-model:visible="menuModalData.visible"
      title="编辑菜单"
      width="800px"
      @cancel="onCancelMenuModal"
      @ok="onSubmitMenuModal"
    >
      <a-form
        :model="menuModalData.form"
        :label-col="{ span: 8 }"
        ref="menuFormRef"
      >
        <a-row>
          <a-col :span="12">
            <h-input
              :disabled="menuModalData.type === '1'"
              required
              label="菜单标识"
              name="name"
              v-model:value="menuModalData.form.name"
            ></h-input>
          </a-col>
          <a-col :span="12">
            <h-input
              required
              label="菜单标题"
              name="title"
              v-model:value="menuModalData.form.title"
            ></h-input>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <h-input
              required
              label="顺序号"
              name="orderNum"
              v-model:value="menuModalData.form.orderNum"
            ></h-input>
          </a-col>
          <a-col :span="12">
            <h-select
              label="是否隐藏"
              name="isHidden"
              v-model:value="menuModalData.form.isHidden"
              :columns="isHideDict"
            ></h-select>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </section>
</template>

<script setup lang="ts">
import { ref, getCurrentInstance } from "vue";
import {
  sysMenuPageApi,
  sysMenuColumnApi,
  sysMenuExportApi,
  sysMenuInitApi,
  sysMenuViewApi,
  sysMenuUpdateApi,
  sysMenuDeleteApi,
} from "@/api/sysadmin/sysMenuApi";
import { isHideDict } from "@/config/dict";
import { MENUS } from "@/config/menuInit";
import { message } from "ant-design-vue";
import { sysMenuUpdateHiddenApi } from "@/api/sysadmin/sysMenuApi";

const { proxy } = getCurrentInstance() as any;

interface SearchForm {
  app: string;
  name: string;
  title: string;
  isHidden: string;
}

let searchForm = ref<SearchForm>({
  app: "",
  name: "",
  title: "",
  isHidden: "",
});

let selectedRowKeys = ref<string[]>([]);

const tableRef = ref();
const loadData = (params: any, callbacks: any) => {
  callbacks(sysMenuPageApi({ ...params, ...searchForm.value }));
  selectedRowKeys.value = [];
};
const onSearch = () => {
  tableRef.value.load(1);
};

const onReset = () => {
  tableRef.value.load(1);
};

const selectionChange = (keys: string[]) => {
  selectedRowKeys.value = keys;
};
const onInit = async () => {
  const res = await sysMenuInitApi(MENUS);
  if (res) {
    message.success("初始化成功!");
    tableRef.value.load(1);
  }
};
const exportRef = ref();
const onShowRecordExport = () => {
  exportRef.value.show();
};
const loadColumn = (callbacks: any) => {
  callbacks(sysMenuColumnApi);
};
const onExport = async (params: any) => {
  const data = await sysMenuExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统目录");
};

let menuModalData = ref<any>({
  visible: false,
  type: "0",
  form: {},
});
const onUpdateRecord = async (id: string) => {
  const res = await sysMenuViewApi(id);
  const { orderNum } = res;
  menuModalData.value.form = { ...res, orderNum: orderNum + "" };
  menuModalData.value.type = "1";
  menuModalData.value.visible = true;
};
const onCancelMenuModal = () => {
  menuModalData.value.visible = false;
  menuModalData.value.form = {};
};
const onSubmitMenuModal = async () => {
  const res = await sysMenuUpdateApi(menuModalData.value.form);
  if (res) {
    message.success("更新成功!");
    menuModalData.value.visible = false;
    tableRef.value.load(1);
  }
};

const onDeleteRecord = async (recordId: string | string[]) => {
  const id = typeof recordId === "string" ? [recordId] : recordId;
  sysMenuDeleteApi(id).then((res) => {
    if (res) {
      tableRef.value.load();
      message.success("操作成功！");
    }
  });
};

const onChangeHidden = (hidden: any, id: string) => {
  sysMenuUpdateHiddenApi({
    id: id,
    hidden: hidden,
  }).then((res) => {
    if (res) {
      message.success("操作成功");
      tableRef.value.load();
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
