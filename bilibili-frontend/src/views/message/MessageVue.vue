<script setup lang="ts">
import Navigate from "@/components/Navigate.vue";
import {onMounted, ref} from "vue";
import request from "@/net";

const url1 = ref('')

onMounted(() => {
  request.get("/file/web/file/getFileById/" + 104).then((res : any) => {
    url1.value = res.data.url
  })
})
</script>

<template>
<div style="width: 100%; height: 100%">
  <Navigate type="fly"/>
  <div style="width: 100%; height: 64px">

  </div>
  <div style="width: 100%; height: calc(100vh - 64px);
    background-size: cover;background-repeat: no-repeat;background-position: center;"
       :style="{ backgroundImage: `url(${url1})` }">

  </div>
</div>
</template>

<style scoped>

</style>