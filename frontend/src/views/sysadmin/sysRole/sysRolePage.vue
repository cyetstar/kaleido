<!--
 * @Author: gongxiaofei
 * @Date: 2023-05-26 16:13:23
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 15:34:02
 * @Description: 角色管理
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
        <h-input v-model:value="searchForm.name" label="角色名称" name="name" />
      </a-col>
      <a-col :span="5">
        <h-input v-model:value="searchForm.code" label="角色代码" name="code" />
      </a-col>
    </h-form-search>
    <a-space class="h-btn-space">
      <h-button
        v-permissKey="'sysRole:create'"
        type="primary"
        @click="onAddRecord"
        >新增
      </h-button>
      <h-button-delete
        v-permissKey="'sysRole:delete'"
        type="danger"
        plain
        :disabled="selectedRowKeys.length === 0"
        @delete="onDeleteRecord(selectedRowKeys)"
      >
        删除
      </h-button-delete>
      <h-button v-permissKey="'sysRole:export'" @click="onShowExportRecord"
        >导出
      </h-button>
    </a-space>
    <h-table-data
      ref="tableRef"
      row-key="id"
      @loadData="loadData"
      @selection-change="selectionChange"
    >
      <a-table-column title="角色名称" data-index="name"></a-table-column>
      <a-table-column title="角色代码" data-index="code"></a-table-column>
      <a-table-column title="简介" data-index="description"></a-table-column>
      <a-table-column title="操作" key="action">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              type="primary"
              link
              plain
              size="small"
              v-permissKey="'sysRole:update'"
              @click="onUpdateRecord(record.id)"
            >
              编辑
            </h-button>
            <h-button-delete
              link
              plain
              size="small"
              v-permissKey="'sysRole:delete'"
              @delete="onDeleteRecord(record.id)"
            >
              删除
            </h-button-delete>
            <h-button
              type="primary"
              link
              plain
              size="small"
              v-permissKey="'sysRole:other'"
              @click="onClickAuthorize(record.id)"
            >
              权限
            </h-button>
          </a-space>
        </template>
      </a-table-column>
    </h-table-data>

    <a-modal
      v-model:visible="roleModalData.visible"
      :title="roleModalData.type === '0' ? '新增角色' : '编辑角色'"
      width="800px"
      @ok="onSubmitRoleModal"
    >
      <a-form
        :model="roleModalData.form"
        :label-col="{ span: 8 }"
        ref="roleFormRef"
      >
        <a-row>
          <a-col :span="12">
            <h-input
              :disabled="roleModalData.type === '1'"
              required
              label="角色代码"
              name="code"
              v-model:value="roleModalData.form.code"
            ></h-input>
          </a-col>
          <a-col :span="12">
            <h-input
              required
              label="角色名称"
              name="name"
              v-model:value="roleModalData.form.name"
            ></h-input>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <h-input
              required
              label="简介"
              name="description"
              v-model:value="roleModalData.form.description"
            ></h-input>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>

    <a-modal
      v-model:visible="permissionModalData.visible"
      title="角色授权"
      width="800px"
      @cancel="onCancelPermissionModal"
      @ok="onSubmitPermissionModal"
    >
      <a-collapse>
        <a-collapse-panel
          v-for="(item, index) of permissionModalData.roleResources"
          :key="index"
          :header="`${item.type}${findName(item.type)}`"
        >
          <div>
            <a-checkbox
              v-model:checked="item.checkAll"
              @change="onCheckAllChange($event, item.actions)"
            >
              选择全部
            </a-checkbox>
            <div class="mt-10px">
              <a-checkbox
                @change="onCheckItemChange(item)"
                v-for="(item2, index2) in item.actions"
                v-model:checked="item2.status"
                >{{ item2.action }}
              </a-checkbox>
            </div>
          </div>
        </a-collapse-panel>
      </a-collapse>
    </a-modal>

    <h-modal-export
      ref="exportRef"
      @loadColumn="loadColumn"
      @export="onExport"
    ></h-modal-export>

    <sys-role-form-authorize ref="refSysRoleFormAuthorize" />
  </section>
