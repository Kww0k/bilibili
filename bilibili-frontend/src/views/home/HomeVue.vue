<script setup lang="ts">
import {onMounted, ref} from 'vue';
import type {Ref} from "vue";
import Navigate from "@/components/Navigate.vue";
import request from "@/net";
import type {TagList} from "../../../type/tag";
import {listTagApi} from "@/api/tag";
import Banner from "@/components/Banner.vue";

const message = ref('Hello, world!');
const height = ref('simple')
const tagList : Ref<TagList[]> = ref([])


const url1 = ref('')
const url2 = ref('')
const user = ref('')



const getTagList = () => {
  listTagApi().then((data) => {
    tagList.value = data
  })
}

window.addEventListener('scroll', function () {
  let scrollDistance = window.scrollY;
  if (scrollDistance >= 35 && height.value !== 'fly')
    height.value = 'fly'
  else if (scrollDistance < 35 && height.value !== 'simple')
    height.value = 'simple'
});

onMounted(() => {
  getTagList()
  request.get("/file/web/file/getFileById/" + 96).then((res: any) => {
    url1.value = res.data.url
  })
  request.get("/file/web/file/getFileById/" + 97).then((res: any) => {
    url2.value = res.data.url
  })
})

</script>

<template>
  <div style="height: 200vh; width: 100vw;">
    <div>
      <Navigate :message="message" :type="height"/>
      <div id="header_banner" class="header_banner" style="height: 19vh">
        <div style="width: 100%; height: 100%;
              background-size: cover;background-repeat: no-repeat;background-position: center; position: absolute"
             :style="{ backgroundImage: `url(${url1})` }">
        </div>
        <div class="inner-banner">
          <a href="//localhost:5173" class="inner-logo">
            <img :src="url2" style="display: block;object-fit: contain;" alt="B站 b站" width="162" height="78">
          </a>
        </div>
      </div>
    </div>
    <div class="main-body">
      <div class="main-channel">
        <div style="z-index: 0;display: flex;align-items: center;margin-right: 20px;height: 100%; padding-top: 24px">
          <div v-if="user == ''" style="width: 70px;height: 100%">
            <div>
              <div class="channel-item">
                <div class="channel-icon">
                  <el-avatar style="width: 46px;height:46px;"
                             src="src/assets/img/defaultAvatar.jpg"/>
                </div>
                <span class="channel-title">动态</span>
              </div>
            </div>
          </div>
          <div style="width: 70px;height: 100%">
            <div>
              <div class="channel-item">
                <div class="channel-icon" style="background-color: #f38e8c">
                  <svg width="22" height="22" viewBox="0 0 22 22" fill="none" xmlns="http://www.w3.org/2000/svg"
                       class="icon-bg--icon">
                    <path fill-rule="evenodd" clip-rule="evenodd"
                          d="M4.89054 17.272L4.89277 17.2742C6.49674 18.8782 8.66472 19.7888 10.9624 19.7888C13.2503 19.7888 15.2113 19.0539 16.6107 17.6108L16.6108 17.6108L16.6128 17.6086C18.0002 16.1345 18.7835 14.182 18.7421 12.1819C18.7852 11.3835 18.6916 9.36321 17.4088 6.75488L17.4082 6.7537C17.209 6.35523 16.8163 6.06598 16.3391 5.96993C15.8904 5.87103 15.4021 6.01997 15.061 6.35741C14.9094 6.48781 14.7796 6.61755 14.6655 6.7317L14.6637 6.73348L14.6329 6.76426C14.2107 3.35588 12.6083 1.7368 11.1654 1.00465C11.148 0.987812 11.1265 0.967972 11.1036 0.950782C11.0775 0.931205 11.0311 0.900467 10.9694 0.888912C10.2276 0.608301 9.41043 1.01168 9.1237 1.77629L9.12314 1.7778C8.50566 3.46558 7.35287 4.62281 6.16627 5.76704C4.51756 7.33121 2.75938 9.03623 2.80163 12.093C2.75906 14.055 3.54464 15.8826 4.89054 17.272ZM3.04999 13.0648C3.01092 12.7459 2.99439 12.4218 3.00168 12.0939C2.95933 9.12977 4.6531 7.47834 6.30453 5.9116C7.49017 4.76831 8.67581 3.58267 9.31098 1.84655C9.56504 1.16904 10.2849 0.830288 10.9201 1.08435C10.9624 1.08435 11.0048 1.1267 11.0471 1.16904C11.9267 1.60884 12.8853 2.39635 13.5753 3.77299C12.8853 2.39633 11.9267 1.6088 11.0471 1.16901C11.0047 1.12666 10.9624 1.08432 10.9201 1.08432C10.2849 0.830251 9.56503 1.16901 9.31097 1.84651C8.6758 3.58263 7.49016 4.76827 6.30452 5.91157C4.65309 7.47831 2.95932 9.12973 3.00166 12.0938C2.99438 12.4218 3.01092 12.7459 3.04999 13.0648ZM14.477 7.18189C14.477 7.1819 14.477 7.18192 14.477 7.18193C14.5828 7.09724 14.6887 6.99138 14.8052 6.87493C14.9216 6.75849 15.0486 6.63146 15.1968 6.50442C15.4932 6.20801 15.9167 6.08098 16.2978 6.16567C16.7212 6.25036 17.06 6.50443 17.2294 6.84318C17.3088 7.0046 17.3835 7.16371 17.4539 7.32035C17.3835 7.1637 17.3087 7.00458 17.2294 6.84314C17.06 6.50439 16.7212 6.25032 16.2978 6.16563C15.9167 6.08094 15.4932 6.20798 15.1968 6.50439C15.0486 6.63142 14.9216 6.75845 14.8051 6.87489L14.8051 6.8749C14.7557 6.92437 14.7081 6.97191 14.6617 7.01675C14.5988 7.07745 14.5379 7.13318 14.477 7.18189ZM12.6977 6.35925C12.8428 7.15476 12.8833 7.97963 12.8679 8.74864C12.8679 8.79032 12.8704 8.8315 12.8754 8.87195C12.8704 8.83151 12.8679 8.79035 12.8679 8.74867C12.8833 7.97966 12.8428 7.15478 12.6977 6.35925ZM16.7358 10.6654C16.8108 11.1478 16.8483 11.6393 16.8483 12.1362V12.1786C16.8483 13.703 16.2978 15.2274 15.2392 16.3283C14.0535 17.4293 12.5291 18.0221 10.9201 17.9374C9.1416 17.9374 7.44783 17.2175 6.21984 15.9472C5.82743 15.5548 5.50448 15.1119 5.25584 14.6331C5.50447 15.1119 5.82743 15.5548 6.21983 15.9472C7.44782 17.2175 9.14159 17.9373 10.9201 17.9373C12.5291 18.022 14.0535 17.4292 15.2392 16.3283C16.2978 15.2273 16.8483 13.7029 16.8483 12.1785V12.1362C16.8483 11.6393 16.8108 11.1478 16.7358 10.6654ZM10.9306 17.7376C12.4802 17.8192 13.9509 17.2497 15.0989 16.1856C16.1154 15.1261 16.6483 13.655 16.6483 12.1785V12.1362C16.6483 10.8624 16.3969 9.6266 15.8955 8.49474C15.2436 9.11663 14.7845 9.49093 14.4179 9.68717C14.2122 9.79725 14.0268 9.85633 13.846 9.86789C13.6644 9.8795 13.5028 9.84219 13.3473 9.78249C12.9207 9.62211 12.6679 9.20129 12.6679 8.74864V8.74464L12.6679 8.74464C12.6889 7.69735 12.6046 6.55594 12.2954 5.53554C12.01 4.59379 11.5372 3.76766 10.7904 3.20655C9.96581 4.94926 8.72521 6.18561 7.58695 7.28323L7.50836 7.35967C5.97191 8.85397 4.81321 9.98087 4.85306 12.1325L4.85313 12.1362H4.85309C4.85309 13.5239 5.38326 14.8277 6.36125 15.8057L6.36365 15.8081L6.36363 15.8082C7.55387 17.0394 9.19573 17.7374 10.9201 17.7374H10.9306L10.9306 17.7376Z"
                          fill="#ffffff"></path>
                  </svg>
                </div>
                <span class="channel-title">动态</span>
              </div>
            </div>
          </div>
          <div style="width: 70px;height: 100%">
            <div>
              <div class="channel-item">
                <div class="channel-icon" style="background-color: #59ca73">
                  <svg width="21" height="21" viewBox="0 0 21 21" fill="none" xmlns="http://www.w3.org/2000/svg"
                       class="icon-bg--icon">
                    <circle cx="5.6875" cy="14" r="3.5" stroke="#ffffff" stroke-width="2" stroke-linecap="round"
                            stroke-linejoin="round"></circle>
                    <circle cx="10.5" cy="5.6875" r="3.5" stroke="#ffffff" stroke-width="2" stroke-linecap="round"
                            stroke-linejoin="round"></circle>
                    <circle cx="15.3125" cy="14" r="3.5" stroke="#ffffff" stroke-width="2" stroke-linecap="round"
                            stroke-linejoin="round"></circle>
                  </svg>
                </div>
                <span class="channel-title">频道</span>
              </div>
            </div>
          </div>
        </div>
        <div style="flex-grow: 1;height: 100%; margin-left: -14px; display: flex">
          <div style="flex-grow: 1;height: 100%; background-color: red">

          </div>
          <div style="width: 240px; height: 100%;background-color: black">

          </div>
        </div>
      </div>
      <div class="main-suggest">
        <el-row :gutter="20" class="suggest-body">
          <el-col :span="10">
            <Banner/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
        </el-row>
      </div>
      <div class="main-video">
        <el-row :gutter="20" class="video-body">
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
          <el-col :span="5">
            <el-input placeholder="请输入商品编码"/>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped>
