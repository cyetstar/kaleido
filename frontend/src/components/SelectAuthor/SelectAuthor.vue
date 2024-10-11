<!--
 * @Author: gongxiaofei
 * @Date: 2023-07-03 17:29:00
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-07-03 17:29:01
 * @Description: 
-->
<template>
  <a-form-item :label="label" :name="name" :rules="innerRules">
    <a-select
      v-model:value="selectVal"
      :filter-option="false"
      v-bind="$attrs"
      :not-found-content="fetching ? undefined : null"
      :options="options"
      show-search
      @search="search"
    >
      <template v-if="fetching" #notFoundContent>
        <a-spin size="small" />
      </template>
    </a-select>
  </a-form-item>
</template>

<script>
import { computed, defineComponent, onMounted, ref } from "vue";
import { Select, FormItem, Spin } from "ant-design-vue";
import { apiAuthorPage } from "@/api/authorApi";
import { isNotEmpty } from "@ht/util";

export default defineComponent({
  name: "KSelectAuthor",
  components: {
    ASelect: Select,
    AFormItem: FormItem,
    ASpin: Spin,
  },
  props: {
    label: String,
    name: String,
    rules: {
      type: Array,
      default: [],
    },
    value: [String, Array, Number],
    required: Boolean,
  },

  setup(props, { emit }) {
    let options = ref([]);
    let fetching = false;
    let selectVal = computed({
      get: () => {
        return props.value;
      },
      set: (val) => {
        emit("update:value", val);
      },
    });

    let innerRules = computed(() => {
      let rules = [...props.rules];
      if (props.required) {
        rules.push({
          required: true,
          message: `请选择${props.label || ""}`,
        });
      }
      return rules;
    });

    const initData = () => {
      if (isNotEmpty(props.value)) {
        let ids = props.value;
        if (Array.isArray(props.value)) {
          ids = props.value.join(",");
        }
        apiAuthorPage({ ids }).then((res) => {
          options.value = res.records.map((s) => {
            return {
              value: s.id + "",
              label: `${s.name}`,
            };
          });
        });
      }
    };

    const search = (v) => {
      fetching = true;
      apiAuthorPage({ keyword: v }).then((res) => {
        fetching = false;
        options.value = res.records.map((s) => {
          return {
            value: s.id + "",
            label: `${s.name}`,
          };
        });
      });
    };

    onMounted(() => {
      initData();
    });

    return {
      search,
      innerRules,
      options,
      selectVal,
      fetching,
    };
  },
});
</script>

<style lang="less" scoped></style>
