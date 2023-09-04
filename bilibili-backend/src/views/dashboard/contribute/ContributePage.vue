<script setup lang="ts">
// 投稿测试
import {SessionStorageService} from "@/util/storage";
import {STORAGE_PREFIX, TOKEN} from "@/config/cache";
import {Plus} from "@element-plus/icons-vue";
import type {BaseResponse} from "../../../../type/response/response";
import {computed, onMounted, ref} from "vue";
import type {Ref} from 'vue'
import type {UploadProps} from "element-plus";
import {ElMessage} from "element-plus";
import type {InsertVideo, TagList} from "../../../../type/response/dashboard/contribute";
import {insertVideoTest, listTagApi} from "@/api/dashboard/contribute";
import request from "@/net";

const imgUrl = ref('')
const videoUrl = ref('')
const insertForm = ref<InsertVideo>({
  title: '',
  description: '',
  previewId: null,
  videoId: null,
  visibility: '',
  typeId: null
})
const listTag :Ref<TagList[]> = ref([])
const maxChars = 300; // 最大字符数
const remainingChars = computed(() => maxChars - insertForm.value.description.length);
const fileUrl = ref('')


const sub = () => {
  insertVideoTest(insertForm.value).then((data) => {
    if (data.code === 200) {
      insertForm.value = {
        title: '',
        description: '',
        previewId: null,
        videoId: null,
        visibility: '',
        typeId: null
      }
      imgUrl.value = ''
      videoUrl.value = ''
      ElMessage.success("提交成功")
    } else {
      ElMessage.error(data.message)
    }
  })
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response: BaseResponse<any>
) => {
  if (response.code === 200) {
    insertForm.value.previewId = response.data.id
    imgUrl.value = response.data.url
    ElMessage.success("上传成功")
  } else {
    ElMessage.error(response.message)
  }
}

const handleVideoSuccess: UploadProps['onSuccess'] = (
    response: BaseResponse<any>
) => {
  if (response.code === 200) {
    insertForm.value.videoId = response.data.id
    videoUrl.value = response.data.url
    ElMessage.success("上传成功")
  } else {
    ElMessage.error(response.message)
  }
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  const allowedFormats = ['image/jpeg', 'image/png', 'image/gif', 'image/bmp', 'image/svg+xml', 'image/webp']
  if (!allowedFormats.includes(rawFile.type)) {
    ElMessage.error('文件格式不正确')
    return false
  }
  if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('文件不能超过10MB!')
    return false
  } else if (rawFile.size < 5 * 1024) {
    ElMessage.error('文件不能小于5KB!')
    return false
  }
  return true
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

onMounted(()=> {
  listTagApi().then((data) => {
    listTag.value = data
  })
  request.get("/file/web/file/getFileById/" + 37).then((res : any) => {
    fileUrl.value = res.data.url
  })
})
</script>

