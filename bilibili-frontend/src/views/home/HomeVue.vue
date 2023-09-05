<script setup lang="ts">
import {onMounted, ref} from 'vue';
import type {Ref} from "vue";
import Navigate from "@/components/Navigate.vue";
import request from "@/net";
import type {TagList} from "../../../type/tag";
import {listTagApi} from "@/api/tag";
import Banner from "@/components/Banner.vue";
import ChannelA from "@/components/ChannelA.vue";
import PageCard from "@/components/PageCard.vue";

const message = ref('Hello, world!');
const height = ref('simple')
const showList : Ref<TagList[]> = ref([])
const moreList : Ref<TagList[]> = ref([])


const url1 = ref('')
const url2 = ref('')
const user = ref('')


const getTagList = () => {
  listTagApi().then((data) => {
    showList.value = data.slice(0, 21)
    moreList.value = data.slice(22, 28)
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
  // @ts-ignore
  document.getElementById('inner-banner').style.padding = '0 ' + (document.body.clientWidth - document.getElementById('main-body').clientWidth) / 2 + 'px'
})

</script>

<template>
  <div>
    <div>
      <Navigate :message="message" :type="height"/>
      <div id="header_banner" class="header_banner" style="height: 19vh">
        <div style="width: 100%; height: 100%;
              background-size: cover;background-repeat: no-repeat;background-position: center; position: absolute"
             :style="{ backgroundImage: `url(${url1})` }">
        </div>
        <div id="inner-banner" class="inner-banner">
          <a href="//localhost:5173" class="inner-logo">
            <img :src="url2" style="display: block;object-fit: contain;" alt="B站 b站" width="162" height="78">
          </a>
        </div>
      </div>
    </div>
    <div id="main-body" class="main-body">
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
        <div style="flex-grow: 1; margin-left: -14px; display: flex; height: 66px; margin-top: 27px">
          <div class="channel-left">
            <ChannelA v-for="item in showList" :name="item.name"/>
            <ChannelA name="更多"/>
          </div>
          <div class="channel-right">
            <a class="channel-link__right" href="//www.bilibili.com/read/home" style="letter-spacing:2px;" target="_blank">
              <svg t="1642646834655" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4061" width="200" height="200"><path d="M810.6496 153.6C843.6736 153.6 870.4 180.3264 870.4 213.3504v597.2992c0 33.024-26.7264 59.7504-59.7504 59.7504H213.3504A59.7504 59.7504 0 0 1 153.6 810.6496V213.3504C153.6 180.3264 180.3264 153.6 213.3504 153.6z m-332.8 460.8H341.3504a34.1504 34.1504 0 0 0 0 68.2496h136.4992a34.1504 34.1504 0 1 0 0-68.2496z m204.8-136.6016H341.3504a34.1504 34.1504 0 0 0 0 68.2496h341.2992a34.1504 34.1504 0 0 0 0-68.2496z m0-136.3968H341.3504a34.1504 34.1504 0 0 0 0 68.2496h341.2992a34.1504 34.1504 0 1 0 0-68.2496z" p-id="4062"></path>
            </svg>
              <span>专栏</span>
            </a>
            <a class="channel-link__right" href="//live.bilibili.com" style="letter-spacing:2px;" target="_blank">
              <svg t="1642646710703" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3158" width="200" height="200"><path d="M682.6496 204.8512c35.4816 0 64.6656 27.136 67.9424 61.696l0.3072 6.6048V358.4l81.92-61.44a34.2016 34.2016 0 0 1 53.248 17.408l1.024 4.7616 0.3584 5.12v392.5504a34.1504 34.1504 0 0 1-50.2784 30.1056l-4.352-2.7648-81.92-61.4912v68.3008c0 33.1264-23.552 60.7232-54.8352 66.9696l-6.8608 1.024-6.5536 0.256H204.8a68.2496 68.2496 0 0 1-67.9936-61.696l-0.3072-6.5536V273.152c0-35.4816 27.136-64.6656 61.696-67.9936L204.8 204.8512h477.8496zM385.3824 389.12a34.1504 34.1504 0 0 0-34.0992 34.1504v179.6096a34.1504 34.1504 0 0 0 53.0432 28.416l134.7584-89.8048a34.1504 34.1504 0 0 0 0-56.832L404.3264 394.8544a34.1504 34.1504 0 0 0-18.944-5.7344z" p-id="3159"></path>
            </svg>
              <span>直播</span>
            </a>
            <a class="channel-link__right" href="//www.bilibili.com/blackboard/activity-list.html?" style="letter-spacing:2px;" target="_blank">
              <svg t="1642646844599" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4188" width="200" height="200"><path d="M772.4544 229.2736l2.048 6.4512 87.2448 283.136c8.8576 33.1264-10.24 66.56-41.984 78.4384l-6.5536 2.048-118.1696 31.744a32.768 32.768 0 0 1-8.192 1.0752l-4.096-0.2048-124.672-14.6432-193.3824 51.8144 36.6592 136.704a49.152 49.152 0 0 1-93.184 30.8736l-1.792-5.4272L155.2896 267.264a49.152 49.152 0 0 1 93.184-30.8736l1.792 5.4272 4.096 15.36 188.928-50.6368a32.768 32.768 0 0 1 8.1408-1.0752l4.096 0.2048 124.5184 14.592 112.1792-30.0544c33.4336-8.96 68.1984 7.7824 80.2304 39.0144z" p-id="4189"></path>
            </svg>
              <span>活动</span>
            </a>
            <a class="channel-link__right" href="//www.bilibili.com/cheese/?csource=common_hp_channelclass_icon" style="letter-spacing:2px;" target="_blank">
              <svg t="1642669966795" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2862" width="200" height="200"><path d="M749.2096 168.96a102.4 102.4 0 0 1 102.2464 96.3584l0.1536 6.0416v477.8496a102.4 102.4 0 0 1-96.3584 102.2464l-6.0416 0.1536H271.36a102.4 102.4 0 0 1-102.2464-96.3584l-0.1536-6.0416V271.36a102.4 102.4 0 0 1 96.3584-102.2464L271.36 168.96h477.8496z m-69.9392 385.4848a34.1504 34.1504 0 0 0-48.2816 0 170.6496 170.6496 0 0 1-241.3568 0 34.1504 34.1504 0 1 0-48.2816 48.2304 238.9504 238.9504 0 0 0 337.92 0 34.1504 34.1504 0 0 0 0-48.2304zM322.56 339.6608a51.2 51.2 0 0 0-51.2 51.2v34.1504a51.2 51.2 0 1 0 102.4 0v-34.1504a51.2 51.2 0 0 0-51.2-51.2z m375.4496 0a51.2 51.2 0 0 0-51.2 51.2v34.1504a51.2 51.2 0 1 0 102.4 0v-34.1504a51.2 51.2 0 0 0-51.2-51.2z" p-id="2863"></path>
            </svg>
              <span>课堂</span>
            </a>
            <a class="channel-link__right" href="https://www.bilibili.com/blackboard/activity-5zJxM3spoS.html" style="letter-spacing:0px;" target="_blank">
              <svg t="1642670011510" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3116" width="200" height="200"><path d="M836.3008 170.6496c17.1008 0 31.2832 12.6464 33.7408 29.0816L870.4 204.8v316.16a34.1504 34.1504 0 0 1-29.0816 33.792l-5.0176 0.3584h-102.4v195.7888a34.1504 34.1504 0 0 1-29.1328 33.792l-5.0176 0.3584H386.7648l-127.3344 95.5392a34.2016 34.2016 0 0 1-53.248-17.3568l-1.024-4.8128-0.3584-5.12-0.0512-68.3008H153.6a34.1504 34.1504 0 0 1-32.6656-24.2176l-1.0752-4.8128-0.4096-5.0688v-409.6c0-17.152 12.6464-31.3344 29.0816-33.792L153.6 307.2h174.2336V204.8c0-17.152 12.6464-31.3344 29.1328-33.792l5.0176-0.3584h474.3168z m-477.9008 409.6H256l-5.0688 0.4096a34.1504 34.1504 0 0 0 0 67.4816l5.0688 0.4096h102.4l5.0688-0.4096A34.1504 34.1504 0 0 0 358.4 580.2496z m102.4-136.4992H256l-5.0688 0.4096a34.1504 34.1504 0 0 0 0 67.4816l5.0688 0.4096h204.8l5.0688-0.4096A34.1504 34.1504 0 0 0 460.8 443.7504z m341.2992-204.8512h-406.016v68.2496h303.616c17.152 0 31.3856 12.6464 33.792 29.1328l0.3584 5.0176v145.4592l68.2496 0.0512V238.8992z" p-id="3117"></path>
            </svg>
              <span>社区中心</span>
            </a>
            <a class="channel-link__right" href="//www.bilibili.com/v/musicplus/" style="letter-spacing:0px;" target="_blank">
              <svg t="1642669999866" class="icon side-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2989" width="200" height="200"><path d="M783.36 221.8496a102.4 102.4 0 0 1 102.0928 94.7712l0.3072 7.68v409.6a102.4 102.4 0 0 1-94.72 102.0928l-7.68 0.256H237.2096a102.4 102.4 0 0 1-102.0928-94.72l-0.3072-7.68v-409.6A102.4 102.4 0 0 1 229.5808 222.208l7.68-0.3072H783.36z m-155.5968 81.408a34.1504 34.1504 0 0 0-50.7904 23.6032l-54.1696 203.3152a128 128 0 1 0-108.288 220.8256l6.7584 1.024a128 128 0 0 0 141.5168-104.8576l71.7824-253.7984 47.4624 35.4816 3.2768 2.2016a34.1504 34.1504 0 0 0 37.5808-56.832l-91.8528-68.7616z" p-id="2990"></path>
            </svg>
              <span>新歌热榜</span>
            </a>
          </div>
        </div>
      </div>
      <div class="main-suggest">
        <el-row :gutter="15" class="suggest-body">
          <el-col :span="10">
            <Banner/>
          </el-col>
          <el-col :span="5">
            <PageCard/>
            <PageCard style="margin-top: 20px; margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard/>
            <PageCard style="margin-top: 20px; margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard/>
            <PageCard style="margin-top: 20px; margin-bottom: 60px"/>
          </el-col>
        </el-row>
      </div>
      <div class="main-video">
        <el-row :gutter="15" class="video-body">
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
        </el-row>
        <el-row :gutter="15" class="video-body">
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
          <el-col :span="5">
            <PageCard style=" margin-bottom: 60px"/>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped>
.side-icon {
  margin-right: 4px;
  width: 20px;
  height: 20px;
  transition: fill .3s;
  fill: #61666D;
}
.channel-link__right {
  height: 28px;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  width: 100%;
  text-decoration: none;
  background-color: transparent;
  color: #61666D;
  text-align: center;
  font-weight: 400;
  transition: background-color .3s,color .3s;
}
.channel-link__right:hover {
  color: #00AEEC;
}
.channel-link__right:hover .side-icon {
  fill: #00AEEC;
}
.channel-right {
  width: 240px;
  height: 100%;
  grid-column: span 1;
  grid-row-gap: 10px;
  grid-auto-flow: column;
  display: grid;
  grid-template-rows: repeat(2,1fr);
  flex-shrink: 0;
}
.channel-left {
  height: 100%;
  padding-right: 30px;
  grid-template-columns: repeat(11, 1fr);
  position: relative;
  display: grid;
  width: 100%;
  grid-auto-flow: column;
  grid-column: span 4;
  grid-gap: 10px;
  grid-template-rows: repeat(2,1fr);
  border-right: 1px solid #E3E5E7;
}

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
  width: 100%;
  height: 120px;
  display: flex;
  justify-content: space-between;
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