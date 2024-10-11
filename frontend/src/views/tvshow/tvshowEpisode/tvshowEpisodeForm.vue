<!--
 * @Author: cyetstar
 * @Date: 2023-11-28 01:40:40
 * @Description: 单集表单页面
-->
<template>
    <h-form-modal ref="formRef" :label-col="{ span: 4 }" width="600px" v-model:form="form" title="单集"
                  @submit="onSubmit">
                <h-col :span="24">
                        <h-input label="单季id" v-model:value="form.seasonId"
                                 name="seasonId"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="标题" v-model:value="form.title"
                                 name="title"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="制片公司" v-model:value="form.studio"
                                 name="studio"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="剧集评级" v-model:value="form.contentRating"
                                 name="contentRating"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="简介" v-model:value="form.summary"
                                 name="summary"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="首播年份" v-model:value="form.year"
                                 name="year"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="首播日期" v-model:value="form.originallyAvailableAt"
                                 name="originallyAvailableAt"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="集号" v-model:value="form.episodeIndex"
                                 name="episodeIndex"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="评分" v-model:value="form.rating"
                                 name="rating"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="片长(秒)" v-model:value="form.duration"
                                 name="duration"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="海报" v-model:value="form.thumb"
                                 name="thumb"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="艺术图" v-model:value="form.art"
                                 name="art"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="加入时间" v-model:value="form.addedAt"
                                 name="addedAt"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="更新时间" v-model:value="form.updatedAt"
                                 name="updatedAt"/>
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
        seasonId: "",
        title: "",
        studio: "",
        contentRating: "",
        summary: "",
        year: "",
        originallyAvailableAt: "",
        episodeIndex: "",
        rating: "",
        duration: "",
        thumb: "",
        art: "",
        addedAt: "",
        updatedAt: "",
    });

    const create = () => {
        formAction.value = "create";
        formRef.value.reset();
        formRef.value.show();
    };

    const update = async (id) => {
        formAction.value = "update";
        formRef.value.reset();
        form.value = await apiTvshowEpisodeView({id});
        formRef.value.show();
    };

    const onSubmit = async () => {
        try {
            if (formAction.value === "create") {
                let res = await apiTvshowEpisodeCreate(form.value);
                if (res) {
                    message.success("操作成功");
                    emits("save-complete");
                    formRef.value.hide();
                }
            } else if (formAction.value === "update") {
                let res = await apiTvshowEpisodeUpdate(form.value);
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
