<!--
 * @Author: gongxiaofei
 * @Date: 2023-05-26 09:51:16
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-12 15:34:46
 * @Description: 用户管理
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
        <h-input
          v-model:value="searchForm.username"
          label="用户名"
          name="username"
        />
      </a-col>
      <a-col :span="5">
        <h-select
          v-model:value="searchForm.enabled"
          label="状态"
          name="enabled"
          :columns="sysUserStatusDict"
        ></h-select>
      </a-col>
    </h-form-search>
    <a-space class="h-btn-space">
      <h-button
        v-permissKey="'sysUser:create'"
        type="primary"
        @click="onAddUser"
        >新增</h-button
      >
      <a-popconfirm
        title="确定删除选中的用户吗？"
        ok-text="确定"
        cancel-text="取消"
        @confirm="onDeleteRecord"
      >
        <h-button
          v-permissKey="'sysUser:delete'"
          type="danger"
          plain
          :disabled="selectedRowKeys.length === 0"
          >删除
        </h-button>
      </a-popconfirm>
      <h-button v-permissKey="'sysUser:export'" @click="showExport"
        >导出</h-button
      >
    </a-space>
    <h-table-data
      ref="tableRef"
      row-key="id"
      @loadData="loadData"
      @selection-change="selectionChange"
    >
      <a-table-column title="用户名" data-index="username"></a-table-column>
      <a-table-column title="角色" data-index="sysRoleList">
        <template #="{ text: sysRoleList }">
          {{ showRoleName(sysRoleList) }}
        </template>
      </a-table-column>
      <a-table-column title="启用状态" data-index="isAccountNonLocked">
        <template #="{ record }">
          <a-switch
            v-model:checked="record.enabled"
            @change="(val: any) => onChangeEnable(val, record.id)"
          />
        </template>
      </a-table-column>
      <a-table-column title="操作" data-index="action">
        <template #="{ record }">
          <a-space :size="0">
            <h-button
              type="primary"
              size="small"
              link
              v-permissKey="'sysUser:update'"
              @click="onUpdateRecord(record.id)"
              >编辑</h-button
            >
            <h-button-delete
              size="small"
              link
              v-permissKey="'sysUser:delete'"
              @delete="onDeleteRecord(record.id)"
              >删除</h-button-delete
            >
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
      v-model:visible="userModalData.visible"
      :title="userModalData.type === '0' ? '新增用户' : '编辑用户'"
      width="800px"
      @cancel="onCancelUserMOdal"
      @ok="onSubmitUserModal"
      :destroyOnClose="true"
    >
      <a-form
        :model="userModalData.form"
        :label-col="{ span: 8 }"
        ref="userFormRef"
      >
        <div v-if="userModalData.type === '0'">
          <h-col :span="12" :right-offset="12">
            <h-input
              read-only
              label="用户名"
              placeholder="请输入用户名"
              name="username"
              v-model:value="userModalData.form.username"
              :unique="checkUsername"
              required
            ></h-input>
          </h-col>
          <a-row>
            <h-col :span="12">
              <h-input-password
                required
                label="密码"
                name="password"
                v-model:value="userModalData.form.password"
              ></h-input-password>
            </h-col>
            <h-col :span="12">
              <h-input-password
                required
                label="重复密码"
                name="password2"
                :password="userModalData.form.password"
                v-model:value="userModalData.form.password2"
              ></h-input-password>
            </h-col>
          </a-row>
        </div>
        <a-row>
          <a-col :span="12">
            <h-input
              required
              label="姓名"
              name="name"
              v-model:value="userModalData.form.name"
            ></h-input>
          </a-col>
          <a-col :span="12">
            <h-input-tel
              label="联系电话"
              name="mobile"
              v-model:value="userModalData.form.mobile"
            ></h-input-tel>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <h-select-ssxq
              name="deptCode"
              required
              data-type="ga"
              label="所属单位"
              :min-level="12"
              v-model:value="userModalData.form.deptCode"
              :default-value="userModalData.deptCode"
            ></h-select-ssxq>
          </a-col>
          <a-col :span="12">
            <h-select-ssxq
              name="filterCode"
              required
              data-type="ga"
              :min-level="12"
              label="查询范围"
              v-model:value="userModalData.form.filterCode"
              :default-value="userModalData.filterCode"
            ></h-select-ssxq>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <h-select
              required
              mode="multiple"
              label="角色"
              name="roleIds"
              v-model:value="userModalData.form.roleIds"
              :columns="rolesList"
              :fieldNames="{ label: 'name', value: 'id' }"
            ></h-select>
          </a-col>
          <a-col :span="12" v-if="userModalData.type === '1'">
            <h-input-password
              label="密码修改"
              name="password"
              placeholder="输入将修改密码"
              v-model:value="userModalData.form.password"
            ></h-input-password>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
  </section>
