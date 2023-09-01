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
            <div style="width: 100%; height: 100%">
              <el-scrollbar style="width: 100%; height: 100%; border-radius: 5px">
                <div>
                  <el-table :data="danmuList" style="width: 100%">
                    <el-table-column prop="id" label="id"/>
                    <el-table-column prop="content" label="Content"/>
                    <el-table-column prop="color" label="Color"/>
                    <el-table-column prop="time" label="Time"/>
                    <el-table-column label="操作" width="180">
                      <template v-slot="scope">
                        <el-button type="success" plain @click="openDialog(scope.row)">修改</el-button>
                        <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteDanmu(scope.row.id)">
                          <template #reference>
                            <el-button type="danger" plain>删除</el-button>
                          </template>
                        </el-popconfirm>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-scrollbar>
            </div>
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

  <el-dialog
      v-model="updateDialog"
      title="更新用户信息"
      :before-close="closeDialog"
      width="400px">
    <el-input v-model="updateForm.content" style="width: 300px;"/>
    <el-input v-model="updateForm.color" style="width: 300px; margin-top: 10px"/>
    <el-input v-model="updateForm.time" style="width: 300px; margin-top: 10px"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeDialog" type="danger" plain>取消</el-button>
        <el-button type="primary" @click="updateDamu" plain>
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import type {Ref} from "vue";
import request from "@/net";
import type {DanmuList, UpdateDanmu} from "../../../../../type/response/dashboard/video/danmu";
import {deleteDanmuApi, listDanmuApi, updateDanmuApi} from "@/api/dashboard/video/danmu";
import {ElMessage} from "element-plus";

const danmuList : Ref<DanmuList[]> = ref([])
const updateDialog = ref(false)
const updateForm = ref<UpdateDanmu>({
  id : null,
  content : '',
  color : '',
  time : ''
})
const url1 = ref('')
const url2 = ref('')
const url3 = ref('')

const getList = () => {
  listDanmuApi().then((data) => {
    danmuList.value = data
  })
}

const openDialog = (updateInfo : UpdateDanmu) => {
  updateForm.value = {
    id : updateInfo.id,
    content : updateInfo.content,
    color : updateInfo.color,
    time : updateInfo.time
  }
  updateDialog.value = true
}

const closeDialog = () => {
  updateForm.value = {
    id : null,
    content : '',
    color : '',
    time : ''
  }
  updateDialog.value = false
}

const updateDamu = () => {
  updateDanmuApi(updateForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("更新成功")
      getList()
    }
    else {
      ElMessage.error(data.message)
    }
    closeDialog()
  })
}

const deleteDanmu = (id : number) => {
  deleteDanmuApi(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success("删除成功")
      getList()
    }
    else {
      ElMessage.error(data.message)
    }
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