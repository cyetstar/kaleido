<!--
 * @Author: cyetstar
 * @Date: 2023-12-29 16:56:31
 * @Description: 豆瓣电影口碑榜表单页面
-->
<template>
    <h-form-modal ref="formRef" :label-col="{ span: 4 }" width="600px" v-model:form="form" title="豆瓣电影口碑榜"
                  @submit="onSubmit">
                <h-col :span="24">
                        <h-input label="电影名" v-model:value="form.title"
                                 name="title"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="原片名" v-model:value="form.originalTitle"
                                 name="originalTitle"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="首映年份" v-model:value="form.year"
                                 name="year"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="海报" v-model:value="form.thumb"
                                 name="thumb"/>
                </h-col>
                <h-col :span="24">
                        <h-select-datetime label="上榜日期"
                                           v-model:value="form.listingDate" name="listingDate"/>
                </h-col>
                <h-col :span="24">
                        <h-select-datetime label="下榜日期"
                                           v-model:value="form.delistingDate" name="delistingDate"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="最高名次" v-model:value="form.top"
                                 name="top"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="备注" v-model:value="form.memo"
                                 name="memo"/>
                </h-col>
    </h-form-modal>
</template>

<script setup>
  import {ref} from "vue";
  import {message} from "ant-design-vue";

  const emits = defineEmits(["save-complete"]);

    let formAction = ref("create");

    let formRef = ref();

    let form = ref({
        id: "",
        title: "",
        originalTitle: "",
        year: "",
        thumb: "",
        listingDate: "",
        delistingDate: "",
        top: "",
        memo: "",
    });

    const create = () => {
        formAction.value = "create";
        formRef.value.reset();
        formRef.value.show();
    };

    const update = async (id) => {
        formAction.value = "update";
        formRef.value.reset();
        form.value = await apiMovieDoubanWeeklyView({id});
        formRef.value.show();
    };

    const onSubmit = async () => {
        try {
            if (formAction.value === "create") {
                let res = await apiMovieDoubanWeeklyCreate(form.value);
                if (res) {
                    message.success("操作成功");
                    emits("save-complete");
                    formRef.value.hide();
                }
            } else if (formAction.value === "update") {
                let res = await apiMovieDoubanWeeklyUpdate(form.value);
                if (res) {
                    message.success("操作成功");
                    emits("save-complete");
                    formRef.value.hide();
                }
            }
        } catch (e) {
        }
    };

    defineExpose({
        create,
        update,
    });
</script>

<style lang="less" scoped></style>
