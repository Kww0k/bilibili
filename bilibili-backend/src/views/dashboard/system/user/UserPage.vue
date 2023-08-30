<script setup lang="ts">

import {onMounted, ref} from "vue";
import type {Ref} from "vue";
import type {
  InsertUser,
  UpdatePassword,
  UpdateUser,
  UserList
} from "../../../../../type/response/dashboard/system/user";
import {
  deleteUserApi,
  insertUserApi,
  listUserApi,
  updatePasswordApi,
  updateUserApi
} from "@/api/dashboard/system/user";
import {ElMessage} from "element-plus";
import request from "@/net";

const listUser: Ref<UserList[]> = ref([])
const url1 = ref('')
const url2 = ref('')
const url3 = ref('')
const name = ref('')
const insertDialog = ref(false)
const updateDialog = ref(false)
const passwordDialog = ref(false)
const insertUserForm = ref<InsertUser>({
  username: '',
  nickname: '',
  password: '',
  email: '',
})
const updateUserForm = ref<UpdateUser>({
  id: null,
  username: '',
  nickname: '',
  email: '',
  birthday: '',
  sex: ''
})
const updatePasswordForm = ref<UpdatePassword>({
  id: null,
  password: '',
  email: '',
})

const close = () => {
  insertDialog.value = false
  updateDialog.value = false
  passwordDialog.value = false
  insertUserForm.value = {
    username: '',
    nickname: '',
    password: '',
    email: '',
  }
  updateUserForm.value = {
    id: null,
    username: '',
    nickname: '',
    email: '',
    birthday: '',
    sex: ''
  }
  updatePasswordForm.value = {
    id: null,
    password: '',
    email: '',
  }
}

const updateUser = (row: UpdateUser) => {
  updateUserForm.value = {
    id: row.id,
    username: row.username,
    nickname: row.nickname,
    email: row.email,
    birthday: row.birthday,
    sex: row.sex
  }
  updateDialog.value = true
}

const updatePassword = (row: UpdatePassword) => {
  updatePasswordForm.value = {
    id: row.id,
    password: row.password,
    email: row.email,
  }
  passwordDialog.value = true
}

const doInsert = () => {
  insertUserApi(insertUserForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("新增成功")
      name.value = ''
      getUserList()
    }
    else
      ElMessage.error(data.message)
    close()
  })
}

const deleteUser = (id : number) => {
  deleteUserApi(id).then((data) => {
    if (data.code === 200) {
      ElMessage.success("删除成功")
      name.value = ''
      getUserList()
    }
    else
      ElMessage.error(data.message)
    close()
  })
}

const doUpdate = () => {
  updateUserApi(updateUserForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("更新成功")
      name.value = ''
      getUserList()
    }
    else
      ElMessage.error(data.message)
    close()
  })
}

const daUpdatePassword = () => {
  updatePasswordApi(updatePasswordForm.value).then((data) => {
    if (data.code === 200) {
      ElMessage.success("更新成功")
    }
    else
      ElMessage.error(data.message)
    close()
  })
}


const getUserList = () => {
  listUserApi(name.value).then((data) => {
    listUser.value = data
  })
}

