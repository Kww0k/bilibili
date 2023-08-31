<template>
  <div style="width: 100%; height: 100vh">
    <div style="width: 100%; height: 100%;
    background-size: cover;background-repeat: no-repeat;background-position: center;"
         :style="{ backgroundImage: `url(${url1})` }">
      <el-form  style="width: 400px ;border-radius: 5px; position: absolute; text-align: center; top: 50%; left: 80%; transform: translate(-50%, -50%); background-color: rgba(255, 255, 255, 0.5)">
        <div style="font-size: 30px; font-weight: bold; margin-top: 20px">
          登陆
        </div>
        <div style="margin-top: 50px; width: 300px; margin-left: 50px">
          <el-input type="text" placeholder="用户名/邮箱" v-model="form.username">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
          <el-input type="password" placeholder="密码" style="margin-top: 10px" v-model="form.password">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </div>
        <div style="margin-top: 30px; margin-bottom: 20px">
          <el-button style="width: 300px" type="success" plain @click="login">立即登陆</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {Lock, User} from "@element-plus/icons-vue";
import router from "@/router";
import {onMounted, ref} from "vue";
import type {Login} from "../../../type/response/login";
import {loginApi} from "@/api/login";
import {ElMessage} from "element-plus";
import {LocalStorageService, SessionStorageService} from "@/util/storage";
import {STORAGE_PREFIX, TOKEN, USER} from "@/config/cache";
import request from "@/net";

const url1 = ref('')

const form = ref<Login>({
  username: '',
  password: ''
})


onMounted(() => {
  request.get("/file/web/file/getFileById/" + 34).then((res : any) => {
    url1.value = res.data.url
  })
  SessionStorageService.clearAll()
  LocalStorageService.clearAll()
})


const login = () => {
  loginApi(form.value).then((data) => {
    if (data.code === 200) {
      SessionStorageService.set(`${STORAGE_PREFIX}${TOKEN}`, data.data.token)
      SessionStorageService.set(`${STORAGE_PREFIX}${USER}`, JSON.stringify(data.data.accountAuthVO))
      router.push('/')
      ElMessage.success("登陆成功")
    } else {
      ElMessage.error(data.message)
    }
  })
}
</script>


<style scoped>

</style>