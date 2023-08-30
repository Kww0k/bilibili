<script setup lang="ts">

import type {Ref} from "vue";
import type {InsertTodo, TodoListVO, UpdateTodo} from "../../../../../type/response/dashboard/matter/todo";
import {onMounted, ref} from "vue";
import {deleteTodoApi, insertTodoApi, listTodoApi, updateTodoApi} from "@/api/dashboard/matter/todo";
import {ElMessage} from "element-plus";
import request from "@/net";

const todoList: Ref<TodoListVO[]> = ref([])
const name = ref('')
const url1 = ref('')
const url2 = ref('')
const url3 = ref('')
const insertForm = ref<InsertTodo>({
  todoAffair: ''
})

const updateForm = ref<UpdateTodo>({
  id: null,
  todoAffair: '',
  completionStatus: ''
})
const insertDialog = ref(false)
const updateDialog = ref(false)

const openUpdate = (row: UpdateTodo) => {
  updateForm.value = {
    id: row.id,
    todoAffair: row.todoAffair,
    completionStatus: row.completionStatus
  }
  updateDialog.value = true
}

const close = () => {
  insertDialog.value = false
  updateDialog.value = false
  insertForm.value = {
    todoAffair: ''
  }
  updateForm.value = {
    id: null,
    todoAffair: '',
    completionStatus: ''
  }
}

const getList = () => {
  listTodoApi(name.value).then((data) => {
    todoList.value = data
  })
}

const reset = () => {
  name.value = ''
  getList()
}

const insertTodo = () => {
  insertTodoApi(insertForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success('插入成功')
      name.value = ''
      getList()
    } else
      ElMessage.error(data.message)
    close()
  })
}

const updateTodo = () => {
  updateTodoApi(updateForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success('更新成功')
      name.value = ''
      getList()
    } else
      ElMessage.error(data.message)
    close()
  })
}

const deleteTodo = (id: number) => {
  deleteTodoApi(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success('删除成功')
      name.value = ''
      getList()
    } else
      ElMessage.error(data.message)
  })
}

onMounted(() => {
  getList()
  request.get("/file/web/file/getFileById/" + 50).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 52).then((res : any) => {
    url2.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 53).then((res : any) => {
    url3.value = res.data.url
  })
})

</script>

<template>
  <div style="height: 95vh; width: 100%;">
    <el-row :gutter="30" style="width: 100%; height: 100%">
      <el-col :span="5">
        <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
          <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                    :src="url2"/>
        </div>
      </el-col>
      <el-col :span="14">
        <div style="width: 100%; height: 100%;">
          <div :style="{ backgroundImage: `url(${url1})` }"
              style="background-size: cover;background-repeat: no-repeat;background-position: center;box-shadow: 0 0 10px grey;width: 100%; height: 55%;border-radius: 10px;">
          </div>
          <div style="width: 100%; height: 5%;">
          </div>
          <div style="box-shadow: 0 0 10px grey;border-radius: 10px;width: 100%; height: 40%;background-color: white">
            <div style="width: 100%; height: 20%; display: flex">
              <div style="height: 100%; margin-left: 10px" class="flex justify-center items-center">
                <el-input style="width: 200px" placeholder="请输入想搜索的事物" v-model="name"/>
                <el-button style="margin-left: 10px" type="primary" plain @click="getList">搜索</el-button>
                <el-button type="danger" plain @click="reset">重置</el-button>
              </div>
              <div style="height: 100%;" class="flex justify-center items-center w-1/6">
                <el-button type="success" plain @click="insertDialog = true">新增</el-button>
              </div>
            </div>
            <div style="width: 100%; height: 80%;">
              <el-scrollbar class="w-full" style="height: 95%">
                <el-table :data="todoList" style="width: 100%">
                  <el-table-column prop="id" label="id" width="80"/>
                  <el-table-column prop="todoAffair" label="todoAffair"/>
                  <el-table-column label="completionStatus">
                    <template v-slot="scope">
                      <el-tag style="width: 60px;height: 30px" v-if="scope.row.completionStatus === '完成'"
                              effect="dark">完成
                      </el-tag>
                      <el-tag style="width: 60px;height: 30px" v-if="scope.row.completionStatus === '未完成'"
                              type="danger" effect="dark">未完成
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作">
                    <template v-slot="scope">
                      <el-button type="success" plain @click="openUpdate(scope.row)">修改</el-button>
                      <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteTodo(scope.row.id)">
                        <template #reference>
                          <el-button type="danger" plain>删除</el-button>
                        </template>
                      </el-popconfirm>
                    </template>
                  </el-table-column>
                </el-table>
              </el-scrollbar>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="5">
        <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
          <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                    :src="url3"/>
        </div>
      </el-col>
    </el-row>
  </div>

  <el-dialog
      v-model="insertDialog"
      title="新增事项信息"
      :before-close="close"
      width="400px">
    <el-input placeholder="请输入要新增的事项" type="textarea" v-model="insertForm.todoAffair" style="width: 300px;"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close" type="danger" plain>取消</el-button>
        <el-button type="primary" @click="insertTodo" plain>
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
      v-model="updateDialog"
      title="更新事项信息"
      :before-close="close"
      width="400px">
    <el-input v-model="updateForm.todoAffair" type="textarea" style="width: 300px;"/>
    <el-radio-group v-model="updateForm.completionStatus" style="width: 300px; margin-top: 10px" class="dx-btn">
      <el-radio-button label="完成"/>
      <el-radio-button label="未完成" style="margin-left: 10px"/>
    </el-radio-group>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close" type="danger" plain>取消</el-button>
        <el-button type="primary" @click="updateTodo" plain>
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
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