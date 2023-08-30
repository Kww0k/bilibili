<script setup lang="ts">
// 收入管理

import {onMounted, ref} from "vue";
import request from "@/net";

const carouselList = ref([{
  id: 1,
  url: ''
},
  {
    id: 2,
    url: ''
  },
  {
    id: 3,
    url: ''
  }, {
    id: 4,
    url: ''
  }
])

onMounted(() => {
  request.get("/file/web/file/getFileById/" + 26).then((res: any) => {
    carouselList.value[0].url = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 27).then((res: any) => {
    carouselList.value[1].url = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 28).then((res: any) => {
    carouselList.value[2].url = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 29).then((res: any) => {
    carouselList.value[3].url = res.data.url
  })
})
</script>

<template>
  <div style="height: 40vh" class="w-full pl-10 pt-10">
    <el-row :gutter="40" style="height: 40vh">
      <el-col :span="12">
        <el-carousel trigger="click" height="auto" autoplay style=" box-shadow: 0 0 10px grey;border-radius: 10px">
          <el-carousel-item v-for="item in carouselList" :key="item.id" style="height: 40vh">
            <el-image fit="cover" style="height: 40vh; width: 100%; border-radius: 10px" :src="item.url"/>
          </el-carousel-item>
        </el-carousel>
      </el-col>
      <el-col :span="12">
        <div style="height: 40vh; width: 100%">
          <div
              style="height: 12vh; width: 100%; border-radius: 10px; color: rebeccapurple; box-shadow: 0 0 10px rebeccapurple;"
              class="pl-5 pt-1 bg-amber-100">
            <div style="font-size: 24px; font-weight: bold">
              收入说明
            </div>
            <div style="margin-top: 5px; font-size: 16px">
              收入由四部分组成，分别是视频时长，视频礼物，视频播放量，视频点击量
            </div>
          </div>
          <div style="height: 12vh; width: 100%; margin-top: 2vh; border-radius: 10px; box-shadow: 0 0 10px gold;"
               class="pl-5 pt-1 bg-green-500">
            <div>
              <div style="font-size: 24px; font-weight: bold">
                各个收入说明
              </div>
              <div style="margin-top: 5px; font-size: 16px">
                礼物 = 礼物 * 0.5, 播放量 = 播放量 * 0.1, 时长 = 时长 * 0.01, 点击量 = 点击量 * 0.01
              </div>
            </div>
          </div>
          <div
              style="height: 12vh; width: 100%; margin-top: 2vh; border-radius: 10px;background-color: lightpink; color: white; box-shadow: 0 0 10px grey;"
              class="pl-5 pt-1">
            <div style="font-size: 24px; font-weight: bold">
              视频收入说明
            </div>
            <div style="margin-top: 5px; font-size: 16px">
              视频收入 = 礼物 * 30 + 播放量 * 20 + 时长 * 25 + 点击量 * 25
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
  <div style="height: 20vh;margin-top: 40px" class="w-full pl-10 pt-10">
    <el-row :gutter="20">
      <el-col :span="6">
        <div style="text-align: center; border-radius: 10px; height: 20vh; color: white; box-shadow: 0 0 10px grey;"
             class="bg-cyan-500 pt-10">
          <div style="font-size: 24px; font-weight: bold">
            礼物收入
          </div>
          <div style="margin-top: 20px; font-size: 20px">
            $10000
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div style="text-align: center; border-radius: 10px; height: 20vh; color: white; box-shadow: 0 0 10px grey;"
             class="bg-emerald-600 pt-10">
          <div style="font-size: 24px; font-weight: bold">
            播放量
          </div>
          <div style="margin-top: 20px; font-size: 20px">
            100000次
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div style="text-align: center; border-radius: 10px; height: 20vh; color: white; box-shadow: 0 0 10px grey;"
             class="bg-red-400 pt-10">
          <div style="font-size: 24px; font-weight: bold">
            播放时长
          </div>
          <div style="margin-top: 20px; font-size: 20px">
            10000小时
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div style="text-align: center; border-radius: 10px; height: 20vh; color: white; box-shadow: 0 0 10px grey;"
             class="bg-indigo-500 pt-10">
          <div style="font-size: 24px; font-weight: bold">
            点击量
          </div>
          <div style="margin-top: 20px; font-size: 20px">
            1000000次
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
  <div style="height: 25vh; margin-top: 40px" class="w-full pl-10 pt-10">
    <el-row :gutter="20">
      <el-col :span="12">
        <div style="height: 20vh; width: 100%; border-radius: 10px; box-shadow: 0 0 10px grey; color: white"
             class="bg-fuchsia-400 pl-10 pt-10">
          <div style="font-size: 20px; font-weight: bold">
            额外收入
          </div>
          <div style="margin-top: 5px">
            <el-row :gutter="20">
              <el-col :span="6">
                <div style="width: 100%; height: 100%; text-align: center; font-size: 20px">
                  <div>
                    点赞
                  </div>
                  <div style="font-size: 16px; margin-top: 5px">
                    1000个
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div style="width: 100%; height: 100%; text-align: center; font-size: 20px">
                  <div>
                    投币
                  </div>
                  <div style="font-size: 16px; margin-top: 5px">
                    2000个
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div style="width: 100%; height: 100%; text-align: center; font-size: 20px">
                  <div>
                    收藏
                  </div>
                  <div style="font-size: 16px; margin-top: 5px">
                    1000个
                  </div>
                </div>
              </el-col>
              <el-col :span="6">
                <div style="width: 100%; height: 100%; text-align: center; font-size: 20px">
                  <div>
                    评论
                  </div>
                  <div style="font-size: 16px; margin-top: 5px">
                    1000条
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div style="height: 20vh; width: 100%; border-radius: 10px; box-shadow: 0 0 10px grey;"
             class="bg-lime-200 pl-10 pt-10">
          <div style="font-size: 20px; font-weight: bold">
            总收入统计
          </div>
          <div style="margin-top: 5px; text-align: center; font-size: 18px">
            总收入由视频收入+额外收入组成，您目前的总收入为:
          </div>
          <div style="margin-top: 5px; text-align: center; font-size: 18px">
            ¥1000000
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
</style>