<template>
  <div style="width: 100%; height: 95vh;">
    <el-row :gutter="40" style="width: 100%; height: 100%">
      <el-col :span="12" style="width: 100%; height: 100%">
        <div style="box-shadow: 0 0 10px grey;border-radius:10px;width: 100%; height: 100%;background-color: white">
          <div style="height: 60%; width: 100%; padding-top: 30px; padding-left: 10px">
            <div>
              <div style="font-size: 20px; font-weight: bold">
                上传封面图片
              </div>
              <div style="width: 100%" class="flex justify-center items-center">
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8080/api/file/web/file/uploadPicture"
                    :show-file-list="false"
                    :headers="{
          'Authorization': 'Bearer ' + (SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) ? SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) : null)
        }"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                >
                  <img v-if="imgUrl != ''" :src="imgUrl" class="avatar" alt="111"/>
                  <el-icon v-else class="avatar-uploader-icon">
                    <Plus/>
                  </el-icon>
                </el-upload>
              </div>
            </div>
            <div style="margin-top: 30px">
              <div style="font-size: 20px; font-weight: bold">
                上传视频文件
              </div>
              <div style="width: 100%" class="flex justify-center items-center">
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8080/api/file/web/file/uploadVideo"
                    :show-file-list="false"
                    :headers="{
          'Authorization': 'Bearer ' + (SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) ? SessionStorageService.get(`${STORAGE_PREFIX}${TOKEN}`) : null)
        }"
                    :on-success="handleVideoSuccess"
                    :before-upload="beforeVideoUpload"
                >
                  <el-image v-if="videoUrl != ''" :src="videoUrl" class="avatar" alt="111"/>
                  <el-icon v-else class="avatar-uploader-icon">
                    <Plus/>
                  </el-icon>
                </el-upload>
              </div>
            </div>
          </div>
          <div style="height: 40%; width: 100%; border-bottom-right-radius: 10px; border-bottom-left-radius: 10px;
              background-size: cover;background-repeat: no-repeat;background-position: center;"
               :style="{ backgroundImage: `url(${fileUrl})` }">
          </div>
        </div>
      </el-col>
      <el-col :span="12" style="width: 100%; height: 100%;">
        <div style="box-shadow: 0 0 10px grey;border-radius:10px;width: 100%; height: 100%;background-color: white">
          <div style="width: 100%; height: 20%">
            <div style="height: 50%; font-weight: bold; font-size: 24px" class="flex justify-center items-center">
              输入您视频的标题
            </div>
            <div style="height: 50%" class="flex justify-center items-center">
              <el-input v-model="insertForm.title" placeholder="标题" style="width: 60%;"/>
            </div>
          </div>
          <div style="width: 100%; height: 20%">
            <div style="height: 50%; font-weight: bold; font-size: 24px" class="flex justify-center items-center">
              输入您视频的描述
            </div>
            <div class="flex justify-center items-center">
              <div style="width: 60%">
                <el-input
                    type="textarea"
                    placeholder="描述"

                    v-model="insertForm.description"
                    :maxlength="maxChars"
                />
                <div style="text-align: right; color: grey; font-size: 14px">{{ remainingChars }} / 300</div>
              </div>
            </div>
          </div>
          <div style="width: 100%; height: 20%">
            <div style="height: 50%; font-weight: bold; font-size: 24px" class="flex justify-center items-center">
              选择作品的类型
            </div>
            <div style="height: 50%; width: 100%" class="flex justify-center items-center">
              <div  style="width: 60%">
                <el-select v-model="insertForm.typeId" placeholder="Select" size="default" style="width: 100%">
                  <el-option
                      v-for="item in listTag"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                  />
                </el-select>
                <div style="text-align: right; color: grey; font-size: 14px">
                  只能选择一个最具代表特征的标签
                </div>
              </div>
            </div>
          </div>
          <div style="width: 100%; height: 20%">
            <div style="height: 50%; font-weight: bold; font-size: 24px" class="flex justify-center items-center">
              选择哪些人可见
            </div>
            <div class="flex justify-center items-center">
              <el-radio-group v-model="insertForm.visibility" size="large" class="dx-btn">
                <el-radio-button label="所有人"/>
                <el-radio-button label="仅粉丝" style="margin-left: 10px"/>
                <el-radio-button label="仅自己" style="margin-left: 10px"/>
              </el-radio-group>
            </div>

          </div>
          <div style="width: 100%; height: 20%" class="flex justify-center items-center">
            <el-button style="width: 80%;" type="primary" plain @click="sub()">
              提交审核
            </el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>


<style scoped>
.avatar-uploader .avatar {
  width: 320px;
  height: 160px;
  display: block;
}

.dx-btn >>> .el-radio-button .el-radio-button__inner {
  border-radius: 10px;
  box-sizing: border-box;
  box-shadow: 0 0 4px grey;
  border: none;
}

.dx-btn >>> .el-radio-button:first-child .el-radio-button__inner {
  border-radius: 10px;
  box-sizing: border-box;
  box-shadow: 0 0 4px grey !important;
}

.dx-btn >>> .is-active .el-radio-button__inner {

  border-radius: 10px;

  background-color: pink;

  box-shadow: 0 0 4px grey;

  color: #fff;

}


.dx-btn >>> .el-radio-button .el-radio-button__inner:hover {
  color: lightpink;
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
  width: 320px;
  height: 160px;
  text-align: center;
}

</style>