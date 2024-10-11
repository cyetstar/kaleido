<!--
 * @Author: cyetstar
 * @Date: 2023-11-20 22:35:49
 * @Description: 艺术家表单页面
-->
<template>
    <h-form-modal ref="formRef" :label-col="{ span: 4 }" width="600px" v-model:form="form" title="艺术家"
                  @submit="onSubmit">
                <h-col :span="24">
                        <h-input label="MusicBrainz编号" v-model:value="form.musicbrainzId"
                                 name="musicbrainzId"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="Plex编号" v-model:value="form.plexId"
                                 name="plexId"/>
                </h-col>
                <h-col :span="24">
                        <h-select-datetime label="创建时间" type="datetime"
                                           v-model:value="form.cjsj" name="cjsj"/>
                </h-col>
                <h-col :span="24">
                        <h-select-datetime label="修改时间" type="datetime"
                                           v-model:value="form.xgsj" name="xgsj"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="Plex缩略图" v-model:value="form.plexThumb"
                                 name="plexThumb"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="网易云音乐编号" v-model:value="form.neteaseId"
                                 name="neteaseId"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="名称" v-model:value="form.name"
                                 name="name"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="国家地区" v-model:value="form.area"
                                 name="area"/>
                </h-col>
                <h-col :span="24">
                        <h-input label="简介" v-model:value="form.summary"
                                 name="summary"/>
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
        musicbrainzId: "",
        plexId: "",
        cjsj: "",
        xgsj: "",
        plexThumb: "",
        neteaseId: "",
        name: "",
        area: "",
        summary: "",
    });

    const create = () => {
        formAction.value = "create";
        formRef.value.reset();
        formRef.value.show();
    };

    const update = async (id) => {
        formAction.value = "update";
        formRef.value.reset();
        form.value = await apiMusicArtistView({id});
        formRef.value.show();
    };

    const onSubmit = async () => {
        try {
            if (formAction.value === "create") {
                let res = await apiMusicArtistCreate(form.value);
                if (res) {
                    message.success("操作成功");
                    emits("save-complete");
                    formRef.value.hide();
                }
            } else if (formAction.value === "update") {
                let res = await apiMusicArtistUpdate(form.value);
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