</template>

<script setup lang="ts">
import { ref, getCurrentInstance, onMounted } from "vue";
import { sysUserStatusDict } from "@/config/dict";
import {
  sysUserPageApi,
  sysUserDeleteApi,
  sysUserUpdateEnableApi,
  sysUserCheckUniqueApi,
  sysUserColumnApi,
  sysUserExportApi,
  sysUserCreateApi,
  sysUserUpdateApi,
  sysUserViewApi,
} from "@/api/sysadmin/sysUserApi";
import { message } from "ant-design-vue";
import { sysRolePageApi } from "@/api/sysadmin/sysRoleApi";

const { proxy } = getCurrentInstance() as any;

interface SearchForm {
  username: string;
  name: string;
  mobile: string;
  enabled: string;
  deptCode: string;
}

let searchForm = ref<SearchForm>({
  username: "",
  name: "",
  mobile: "",
  enabled: "",
  deptCode: "",
});

const loadData = (params: any, callbacks: any) => {
  callbacks(sysUserPageApi({ ...params, ...searchForm.value }));
  selectedRowKeys.value = [];
};
let selectedRowKeys = ref<string[]>([]);
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

const rolesList = ref<any>([]);
const getRoles = async () => {
  const { records } = await sysRolePageApi({ pageSize: 999, pageNumber: 1 });
  const arr = records.reduce((list: any, item: any) => {
    const { id, ...other } = item;
    list.push({
      id: id + "",
      ...other,
    });
    return list;
  }, []);
  rolesList.value = arr;
};
onMounted(() => {
  getRoles();
});
const onAddUser = async () => {
  userModalData.value.type = "0";
  userModalData.value.visible = true;
};
const onDeleteRecord = async (recordId?: string) => {
  let id = recordId ? [recordId] : selectedRowKeys.value;
  let res = await sysUserDeleteApi(id);
  if (res) {
    tableRef.value.load();
    message.success("操作成功！");
  }
};

const exportRef = ref();
const loadColumn = (callbacks: any) => {
  callbacks(sysUserColumnApi);
};
const showExport = () => {
  exportRef.value.show();
};
const onExport = async (params: any) => {
  const data = await sysUserExportApi({
    ...params,
    ...searchForm.value,
  });
  proxy.$dowloadExcel(data, "系统用户");
};

interface RoleItem {
  name: string;

  [key: string]: string;
}

const showRoleName = (roleList: RoleItem[]) => {
  return roleList.map((s) => s.name).join("/");
};
const onChangeEnable = async (val: any, id: any) => {
  const res = await sysUserUpdateEnableApi({
    userId: id,
    enable: val,
  });
  if (res) {
    message.success("操作成功");
    tableRef.value.load();
  }
};

const onUpdateRecord = async (id: string) => {
  const res = await sysUserViewApi(id);
  const { deptCode, filterCode } = res;
  userModalData.value.form = res;
  userModalData.value.deptCode = deptCode;
  userModalData.value.filterCode = filterCode;
  userModalData.value.type = "1";
  userModalData.value.visible = true;
};

interface UserModalData {
  visible: boolean;
  type: "0" | "1";
  deptCode: string;
  filterCode: string;
  form: {
    username: string;
    name: string;
    mobile: string;
    deptCode: string;
    filterCode: string;
    roleIds: string[];
    password: string;
    password2: string;
  };
}

const userModalData = ref<UserModalData>({
  visible: false,
  type: "0",
  deptCode: "",
  filterCode: "",
  form: {
    username: "",
    name: "",
    mobile: "",
    deptCode: "",
    filterCode: "",
    roleIds: [],
    password: "",
    password2: "",
  },
});

const checkUsername = (val: string) => {
  // 不存在用户名 检验唯一性rule 通过
  if (!val) return true;
  return sysUserCheckUniqueApi(val);
};
// const listDictByType = (callbacks: any) => {
//   callbacks(dictListAllByType);
// };
const userFormRef = ref();
const onCancelUserMOdal = () => {
  userModalData.value.visible = false;
  userModalData.value.form = {
    username: "",
    name: "",
    mobile: "",
    deptCode: "",
    filterCode: "",
    roleIds: [],
    password: "",
    password2: "",
  };
};
const onSubmitUserModal = async () => {
  try {
    const res =
      userModalData.value.type === "0"
        ? await sysUserCreateApi(userModalData.value.form)
        : await sysUserUpdateApi(userModalData.value.form);
    if (res) {
      message.success(
        userModalData.value.type === "0" ? "新增成功" : "编辑成功"
      );
      userModalData.value.visible = false;
      tableRef.value.load(1);
    }
  } catch (e) {}
};

const filterColumns = (item) => {
  return item.value === "xndz";
};
</script>

<style lang="less" scoped></style>
