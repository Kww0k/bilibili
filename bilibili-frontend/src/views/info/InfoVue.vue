<script setup lang="ts">
import {defineProps, onMounted, ref, watch} from 'vue';
import {getInfoApi} from "@/api/info";
import type {Info} from "../../../type/info";
import router from "@/router";
import VideoPlayer from "@/components/VideoPlayer.vue";
import Navigate from "@/components/Navigate.vue";
import {Warning, Message} from '@element-plus/icons-vue'

const rightWidth = ref(document.body.clientWidth / 5)
const minWidth = ref(500);
const maxWidth = ref(1400);
const leftWidth = ref(0)

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
});
const pageInfo = ref<Info>({
  id : 0,
  title : '',
  description : '',
  previewUrl : '',
  videoUrl : '',
  type : '',
  createTime : '',
  createBy : 0,
  url: '',
  signature : '',
  nickname : ''
})

const setSize = () => {
  let dw = 440
  let width = document.body.clientWidth - rightWidth.value - dw
  if (width < minWidth.value + dw)
    width = minWidth.value + dw
  else if (width > maxWidth.value + dw)
    width = maxWidth.value + dw

  leftWidth.value = width
}

const getInfo = () => {
  getInfoApi(props.id).then((data) => {
    if (data.code === 200)
      pageInfo.value = data.data
    else
      router.push('/404/NotFind')
  })
}

onMounted(() => {
  getInfo()
  setSize()
})

watch(() => props.id, () => {
  getInfo();
});
window.addEventListener("resize", setSize)
</script>

