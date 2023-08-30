<script setup lang="ts">
import {onMounted, ref} from "vue";
import type {Ref} from "vue";
import type {
  InsertTag,
  ParentTagList,
  SimpleTagList,
  UpdateTag
} from "../../../../../type/response/dashboard/system/tag";
import {deleteTagApi, insertTagApi, listParentTagApi, listSimpleTagApi, updateTagApi} from "@/api/dashboard/system/tag";
import {ElMessage} from "element-plus";
import request from "@/net";

const parentTagList: Ref<ParentTagList[]> = ref([])
const simpleTagList: Ref<SimpleTagList[]> = ref([])
const insertParentTagDialog = ref(false)
const updateParentTagDialog = ref(false)
const insertSimpleTagDialog = ref(false)
const updateSimpleTagDialog = ref(false)
const insertForm = ref<InsertTag>({
  name: '',
  parentId: null
})
const updateForm = ref<UpdateTag>({
  id: null,
  name: '',
  parentId: null
})
const url1 = ref('')
const url2 = ref('')

const handleParentEdit = (row : UpdateTag) => {
  updateParentTagDialog.value = true
  updateForm.value = {
    id: row.id,
    name: row.name,
    parentId: row.parentId
  }
}

const handleSimpleEdit = (row : SimpleTagList) => {
  updateSimpleTagDialog.value = true
    updateForm.value = {
      id : row.id,
      name: row.name,
      parentId : parentTagList.value.find(tag => tag.name === row.parent)?.id || null
  }
}

const close = () => {
  insertParentTagDialog.value = false
  updateParentTagDialog.value = false
  insertSimpleTagDialog.value = false
  updateSimpleTagDialog.value = false
  insertForm.value = {
    name: '',
    parentId: null
  }
  updateForm.value = {
    id: null,
    name: '',
    parentId: null
  }
}

const getParentTagList = () => {
  listParentTagApi().then((data) => {
    parentTagList.value = data
  })
}

const getSimpleTagList = () => {
  listSimpleTagApi().then((data) => {
    simpleTagList.value = data
  })
}

onMounted(() => {
  getParentTagList()
  getSimpleTagList()
  request.get("/file/web/file/getFileById/" + 32).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 33).then((res : any) => {
    url2.value = res.data.url
  })
})

const doInsert = () => {
  insertTagApi(insertForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("新增成功")
      getParentTagList()
      getSimpleTagList()
    }
    else {
      ElMessage.error(data.message)
    }
  })
  close()
}

const doUpdate = () => {
  updateTagApi(updateForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("更新成功")
      getParentTagList()
      getSimpleTagList()
    }
    else {
      ElMessage.error(data.message)
    }
  })
  close()
}

const deleteTag = (id : number) => {
  deleteTagApi(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success("删除成功")
      getParentTagList()
      getSimpleTagList()
    }
    else {
      ElMessage.error(data.message)
    }
  })
}


</script>

<template>
  <div class="w-full">
    <div style="height: 45vh;width: 100%">
      <el-row :gutter="30" style="height: 100%">
        <el-col :span="12" style="height: 100%">
          <div style=" box-shadow: 0 0 10px grey;height: 100%;  border-radius: 10px">
            <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                      :src="url1"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
            <div style="height: 10vh; width: 20%">
              <div style="height: 100%; width: 100%" class="flex justify-center items-center">
                <el-button type="primary" plain @click="insertParentTagDialog = true">新增</el-button>
              </div>
            </div>
            <div style="height: 30vh; width: 100%">
              <el-scrollbar class="w-full" style="height: 30vh">
                <div>
                  <el-table :data="parentTagList" style="width: 100%">
                    <el-table-column prop="id" label="id"/>
                    <el-table-column prop="name" label="Name"/>
                    <el-table-column label="操作">
                      <template v-slot="scope">
                        <el-button type="success" plain @click="handleParentEdit(scope.row)">修改</el-button>
                        <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteTag(scope.row.id)">
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
    <div style="height:45vh;margin-top: 40px;width: 100%">
      <el-row :gutter="30" style="height: 100%">
        <el-col :span="12" style="height: 100%">
          <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
            <div style="height: 10vh; width: 20%">
              <div style="height: 100%; width: 100%" class="flex justify-center items-center">
                <el-button type="primary" plain @click="insertSimpleTagDialog = true">新增</el-button>
              </div>
            </div>
            <div style="height: 30vh; width: 100%">
              <el-scrollbar class="w-full" style="height: 30vh">
                <div>
                  <el-table :data="simpleTagList" style="width: 100%">
                    <el-table-column prop="id" label="id"/>
                    <el-table-column prop="name" label="Name"/>
                    <el-table-column prop="parent" label="parent"/>
                    <el-table-column label="操作" width="200">
                      <template v-slot="scope">
                        <el-button type="success" plain @click="handleSimpleEdit(scope.row)">修改</el-button>
                        <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteTag(scope.row.id)">
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
        <el-col :span="12">
          <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
            <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                      :src="url2"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>

  <el-dialog
      v-model="insertParentTagDialog"
      title="插入父标签信息"
      :before-close="close"
      width="400px">
    <el-input v-model="insertForm.name" placeholder="请输入想要插入的名称" style="width: 300px;"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close" type="danger" plain>取消</el-button>
        <el-button type="primary" @click="doInsert" plain>
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
      v-model="updateParentTagDialog"
      title="更新父标签信息"
      :before-close="close"
      width="400px">
    <el-input v-model="updateForm.name" style="width: 300px;"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close" type="danger" plain>取消</el-button>
        <el-button type="primary" @click="doUpdate" plain>
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
      v-model="insertSimpleTagDialog"
      :before-close="close"
      title="插入子标签信息"
      width="400px">
    <el-input v-model="insertForm.name" placeholder="请输入想要插入的名称" style="width: 300px;"/>
    <el-select v-model="insertForm.parentId" placeholder="Select" size="default" style="width: 300px; margin-top: 10px">
      <el-option
          style="width: 300px"
          v-for="item in parentTagList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
      />
    </el-select>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="doInsert">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
      v-model="updateSimpleTagDialog"
      :before-close="close"
      title="更新子标签信息"
      width="400px">
    <el-input v-model="updateForm.name" style="width: 300px;"/>
    <el-select v-model="updateForm.parentId" placeholder="Select" size="default" style="width: 300px; margin-top: 10px">
      <el-option
          style="width: 300px"
          v-for="item in parentTagList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
      />
    </el-select>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="doUpdate">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>

</style>