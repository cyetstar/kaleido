/*
 * @Author: gongxiaofei
 * @Date: 2022-08-16 13:06:36
 * @LastEditors: ximusunian ximusunian@gmail.com
 * @LastEditTime: 2023-10-09 09:43:13
 * @Description:
 */
import type { App } from "vue";
import {
  Layout,
  Button,
  Input,
  Checkbox,
  Form,
  Table,
  Dropdown,
  Menu,
  Tree,
  Row,
  Col,
  Space,
  Popconfirm,
  Modal,
  Pagination,
  PageHeader,
  Progress,
  Select,
  Radio,
  Upload,
  Cascader,
  Avatar,
  Anchor,
  Image,
  Card,
  Tabs,
  Collapse,
  InputNumber,
  Switch,
  TimePicker,
  DatePicker,
  Rate,
  Slider,
  Divider,
  Spin,
  Tag,
  Empty,
  Popover,
  Steps,
  Drawer,
  Tooltip,
  Timeline,
  Affix,
  AutoComplete,
  Badge,
} from "ant-design-vue";

import {
  HButton,
  HButtonDelete,
  HCheckbox,
  HCol,
  HDatePicker,
  HFormModal,
  HFormSearch,
  HInput,
  HInputGmsfhm,
  HInputPassword,
  HInputTel,
  HModalExport,
  HModuleTitle,
  HRadio,
  HSelect,
  HSelectSsxq,
  HTableData,
  HUploadImage,
} from "hta-ui";

import GlobalConfig from "@/components/GlobalConfig";
import ThumbImage from "@/components/ThumbImage";
import LogoLink from "@/components/LogoLink";
import FileModal from "@/components/FileModal";
import ActionButton from "@/components/ActionButton";
import SelectActor from "@/components/SelectActor";
import SelectAuthor from "@/components/SelectAuthor";

export const registerGlobComp = (app: App) => {
  app
    .component("h-button", HButton)
    .component("h-button-delete", HButtonDelete)
    .component("h-checkbox", HCheckbox)
    .component("h-col", HCol)
    .component("h-date-picker", HDatePicker)
    .component("h-form-modal", HFormModal)
    .component("h-form-search", HFormSearch)
    .component("h-input", HInput)
    .component("h-input-gmsfhm", HInputGmsfhm)
    .component("h-input-password", HInputPassword)
    .component("h-input-tel", HInputTel)
    .component("h-modal-export", HModalExport)
    .component("h-module-title", HModuleTitle)
    .component("h-radio", HRadio)
    .component("h-select", HSelect)
    .component("h-select-ssxq", HSelectSsxq)
    .component("h-table-data", HTableData)
    .component("h-upload-image", HUploadImage)

    .component("k-action-button", ActionButton)
    .component("k-file-modal", FileModal)
    .component("k-global-config", GlobalConfig)
    .component("k-thumb-image", ThumbImage)
    .component("k-logo-link", LogoLink)
    .component("k-select-actor", SelectActor)
    .component("k-select-author", SelectAuthor)
    .use(Layout)
    .use(Button)
    .use(Input)
    .use(Checkbox)
    .use(Form)
    .use(Table)
    .use(Dropdown)
    .use(Menu)
    .use(Tree)
    .use(Row)
    .use(Col)
    .use(Space)
    .use(Popconfirm)
    .use(Modal)
    .use(Pagination)
    .use(PageHeader)
    .use(Progress)
    .use(Select)
    .use(Radio)
    .use(Upload)
    .use(Cascader)
    .use(Avatar)
    .use(Anchor)
    .use(Image)
    .use(Card)
    .use(Tabs)
    .use(Collapse)
    .use(InputNumber)
    .use(Switch)
    .use(TimePicker)
    .use(DatePicker)
    .use(Rate)
    .use(Slider)
    .use(Divider)
    .use(Spin)
    .use(Tag)
    .use(Empty)
    .use(Popover)
    .use(Steps)
    .use(Drawer)
    .use(Tooltip)
    .use(Timeline)
    .use(Affix)
    .use(AutoComplete)
    .use(Badge);
};
