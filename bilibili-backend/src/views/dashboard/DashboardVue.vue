<script setup lang="ts">
import {
  User,
  VideoPlay,
  Wallet,
  VideoCameraFilled,
  Tools,
  DocumentAdd,
  SuitcaseLine,
  Discount,
  HomeFilled,
  BellFilled,
  Iphone,
  Files,
  Tickets,
  Promotion,
  Comment,
  WindPower,
  UploadFilled,
  CollectionTag,
  ChatLineSquare,
  Van,
  Message,
  Sunny
} from '@element-plus/icons-vue'
import router from "@/router";

</script>


<template>
  <div class="h-screen w-full flex">
    <div style="border-right: solid 1px var(--el-menu-border-color); height: 100vh">
      <div class="flex justify-center items-center text-4xl mt-3">
        <el-image style="width: 200px; height: 50px" fit="cover"
                  src="https://ts1.cn.mm.bing.net/th/id/R-C.63648cf2d1a8a1fd20758b9df255b5ff?rik=a%2fP6%2bxo1DEVhPw&riu=http%3a%2f%2fimg.sccnn.com%2fbimg%2f338%2f68306.jpg&ehk=UsdHeKLLx4673Yr56O7vvexNfWV%2bI2pFkooNF0O5jyw%3d&risl=&pid=ImgRaw&r=0"/>
      </div>
      <div class="flex justify-center items-center text-2xl mt-2 ">
        后台管理系统
      </div>
      <el-scrollbar style="height: calc(100vh - 113px); margin-top: 10px">
        <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            style="border-right: 0;width: 200px;"
            :collapse="false"
        >
          <el-menu-item index="1" @click="router.push('/')">
            <template #title>
              <el-icon>
                <HomeFilled/>
              </el-icon>
              <span>首页</span>
            </template>
          </el-menu-item>
          <el-menu-item index="2" @click="router.push('/income')">
            <template #title>
              <el-icon>
                <Wallet/>
              </el-icon>
              <span>收入管理</span>
            </template>
          </el-menu-item>
          <el-menu-item index="3" @click="router.push('/contribute')">
            <template #title>
              <el-icon>
                <DocumentAdd/>
              </el-icon>
              <span>投稿(测试)</span>
            </template>
          </el-menu-item>
          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <SuitcaseLine/>
              </el-icon>
              <span>事物管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="4-1" @click="router.push('/matter/project')">
                <template #title>
                  <el-icon>
                    <Files/>
                  </el-icon>
                  <span>项目管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="4-2" @click="router.push('/matter/todo')">
                <template #title>
                  <el-icon>
                    <BellFilled/>
                  </el-icon>
                  <span>待办管理</span>
                </template>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="5">
            <template #title>
              <el-icon>
                <Discount/>
              </el-icon>
              <span>首页管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="5-1" @click="router.push('/home/advice')">
                <template #title>
                  <el-icon>
                    <Promotion/>
                  </el-icon>
                  <span>推荐管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="5-2" @click="router.push('/home/examine')">
                <template #title>
                  <el-icon>
                    <Tickets/>
                  </el-icon>
                  <span>审核管理</span>
                </template>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="6">
            <template #title>
              <el-icon>
                <Iphone/>
              </el-icon>
              <span>动态管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="6-1" @click="router.push('/state/state')">
                <template #title>
                  <el-icon>
                    <WindPower/>
                  </el-icon>
                  <span>动态圈管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="6-2" @click="router.push('/state/comment')">
                <template #title>
                  <el-icon>
                    <Comment/>
                  </el-icon>
                  <span>动态评论管理</span>
                </template>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="7">
            <template #title>
              <el-icon>
                <Tools/>
              </el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="7-1" @click="router.push('/system/file')">
                <template #title>
                  <el-icon>
                    <UploadFilled/>
                  </el-icon>
                  <span>文件管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="7-2" @click="router.push('/system/tag')">
                <template #title>
                  <el-icon>
                    <CollectionTag/>
                  </el-icon>
                  <span>标签管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="7-3" @click="router.push('/system/user')">
                <template #title>
                  <el-icon>
                    <User/>
                  </el-icon>
                  <span>用户管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="7-4" @click="router.push('/system/message')">
                <template #title>
                  <el-icon>
                    <Message/>
                  </el-icon>
                  <span>信息管理</span>
                </template>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="8">
            <template #title>
              <el-icon>
                <VideoPlay/>
              </el-icon>
              <span>影视管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="8-1" @click="router.push('/video/video')">
                <template #title>
                  <el-icon>
                    <VideoCameraFilled/>
                  </el-icon>
                  <span>视频管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="8-2" @click="router.push('/video/danmu')">
                <template #title>
                  <el-icon>
                    <ChatLineSquare/>
                  </el-icon>
                  <span>弹幕管理</span>
                </template>
              </el-menu-item>
              <el-menu-item index="8-3" @click="router.push('/video/review')">
                <template #title>
                  <el-icon>
                    <Van/>
                  </el-icon>
                  <span>评论管理</span>
                </template>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="9" @click="router.push('/grassGod')">
            <template #title>
              <el-icon><Sunny /></el-icon>
              <span>小草神</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </div>
    <div class="w-full" style="background-color: #f1f1f1">
      <div style="margin: 20px; min-height: calc(100vh - 20px)">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>