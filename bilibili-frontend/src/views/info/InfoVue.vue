<template>
  <div>
    <Navigate type="top"/>
    <div style="height: 172px; width: 100%;">
      <div style="width: 100%; height: 64px;"></div>
      <div style="width: 100%; height: 108px;">
      </div>
    </div>
    <video-player/>
  </div>
</template>

<script setup lang="ts">
import {defineProps, onMounted, ref, watch} from 'vue';
import {getInfoApi} from "@/api/info";
import type {Info} from "../../../type/info";
import router from "@/router";
import VideoPlayer from "@/components/VideoPlayer.vue";
import Navigate from "@/components/Navigate.vue";

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
});

const pageInfo = ref<Info>({
  id : 0,
  title : '',
  description : '',
  previewUrl : '',
  videoUrl : '',
  type : '',
  createTime : '',
  createBy : ''
})

const getInfo = () => {
  getInfoApi(props.id).then((data) => {
    if (data.code === 200)
      pageInfo.value = data.data
    else
      router.push('/404/NotFind')
  })
}

onMounted(() => {
  getInfo()
})

watch(() => props.id, (newId, oldId) => {
  getInfo();
});
</script>