onMounted(() => {
  getUserList()
  request.get("/file/web/file/getFileById/" + 47).then((res : any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 48).then((res : any) => {
    url2.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 49).then((res : any) => {
    url3.value = res.data.url
  })
})

const reset = () => {
  name.value = ''
  getUserList()
}

</script>

<template>
  <div class="w-full">
    <div style="height: 30vh;width: 100%">
      <el-row :gutter="20" style="height: 100%">
        <el-col :span="8" style="height: 100%">
          <div style=" box-shadow: 0 0 10px grey;height: 100%;  border-radius: 10px">
            <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                      :src="url1"/>
          </div>
        </el-col>
        <el-col :span="8" style="height: 100%">
          <div style=" box-shadow: 0 0 10px grey;height: 100%;  border-radius: 10px">
            <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                      :src="url2"/>
          </div>
        </el-col>
        <el-col :span="8" style="height: 100%">
          <div style=" box-shadow: 0 0 10px grey;height: 100%;  border-radius: 10px">
            <el-image style="height: 100%; width: 100%;border-radius: 10px" fit="cover"
                     :src="url3"/>
          </div>
        </el-col>
      </el-row>
    </div>
    <div style="height:55vh;margin-top: 60px;width: 100%;">
      <div style=" box-shadow: 0 0 10px grey;height: 100%; background-color: white; border-radius: 10px">
        <div class=" w-full flex" style="height: 10vh">
          <div style="height: 100%; margin-left: 10px" class="flex justify-center items-center w-1/4">
            <el-input placeholder="请输入想搜索的用户名" v-model="name"/>
            <el-button style="margin-left: 10px" type="primary" plain @click="getUserList">搜索</el-button>
            <el-button type="danger" plain @click="reset">重置</el-button>
          </div>
          <div style="height: 100%;" class="flex justify-center items-center w-1/6">
            <el-button type="success" plain @click="insertDialog = true">新增</el-button>
          </div>
        </div>
        <div style="height: 40vh; width: 100%">
          <el-scrollbar class="w-full" style="height: 40vh">
            <el-table :data="listUser" style="width: 100%">
              <el-table-column prop="id" label="id" width="80"/>
              <el-table-column prop="username" label="username"/>
              <el-table-column prop="nickname" label="nickname"/>
              <el-table-column prop="email" label="email"/>
              <el-table-column prop="birthday" label="birthday"/>
              <el-table-column prop="sex" label="sex"/>
              <el-table-column label="操作" width="300">
                <template v-slot="scope">
                  <el-button type="success" plain @click="updateUser(scope.row)">修改</el-button>
                  <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteUser(scope.row.id)">
                    <template #reference>
                      <el-button type="danger" plain>删除</el-button>
                    </template>
                  </el-popconfirm>
                  <el-button type="warning" plain @click="updatePassword(scope.row)">修改密码</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-scrollbar>
        </div>
      </div>
    </div>
  </div>

  <el-dialog
      v-model="insertDialog"
      title="新增用户信息"
      :before-close="close"
      width="400px">
    <el-input v-model="insertUserForm.username" placeholder="请输入用户名(选填)" style="width: 300px;"/>
    <el-input v-model="insertUserForm.nickname" placeholder="请输入昵称(选填)" style="width: 300px; margin-top: 10px"/>
    <el-input v-model="insertUserForm.password" type="password" placeholder="请输入密码(必填)" style="width: 300px; margin-top: 10px"/>
    <el-input v-model="insertUserForm.email" placeholder="请输入邮箱(必填)" style="width: 300px; margin-top: 10px"/>
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
      v-model="updateDialog"
      title="更新用户信息"
      :before-close="close"
      width="400px">
    <el-input v-model="updateUserForm.username" style="width: 300px;"/>
    <el-input v-model="updateUserForm.nickname" style="width: 300px; margin-top: 10px"/>
    <el-input v-model="updateUserForm.email" style="width: 300px; margin-top: 10px"/>
    <el-date-picker
        v-model="updateUserForm.birthday"
        type="date"
        format="YYYY-MM-DD"
        value-format="YYYY-MM-DD"
        placeholder="选择生日"
        style="width: 300px; margin-top: 10px"
    />
    <el-radio-group v-model="updateUserForm.sex" style="width: 300px; margin-top: 10px" class="dx-btn">
      <el-radio-button label="男" />
      <el-radio-button label="女"  style="margin-left: 10px"/>
      <el-radio-button label="保密"  style="margin-left: 10px"/>
    </el-radio-group>
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
      v-model="passwordDialog"
      title="更新用户密码"
      :before-close="close"
      width="400px">
    <el-input v-model="updatePasswordForm.password" type="password" style="width: 300px;"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close" type="danger" plain>取消</el-button>
        <el-popconfirm title="你确定要修改这个用户的密码吗?" @confirm="daUpdatePassword">
          <template #reference >
            <el-button type="primary"  plain>
              确认
            </el-button>
          </template>
        </el-popconfirm>
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