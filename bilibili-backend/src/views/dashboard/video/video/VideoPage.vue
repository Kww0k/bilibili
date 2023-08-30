<script setup lang="ts">
import {onMounted, ref} from 'vue'
import type {Ref} from "vue";
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'

import type {UploadProps} from 'element-plus'
import type {BaseResponse} from "../../../../../type/response/response";
import type {FileList} from "../../../../../type/response/dashboard/system/file";
import {SessionStorageService} from "@/util/storage";
import {STORAGE_PREFIX, TOKEN} from "@/config/cache";
import {videoListApi} from "@/api/dashboard/video/video";
import request from "@/net";

const imageUrl = ref('')
const dialogUrl = ref('')
const url1 = ref('')
const url2 = ref('')
const videoDialog = ref(false)
const dialogVisible = ref(false)
const pictureList : Ref<FileList[]> = ref([])
const filename : Ref<String> = ref('')


const getList = () => {
  videoListApi(filename.value).then(data => {
    pictureList.value = data
  })
}

onMounted(() => {
  getList()
  request.get("/file/web/file/getFileById/" + 35).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 36).then((res : any) => {
    url2.value = res.data.url
  })
})

const reset = () => {
  filename.value = ''
  getList()
}

const openVideo = (url : string) => {
  dialogUrl.value = url
  videoDialog.value = true
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response: BaseResponse<String>
) => {
  if (response.code === 200) {
    dialogVisible.value = false
    filename.value = ''
    getList()
    ElMessage.success("上传成功")
  } else {
    ElMessage.error(response.message)
  }
}

const beforeVideoUpload: UploadProps['beforeUpload'] = (rawFile) => {
  const allowedFormats = ['video/mp4', 'video/mpeg', 'video/ogg', 'video/quicktime', 'video/webm']
  if (!allowedFormats.includes(rawFile.type)) {
    ElMessage.error('文件格式不正确')
    return false
  }
  if (rawFile.size > 1024 * 1024 * 1024) {
    ElMessage.error('文件不能超过1GB！')
    return false
  } else if (rawFile.size < 1024) {
    ElMessage.error('文件不能小于1KB！')
    return false
  }
  return true
}

</script>

<template>
<div style="width: 100%; height: 95vh">
  <div style="box-shadow: 0 0 10px grey;width: 100%; height: 50vh;margin-top: 20px; background-color: white; border-radius: 10px">
    <div class="w-full flex" style="height: 10vh">
      <div style="height: 100%; margin-left: 10px" class="flex justify-center items-center w-1/4">
        <el-input placeholder="请输入想搜索的文件名" v-model="filename"/>
        <el-button style="margin-left: 10px" @click="getList" type="primary" plain>搜索</el-button>
        <el-button @click="reset" type="danger" plain>重置</el-button>
      </div>
      <div style="height: 100%;" class="flex justify-center items-center w-1/6">
        <el-button @click="dialogVisible = true" type="success" plain>上传文件</el-button>
      </div>
    </div>
    <el-scrollbar class="w-full" style="padding: 20px; height: 40vh">
      <div>
        <el-table :data="pictureList" style="width: 100%">
          <el-table-column prop="id" label="id" width="80"/>
          <el-table-column prop="name" label="Name"/>
          <el-table-column label="preview">
            <template v-slot="scope">
              <el-button @click="openVideo(scope.row.url)">预览</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="type" label="type"/>
          <el-table-column prop="fileType" label="fileType"/>
        </el-table>
      </div>
    </el-scrollbar>
  </div>
  <div style="width: 100%; height: 40vh; margin-top: 3vh">
    <el-row :gutter="30">
      <el-col :span="12">
        <el-image style="box-shadow: 0 0 10px grey;height: 100%; border-radius: 5px" fit="cover"
                  :src="url1"/>
      </el-col>
      <el-col :span="12">
        <el-image style="box-shadow: 0 0 10px grey;height: 100%;border-radius: 5px" fit="cover"
                  :src="url2"/>
      </el-col>
    </el-row>
  </div>
</div>

  <el-dialog title="上传视频" v-model="dialogVisible" width="300">
    <el-upload
        class="avatar-uploader"
        action="http://localhost:8080/api/file/web/file/uploadVideo"
        :show-file-list="false"
        :headers="{
          'Authorization': 'Bearer ' + (SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) ? SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) : null)
        }"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeVideoUpload"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" alt="111" />
      <el-icon v-else class="avatar-uploader-icon" >
        <Plus/>
      </el-icon>
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="videoDialog">
    <video :src="dialogUrl" style="object-fit: cover" controls="controls"></video>
  </el-dialog>
</template>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>