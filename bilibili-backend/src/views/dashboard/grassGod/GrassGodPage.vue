<script setup lang="ts">

import {onMounted, ref} from "vue";
import request from "@/net";

const url1 = ref('')
const url2 = ref('')
const url3 = ref('')
const url4 = ref('')

onMounted(() => {
  request.get("/file/web/file/getFileById/" + 55).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 56).then((res : any) => {
    url2.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 57).then((res : any) => {
    url3.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 58).then((res : any) => {
    url4.value = res.data.url
  })
})
</script>

<template>
  <div style="height: 95vh; width: 100%; padding-top: 5vh">
    <el-row :gutter="20" style="width: 100%; height: 100%;">
      <el-col :span="6" style="width: 100%; height: 100%">
        <div :style="{ backgroundImage: `url(${url1})` }"
            style="box-shadow: 0 0 10px grey;width: 100%; height: 85vh;border-radius: 10px;
        background-size: cover;background-repeat: no-repeat;background-position: center;">
        </div>
      </el-col>
      <el-col :span="6" style="width: 100%; height: 100%">
        <div :style="{ backgroundImage: `url(${url2})` }"
            style="box-shadow: 0 0 10px grey;width: 100%; height: 85vh;border-radius: 10px;
        background-size: cover;background-repeat: no-repeat;background-position: center;">
        </div>
      </el-col>
      <el-col :span="6" style="width: 100%; height: 100%">
        <div :style="{ backgroundImage: `url(${url3})` }"
            style="box-shadow: 0 0 10px grey;width: 100%; height: 85vh;border-radius: 10px;
        background-size: cover;background-repeat: no-repeat;background-position: center;">
        </div>
      </el-col>
      <el-col :span="6" style="width: 100%; height: 100%">
        <div :style="{ backgroundImage: `url(${url4})` }"
            style="box-shadow: 0 0 10px grey;width: 100%; height: 85vh;border-radius: 10px;
        background-size: cover;background-repeat: no-repeat;background-position: center;">
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>

</style>