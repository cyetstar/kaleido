<template>
  <h-form-modal
    ref="refFormModal"
    :form="form"
    title="角色授权"
    width="650px"
    @submit="onSubmit"
  >
    <h-col :span="24">
      <a-collapse>
        <a-collapse-panel
          v-for="(value, key) of resources"
          :key="key"
          :header="key"
        >
          <p>
            <h-checkbox
              :columns="value"
              :field-names="{ label: 'name', value: 'id' }"
              v-model:value="roleResources[key]"
            />
          </p>
        </a-collapse-panel>
      </a-collapse>
    </h-col>
  </h-form-modal>
</template>

<script setup>
import {
  apiSysResourceListType,
  apiSysResourceListTypeByRoleId,
} from "@/api/sysadmin/sysResourceApi";
import { apiSysRoleAuthorize } from "@/api/sysadmin/sysRoleApi";
import { reactive, ref } from "vue";
import { message } from "ant-design-vue";

const refFormModal = ref("");
const form = ref({});
const resources = ref({});
const roleResources = ref({});

const show = (id) => {
  form.value.id = id;
  Promise.all([
    apiSysResourceListType(),
    apiSysResourceListTypeByRoleId(id),
  ]).then(([data1, data2]) => {
    resources.value = data1;
    for (let value in data2) {
      roleResources.value[value] = data2[value].map((s) => s.id);
    }
    console.log(resources.value);
    console.log(roleResources.value);
    refFormModal.value.show();
  });
};

const onSubmit = () => {
  let resourceIdList = [];
  for (let value in roleResources.value) {
    resourceIdList = resourceIdList.concat(roleResources.value[value]);
  }
  form.value.resourceIdList = resourceIdList;
  apiSysRoleAuthorize(form.value).then(() => {
    message.success("操作成功");
    refFormModal.value.hide();
  });
};

defineExpose({
  show,
});
</script>

<style scoped></style>