<template>
  <div>
    <Navigate type="fly"/>
    <div style="width: 100%; height: 172px">
      <div style="width: 100%; height: 64px"></div>
      <div :style="{width: (rightWidth + leftWidth + 20) + 'px'}" style="height: 108px; margin: 0 auto; display: flex">
        <div :style="{width : leftWidth + 'px'}" class="top-left">
          <h1 class="left-title">{{ pageInfo.title}}</h1>
          <div class="left-info-details">
            <div class="left-info-details-list">
            <span class="view-item">
              <svg class="view-icon" style="width:20px;height:20px;" data-v-3b903b56="" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20" width="20" height="20"><path d="M10 4.040041666666666C7.897383333333334 4.040041666666666 6.061606666666667 4.147 4.765636666666667 4.252088333333334C3.806826666666667 4.32984 3.061106666666667 5.0637316666666665 2.9755000000000003 6.015921666666667C2.8803183333333333 7.074671666666667 2.791666666666667 8.471183333333332 2.791666666666667 9.998333333333333C2.791666666666667 11.525566666666668 2.8803183333333333 12.922083333333333 2.9755000000000003 13.9808C3.061106666666667 14.932983333333334 3.806826666666667 15.666916666666667 4.765636666666667 15.744683333333336C6.061611666666668 15.849716666666666 7.897383333333334 15.956666666666667 10 15.956666666666667C12.10285 15.956666666666667 13.93871666666667 15.849716666666666 15.234766666666667 15.74461666666667C16.193416666666668 15.66685 16.939000000000004 14.933216666666667 17.024583333333336 13.981216666666668C17.11975 12.922916666666667 17.208333333333332 11.526666666666666 17.208333333333332 9.998333333333333C17.208333333333332 8.470083333333333 17.11975 7.073818333333334 17.024583333333336 6.015513333333334C16.939000000000004 5.063538333333333 16.193416666666668 4.329865000000001 15.234766666666667 4.252118333333334C13.93871666666667 4.147016666666667 12.10285 4.040041666666666 10 4.040041666666666zM4.684808333333334 3.255365C6.001155 3.14862 7.864583333333334 3.0400416666666668 10 3.0400416666666668C12.13565 3.0400416666666668 13.999199999999998 3.148636666666667 15.315566666666667 3.2553900000000002C16.753416666666666 3.3720016666666672 17.890833333333333 4.483195 18.020583333333335 5.925965000000001C18.11766666666667 7.005906666666667 18.208333333333336 8.433 18.208333333333336 9.998333333333333C18.208333333333336 11.56375 18.11766666666667 12.990833333333335 18.020583333333335 14.0708C17.890833333333333 15.513533333333331 16.753416666666666 16.624733333333335 15.315566666666667 16.74138333333333C13.999199999999998 16.848116666666666 12.13565 16.95666666666667 10 16.95666666666667C7.864583333333334 16.95666666666667 6.001155 16.848116666666666 4.684808333333334 16.7414C3.2467266666666665 16.624750000000002 2.1092383333333338 15.513266666666667 1.9795200000000002 14.070383333333334C1.8823900000000002 12.990000000000002 1.7916666666666667 11.562683333333334 1.7916666666666667 9.998333333333333C1.7916666666666667 8.434066666666666 1.8823900000000002 7.00672 1.9795200000000002 5.926381666666667C2.1092383333333338 4.483463333333334 3.2467266666666665 3.371976666666667 4.684808333333334 3.255365z" fill="currentColor"></path><path d="M12.23275 9.1962C12.851516666666667 9.553483333333332 12.851516666666667 10.44665 12.232683333333332 10.803866666666666L9.57975 12.335600000000001C8.960983333333335 12.692816666666667 8.1875 12.246250000000002 8.187503333333334 11.531733333333333L8.187503333333334 8.4684C8.187503333333334 7.753871666666667 8.960983333333335 7.307296666666667 9.57975 7.66456L12.23275 9.1962z" fill="currentColor">
              </path>
              </svg>
              3.3万
            </span>
              <span class="view-item">
              <svg class="dm-icon" style="width:20px;height:20px;" data-v-3b903b56="" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20" width="20" height="20"><path d="M10 4.040041666666666C7.897383333333334 4.040041666666666 6.061606666666667 4.147 4.765636666666667 4.252088333333334C3.806826666666667 4.32984 3.061106666666667 5.0637316666666665 2.9755000000000003 6.015921666666667C2.8803183333333333 7.074671666666667 2.791666666666667 8.471183333333332 2.791666666666667 9.998333333333333C2.791666666666667 11.525566666666668 2.8803183333333333 12.922083333333333 2.9755000000000003 13.9808C3.061106666666667 14.932983333333334 3.806826666666667 15.666916666666667 4.765636666666667 15.744683333333336C6.061611666666668 15.849716666666666 7.897383333333334 15.956666666666667 10 15.956666666666667C12.10285 15.956666666666667 13.93871666666667 15.849716666666666 15.234766666666667 15.74461666666667C16.193416666666668 15.66685 16.939000000000004 14.933216666666667 17.024583333333336 13.981216666666668C17.11975 12.922916666666667 17.208333333333332 11.526666666666666 17.208333333333332 9.998333333333333C17.208333333333332 8.470083333333333 17.11975 7.073818333333334 17.024583333333336 6.015513333333334C16.939000000000004 5.063538333333333 16.193416666666668 4.329865000000001 15.234766666666667 4.252118333333334C13.93871666666667 4.147016666666667 12.10285 4.040041666666666 10 4.040041666666666zM4.684808333333334 3.255365C6.001155 3.14862 7.864583333333334 3.0400416666666668 10 3.0400416666666668C12.13565 3.0400416666666668 13.999199999999998 3.148636666666667 15.315566666666667 3.2553900000000002C16.753416666666666 3.3720016666666672 17.890833333333333 4.483195 18.020583333333335 5.925965000000001C18.11766666666667 7.005906666666667 18.208333333333336 8.433 18.208333333333336 9.998333333333333C18.208333333333336 11.56375 18.11766666666667 12.990833333333335 18.020583333333335 14.0708C17.890833333333333 15.513533333333331 16.753416666666666 16.624733333333335 15.315566666666667 16.74138333333333C13.999199999999998 16.848116666666666 12.13565 16.95666666666667 10 16.95666666666667C7.864583333333334 16.95666666666667 6.001155 16.848116666666666 4.684808333333334 16.7414C3.2467266666666665 16.624750000000002 2.1092383333333338 15.513266666666667 1.9795200000000002 14.070383333333334C1.8823900000000002 12.990000000000002 1.7916666666666667 11.562683333333334 1.7916666666666667 9.998333333333333C1.7916666666666667 8.434066666666666 1.8823900000000002 7.00672 1.9795200000000002 5.926381666666667C2.1092383333333338 4.483463333333334 3.2467266666666665 3.371976666666667 4.684808333333334 3.255365z" fill="currentColor"></path><path d="M13.291666666666666 8.833333333333334L8.166666666666668 8.833333333333334C7.890526666666666 8.833333333333334 7.666666666666666 8.609449999999999 7.666666666666666 8.333333333333334C7.666666666666666 8.057193333333334 7.890526666666666 7.833333333333334 8.166666666666668 7.833333333333334L13.291666666666666 7.833333333333334C13.567783333333335 7.833333333333334 13.791666666666668 8.057193333333334 13.791666666666668 8.333333333333334C13.791666666666668 8.609449999999999 13.567783333333335 8.833333333333334 13.291666666666666 8.833333333333334z" fill="currentColor"></path><path d="M14.541666666666666 12.166666666666666L9.416666666666668 12.166666666666666C9.140550000000001 12.166666666666666 8.916666666666666 11.942783333333333 8.916666666666666 11.666666666666668C8.916666666666666 11.390550000000001 9.140550000000001 11.166666666666668 9.416666666666668 11.166666666666668L14.541666666666666 11.166666666666668C14.817783333333335 11.166666666666668 15.041666666666668 11.390550000000001 15.041666666666668 11.666666666666668C15.041666666666668 11.942783333333333 14.817783333333335 12.166666666666666 14.541666666666666 12.166666666666666z" fill="currentColor"></path><path d="M6.5 8.333333333333334C6.5 8.609449999999999 6.27614 8.833333333333334 6 8.833333333333334L5.458333333333333 8.833333333333334C5.182193333333334 8.833333333333334 4.958333333333334 8.609449999999999 4.958333333333334 8.333333333333334C4.958333333333334 8.057193333333334 5.182193333333334 7.833333333333334 5.458333333333333 7.833333333333334L6 7.833333333333334C6.27614 7.833333333333334 6.5 8.057193333333334 6.5 8.333333333333334z" fill="currentColor"></path><path d="M7.750000000000001 11.666666666666668C7.750000000000001 11.942783333333333 7.526140000000001 12.166666666666666 7.25 12.166666666666666L6.708333333333334 12.166666666666666C6.432193333333334 12.166666666666666 6.208333333333334 11.942783333333333 6.208333333333334 11.666666666666668C6.208333333333334 11.390550000000001 6.432193333333334 11.166666666666668 6.708333333333334 11.166666666666668L7.25 11.166666666666668C7.526140000000001 11.166666666666668 7.750000000000001 11.390550000000001 7.750000000000001 11.666666666666668z" fill="currentColor">

              </path>
              </svg>
              14
            </span>
              <span style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;line-height: 24px;font-size: 0px;flex-shrink: 0;margin-right: 12px;">
              <span style="white-space: nowrap;line-height: 24px;font-size: 0;">
                <span style="font-size: 13px;height: 100%;display: inline-block;vertical-align: middle;">
                  {{ pageInfo.createTime }}
                </span>
              </span>
            </span>
              <span class="copyright-item">
                <el-icon style="color: #fd676f;margin-right: 2px;"><Warning /></el-icon>
                <span class="item-text" data-v-3b903b56="">未经作者授权，禁止转载</span>
            </span>
            </div>
          </div>
        </div>
        <div :style="{width : rightWidth + 'px'}" class="top-right">
          <div class="up-info-left">
            <div class="up-avatar">
              <el-avatar style="width: 48px; height: 48px" :src="pageInfo.url"/>
            </div>
          </div>
          <div class="up-info-right">
            <div style="margin-bottom: 5px;">
              <div style="margin: 0; padding: 0">
                <div style="display: flex;align-items: center;">
                  <a class="up-nickname">
                    {{ pageInfo.nickname }}
                  </a>
                  <div class="send-message">
                    <a style="display: flex">
                      <div style="margin-top: 2px; margin-right: 2px">
                      <el-icon>
                        <Message/>
                      </el-icon>
                    </div>
                      发消息
                    </a>
                  </div>
                </div>
                <div class="up-desc">
                  {{ pageInfo.signature }}
                </div>
              </div>
            </div>
            <div class="up-btn">
              <div class="bt-body">
                <div class="default-btn">
                  <span data-v-1e89c9b3="" class="follow-btn-inner">
                    <svg data-v-1e89c9b3="" width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" class="follow-btn-icon"><path fill-rule="evenodd" clip-rule="evenodd" d="M7.25098 8.75V13.25C7.25098 13.6642 7.58676 14 8.00098 14C8.41519 14 8.75098 13.6642 8.75098 13.25V8.75H13.251C13.6652 8.75 14.001 8.41421 14.001 8C14.001 7.58579 13.6652 7.25 13.251 7.25H8.75098V2.75C8.75098 2.33579 8.41519 2 8.00098 2C7.58676 2 7.25098 2.33579 7.25098 2.75V7.25H2.75098C2.33676 7.25 2.00098 7.58579 2.00098 8C2.00098 8.41421 2.33676 8.75 2.75098 8.75H7.25098Z" fill="currentColor"></path></svg>
                        关注 3265
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <video-player/>

  </div>
