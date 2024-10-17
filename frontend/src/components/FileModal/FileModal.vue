<!--
 * @Author: gongxiaofei
 * @Date: 2023-07-03 17:29:00
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-03 17:29:01
 * @Description: 
-->
<template>
  <a-modal
    ref="refModal"
    v-model:visible="visible"
    :title="title"
    :width="width"
    zIndex="100"
  >
    <div class="flex">
      <a-space class="mr-2">
        <h-button small @click="onBack" :disabled="history.length === 0">
          <left-outlined />
        </h-button>
        <h-button small @click="onRefresh">
          <reload-outlined />
        </h-button>
      </a-space>
      <a-input v-model:value="searchForm.path" readonly />
      <a-space class="ml-2">
        <h-button small @click="onNewDirectory">
          <folder-add-outlined />
        </h-button>
        <a-upload
          name="file"
          :showUploadList="false"
          :custom-request="onUpload"
        >
          <h-button small>
            <upload-outlined />
          </h-button>
        </a-upload>
        <h-button @click="onCopy" :disabled="selectedRows.length === 0">
          <copy-outlined />
          <span v-if="copyOrCutPaths.copy === true"
            >({{ copyOrCutPaths.pathList.length }})</span
          >
        </h-button>
        <h-button @click="onCut" :disabled="selectedRows.length === 0">
          <scissor-outlined />
          <span v-if="copyOrCutPaths.copy === false"
            >({{ copyOrCutPaths.pathList.length }})</span
          >
        </h-button>
        <h-button v-if="canPaste" @click="onPaste">
          <snippets-outlined />
        </h-button>
        <h-button @click="onDelete" :disabled="selectedRows.length === 0">
          <delete-outlined />
        </h-button>
      </a-space>
    </div>
    <h-table-data
      ref="refTableData"
      :show-index="false"
      :show-pagination="false"
      :page-size="100"
      @load-data="onLoadData"
      class="my-4"
      @selection-change="onSelectionChange"
      :scroll="{ y: 400 }"
    >
      <a-table-column
        title="名称"
        width="540px"
        data-index="name"
        sorter="true"
      >
        <template #="{ record }">
          <div v-if="record.editable" class="flex items-center">
            <a-input
              class="flex-1"
              v-model:value="record.name"
              @keyup.enter="onRename(record)"
            />
            <check-outlined class="ml-2" @click="onRename(record)" />
          </div>
          <div
            v-else
            class="flex justify-between items-center"
            :class="canOpen(record) ? 'cursor-pointer' : 'cursor-not-allowed'"
          >
            <span
              class="break-all"
              @click="canOpen(record) ? onOpen(record) : null"
            >
              <folder-filled
                v-if="record.isDir"
                class="mr-2"
                :style="{
                  fontSize: '20px',
                  color: '#1890ff',
                }"
              />
              {{ record.name }}</span
            >
            <edit-outlined @click="record.editable = true" />
          </div>
        </template>
      </a-table-column>
      <a-table-column
        title="修改日期"
        data-index="lastModified"
        align="center"
        sorter="true"
      >
      </a-table-column>
      <a-table-column
        title="大小"
        data-index="length"
        align="center"
        sorter="true"
      >
        <template #="{ record }">
          <span v-if="!record.isDir">{{ record.lengthLabel }}</span>
        </template>
      </a-table-column>
    </h-table-data>
    <template #footer>
      <h-button @click="visible = false" class="mr-2">关闭</h-button>
      <slot name="footer"></slot>
    </template>
  </a-modal>
</template>

<script>
import { computed, defineComponent, ref } from "vue";
import { message, Modal, Input, Table } from "ant-design-vue";
import {
  SnippetsOutlined,
  ScissorOutlined,
  DeleteOutlined,
  CopyOutlined,
  UploadOutlined,
  FolderAddOutlined,
  CheckOutlined,
  EditOutlined,
  FolderFilled,
  LeftOutlined,
  ReloadOutlined,
} from "@ant-design/icons-vue";
import { HButton, HTableData } from "hta-ui";
import {
  apiFileCopyOrCut,
  apiFileDelete,
  apiFileNewDirectory,
  apiFileOpen,
  apiFilePage,
  apiFileRename,
  apiFileUpload,
} from "@/api/sysadmin/fileApi";
import { isNotEmpty } from "@ht/util";