</template>

<script setup lang="ts">
import { ref, getCurrentInstance } from "vue";
import {
  sysRolePageApi,
  sysRoleDeleteApi,
  sysRoleCreateApi,
  sysRoleUpdateApi,
  sysRoleViewApi,
  sysRoleColumnApi,
  sysRoleExportApi,
} from "@/api/sysadmin/sysRoleApi";
import { ALL_RESOURCE } from "@/config/resourceInit";
import { message } from "ant-design-vue";
import SysRoleFormAuthorize from "@/views/sysadmin/sysRole/sysRoleFormAuthorize.vue";

const { proxy } = getCurrentInstance() as any;

interface SearchForm {
  name: string;
  code: string;
}

let searchForm = ref<SearchForm>({
  name: "",
  code: "",
});

const refSysRoleFormAuthorize = ref("");

const loadData = (params: any, callbacks: any) => {
  callbacks(sysRolePageApi({ ...params, ...searchForm.value }));
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

const roleFormRef = ref();
let roleModalData = ref<any>({
  visible: false,
  type: "0",
  form: {},
});
const onAddRecord = () => {
  roleModalData.value.type = "0";
  roleModalData.value.visible = true;
};
const onUpdateRecord = async (id: string) => {
  const res = await sysRoleViewApi(id);
  roleModalData.value.type = "1";
  roleModalData.value.form = res;
  roleModalData.value.visible = true;
};
const onClickAuthorize = (id: string) => {
  refSysRoleFormAuthorize.value.show(id);
};
const onSubmitRoleModal = async () => {
  try {
    const res =
      roleModalData.value.type === "0"
        ? await sysRoleCreateApi(roleModalData.value.form)
        : await sysRoleUpdateApi(roleModalData.value.form);
    if (res) {
      message.success(
        roleModalData.value.type === "0" ? "新增成功" : "编辑成功"
      );
      tableRef.value.load(1);
    }
  } catch (e) {}
};

let selectedRowKeys = ref<string[]>([]);
const onDeleteRecord = async (recordId: string | string[]) => {
  let id = Array.isArray(recordId) ? recordId : [recordId];
  let res = await sysRoleDeleteApi(id);
  if (res) {
    tableRef.value.load();
    message.success("删除成功！");
  }
};

const exportRef = ref();
const onShowExportRecord = () => {
  exportRef.value.show();
};
const loadColumn = (callbacks: any) => {
  callbacks(sysRoleColumnApi);
};
const onExport = async (params: any) => {
  const data = await sysRoleExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统角色");
};

let permissionModalData = ref<any>({
  visible: false,
  roleId: "",
  roleResources: [],
});
const onPermission = async (id: string) => {
  permissionModalData.value.roleId = id;
  const res = await sysResourceApi(id);
  permissionModalData.value.roleResources = res.reduce(
    (list: any, item: any) => {
      list.push({
        ...item,
        checkAll: item.actions.every((item2: any) => item2.status),
      });
      return list;
    },
    []
  );
  permissionModalData.value.visible = true;
};
const findName = (type: string) => {
  let res = [...ALL_RESOURCE].find((item: any) => item.type === type);
  if (res) {
    return "-" + res.name;
  }
  return "";
};
const onCheckAllChange = (e: any, actions: any) => {
  actions.forEach((item: any) => {
    item.status = e.target.checked;
  });
};
const onCheckItemChange = (item: any) => {
  item.checkAll = item.actions.every((item2: any) => item2.status);
};
const onCancelPermissionModal = () => {
  permissionModalData.value.visible = false;
};
const onSubmitPermissionModal = async () => {
  let resourceIds: any[] = [];
  permissionModalData.value.roleResources.forEach((item: any) => {
    item.actions.forEach((item2: any) => {
      if (item2.status) resourceIds.push(item2.id);
    });
  });
  try {
    const res = await sysRoleResourceBindApi({
      roleId: permissionModalData.value.roleId,
      resourceIds: resourceIds,
    });
    if (res) {
      message.success("授权成功");
      permissionModalData.value.visible = false;
    }
  } catch (e) {}
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