</template>

<style scoped>
.follow-btn-inner {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex: 1;
}
.default-btn:hover {
  background: #00b8f6;
}
.default-btn {
  background: #00AEEC;
  color: #FFFFFF;
  max-width: 230px;
  flex: 1 1 auto;
  box-sizing: border-box;
  padding: 0;
  line-height: 30px;
  height: 30px;
  border-radius: 6px;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  position: relative;
  transition: 0.3s all;
}
.bt-body {
  width: 70%;
  clear: both;
  display: flex;
  margin-top: 5px;
  white-space: nowrap;
}
.up-btn {
  width: 100%;
  clear: both;
  display: flex;
  margin-top: 5px;
  white-space: nowrap;
}
.up-desc {
  font-size: 14px;
  margin-top: 2px;
  line-height: 16px;
  height: 16px;
  color: #9499A0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.send-message {
  font-size: 14px;
  transition: color 0.3s;
  flex-shrink: 0;
  color: #61666D;
  touch-action: manipulation;
  background-color: transparent;
  text-decoration: none;
  outline: none;
  cursor: pointer;
}
.send-message:hover {
  color: #00AEEC;
  outline: 0;
  text-decoration: none;
}
.up-nickname {
  color: #18191C;
  font-size: 16px;
  max-width: calc(100% - 12px - 60px);
  font-weight: 500;
  position: relative;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-right: 12px;
  touch-action: manipulation;
  background-color: transparent;
  text-decoration: none;
  outline: none;
  cursor: pointer;
  transition: color .3s;
}
.up-avatar {
  width: 48px;
  height: 48px;
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.up-info-left {
  display: block;
}
.up-info-right {
  margin-left: 12px;
  flex: 1;
  overflow: auto;
  display: block;
}
.item-text {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.copyright-item {
  margin-right: 0;
  display: inline-flex;
  align-items: center;
  flex-shrink: 0;
  overflow: hidden;
}
.view-icon {
  width: 20px;
  height: 20px;
  margin-right: 4px;
}
.view-item {
  display: inline-flex;
  align-items: center;
  flex-shrink: 0;
  margin-right: 12px;
  overflow: hidden;
}
.left-info-details-list {
  display: flex;
  align-items: center;
  overflow: hidden;
  box-sizing: border-box;
}
.left-info-details {
  font-size: 13px;
  color: #9499A0;
  display: flex;
  align-items: center;
  height: 24px;
  line-height: 18px;
  position: relative;
  overflow: hidden;
}
.left-title {
  font-size: 22px;
  line-height: 40px;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
  font-weight: 500;
  -webkit-font-smoothing: antialiased;
  color: #18191C;
  margin-bottom: 6px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.top-left {
  height: 108px;
}
.top-right {
  margin-left: 20px;
  height: 108px;
  display: flex;
  align-items: center;
}
</style>