export default defineComponent({
  name: "KFileModal",
  methods: { isNotEmpty },
  components: {
    AModal: Modal,
    AInput: Input,
    ATableColumn: Table.Column,
    CheckOutlined,
    EditOutlined,
    FolderFilled,
    LeftOutlined,
    ReloadOutlined,
    UploadOutlined,
    FolderAddOutlined,
    CopyOutlined,
    SnippetsOutlined,
    ScissorOutlined,
    DeleteOutlined,
    HButton,
    HTableData,
  },
  props: {
    title: {
      type: String,
      default: "文件管理",
    },
    width: {
      type: Number,
      default: 960,
    },
  },

  setup(props, { emit }) {
    let visible = ref(false);
    const searchForm = ref({});
    const history = ref([]);
    const selectedRows = ref([]);
    const refTableData = ref();
    const copyOrCutPaths = ref([]);

    const canPaste = computed(() => {
      const pathList = copyOrCutPaths.value.pathList;
      return isNotEmpty(pathList);
    });

    const canOpen = (record) => {
      if (record.isDir) {
        return true;
      }
      const mediaType = record.mediaType;
      if (mediaType && mediaType.indexOf("image") !== -1) {
        return true;
      }
      if (mediaType && mediaType.indexOf("text") !== -1) {
        return true;
      }
      return false;
    };

    const show = (path) => {
      searchForm.value.path = path;
      visible.value = true;
      load();
    };

    const close = () => {
      visible.value = false;
    };

    const load = () => {
      refTableData.value.load(1);
    };

    const onLoadData = (params, callbacks) => {
      callbacks(
        apiFilePage({ ...params, ...searchForm.value, searchCount: true })
      );
    };

    const onSelectionChange = (keys, rows) => {
      selectedRows.value = rows;
    };

    const onOpen = (record) => {
      if (!canOpen(record)) {
        return;
      }
      if (record.isDir) {
        history.value.push(searchForm.value.path);
        searchForm.value.path = record.path;
        refTableData.value.load(1);
      } else {
        apiFileOpen({ path: record.path });
      }
    };

    const onBack = () => {
      searchForm.value.path = history.value.pop();
      refTableData.value.load(1);
    };

    const onRefresh = () => {
      refTableData.value.load(1);
    };

    const onRename = (record) => {
      record.editable = false;
      let pathArray = record.path.split("/").slice(0, -1);
      pathArray.push(record.name);
      let newPath = pathArray.join("/");
      if (record.path === newPath) {
        return;
      }
      apiFileRename({
        path: record.path,
        newPath,
      }).then((res) => {
        if (res) {
          refTableData.value.load(1);
        } else {
          message.error("重命名失败");
        }
      });
    };

    const onNewDirectory = () => {
      apiFileNewDirectory(searchForm.value).then((res) => {
        if (res) {
          refTableData.value.load(1);
        } else {
          message.error("新增文件夹失败");
        }
      });
    };

    const onCopy = () => {
      copyOrCutPaths.value = {
        pathList: selectedRows.value.map((s) => s.path),
        copy: true,
      };
    };

    const onCut = () => {
      copyOrCutPaths.value = {
        pathList: selectedRows.value.map((s) => s.path),
        copy: false,
      };
    };

    const onPaste = () => {
      const data = copyOrCutPaths.value;
      data.destPath = searchForm.value.path;
      apiFileCopyOrCut(data).then((res) => {
        if (res) {
          refTableData.value.load(1);
        } else {
          message.error("粘贴失败");
        }
        copyOrCutPaths.value.pathList = [];
        copyOrCutPaths.value.copy = null;
      });
    };
    const onDelete = () => {
      Modal.confirm({
        title: '确认删除所选文件吗?',
        onOk() {
          let path = selectedRows.value.map((s) => s.path);
          apiFileDelete(path).then((res) => {
            if (res) {
              refTableData.value.load(1);
            } else {
              message.error("删除失败");
            }
          });
        },
        onCancel() {
        },
        class: 'test',
      });

    };

    const onUpload = (options) => {
      let path = searchForm.value.path;
      apiFileUpload({
        path,
        file: options.file,
      }).then((res) => {
        if (res) {
          refTableData.value.load(1);
        } else {
          message.error("上传失败");
        }
      });
    };

    return {
      visible,
      searchForm,
      history,
      canPaste,
      canOpen,
      selectedRows,
      refTableData,
      copyOrCutPaths,

      onLoadData,
      onSelectionChange,
      onRefresh,
      onBack,
      onOpen,
      onRename,
      onNewDirectory,
      onCopy,
      onCut,
      onPaste,
      onDelete,
      onUpload,
      show,
      close,
      load,
    };
  },
});
</script>

<style lang="less" scoped></style>
