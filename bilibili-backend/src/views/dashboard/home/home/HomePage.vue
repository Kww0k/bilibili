<script setup lang="ts">
import {Wallet, Tickets, DocumentAdd, Files,   User, VideoCameraFilled} from "@element-plus/icons-vue";
import router from "@/router";
import {onMounted, ref} from "vue";
import type {Ref} from "vue";
import type {TodoListVO} from "../../../../../type/response/dashboard/matter/todo";
import {listTodoApi} from "@/api/dashboard/matter/todo";

const todoList: Ref<TodoListVO[]> = ref([])

const getTodoList = () => {
  listTodoApi('').then((data) => {
    todoList.value = data
  })
}

onMounted(() => {
  getTodoList()
})

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

<template>
  <div class="w-full">
    <div>
      <el-row :gutter="20">
        <el-col :span="16">
          <div style="box-shadow: 0 0 10px rgb(216, 180, 254);" class="bg-white py-4 px-5 rounded-xl">
            <div class="text-2xl font-bold">
              工作台
            </div>
            <div class="mt-5 flex">
              <div>
                <el-avatar style="width: 70px; height: 70px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
              </div>
              <div class="ml-3">
                <div class=" font-serif text-xl mt-2.5 font-normal">
                  欢迎康哥回到后台管理系统，技术宅拯救世界，真的很酷！
                </div>
                <div class="text-sm text-slate-400 mt-1">
                  无论你去向何方不管天气如何永远带上自己的阳光。Bilibili，乾杯~
                </div>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div
              style="box-shadow: 0 0 10px rgb(249, 168, 212);height: 164px;background-size: cover;background-repeat: no-repeat;background-position: center;background-image: url(https://patchwiki.biligame.com/images/blhx/e/ee/h58rgcnlzjam7fvb1iaum3kbva21d2r.jpg)"
              class="bg-white rounded-xl">
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="mt-5">
      <el-row :gutter="20">
        <el-col :span="14">
          <div style="box-shadow: 0 0 10px rgb(147, 197, 253); border-radius: 10px" class="bg-white rounded">
            <div class="py-4 px-5 bg-white">
              项目进度
            </div>
            <el-divider style="margin: 0"/>
            <el-scrollbar style="height: 240px;">
              <div>
                <el-table :data="tableData" style="width: 100%">
                  <el-table-column prop="date" label="Date" width="180"/>
                  <el-table-column prop="name" label="Name" width="180"/>
                  <el-table-column prop="address" label="Address"/>
                </el-table>
              </div>
            </el-scrollbar>
          </div>
        </el-col>
        <el-col :span="10">
          <div style="box-shadow: 0 0 10px rgb(253, 186, 116); height: 297px" class="bg-white rounded">
            <div class="py-4 px-5 bg-white">
              快捷操作
            </div>
            <div style="height: 2px" class="bg-orange-300"></div>
            <div style="height: 120px" >
              <el-row>
                <el-col :span="8">
                  <div @click="router.push('/income')" class="border-orange-300" style="border-bottom-width: 2px;border-right-width: 2px; cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <Wallet/>
                      </el-icon>
                    </div>
                    <div style="height: 60px" class="flex justify-center">
                      <span>收入管理</span>
                    </div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div @click="router.push('/home/examine')" class="border-orange-300" style="border-bottom-width: 2px;border-right-width: 2px; cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <Tickets/>
                      </el-icon>
                    </div>
                    <div style="height: 60px" class="flex justify-center">
                      <span>审核管理</span>
                    </div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div @click="router.push('/contribute')" class="border-orange-300" style="border-bottom-width: 2px; cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <DocumentAdd/>
                      </el-icon>
                    </div>
                    <div style="height: 60px" class="flex justify-center">
                      <span>投稿测试</span>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div style="height: 116px">
              <el-row>
                <el-col :span="8">
                  <div @click="router.push('/system/user')" class="border-orange-300" style="border-right-width: 2px; cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <User/>
                      </el-icon>
                    </div>
                    <div style="height: 56px" class="flex justify-center">
                      <span>用户管理</span>
                    </div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div @click="router.push('/system/file')" class="border-orange-300" style="border-right-width: 2px; cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <Files/>
                      </el-icon>
                    </div>
                    <div style="height: 56px" class="flex justify-center">
                      <span>文件管理</span>
                    </div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div @click="router.push('/video/video')" class="border-orange-300" style="cursor:pointer;">
                    <div style="height: 60px" class="text-4xl flex justify-center items-center">
                      <el-icon>
                        <VideoCameraFilled/>
                      </el-icon>
                    </div>
                    <div style="height: 60px" class="flex justify-center">
                      <span>视频管理</span>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="mt-5" >
      <el-row :gutter="20">
        <el-col :span="8">
          <div style="box-shadow: 0 0 10px rgb(134, 239, 172); border-radius: 10px" class="bg-white rounded">
            <div class="py-4 px-5 bg-white">
              动态列表
            </div>
            <el-divider style="margin: 0"/>
            <el-scrollbar style="height: 27vh;">
              <div class="flex" style="height: 74px">
                <div class="pt-1.5 px-4">
                  <el-avatar style="width: 40px; height: 40px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
                  <div style="margin-top: -5px">pangzi</div>
                </div>
                <div class="ml-1">
                  <div class=" font-serif text-base mt-2.5 font-normal">
                    我投稿了一个视频，快来观看吧
                  </div>
                  <div class="text-xs text-slate-400 mt-1">
                    2023-6-20 14:50
                  </div>
                </div>
              </div>
              <el-divider style="margin: 0"/>
              <div class="flex" style="height: 74px">
                <div class="pt-1.5 px-4">
                  <el-avatar style="width: 40px; height: 40px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
                  <div style="margin-top: -5px">pangzi</div>
                </div>
                <div class="ml-1">
                  <div class=" font-serif text-base mt-2.5 font-normal">
                    我投稿了一个视频，快来观看吧
                  </div>
                  <div class="text-xs text-slate-400 mt-1">
                    2023-6-20 14:50
                  </div>
                </div>
              </div>
              <el-divider style="margin: 0"/>
              <div class="flex" style="height: 74px">
                <div class="pt-1.5 px-4">
                  <el-avatar style="width: 40px; height: 40px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
                  <div style="margin-top: -5px">pangzi</div>
                </div>
                <div class="ml-1">
                  <div class=" font-serif text-base mt-2.5 font-normal">
                    我投稿了一个视频，快来观看吧
                  </div>
                  <div class="text-xs text-slate-400 mt-1">
                    2023-6-20 14:50
                  </div>
                </div>
              </div>
              <el-divider style="margin: 0"/>
              <div class="flex" style="height: 74px">
                <div class="pt-1.5 px-4">
                  <el-avatar style="width: 40px; height: 40px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
                  <div style="margin-top: -5px">pangzi</div>
                </div>
                <div class="ml-1">
                  <div class=" font-serif text-base mt-2.5 font-normal">
                    我投稿了一个视频，快来观看吧
                  </div>
                  <div class="text-xs text-slate-400 mt-1">
                    2023-6-20 14:50
                  </div>
                </div>
              </div>
              <el-divider style="margin: 0"/>
              <div class="flex" style="height: 74px">
                <div class="pt-1.5 px-4">
                  <el-avatar style="width: 40px; height: 40px" src="src/assets/img/defaultAvatar.jpg"></el-avatar>
                  <div style="margin-top: -5px">pangzi</div>
                </div>
                <div class="ml-1">
                  <div class=" font-serif text-base mt-2.5 font-normal">
                    我投稿了一个视频，快来观看吧
                  </div>
                  <div class="text-xs text-slate-400 mt-1">
                    2023-6-20 14:50
                  </div>
                </div>
              </div>
              <el-divider style="margin: 0"/>
            </el-scrollbar>
          </div>
        </el-col>
        <el-col :span="16">
          <div style="box-shadow: 0 0 10px rgb(252, 211, 77); border-radius: 10px" class="bg-white rounded">
            <div class="py-4 px-5 bg-white">
              待办事项
            </div>
            <el-divider style="margin: 0;" />
            <el-scrollbar style="height: 27vh;">
              <div v-for="item in todoList" :key="item.id">
                <div class="flex" style="height: 74px">
                  <div class="flex items-center ml-5" style="flex: 1">
                    {{ item.todoAffair }}
                  </div>
                  <div style="width: 100px" class="flex justify-center items-center">
                    <el-tag v-if="item.completionStatus === '完成'" style="width: 60px;height: 30px" effect="dark">完成</el-tag>
                    <el-tag v-if="item.completionStatus === '未完成'" style="width: 60px;height: 30px" type="danger" effect="dark">未完成</el-tag>
                  </div>
                </div>
                <el-divider style="margin: 0"/>
              </div>
            </el-scrollbar>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>

</style>