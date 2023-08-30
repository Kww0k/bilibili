<script setup lang="ts">

import type {Ref} from "vue";
import type {ExamineList} from "../../../../../type/response/dashboard/home/examine";
import {onMounted, ref} from "vue";
import {deleteVideo, listUnPassVideo, passVideo} from "@/api/dashboard/home/Examine";
import {ElMessage} from "element-plus";
import request from "@/net";

const examineList: Ref<ExamineList[]> = ref([])
const title = ref('')
const imgDialog = ref(false)
const videoDialog = ref(false)
const imgUrl = ref('')
const videoUrl = ref('')
const url1 = ref('')
const url2 = ref('')
const url3 = ref('')

const openImg = (url : string) => {
  imgUrl.value = url
  imgDialog.value = true
}

const openVideo = (url : string) => {
  videoUrl.value = url
  videoDialog.value = true
}

const getList = () => {
  listUnPassVideo(title.value).then((data) => {
    examineList.value = data
  })
}

const pass = (id: number) => {
  passVideo(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success("审批成功")
      getList()
    } else {
      ElMessage.error(data.message)
    }
  })
}

const del = (id: number) => {
  deleteVideo(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success("删除成功")
      getList()
    } else {
      ElMessage.error(data.message)
    }
  })
}

const reset = () => {
  title.value = ''
  getList()
}

onMounted(() => {
  getList()
  request.get("/file/web/file/getFileById/" + 63).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 39).then((res : any) => {
    url2.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 40).then((res : any) => {
    url3.value = res.data.url
  })
})
</script>

<template>
  <div style="height: 95vh; width: 100%">
    <div style="height: 50vh; width: 100%;">
      <el-row :gutter="30" style="width: 100%; height: 100%">
        <el-col :span="20" style="width: 100%; height: 100%">
          <div style="width: 100%; height: 100%; background-color: white;box-shadow: 0 0 10px grey;border-radius: 5px">
            <div style="height: 25%">
              <div style="height: 100%; margin-left: 10px" class="flex justify-center items-center w-1/3">
                <el-input placeholder="请输入想搜索的标题" v-model="title"/>
                <el-button style="margin-left: 10px" type="primary" plain @click="getList">搜索</el-button>
                <el-button type="danger" plain @click="reset">重置</el-button>
              </div>
            </div>
            <el-scrollbar class="w-full" style="padding: 20px; height: 75%">
              <el-table :data="examineList" style="width: 100%">
                <el-table-column prop="id" label="id"/>
                <el-table-column prop="title" label="title"/>
                <el-table-column label="Description" prop="description"/>
                <el-table-column label="previewUrl">
                  <template v-slot="scope">
                    <el-button type="primary" plain @click="openImg(scope.row.previewUrl)">审核</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="previewUrl">
                  <template v-slot="scope">
                    <el-button type="primary" plain @click="openVideo(scope.row.videoUrl)">审核</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template v-slot="scope">
                    <el-popconfirm title="你确定要通过这条记录吗?" @confirm="pass(scope.row.id)">
                      <template #reference>
                        <el-button type="success" plain>通过</el-button>
                      </template>
                    </el-popconfirm>
                    <el-popconfirm title="你确定要删除这条记录吗?" @confirm="del(scope.row.id)">
                      <template #reference>
                        <el-button type="danger" plain>删除</el-button>
                      </template>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </el-scrollbar>
          </div>
        </el-col>
        <el-col :span="4" style="width: 100%; height: 100%">
          <el-image style="box-shadow: 0 0 10px grey;height: 100%; border-radius: 5px" fit="cover"
                    :src="url1"/>
        </el-col>
      </el-row>
    </div>
    <div style="height: 40vh; width: 100%; margin-top: 5vh">
      <el-row :gutter="30" style="width: 100%; height: 100%">
        <el-col :span="12" style="width: 100%; height: 100%">
          <el-image style="box-shadow: 0 0 10px grey;height: 100%; width:100%;border-radius: 5px" fit="cover"
                    :src="url2"/>
        </el-col>
        <el-col :span="12" style="width: 100%; height: 100%">
          <el-image style="box-shadow: 0 0 10px grey;height: 100%; width:100%;border-radius: 5px" fit="cover"
                    :src="url3"/>
        </el-col>
      </el-row>
    </div>
  </div>

  <el-dialog v-model="imgDialog">
    <el-image :src="imgUrl" fit="cover"/>
  </el-dialog>

  <el-dialog v-model="videoDialog">
    <video :src="videoUrl" style="object-fit: cover" controls="controls"></video>
  </el-dialog>
</template>

<style scoped>

</style>