.channel-title {
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  color: inherit;
}

.channel-icon {
  width: 46px;
  height: 46px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 6px;
  border-radius: 50%;
  transition: background .3s;
}

.channel-item {
  cursor: pointer;
  position: relative;
  display: flex;
  flex-direction: column;
  margin-right: 24px;
}

.main-channel {
  display: flex;
  width: 100%;
  height: 120px;
}

.suggest-body .el-col-10 {
  max-width: 40%;
  flex: 0 0 40%;
}

.suggest-body .el-col-5 {
  max-width: 20%;
  flex: 0 0 20%;
}

.video-body .el-col-5 {
  max-width: 20%;
  flex: 0 0 20%;
}
</style>

<style scoped>
.main-body {
  max-width: 1620px;
  min-width: 1384px;
  width: 90%;
  margin: 0 auto;
}

.inner-logo {
  margin-bottom: 10px;
  z-index: 1;
  display: inline-block;
  min-height: 60px;
  width: 180px;
  height: 50%;
}

.inner-banner {
  display: flex;
  align-items: flex-end;
  padding: 0 96px;
  max-width: 2270px;
  position: relative;
  width: 100%;
  margin: 0 auto;
}

.header_banner {
  position: relative;
  z-index: 0;
  display: flex;
  -ms-flex-pack: center;
  justify-content: center;
  margin: 0 auto;
  min-width: 1000px;
  min-height: 155px;
  height: 9.375vw;
  max-height: 240px;
  background-color: #e3e5e7;
  background-position: center 0;
  background-size: cover;
  background-repeat: no-repeat;
}

.v-img {
  position: absolute;
  object-fit: cover;
  display: inline-block;
  line-height: 1;
  width: 100%;
  height: 100%;
  vertical-align: middle;
}
</style>