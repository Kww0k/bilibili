<template>
  <div style="height: 95vh; width: 100%">
    <div style="height: 45vh; width: 100%">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-image style="box-shadow: 0 0 10px grey;height: 45vh; border-radius: 5px" fit="cover"
                    :src="url3"/>
        </el-col>
        <el-col :span="12">
          <div style="box-shadow: 0 0 10px grey;width: 100%; height: 45vh; border-radius: 5px">

          </div>
        </el-col>
      </el-row>
    </div>
    <div style="height: 45vh; margin-top: 5vh; width: 100%">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-image style="box-shadow: 0 0 10px grey;height: 45vh; border-radius: 5px" fit="cover"
                    :src="url1"/>
        </el-col>
        <el-col :span="12">
          <el-image style="box-shadow: 0 0 10px grey;height: 45vh;border-radius: 5px" fit="cover"
                    :src="url2"/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, Ref, ref} from "vue";
import request from "@/net";
import type {DanmuList} from "../../../../../type/response/dashboard/video/danmu";
import {listDanmuApi} from "@/api/dashboard/video/danmu";

const danmuList : Ref<DanmuList[]> = ref([])
const url1 = ref('')
const url2 = ref('')
const url3 = ref('')

const getList = () => {
  listDanmuApi().then((data) => {
    danmuList.value = data
  })
}

onMounted(() => {
  getList()
  request.get("/file/web/file/getFileById/" + 65).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 66).then((res : any) => {
    url2.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 67).then((res : any) => {
    url3.value = res.data.url
  })
})
</script>