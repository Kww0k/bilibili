<script setup lang="ts">

import {onMounted, ref} from "vue";
import type {Ref} from "vue";
import request from "@/net";
import {listCardApi} from "@/api/card";
import type {Card} from "../../type/card";
import router from "@/router";

const asideWidth = ref(document.body.clientWidth / 5);
const videoWidth = ref(0)
const videoHeight = ref(0)
const minWidth = ref(500);
const maxWidth = ref(1500);
const Wah = ref(0);
const nowWight = ref(0)
const vplayHeight = ref(0)
const vplayWight = ref(0)
const playWindowWidth = ref(920)
const playWindowHeight = ref(0)
const playBoxWidth = ref(0)
const playBoxHeight = ref(0)
const videoBoxWidth = ref(0)
const videoBoxHeight = ref(0)
const progressWidth = ref(0)
const masideWidth = ref(0)
const masideHeight = ref(0)
const topHeight = ref(0)
const topButton = ref(true)
const commitDanmuWidth = ref(0)
const commitDanmuHeight = ref(0)
const asideDisplay = ref('block')
const vplayDisplay = ref('block')
const isPrev = ref(false)
const isSmall = ref(false)
const processHover = ref(false)
const danmuSwitch = ref(false)
const danmuText = ref('')
const isHovered = ref(false)
const isHour = ref(false)
const ctime = ref('')
const dtime = ref('')
// @ts-ignore
let cd;
const videoPlayed = ref(false)
const commitChoose = ref(false)
const url1 = ref('')
const autoPlay = ref(true)
const cardList : Ref<Card[]> = ref([])

const getCardList = () => {
  listCardApi().then((data) => {
    cardList.value = data
  })
}

const setProcess = () => {
  let video = document.getElementById("vd")
  // @ts-ignore
  nowWight.value = video.currentTime / video.duration * progressWidth.value
}

// @ts-ignore
const makeTime = (time) => {
  time = Math.floor(time);
  let h = Math.floor(time / 3600);
  let m = Math.floor((time - h * 3600) / 60);
  let s = time - h * 3600 - m * 60;
  // @ts-ignore
  m = m < 10 ? "0" + m : m;
  // @ts-ignore
  s = s < 10 ? "0" + s : s;
  if (isHour.value) {
    // @ts-ignore
    h = h < 10 ? "0" + h : h;
    return h + ":" + m + ":" + s;
  }
  return m + ":" + s;
}

// @ts-ignore
const changeProcess = (e) => {
  let video = document.getElementById("vd")
  // @ts-ignore
  if (video.paused) {
    // @ts-ignore
    video.play()
    videoPlayed.value = true
  }
  cd = setInterval(() => {
    // @ts-ignore
    ctime.value = makeTime(video.currentTime)
    setProcess()
  }, 1000)
  // @ts-ignore
  if (video.currentTime < 0) return;
  let cur = e.offsetX / progressWidth.value;
  // @ts-ignore
  video.currentTime = cur * video.duration;
  nowWight.value = cur
}

const fullScreen = () => {
  let video = document.getElementById("vd")
  // @ts-ignore
  if (video.RequestFullScreen) {
    // @ts-ignore
    video.RequestFullScreen()
    //兼容Firefox
    // @ts-ignore
  } else if (video.mozRequestFullScreen) {
    // @ts-ignore
    video.mozRequestFullScreen()
    //兼容Chrome, Safari and Opera等
    // @ts-ignore
  } else if (video.webkitRequestFullScreen) {
    // @ts-ignore
    video.webkitRequestFullScreen()
    //兼容IE/Edge
    // @ts-ignore
  } else if (video.msRequestFullscreen) {
    // @ts-ignore
    video.msRequestFullscreen()
  }
}


const setSize = () => {
  Wah.value = 16 / 9
  let aWidth = asideWidth.value
  let dw = 440
  let width = document.body.clientWidth - aWidth - dw
  if (width < minWidth.value + dw)
    width = minWidth.value + dw
  else if (width > maxWidth.value + dw)
    width = maxWidth.value + dw

  let height = width / Wah.value

  videoWidth.value = width
  videoHeight.value = height
  vplayWight.value = width
  vplayHeight.value = height
  playWindowWidth.value = width + 20 + aWidth
  playWindowHeight.value = height
  playBoxWidth.value = width
  playBoxHeight.value = height
  videoBoxWidth.value = width
  videoBoxHeight.value = height
  progressWidth.value = width - 4
  masideHeight.value = height
  masideWidth.value = aWidth
  commitDanmuWidth.value = width
  commitDanmuHeight.value = height / 10
  topHeight.value = height - 105
}

const setSmall = () => {
  if (isSmall.value) return
  videoHeight.value = 400 / Wah.value
  videoWidth.value = 400
  // @ts-ignore
  document.getElementById('videoBox').className = 'videoBoxSmall'
  videoBoxWidth.value = 400
  videoBoxHeight.value = 400 / Wah.value
  vplayDisplay.value = 'none'
  isSmall.value = true
}

const exitSmall = () => {
  if (!isSmall.value) return
  // @ts-ignore
  document.getElementById('videoBox').className = 'videoBox'
  setSize()
  isSmall.value = false
}

window.addEventListener("scroll", function () {
  let playBox = document.getElementById("playBox")
  // @ts-ignore
  let height = parseInt(playBox.style.height) + playBox.offsetTop
  let top = document.documentElement.scrollTop
  if (top > height)
    setSmall()
  else
    exitSmall()
})

window.addEventListener("resize", setSize)

const play = () => {
  let video = document.getElementById("vd")
  // @ts-ignore
  if (video.paused) {
    // @ts-ignore
    video.play()
    videoPlayed.value = true
  } else {
    // @ts-ignore
    video.pause()
    videoPlayed.value = false
    // @ts-ignore
    clearInterval(cd)
  }
  cd = setInterval(() => {
    let video = document.getElementById("vd")
    // @ts-ignore
    ctime.value = makeTime(video.currentTime)
    setProcess()
  }, 1000)
}

onMounted(() => {
  setSize()
  let video = document.getElementById("vd")
  // @ts-ignore
  video.oncanplay = function () {
    // @ts-ignore
    let time = video.duration;//时长
    isHour.value = time >= 3600;
    dtime.value = makeTime(time)
    // @ts-ignore
    ctime.value = makeTime(video.currentTime)
  }
  request.get("/file/web/file/getFileById/" + 102).then((res) => {
    url1.value = res.data.url
  })
  getCardList()
})

</script>

<template>
  <!-- 整体 -->
  <div :style="{ width: playWindowWidth.toString() + 'px', height : playWindowHeight.toString() + 'px'}" id="playWindow"
       class="playWindow">
    <!-- 左边 -->
    <div id="fullback" class="fullback">
      <div :style="{ height: playBoxHeight.toString( ) + 'px', width: playBoxWidth.toString() + 'px'}" id="playBox"
           class="playBox">
        <!-- 视频 -->
        <div @mouseenter="isPrev = true" @mouseleave="isPrev = false"
             :style="{ height: videoBoxHeight.toString( ) + 'px', width: videoBoxWidth.toString() + 'px'}" id="videoBox"
             class="videoBox">
          <video :style="{ height: videoHeight.toString( ) + 'px', width: videoWidth.toString() + 'px'}" id="vd"
                 src="../assets/img/屏幕录制2023-08-29%2012.58.04.mov"/>
          <!-- 弹幕 -->
          <div id="danmu" class="danmu">
          </div>
          <div
              :style="{ height: vplayHeight.toString( ) + 'px', width: vplayWight.toString() + 'px'}"
              id="vplay" class="vplay">
            <div @click="play" id="care" class="prev-false" :class="{'prev-true' : isPrev}"
                 :style="{height: (topHeight)+ 'px'}">
              <div class="bpx-player-follow" @click.stop v-if="topButton">
                <img class="bpx-player-follow-face" src="../assets/img/defaultAvatar.jpg">
                <span class="bpx-player-follow-icon">
                  <span class="bpx-common-svg-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" xml:space="preserve" fill="currentColor" data-pointer="none"
                         style="enable-background:new 0 0 16 16" viewBox="0 0 16 16"><path d="M7.25 7.25v-4.5a.75.75 0 0 1 1.5 0v4.5h4.5a.75.75 0 0 1 0 1.5h-4.5v4.5a.75.75 0 0 1-1.5 0v-4.5h-4.5a.75.75 0 0 1 0-1.5h4.5z"></path>
                  </svg>
                  </span>
                </span>
                <span class="bpx-player-follow-text">关注</span>
              </div>
            </div>
            <div>
              <div @click="play" id="stop" style="width: 100%; text-align: right; height: 50px;">
                <svg v-if="!videoPlayed" style="margin-right: 50px; margin-top: -60px" viewBox="0 0 80 80" width="80"
                     height="80" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                  <defs>
                    <path id="pid-53-svgo-a" d="M0 0h80v80H0z"></path>
                    <path
                        d="M52.546 8.014a3.998 3.998 0 014.222 3.077c.104.446.093.808.039 1.138a2.74 2.74 0 01-.312.881c-.073.132-.16.254-.246.376l-.257.366-.521.73c-.7.969-1.415 1.926-2.154 2.866l-.015.02a240.945 240.945 0 015.986.341l1.643.123.822.066.41.034.206.018.103.008.115.012c1.266.116 2.516.45 3.677.975a11.663 11.663 0 013.166 2.114c.931.87 1.719 1.895 2.321 3.022a11.595 11.595 0 011.224 3.613c.03.157.046.316.068.474l.015.119.013.112.022.206.085.822.159 1.646c.1 1.098.19 2.198.27 3.298.315 4.4.463 8.829.36 13.255a166.489 166.489 0 01-.843 13.213c-.012.127-.034.297-.053.454a7.589 7.589 0 01-.072.475l-.04.237-.05.236a11.762 11.762 0 01-.74 2.287 11.755 11.755 0 01-5.118 5.57 11.705 11.705 0 01-3.623 1.263c-.158.024-.316.052-.475.072l-.477.053-.821.071-1.644.134c-1.096.086-2.192.16-3.288.23a260.08 260.08 0 01-6.578.325c-8.772.324-17.546.22-26.313-.302a242.458 242.458 0 01-3.287-.22l-1.643-.129-.822-.069-.41-.035-.206-.018c-.068-.006-.133-.01-.218-.02a11.566 11.566 0 01-3.7-.992 11.732 11.732 0 01-5.497-5.178 11.73 11.73 0 01-1.215-3.627c-.024-.158-.051-.316-.067-.475l-.026-.238-.013-.119-.01-.103-.07-.823-.132-1.648a190.637 190.637 0 01-.22-3.298c-.256-4.399-.358-8.817-.258-13.233.099-4.412.372-8.811.788-13.197a11.65 11.65 0 013.039-6.835 11.585 11.585 0 016.572-3.563c.157-.023.312-.051.47-.07l.47-.05.82-.07 1.643-.13a228.493 228.493 0 016.647-.405l-.041-.05a88.145 88.145 0 01-2.154-2.867l-.52-.73-.258-.366c-.086-.122-.173-.244-.246-.376a2.74 2.74 0 01-.312-.881 2.808 2.808 0 01.04-1.138 3.998 3.998 0 014.22-3.077 2.8 2.8 0 011.093.313c.294.155.538.347.742.568.102.11.19.23.28.35l.27.359.532.72a88.059 88.059 0 012.06 2.936 73.036 73.036 0 011.929 3.03c.187.313.373.628.556.945 2.724-.047 5.447-.056 8.17-.038.748.006 1.496.015 2.244.026.18-.313.364-.624.549-.934a73.281 73.281 0 011.93-3.03 88.737 88.737 0 012.059-2.935l.533-.72.268-.359c.09-.12.179-.24.281-.35a2.8 2.8 0 011.834-.881zM30.13 34.631a4 4 0 00-.418 1.42 91.157 91.157 0 00-.446 9.128c0 2.828.121 5.656.364 8.483l.11 1.212a4 4 0 005.858 3.143c2.82-1.498 5.55-3.033 8.193-4.606a177.41 177.41 0 005.896-3.666l1.434-.942a4 4 0 00.047-6.632 137.703 137.703 0 00-7.377-4.708 146.88 146.88 0 00-6.879-3.849l-1.4-.725a4 4 0 00-5.382 1.742z"
                        id="pid-53-svgo-d"></path>
                    <filter x="-15.4%" y="-16.3%" width="130.9%" height="132.5%" filterUnits="objectBoundingBox"
                            id="pid-53-svgo-c">
                      <feOffset dy="2" in="SourceAlpha" result="shadowOffsetOuter1"></feOffset>
                      <feGaussianBlur stdDeviation="1" in="shadowOffsetOuter1"
                                      result="shadowBlurOuter1"></feGaussianBlur>
                      <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.3 0" in="shadowBlurOuter1"
                                     result="shadowMatrixOuter1"></feColorMatrix>
                      <feOffset in="SourceAlpha" result="shadowOffsetOuter2"></feOffset>
                      <feGaussianBlur stdDeviation="3.5" in="shadowOffsetOuter2"
                                      result="shadowBlurOuter2"></feGaussianBlur>
                      <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.2 0" in="shadowBlurOuter2"
                                     result="shadowMatrixOuter2"></feColorMatrix>
                      <feMerge>
                        <feMergeNode in="shadowMatrixOuter1"></feMergeNode>
                        <feMergeNode in="shadowMatrixOuter2"></feMergeNode>
                      </feMerge>
                    </filter>
                  </defs>
                  <g fill="none" fill-rule="evenodd" opacity=".8">
                    <mask id="pid-53-svgo-b" fill="#fff">
                      <use xlink:href="#pid-53-svgo-a"></use>
                    </mask>
                    <g mask="url(#pid-53-svgo-b)">
                      <use fill="#000" filter="url(#pid-53-svgo-c)" xlink:href="#pid-53-svgo-d"></use>
                      <use fill="#FFF" xlink:href="#pid-53-svgo-d"></use>
                    </g>
                  </g>
                </svg>
              </div>
              <div @mouseenter="processHover = true" @mouseleave="processHover = false"
                   style="height: 5px; width: 100%;" class="prev-false" :class="{'prev-true' : isPrev}">
                <div @click="changeProcess" id="progress" :style="{ width : progressWidth.toString() + 'px' }"
                     class="progress">
                  <div id="now" :style="{ width : nowWight.toString() + 'px' }" class="now">
                  </div>
                </div>
              </div>
              <div id="consoles" class="consoles prev-false" :class="{'prev-true' : isPrev}">
                <!-- 左边组件 -->
                <div id="group1" class="group1">
                  <div id="play" class="play" @click="play">
                    <span class="bottom-svg">
                      <svg v-if="!videoPlayed" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" width="28"
                           height="28" preserveAspectRatio="xMidYMid meet"
                           style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                          id="__lottie_element_267"><rect width="28" height="28" x="0" y="0"></rect></clipPath></defs><g
                          clip-path="url(#__lottie_element_267)"><g
                          transform="matrix(0.2027878612279892,0,0,0.2027878612279892,20.527984619140625,14.099651336669922)"
                          opacity="0.0000748972336653253" style="display: none;"><g opacity="1"
                                                                                    transform="matrix(1,0,0,1,0,0)"><path
                          fill="rgb(255,255,255)" fill-opacity="1"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path></g></g><g
                          transform="matrix(0.7250617146492004,0,0,0.7250617146492004,22.775720596313477,14)"
                          opacity="0.06871210579857603" style="display: none;"><g opacity="1"
                                                                                  transform="matrix(1,0,0,1,0,0)"><path
                          fill="rgb(255,255,255)" fill-opacity="1"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path></g></g><g
                          transform="matrix(1,0,0,1,14,14)" opacity="1" style="display: block;"><g opacity="1"
                                                                                                   transform="matrix(1,0,0,1,0,0)"><path
                          fill="rgb(255,255,255)" fill-opacity="1" d="M0 0"></path><path stroke-linecap="butt"
                                                                                         stroke-linejoin="miter"
                                                                                         fill-opacity="0"
                                                                                         stroke-miterlimit="4"
                                                                                         stroke="rgb(255,255,255)"
                                                                                         stroke-opacity="1"
                                                                                         stroke-width="0"
                                                                                         d="M0 0"></path></g><g
                          opacity="1" transform="matrix(1,0,0,1,0,0)"><path fill="rgb(255,255,255)" fill-opacity="1"
                                                                            d=" M-7.031000137329102,-10.875 C-7.031000137329102,-10.875 -8.32800006866455,-11.25 -9.42199993133545,-10.468999862670898 C-10.109999656677246,-9.906999588012695 -10,-7.992000102996826 -10,-7.992000102996826 C-10,-7.992000102996826 -10,8.015999794006348 -10,8.015999794006348 C-10,8.015999794006348 -10.125,10.241999626159668 -9,10.991999626159668 C-7.875,11.741999626159668 -5,10.031000137329102 -5,10.031000137329102 C-5,10.031000137329102 7.968999862670898,1.875 7.968999862670898,1.875 C7.968999862670898,1.875 9,1.062000036239624 9,0 C9,-1.062000036239624 7.968999862670898,-1.937999963760376 7.968999862670898,-1.937999963760376 C7.968999862670898,-1.937999963760376 -7.031000137329102,-10.875 -7.031000137329102,-10.875z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-7.031000137329102,-10.875 C-7.031000137329102,-10.875 -8.32800006866455,-11.25 -9.42199993133545,-10.468999862670898 C-10.109999656677246,-9.906999588012695 -10,-7.992000102996826 -10,-7.992000102996826 C-10,-7.992000102996826 -10,8.015999794006348 -10,8.015999794006348 C-10,8.015999794006348 -10.125,10.241999626159668 -9,10.991999626159668 C-7.875,11.741999626159668 -5,10.031000137329102 -5,10.031000137329102 C-5,10.031000137329102 7.968999862670898,1.875 7.968999862670898,1.875 C7.968999862670898,1.875 9,1.062000036239624 9,0 C9,-1.062000036239624 7.968999862670898,-1.937999963760376 7.968999862670898,-1.937999963760376 C7.968999862670898,-1.937999963760376 -7.031000137329102,-10.875 -7.031000137329102,-10.875z"></path></g></g></g>
                      </svg>
                      <svg v-if="videoPlayed" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" width="28"
                           height="28" preserveAspectRatio="xMidYMid meet"
                           style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                          id="__lottie_element_278"><rect width="28" height="28" x="0" y="0"></rect></clipPath></defs><g
                          clip-path="url(#__lottie_element_278)"><g transform="matrix(1,0,0,1,14,14)"
                                                                    opacity="0.9999924555015942"
                                                                    style="display: block;"><g opacity="1"
                                                                                               transform="matrix(1,0,0,1,0,0)"><path
                          fill="rgb(255,255,255)" fill-opacity="1"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path></g></g><g
                          transform="matrix(1,0,0,1,24.812000274658203,14)" opacity="1" style="display: block;"><g
                          opacity="1" transform="matrix(1,0,0,1,0,0)"><path fill="rgb(255,255,255)" fill-opacity="1"
                                                                            d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-5.484000205993652,-10 C-7.953000068664551,-10 -8,-7.984000205993652 -8,-7.984000205993652 C-8,-7.984000205993652 -8.008000373840332,7.984000205993652 -8.008000373840332,7.984000205993652 C-8.008000373840332,7.984000205993652 -7.984000205993652,9.991999626159668 -5.5,9.991999626159668 C-3.0160000324249268,9.991999626159668 -3.003999948501587,7.995999813079834 -3.003999948501587,7.995999813079834 C-3.003999948501587,7.995999813079834 -2.9839999675750732,-8 -2.9839999675750732,-8 C-2.9839999675750732,-8 -3.015000104904175,-10 -5.484000205993652,-10z"></path></g></g><g
                          transform="matrix(1,0,0,1,8.969917297363281,14)" opacity="0.01935606072262246"
                          style="display: none;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                          fill="rgb(255,255,255)" fill-opacity="1" d="M0 0"></path><path stroke-linecap="butt"
                                                                                         stroke-linejoin="miter"
                                                                                         fill-opacity="0"
                                                                                         stroke-miterlimit="4"
                                                                                         stroke="rgb(255,255,255)"
                                                                                         stroke-opacity="1"
                                                                                         stroke-width="0"
                                                                                         d="M0 0"></path></g><g
                          opacity="1" transform="matrix(1,0,0,1,0,0)"><path fill="rgb(255,255,255)" fill-opacity="1"
                                                                            d=" M-7.031000137329102,-10.875 C-7.031000137329102,-10.875 -8.32800006866455,-11.25 -9.42199993133545,-10.468999862670898 C-10.109999656677246,-9.906999588012695 -10,-7.992000102996826 -10,-7.992000102996826 C-10,-7.992000102996826 -10,8.015999794006348 -10,8.015999794006348 C-10,8.015999794006348 -10.125,10.241999626159668 -9,10.991999626159668 C-7.875,11.741999626159668 -5,10.031000137329102 -5,10.031000137329102 C-5,10.031000137329102 7.968999862670898,1.875 7.968999862670898,1.875 C7.968999862670898,1.875 9,1.062000036239624 9,0 C9,-1.062000036239624 7.968999862670898,-1.937999963760376 7.968999862670898,-1.937999963760376 C7.968999862670898,-1.937999963760376 -7.031000137329102,-10.875 -7.031000137329102,-10.875z"></path><path
                          stroke-linecap="butt" stroke-linejoin="miter" fill-opacity="0" stroke-miterlimit="4"
                          stroke="rgb(255,255,255)" stroke-opacity="1" stroke-width="0"
                          d=" M-7.031000137329102,-10.875 C-7.031000137329102,-10.875 -8.32800006866455,-11.25 -9.42199993133545,-10.468999862670898 C-10.109999656677246,-9.906999588012695 -10,-7.992000102996826 -10,-7.992000102996826 C-10,-7.992000102996826 -10,8.015999794006348 -10,8.015999794006348 C-10,8.015999794006348 -10.125,10.241999626159668 -9,10.991999626159668 C-7.875,11.741999626159668 -5,10.031000137329102 -5,10.031000137329102 C-5,10.031000137329102 7.968999862670898,1.875 7.968999862670898,1.875 C7.968999862670898,1.875 9,1.062000036239624 9,0 C9,-1.062000036239624 7.968999862670898,-1.937999963760376 7.968999862670898,-1.937999963760376 C7.968999862670898,-1.937999963760376 -7.031000137329102,-10.875 -7.031000137329102,-10.875z"></path></g></g></g>
                      </svg>
                    </span>
                  </div>
                  <div id="time" class="time">
                    <span class="ctime">{{ ctime }}</span> / <span class="dtime">{{ dtime }}</span>
                  </div>
                </div>
                <!-- 右边组件 -->
                <div id="group2" class="group2">
                  <div style="margin-right: 10px">
                    <div style="cursor: pointer;font-size: 14px;font-weight: 600;">4k 60HZ</div>
                  </div>
                  <div id="speed" class="speed">
                    <div style="cursor: pointer;font-size: 14px;font-weight: 600;">倍速</div>
                  </div>
                  <div class="full-screen">
                      <span class="bottom-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                             preserveAspectRatio="xMidYMid meet"
                             style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                            id="__lottie_element_94"><rect width="88" height="88" x="0" y="0"></rect></clipPath><clipPath
                            id="__lottie_element_96"><path d="M0,0 L88,0 L88,88 L0,88z"></path></clipPath></defs><g
                            clip-path="url(#__lottie_element_94)"><g clip-path="url(#__lottie_element_96)"
                                                                     transform="matrix(1,0,0,1,0,0)" opacity="1"
                                                                     style="display: block;"><g
                            transform="matrix(0.9999999403953552,0,0,0.9999999403953552,28,44)" opacity="1"
                            style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M15.5600004196167,-25.089000701904297 C15.850000381469727,-24.729000091552734 16,-24.288999557495117 16,-23.839000701904297 C16,-23.839000701904297 16,23.840999603271484 16,23.840999603271484 C16,24.94099998474121 15.100000381469727,25.840999603271484 14,25.840999603271484 C13.550000190734863,25.840999603271484 13.109999656677246,25.680999755859375 12.75,25.400999069213867 C12.75,25.400999069213867 -4,12.00100040435791 -4,12.00100040435791 C-4,12.00100040435791 -8,12.00100040435791 -8,12.00100040435791 C-12.420000076293945,12.00100040435791 -16,8.420999526977539 -16,4.000999927520752 C-16,4.000999927520752 -16,-3.999000072479248 -16,-3.999000072479248 C-16,-8.418999671936035 -12.420000076293945,-11.99899959564209 -8,-11.99899959564209 C-8,-11.99899959564209 -4,-11.99899959564209 -4,-11.99899959564209 C-4,-11.99899959564209 12.75,-25.39900016784668 12.75,-25.39900016784668 C13.609999656677246,-26.089000701904297 14.869999885559082,-25.948999404907227 15.5600004196167,-25.089000701904297z"></path></g></g><g
                            transform="matrix(1.005338430404663,0,0,1.005338430404663,56.0044059753418,44.0004997253418)"
                            opacity="0.04031509621745812" style="display: none;"><g opacity="1"
                                                                                    transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-4,-13.859000205993652 C0.7799999713897705,-11.08899974822998 4,-5.919000148773193 4,0.0010000000474974513 C4,5.921000003814697 0.7799999713897705,11.090999603271484 -4,13.861000061035156 C-4,13.861000061035156 -4,-13.859000205993652 -4,-13.859000205993652z"></path></g></g><g
                            transform="matrix(1.0126574039459229,0,0,1.0126574039459229,64.37825012207031,44.0057487487793)"
                            opacity="0.017367325511733187" style="display: none;"><g opacity="1"
                                                                                     transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-6.236000061035156,-28.895999908447266 C4.803999900817871,-23.615999221801758 11.984000205993652,-12.456000328063965 11.984000205993652,-0.006000000052154064 C11.984000205993652,12.454000473022461 4.794000148773193,23.624000549316406 -6.265999794006348,28.893999099731445 C-8.255999565124512,29.8439998626709 -10.645999908447266,29.003999710083008 -11.595999717712402,27.003999710083008 C-12.545999526977539,25.013999938964844 -11.696000099182129,22.624000549316406 -9.706000328063965,21.673999786376953 C-1.406000018119812,17.724000930786133 3.9839999675750732,9.343999862670898 3.9839999675750732,-0.006000000052154064 C3.9839999675750732,-9.345999717712402 -1.3960000276565552,-17.715999603271484 -9.675999641418457,-21.676000595092773 C-11.675999641418457,-22.625999450683594 -12.515999794006348,-25.016000747680664 -11.565999984741211,-27.006000518798828 C-10.616000175476074,-29.006000518798828 -8.22599983215332,-29.84600067138672 -6.236000061035156,-28.895999908447266z"></path></g></g><g
                            transform="matrix(1.000211238861084,0,0,1.000211238861084,56.002994537353516,44)"
                            opacity="1" style="display: none;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-4,-13.859000205993652 C0.7799999713897705,-11.08899974822998 4,-5.919000148773193 4,0.0010000000474974513 C4,5.921000003814697 0.7799999713897705,11.090999603271484 -4,13.861000061035156 C-4,13.861000061035156 -4,-13.859000205993652 -4,-13.859000205993652z"></path></g></g><g
                            transform="matrix(1.0002055168151855,0,0,1.0002055168151855,64.00399780273438,44.00699996948242)"
                            opacity="1" style="display: none;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-6.236000061035156,-28.895999908447266 C4.803999900817871,-23.615999221801758 11.984000205993652,-12.456000328063965 11.984000205993652,-0.006000000052154064 C11.984000205993652,12.454000473022461 4.794000148773193,23.624000549316406 -6.265999794006348,28.893999099731445 C-8.255999565124512,29.8439998626709 -10.645999908447266,29.003999710083008 -11.595999717712402,27.003999710083008 C-12.545999526977539,25.013999938964844 -11.696000099182129,22.624000549316406 -9.706000328063965,21.673999786376953 C-1.406000018119812,17.724000930786133 3.9839999675750732,9.343999862670898 3.9839999675750732,-0.006000000052154064 C3.9839999675750732,-9.345999717712402 -1.3960000276565552,-17.715999603271484 -9.675999641418457,-21.676000595092773 C-11.675999641418457,-22.625999450683594 -12.515999794006348,-25.016000747680664 -11.565999984741211,-27.006000518798828 C-10.616000175476074,-29.006000518798828 -8.22599983215332,-29.84600067138672 -6.236000061035156,-28.895999908447266z"></path></g></g><g
                            transform="matrix(0.9999999403953552,0,0,0.9999999403953552,56,44)" opacity="1"
                            style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-4,-13.859000205993652 C0.7799999713897705,-11.08899974822998 4,-5.919000148773193 4,0.0010000000474974513 C4,5.921000003814697 0.7799999713897705,11.090999603271484 -4,13.861000061035156 C-4,13.861000061035156 -4,-13.859000205993652 -4,-13.859000205993652z"></path></g></g><g
                            transform="matrix(0.9999999403953552,0,0,0.9999999403953552,64.01399993896484,44.00699996948242)"
                            opacity="1" style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-6.236000061035156,-28.895999908447266 C4.803999900817871,-23.615999221801758 11.984000205993652,-12.456000328063965 11.984000205993652,-0.006000000052154064 C11.984000205993652,12.454000473022461 4.794000148773193,23.624000549316406 -6.265999794006348,28.893999099731445 C-8.255999565124512,29.8439998626709 -10.645999908447266,29.003999710083008 -11.595999717712402,27.003999710083008 C-12.545999526977539,25.013999938964844 -11.696000099182129,22.624000549316406 -9.706000328063965,21.673999786376953 C-1.406000018119812,17.724000930786133 3.9839999675750732,9.343999862670898 3.9839999675750732,-0.006000000052154064 C3.9839999675750732,-9.345999717712402 -1.3960000276565552,-17.715999603271484 -9.675999641418457,-21.676000595092773 C-11.675999641418457,-22.625999450683594 -12.515999794006348,-25.016000747680664 -11.565999984741211,-27.006000518798828 C-10.616000175476074,-29.006000518798828 -8.22599983215332,-29.84600067138672 -6.236000061035156,-28.895999908447266z"></path></g></g></g></g>
                        </svg>
                      </span>
                  </div>
                  <div class="full-screen">
                      <span class="bottom-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                             preserveAspectRatio="xMidYMid meet"
                             style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                            id="__lottie_element_134"><rect width="88" height="88" x="0" y="0"></rect></clipPath></defs><g
                            clip-path="url(#__lottie_element_134)"><g
                            transform="matrix(0.9999997615814209,0,0,0.9999997615814209,44,43.875)" opacity="1"
                            style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M0,8.125 C-4.420000076293945,8.125 -8,4.545000076293945 -8,0.125 C-8,-4.295000076293945 -4.420000076293945,-7.875 0,-7.875 C4.420000076293945,-7.875 8,-4.295000076293945 8,0.125 C8,4.545000076293945 4.420000076293945,8.125 0,8.125z M0,16.125 C8.84000015258789,16.125 16,8.96500015258789 16,0.125 C16,-8.71500015258789 8.84000015258789,-15.875 0,-15.875 C-8.84000015258789,-15.875 -16,-8.71500015258789 -16,0.125 C-16,8.96500015258789 -8.84000015258789,16.125 0,16.125z M4.539999961853027,27.51099967956543 C3.059999942779541,27.750999450683594 1.5499999523162842,27.871000289916992 0,27.871000289916992 C-1.5499999523162842,27.871000289916992 -3.059999942779541,27.750999450683594 -4.539999961853027,27.51099967956543 C-4.539999961853027,27.51099967956543 -8.699999809265137,32.56100082397461 -8.699999809265137,32.56100082397461 C-9.9399995803833,34.07099914550781 -12.100000381469727,34.46099853515625 -13.789999961853027,33.48099899291992 C-13.789999961853027,33.48099899291992 -21.780000686645508,28.871000289916992 -21.780000686645508,28.871000289916992 C-23.469999313354492,27.891000747680664 -24.209999084472656,25.83099937438965 -23.520000457763672,24.000999450683594 C-23.520000457763672,24.000999450683594 -21.290000915527344,18.06100082397461 -21.290000915527344,18.06100082397461 C-23.3799991607666,15.621000289916992 -25.049999237060547,12.810999870300293 -26.209999084472656,9.76099967956543 C-26.209999084472656,9.76099967956543 -32.65999984741211,8.680999755859375 -32.65999984741211,8.680999755859375 C-34.59000015258789,8.361000061035156 -36,6.690999984741211 -36,4.741000175476074 C-36,4.741000175476074 -36,-4.488999843597412 -36,-4.488999843597412 C-36,-6.439000129699707 -34.59000015258789,-8.109000205993652 -32.65999984741211,-8.428999900817871 C-32.65999984741211,-8.428999900817871 -26.399999618530273,-9.479000091552734 -26.399999618530273,-9.479000091552734 C-25.309999465942383,-12.559000015258789 -23.690000534057617,-15.388999938964844 -21.65999984741211,-17.868999481201172 C-21.65999984741211,-17.868999481201172 -23.959999084472656,-23.999000549316406 -23.959999084472656,-23.999000549316406 C-24.639999389648438,-25.839000701904297 -23.899999618530273,-27.888999938964844 -22.209999084472656,-28.868999481201172 C-22.209999084472656,-28.868999481201172 -14.220000267028809,-33.479000091552734 -14.220000267028809,-33.479000091552734 C-12.529999732971191,-34.45899963378906 -10.380000114440918,-34.069000244140625 -9.130000114440918,-32.558998107910156 C-9.130000114440918,-32.558998107910156 -5.099999904632568,-27.659000396728516 -5.099999904632568,-27.659000396728516 C-3.450000047683716,-27.9689998626709 -1.7400000095367432,-28.128999710083008 0,-28.128999710083008 C1.7400000095367432,-28.128999710083008 3.450000047683716,-27.9689998626709 5.099999904632568,-27.659000396728516 C5.099999904632568,-27.659000396728516 9.130000114440918,-32.558998107910156 9.130000114440918,-32.558998107910156 C10.380000114440918,-34.069000244140625 12.529999732971191,-34.45899963378906 14.220000267028809,-33.479000091552734 C14.220000267028809,-33.479000091552734 22.209999084472656,-28.868999481201172 22.209999084472656,-28.868999481201172 C23.899999618530273,-27.888999938964844 24.639999389648438,-25.839000701904297 23.959999084472656,-23.999000549316406 C23.959999084472656,-23.999000549316406 21.65999984741211,-17.868999481201172 21.65999984741211,-17.868999481201172 C23.690000534057617,-15.388999938964844 25.309999465942383,-12.559000015258789 26.399999618530273,-9.479000091552734 C26.399999618530273,-9.479000091552734 32.65999984741211,-8.428999900817871 32.65999984741211,-8.428999900817871 C34.59000015258789,-8.109000205993652 36,-6.439000129699707 36,-4.488999843597412 C36,-4.488999843597412 36,4.741000175476074 36,4.741000175476074 C36,6.690999984741211 34.59000015258789,8.361000061035156 32.65999984741211,8.680999755859375 C32.65999984741211,8.680999755859375 26.209999084472656,9.76099967956543 26.209999084472656,9.76099967956543 C25.049999237060547,12.810999870300293 23.3799991607666,15.621000289916992 21.290000915527344,18.06100082397461 C21.290000915527344,18.06100082397461 23.520000457763672,24.000999450683594 23.520000457763672,24.000999450683594 C24.209999084472656,25.83099937438965 23.469999313354492,27.891000747680664 21.780000686645508,28.871000289916992 C21.780000686645508,28.871000289916992 13.789999961853027,33.48099899291992 13.789999961853027,33.48099899291992 C12.100000381469727,34.46099853515625 9.9399995803833,34.07099914550781 8.699999809265137,32.56100082397461 C8.699999809265137,32.56100082397461 4.539999961853027,27.51099967956543 4.539999961853027,27.51099967956543z"></path></g></g></g>
                        </svg>
                      </span>
                  </div>
                  <div class="full-screen">
                      <span class="bottom-svg">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                       preserveAspectRatio="xMidYMid meet"
                       style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                      id="__lottie_element_139"><rect width="88" height="88" x="0" y="0"></rect></clipPath></defs><g
                      clip-path="url(#__lottie_element_139)"><g transform="matrix(1,0,0,1,41,41)" opacity="1"
                                                                style="display: block;"><g opacity="1"
                                                                                           transform="matrix(1,0,0,1,0,0)"><path
                      fill="rgb(255,255,255)" fill-opacity="1"
                      d=" M26,-28 C30.420000076293945,-28 34,-24.420000076293945 34,-20 C34,-20 34,4 34,4 C34,4 26,4 26,4 C26,4 26,-16.799999237060547 26,-16.799999237060547 C26,-18.56999969482422 24.56999969482422,-20 22.799999237060547,-20 C22.799999237060547,-20 -22.799999237060547,-20 -22.799999237060547,-20 C-24.510000228881836,-20 -25.899999618530273,-18.65999984741211 -25.989999771118164,-16.979999542236328 C-25.989999771118164,-16.979999542236328 -26,-16.799999237060547 -26,-16.799999237060547 C-26,-16.799999237060547 -26,16.799999237060547 -26,16.799999237060547 C-26,18.510000228881836 -24.65999984741211,19.899999618530273 -22.979999542236328,19.989999771118164 C-22.979999542236328,19.989999771118164 -22.799999237060547,20 -22.799999237060547,20 C-22.799999237060547,20 2,20 2,20 C2,20 2,28 2,28 C2,28 -26,28 -26,28 C-30.420000076293945,28 -34,24.420000076293945 -34,20 C-34,20 -34,-20 -34,-20 C-34,-24.420000076293945 -30.420000076293945,-28 -26,-28 C-26,-28 26,-28 26,-28z"></path></g></g><g
                      transform="matrix(1,0,0,1,67,65)" opacity="1" style="display: block;"><g opacity="1"
                                                                                               transform="matrix(1,0,0,1,0,0)"><path
                      fill="rgb(255,255,255)" fill-opacity="1"
                      d=" M16,-8 C16,-8 16,8 16,8 C16,10.208999633789062 14.208999633789062,12 12,12 C12,12 -12,12 -12,12 C-14.208999633789062,12 -16,10.208999633789062 -16,8 C-16,8 -16,-8 -16,-8 C-16,-10.208999633789062 -14.208999633789062,-12 -12,-12 C-12,-12 12,-12 12,-12 C14.208999633789062,-12 16,-10.208999633789062 16,-8z"></path></g></g><g
                      transform="matrix(1,0,0,1,30.496000289916992,33.74300003051758)" opacity="0.0039999999999984935"
                      style="display: none;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                      fill="rgb(255,255,255)" fill-opacity="1"
                      d=" M-4.038000106811523,-9.809000015258789 C-4.038000106811523,-9.809000015258789 2.2820000648498535,-3.499000072479248 2.2820000648498535,-3.499000072479248 C2.2820000648498535,-3.499000072479248 2.2820000648498535,-7.599999904632568 2.2820000648498535,-7.599999904632568 C2.2820000648498535,-8.65999984741211 3.0920000076293945,-9.520000457763672 4.131999969482422,-9.600000381469727 C4.131999969482422,-9.600000381469727 4.2820000648498535,-9.600000381469727 4.2820000648498535,-9.600000381469727 C4.2820000648498535,-9.600000381469727 8.281999588012695,-9.600000381469727 8.281999588012695,-9.600000381469727 C9.331999778747559,-9.600000381469727 10.192000389099121,-8.788999557495117 10.272000312805176,-7.749000072479248 C10.272000312805176,-7.749000072479248 10.281999588012695,-7.599999904632568 10.281999588012695,-7.599999904632568 C10.281999588012695,-7.599999904632568 10.281999588012695,8.399999618530273 10.281999588012695,8.399999618530273 C10.281999588012695,9.449999809265137 9.461999893188477,10.321000099182129 8.432000160217285,10.390999794006348 C8.432000160217285,10.390999794006348 8.281999588012695,10.399999618530273 8.281999588012695,10.399999618530273 C8.281999588012695,10.399999618530273 -7.7179999351501465,10.399999618530273 -7.7179999351501465,10.399999618530273 C-8.777999877929688,10.399999618530273 -9.637999534606934,9.581000328063965 -9.718000411987305,8.550999641418457 C-9.718000411987305,8.550999641418457 -9.718000411987305,8.399999618530273 -9.718000411987305,8.399999618530273 C-9.718000411987305,8.399999618530273 -9.718000411987305,4.400000095367432 -9.718000411987305,4.400000095367432 C-9.718000411987305,3.3399999141693115 -8.907999992370605,2.4800000190734863 -7.868000030517578,2.4000000953674316 C-7.868000030517578,2.4000000953674316 -7.7179999351501465,2.4000000953674316 -7.7179999351501465,2.4000000953674316 C-7.7179999351501465,2.4000000953674316 -3.138000011444092,2.4000000953674316 -3.138000011444092,2.4000000953674316 C-3.138000011444092,2.4000000953674316 -9.687999725341797,-4.159999847412109 -9.687999725341797,-4.159999847412109 C-10.477999687194824,-4.940000057220459 -10.477999687194824,-6.199999809265137 -9.687999725341797,-6.989999771118164 C-9.687999725341797,-6.989999771118164 -6.868000030517578,-9.809000015258789 -6.868000030517578,-9.809000015258789 C-6.078000068664551,-10.598999977111816 -4.817999839782715,-10.598999977111816 -4.038000106811523,-9.809000015258789z"></path></g></g></g>
                  </svg>
                      </span>
                  </div>
                  <div class="full-screen">
                      <span class="bottom-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                             preserveAspectRatio="xMidYMid meet"
                             style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                            id="__lottie_element_162"><rect width="88" height="88" x="0" y="0"></rect></clipPath></defs><g
                            clip-path="url(#__lottie_element_162)"><g transform="matrix(1,0,0,1,44,44)" opacity="1"
                                                                      style="display: block;"><g opacity="1"
                                                                                                 transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M12.437999725341797,-12.70199966430664 C12.437999725341797,-12.70199966430664 9.618000030517578,-9.881999969482422 9.618000030517578,-9.881999969482422 C8.82800006866455,-9.092000007629395 8.82800006866455,-7.831999778747559 9.618000030517578,-7.052000045776367 C9.618000030517578,-7.052000045776367 16.687999725341797,0.017999999225139618 16.687999725341797,0.017999999225139618 C16.687999725341797,0.017999999225139618 9.618000030517578,7.0879998207092285 9.618000030517578,7.0879998207092285 C8.82800006866455,7.877999782562256 8.82800006866455,9.137999534606934 9.618000030517578,9.918000221252441 C9.618000030517578,9.918000221252441 12.437999725341797,12.748000144958496 12.437999725341797,12.748000144958496 C13.227999687194824,13.527999877929688 14.48799991607666,13.527999877929688 15.267999649047852,12.748000144958496 C15.267999649047852,12.748000144958496 26.58799934387207,1.437999963760376 26.58799934387207,1.437999963760376 C27.368000030517578,0.6579999923706055 27.368000030517578,-0.6119999885559082 26.58799934387207,-1.3919999599456787 C26.58799934387207,-1.3919999599456787 15.267999649047852,-12.70199966430664 15.267999649047852,-12.70199966430664 C14.48799991607666,-13.491999626159668 13.227999687194824,-13.491999626159668 12.437999725341797,-12.70199966430664z M-12.442000389099121,-12.70199966430664 C-13.182000160217285,-13.442000389099121 -14.362000465393066,-13.482000350952148 -15.142000198364258,-12.821999549865723 C-15.142000198364258,-12.821999549865723 -15.272000312805176,-12.70199966430664 -15.272000312805176,-12.70199966430664 C-15.272000312805176,-12.70199966430664 -26.582000732421875,-1.3919999599456787 -26.582000732421875,-1.3919999599456787 C-27.32200050354004,-0.6520000100135803 -27.36199951171875,0.5180000066757202 -26.70199966430664,1.3079999685287476 C-26.70199966430664,1.3079999685287476 -26.582000732421875,1.437999963760376 -26.582000732421875,1.437999963760376 C-26.582000732421875,1.437999963760376 -15.272000312805176,12.748000144958496 -15.272000312805176,12.748000144958496 C-14.531999588012695,13.48799991607666 -13.362000465393066,13.527999877929688 -12.571999549865723,12.868000030517578 C-12.571999549865723,12.868000030517578 -12.442000389099121,12.748000144958496 -12.442000389099121,12.748000144958496 C-12.442000389099121,12.748000144958496 -9.612000465393066,9.918000221252441 -9.612000465393066,9.918000221252441 C-8.871999740600586,9.178000450134277 -8.831999778747559,8.008000373840332 -9.501999855041504,7.2179999351501465 C-9.501999855041504,7.2179999351501465 -9.612000465393066,7.0879998207092285 -9.612000465393066,7.0879998207092285 C-9.612000465393066,7.0879998207092285 -16.68199920654297,0.017999999225139618 -16.68199920654297,0.017999999225139618 C-16.68199920654297,0.017999999225139618 -9.612000465393066,-7.052000045776367 -9.612000465393066,-7.052000045776367 C-8.871999740600586,-7.791999816894531 -8.831999778747559,-8.961999893188477 -9.501999855041504,-9.751999855041504 C-9.501999855041504,-9.751999855041504 -9.612000465393066,-9.881999969482422 -9.612000465393066,-9.881999969482422 C-9.612000465393066,-9.881999969482422 -12.442000389099121,-12.70199966430664 -12.442000389099121,-12.70199966430664z M28,-28 C32.41999816894531,-28 36,-24.420000076293945 36,-20 C36,-20 36,20 36,20 C36,24.420000076293945 32.41999816894531,28 28,28 C28,28 -28,28 -28,28 C-32.41999816894531,28 -36,24.420000076293945 -36,20 C-36,20 -36,-20 -36,-20 C-36,-24.420000076293945 -32.41999816894531,-28 -28,-28 C-28,-28 28,-28 28,-28z"></path></g></g></g>
                        </svg>
                      </span>
                  </div>
                  <div class="full-screen">
                      <span class="bottom-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                             preserveAspectRatio="xMidYMid meet"
                             style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                            id="__lottie_element_172"><rect width="88" height="88" x="0" y="0"></rect></clipPath></defs><g
                            clip-path="url(#__lottie_element_172)"><g transform="matrix(1,0,0,1,44,44)" opacity="1"
                                                                      style="display: block;"><g opacity="1"
                                                                                                 transform="matrix(1,0,0,1,0,0)"><path
                            fill="rgb(255,255,255)" fill-opacity="1"
                            d=" M-14,-20 C-14,-20 -26,-20 -26,-20 C-27.049999237060547,-20 -27.920000076293945,-19.18000030517578 -27.989999771118164,-18.149999618530273 C-27.989999771118164,-18.149999618530273 -28,-18 -28,-18 C-28,-18 -28,-6 -28,-6 C-28,-4.949999809265137 -27.18000030517578,-4.079999923706055 -26.149999618530273,-4.010000228881836 C-26.149999618530273,-4.010000228881836 -26,-4 -26,-4 C-26,-4 -22,-4 -22,-4 C-20.950000762939453,-4 -20.079999923706055,-4.820000171661377 -20.010000228881836,-5.849999904632568 C-20.010000228881836,-5.849999904632568 -20,-6 -20,-6 C-20,-6 -20,-12 -20,-12 C-20,-12 -14,-12 -14,-12 C-12.949999809265137,-12 -12.079999923706055,-12.819999694824219 -12.010000228881836,-13.850000381469727 C-12.010000228881836,-13.850000381469727 -12,-14 -12,-14 C-12,-14 -12,-18 -12,-18 C-12,-19.049999237060547 -12.819999694824219,-19.920000076293945 -13.850000381469727,-19.989999771118164 C-13.850000381469727,-19.989999771118164 -14,-20 -14,-20z M26,-20 C26,-20 14,-20 14,-20 C12.949999809265137,-20 12.079999923706055,-19.18000030517578 12.010000228881836,-18.149999618530273 C12.010000228881836,-18.149999618530273 12,-18 12,-18 C12,-18 12,-14 12,-14 C12,-12.949999809265137 12.819999694824219,-12.079999923706055 13.850000381469727,-12.010000228881836 C13.850000381469727,-12.010000228881836 14,-12 14,-12 C14,-12 20,-12 20,-12 C20,-12 20,-6 20,-6 C20,-4.949999809265137 20.81999969482422,-4.079999923706055 21.850000381469727,-4.010000228881836 C21.850000381469727,-4.010000228881836 22,-4 22,-4 C22,-4 26,-4 26,-4 C27.049999237060547,-4 27.920000076293945,-4.820000171661377 27.989999771118164,-5.849999904632568 C27.989999771118164,-5.849999904632568 28,-6 28,-6 C28,-6 28,-18 28,-18 C28,-19.049999237060547 27.18000030517578,-19.920000076293945 26.149999618530273,-19.989999771118164 C26.149999618530273,-19.989999771118164 26,-20 26,-20z M-22,4 C-22,4 -26,4 -26,4 C-27.049999237060547,4 -27.920000076293945,4.820000171661377 -27.989999771118164,5.849999904632568 C-27.989999771118164,5.849999904632568 -28,6 -28,6 C-28,6 -28,18 -28,18 C-28,19.049999237060547 -27.18000030517578,19.920000076293945 -26.149999618530273,19.989999771118164 C-26.149999618530273,19.989999771118164 -26,20 -26,20 C-26,20 -14,20 -14,20 C-12.949999809265137,20 -12.079999923706055,19.18000030517578 -12.010000228881836,18.149999618530273 C-12.010000228881836,18.149999618530273 -12,18 -12,18 C-12,18 -12,14 -12,14 C-12,12.949999809265137 -12.819999694824219,12.079999923706055 -13.850000381469727,12.010000228881836 C-13.850000381469727,12.010000228881836 -14,12 -14,12 C-14,12 -20,12 -20,12 C-20,12 -20,6 -20,6 C-20,4.949999809265137 -20.81999969482422,4.079999923706055 -21.850000381469727,4.010000228881836 C-21.850000381469727,4.010000228881836 -22,4 -22,4z M26,4 C26,4 22,4 22,4 C20.950000762939453,4 20.079999923706055,4.820000171661377 20.010000228881836,5.849999904632568 C20.010000228881836,5.849999904632568 20,6 20,6 C20,6 20,12 20,12 C20,12 14,12 14,12 C12.949999809265137,12 12.079999923706055,12.819999694824219 12.010000228881836,13.850000381469727 C12.010000228881836,13.850000381469727 12,14 12,14 C12,14 12,18 12,18 C12,19.049999237060547 12.819999694824219,19.920000076293945 13.850000381469727,19.989999771118164 C13.850000381469727,19.989999771118164 14,20 14,20 C14,20 26,20 26,20 C27.049999237060547,20 27.920000076293945,19.18000030517578 27.989999771118164,18.149999618530273 C27.989999771118164,18.149999618530273 28,18 28,18 C28,18 28,6 28,6 C28,4.949999809265137 27.18000030517578,4.079999923706055 26.149999618530273,4.010000228881836 C26.149999618530273,4.010000228881836 26,4 26,4z M28,-28 C32.41999816894531,-28 36,-24.420000076293945 36,-20 C36,-20 36,20 36,20 C36,24.420000076293945 32.41999816894531,28 28,28 C28,28 -28,28 -28,28 C-32.41999816894531,28 -36,24.420000076293945 -36,20 C-36,20 -36,-20 -36,-20 C-36,-24.420000076293945 -32.41999816894531,-28 -28,-28 C-28,-28 28,-28 28,-28z"></path></g></g></g>
                        </svg>
                      </span>
                  </div>
                  <div id="full-screen" class="full-screen" @click="fullScreen">
                      <span class="bottom-svg">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 88 88" width="88" height="88"
                         preserveAspectRatio="xMidYMid meet"
                         style="width: 100%; height: 100%; transform: translate3d(0px, 0px, 0px);"><defs><clipPath
                        id="__lottie_element_182"><rect width="88" height="88" x="0" y="0"></rect></clipPath></defs><g
                        clip-path="url(#__lottie_element_182)"><g transform="matrix(1,0,0,1,44,74.22000122070312)"
                                                                  opacity="1" style="display: block;"><g opacity="1"
                                                                                                         transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M19.219999313354492,0.2199999988079071 C7.480000019073486,7.630000114440918 -7.480000019073486,7.630000114440918 -19.219999313354492,0.2199999988079071 C-19.219999313354492,0.2199999988079071 -16.219999313354492,-5.78000020980835 -16.219999313354492,-5.78000020980835 C-6.389999866485596,0.75 6.409999847412109,0.75 16.239999771118164,-5.78000020980835 C16.239999771118164,-5.78000020980835 19.219999313354492,0.2199999988079071 19.219999313354492,0.2199999988079071z"></path></g></g><g
                        transform="matrix(1,0,0,1,68.58000183105469,27.895000457763672)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M11.420000076293945,16.104999542236328 C11.420000076293945,16.104999542236328 4.78000020980835,16.104999542236328 4.78000020980835,16.104999542236328 C4.78000020980835,16.104999542236328 4.78000020980835,14.635000228881836 4.78000020980835,14.635000228881836 C4.25,4.054999828338623 -1.940000057220459,-5.425000190734863 -11.420000076293945,-10.164999961853027 C-11.420000076293945,-10.164999961853027 -8.479999542236328,-16.104999542236328 -8.479999542236328,-16.104999542236328 C3.7200000286102295,-10.005000114440918 11.420000076293945,2.4649999141693115 11.420000076293945,16.104999542236328 C11.420000076293945,16.104999542236328 11.420000076293945,16.104999542236328 11.420000076293945,16.104999542236328z"></path></g></g><g
                        transform="matrix(1,0,0,1,19.450000762939453,27.895000457763672)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M-4.809999942779541,16.104999542236328 C-4.809999942779541,16.104999542236328 -11.449999809265137,16.104999542236328 -11.449999809265137,16.104999542236328 C-11.449999809265137,2.4649999141693115 -3.75,-10.005000114440918 8.449999809265137,-16.104999542236328 C8.449999809265137,-16.104999542236328 11.449999809265137,-10.164999961853027 11.449999809265137,-10.164999961853027 C1.4900000095367432,-5.204999923706055 -4.809999942779541,4.974999904632568 -4.809999942779541,16.104999542236328 C-4.809999942779541,16.104999542236328 -4.809999942779541,16.104999542236328 -4.809999942779541,16.104999542236328z"></path></g></g><g
                        transform="matrix(1,0,0,1,44.0099983215332,65.96499633789062)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M-0.009999999776482582,5.34499979019165 C-5.46999979019165,5.355000019073486 -10.800000190734863,3.7149999141693115 -15.319999694824219,0.6549999713897705 C-15.319999694824219,0.6549999713897705 -12.319999694824219,-5.34499979019165 -12.319999694824219,-5.34499979019165 C-5,0.08500000089406967 5,0.08500000089406967 12.319999694824219,-5.34499979019165 C12.319999694824219,-5.34499979019165 15.319999694824219,0.6549999713897705 15.319999694824219,0.6549999713897705 C10.800000190734863,3.7249999046325684 5.460000038146973,5.355000019073486 -0.009999999776482582,5.34499979019165z"></path></g></g><g
                        transform="matrix(1,0,0,1,62.275001525878906,31.780000686645508)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M9.015000343322754,10.850000381469727 C9.015000343322754,10.850000381469727 9.015000343322754,12.220000267028809 9.015000343322754,12.220000267028809 C9.015000343322754,12.220000267028809 2.434999942779541,12.220000267028809 2.434999942779541,12.220000267028809 C2.434999942779541,12.220000267028809 2.434999942779541,11.220000267028809 2.434999942779541,11.220000267028809 C2.075000047683716,3.740000009536743 -2.305000066757202,-2.9700000286102295 -9.015000343322754,-6.309999942779541 C-9.015000343322754,-6.309999942779541 -6.014999866485596,-12.220000267028809 -6.014999866485596,-12.220000267028809 C-6.014999866485596,-12.220000267028809 -6.014999866485596,-12.220000267028809 -6.014999866485596,-12.220000267028809 C2.7850000858306885,-7.800000190734863 8.524999618530273,1.0099999904632568 9.015000343322754,10.850000381469727 C9.015000343322754,10.850000381469727 9.015000343322754,10.850000381469727 9.015000343322754,10.850000381469727z"></path></g></g><g
                        transform="matrix(1,0,0,1,25.729999542236328,31.780000686645508)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M-2.440000057220459,12.220000267028809 C-2.440000057220459,12.220000267028809 -9.050000190734863,12.220000267028809 -9.050000190734863,12.220000267028809 C-9.050000190734863,1.8700000047683716 -3.2100000381469727,-7.590000152587891 6.050000190734863,-12.220000267028809 C6.050000190734863,-12.220000267028809 9.050000190734863,-6.309999942779541 9.050000190734863,-6.309999942779541 C2.0199999809265137,-2.809999942779541 -2.430000066757202,4.360000133514404 -2.440000057220459,12.220000267028809 C-2.440000057220459,12.220000267028809 -2.440000057220459,12.220000267028809 -2.440000057220459,12.220000267028809z"></path></g></g><g
                        transform="matrix(1,0,0,1,44,57.654998779296875)" opacity="1" style="display: block;"><g
                        opacity="1" transform="matrix(1,0,0,1,0,0)"><path fill="rgb(255,255,255)" fill-opacity="1"
                                                                          d=" M0,4.974999904632568 C-4.110000133514404,4.994999885559082 -8.119999885559082,3.6449999809265137 -11.380000114440918,1.1349999904632568 C-11.380000114440918,1.1349999904632568 -8.319999694824219,-4.974999904632568 -8.319999694824219,-4.974999904632568 C-3.6700000762939453,-0.5049999952316284 3.6700000762939453,-0.5049999952316284 8.319999694824219,-4.974999904632568 C8.319999694824219,-4.974999904632568 11.380000114440918,1.1349999904632568 11.380000114440918,1.1349999904632568 C8.109999656677246,3.634999990463257 4.110000133514404,4.985000133514404 0,4.974999904632568 C0,4.974999904632568 0,4.974999904632568 0,4.974999904632568z"></path></g></g><g
                        transform="matrix(1,0,0,1,55.9900016784668,35.665000915527344)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M6.619999885559082,7.40500020980835 C6.619999885559082,7.40500020980835 6.619999885559082,8.335000038146973 6.619999885559082,8.335000038146973 C6.619999885559082,8.335000038146973 0.009999999776482582,8.335000038146973 0.009999999776482582,8.335000038146973 C0.009999999776482582,3.7850000858306885 -2.549999952316284,-0.375 -6.619999885559082,-2.4049999713897705 C-6.619999885559082,-2.4049999713897705 -3.619999885559082,-8.335000038146973 -3.619999885559082,-8.335000038146973 C2.380000114440918,-5.324999809265137 6.300000190734863,0.6949999928474426 6.619999885559082,7.40500020980835 C6.619999885559082,7.40500020980835 6.619999885559082,7.40500020980835 6.619999885559082,7.40500020980835z"></path></g></g><g
                        transform="matrix(1,0,0,1,31.9950008392334,35.665000915527344)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M6.635000228881836,-2.4049999713897705 C2.565000057220459,-0.375 0.004999999888241291,3.7850000858306885 0.004999999888241291,8.335000038146973 C0.004999999888241291,8.335000038146973 -6.635000228881836,8.335000038146973 -6.635000228881836,8.335000038146973 C-6.635000228881836,1.274999976158142 -2.6449999809265137,-5.184999942779541 3.674999952316284,-8.335000038146973 C3.674999952316284,-8.335000038146973 6.635000228881836,-2.4049999713897705 6.635000228881836,-2.4049999713897705z"></path></g></g><g
                        transform="matrix(1,0,0,1,44,66.322998046875)" opacity="1" style="display: block;"><g
                        opacity="1" transform="matrix(1,0,0,1,0,0)"><path fill="rgb(255,255,255)" fill-opacity="1"
                                                                          d=" M8.319000244140625,-13.677000045776367 C8.319000244140625,-13.677000045776367 19.2189998626709,8.123000144958496 19.2189998626709,8.123000144958496 C13.659000396728516,11.642999649047852 7.068999767303467,13.67300033569336 -0.0010000000474974513,13.67300033569336 C-7.071000099182129,13.67300033569336 -13.66100025177002,11.642999649047852 -19.22100067138672,8.123000144958496 C-19.22100067138672,8.123000144958496 -8.321000099182129,-13.677000045776367 -8.321000099182129,-13.677000045776367 C-6.160999774932861,-11.597000122070312 -3.2309999465942383,-10.32699966430664 -0.0010000000474974513,-10.32699966430664 C3.2290000915527344,-10.32699966430664 6.169000148773193,-11.597000122070312 8.319000244140625,-13.677000045776367z"></path></g></g><g
                        transform="matrix(1,0,0,1,64.68399810791016,27.89699935913086)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M15.314000129699707,16.10700035095215 C15.314000129699707,16.10700035095215 -8.685999870300293,16.10700035095215 -8.685999870300293,16.10700035095215 C-8.685999870300293,11.406999588012695 -11.38599967956543,7.336999893188477 -15.315999984741211,5.367000102996826 C-15.315999984741211,5.367000102996826 -4.576000213623047,-16.10300064086914 -4.576000213623047,-16.10300064086914 C7.214000225067139,-10.192999839782715 15.314000129699707,2.006999969482422 15.314000129699707,16.10700035095215z"></path></g></g><g
                        transform="matrix(1,0,0,1,23.31599998474121,27.89699935913086)" opacity="1"
                        style="display: block;"><g opacity="1" transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M4.584000110626221,-16.10300064086914 C4.584000110626221,-16.10300064086914 15.314000129699707,5.367000102996826 15.314000129699707,5.367000102996826 C11.383999824523926,7.336999893188477 8.684000015258789,11.406999588012695 8.684000015258789,16.10700035095215 C8.684000015258789,16.10700035095215 -15.315999984741211,16.10700035095215 -15.315999984741211,16.10700035095215 C-15.315999984741211,2.006999969482422 -7.216000080108643,-10.192999839782715 4.584000110626221,-16.10300064086914z"></path></g></g><g
                        transform="matrix(1,0,0,1,44,44)" opacity="1" style="display: block;"><g opacity="1"
                                                                                                 transform="matrix(1,0,0,1,0,0)"><path
                        fill="rgb(255,255,255)" fill-opacity="1"
                        d=" M0,-4 C2.140000104904175,-4 3.890000104904175,-2.319999933242798 4,-0.20000000298023224 C4,-0.20000000298023224 4,0 4,0 C4,0 4,0.20000000298023224 4,0.20000000298023224 C3.890000104904175,2.319999933242798 2.140000104904175,4 0,4 C-2.2100000381469727,4 -4,2.2100000381469727 -4,0 C-4,-2.2100000381469727 -2.2100000381469727,-4 0,-4z"></path></g></g></g>
                    </svg>
                      </span>
                  </div>
                </div>
              </div>
              <div @mouseenter="processHover = true" @mouseleave="processHover = false" style="height: 5px; width: 100%"
                   :style="{visibility : isPrev ? 'hidden' : 'visible'}">
                <div @click="changeProcess" id="progress" :style="{ width : progressWidth.toString() + 'px' }"
                     class="progress">
                  <div id="now" :style="{ width : nowWight.toString() + 'px' }" class="now">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="danmu-bottom"
           :style="{ height: commitDanmuHeight.toString( ) + 'px', width: commitDanmuWidth.toString() + 'px'}">
        <div style="width: calc(100% - 24px); height: 100%;display: flex;align-items: center; margin-left: 12px">
          <div class="danmu-bottom-left">
            <div style="position: relative">
              111人正在看，
            </div>
            <div style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
              已装填 1 条弹幕
            </div>
          </div>
          <div class="danmu-bottom-right">
            <div class="bpx-player-dm-switch" aria-label="弹幕显示隐藏" @click="danmuSwitch = !danmuSwitch">
              <span v-if="danmuSwitch" class="bui-danmaku-switch-on"><svg xmlns="http://www.w3.org/2000/svg"
                                                                          data-pointer="none" viewBox="0 0 24 24"><path
                  fill-rule="evenodd"
                  d="M11.989 4.828c-.47 0-.975.004-1.515.012l-1.71-2.566a1.008 1.008 0 0 0-1.678 1.118l.999 1.5c-.681.018-1.403.04-2.164.068a4.013 4.013 0 0 0-3.83 3.44c-.165 1.15-.245 2.545-.245 4.185 0 1.965.115 3.67.35 5.116a4.012 4.012 0 0 0 3.763 3.363l.906.046c1.205.063 1.808.095 3.607.095a.988.988 0 0 0 0-1.975c-1.758 0-2.339-.03-3.501-.092l-.915-.047a2.037 2.037 0 0 1-1.91-1.708c-.216-1.324-.325-2.924-.325-4.798 0-1.563.076-2.864.225-3.904.14-.977.96-1.713 1.945-1.747 2.444-.087 4.465-.13 6.063-.131 1.598 0 3.62.044 6.064.13.96.034 1.71.81 1.855 1.814.075.524.113 1.962.141 3.065v.002c.01.342.017.65.025.88a.987.987 0 1 0 1.974-.068c-.008-.226-.016-.523-.025-.856v-.027c-.03-1.118-.073-2.663-.16-3.276-.273-1.906-1.783-3.438-3.74-3.507-.9-.032-1.743-.058-2.531-.078l1.05-1.46a1.008 1.008 0 0 0-1.638-1.177l-1.862 2.59c-.38-.004-.744-.007-1.088-.007h-.13Zm.521 4.775h-1.32v4.631h2.222v.847h-2.618v1.078h2.618l.003.678c.36.026.714.163 1.01.407h.11v-1.085h2.694v-1.078h-2.695v-.847H16.8v-4.63h-1.276a8.59 8.59 0 0 0 .748-1.42L15.183 7.8a14.232 14.232 0 0 1-.814 1.804h-1.518l.693-.308a8.862 8.862 0 0 0-.814-1.408l-1.045.352c.297.396.572.847.825 1.364Zm-4.18 3.564.154-1.485h1.98V8.294h-3.2v.98H9.33v1.43H7.472l-.308 3.453h2.277c0 1.166-.044 1.925-.12 2.277-.078.352-.386.528-.936.528-.308 0-.616-.022-.902-.055l.297 1.067.062.005c.285.02.551.04.818.04 1.001-.067 1.562-.419 1.694-1.057.11-.638.176-1.903.176-3.795h-2.2Zm7.458.11v-.858h-1.254v.858h1.254Zm-2.376-.858v.858h-1.199v-.858h1.2Zm-1.199-.946h1.2v-.902h-1.2v.902Zm2.321 0v-.902h1.254v.902h-1.254Z"
                  clip-rule="evenodd"></path><path fill="#00AEEC" fill-rule="evenodd"
                                                   d="M22.846 14.627a1 1 0 0 0-1.412.075l-5.091 5.703-2.216-2.275-.097-.086-.008-.005a1 1 0 0 0-1.322 1.493l2.963 3.041.093.083.007.005c.407.315 1 .27 1.354-.124l5.81-6.505.08-.102.005-.008a1 1 0 0 0-.166-1.295Z"
                                                   clip-rule="evenodd"></path></svg></span>
              <span v-if="!danmuSwitch" class="bui-danmaku-switch-off"><svg xmlns="http://www.w3.org/2000/svg"
                                                                            data-pointer="none" viewBox="0 0 24 24"><path
                  fill-rule="evenodd"
                  d="m8.085 4.891-.999-1.499a1.008 1.008 0 0 1 1.679-1.118l1.709 2.566c.54-.008 1.045-.012 1.515-.012h.13c.345 0 .707.003 1.088.007l1.862-2.59a1.008 1.008 0 0 1 1.637 1.177l-1.049 1.46c.788.02 1.631.046 2.53.078 1.958.069 3.468 1.6 3.74 3.507.088.613.13 2.158.16 3.276l.001.027c.01.333.017.63.025.856a.987.987 0 0 1-1.974.069c-.008-.23-.016-.539-.025-.881v-.002c-.028-1.103-.066-2.541-.142-3.065-.143-1.004-.895-1.78-1.854-1.813-2.444-.087-4.466-.13-6.064-.131-1.598 0-3.619.044-6.063.13a2.037 2.037 0 0 0-1.945 1.748c-.15 1.04-.225 2.341-.225 3.904 0 1.874.11 3.474.325 4.798.154.949.95 1.66 1.91 1.708a97.58 97.58 0 0 0 5.416.139.988.988 0 0 1 0 1.975c-2.196 0-3.61-.047-5.513-.141A4.012 4.012 0 0 1 2.197 17.7c-.236-1.446-.351-3.151-.351-5.116 0-1.64.08-3.035.245-4.184A4.013 4.013 0 0 1 5.92 4.96c.761-.027 1.483-.05 2.164-.069Zm4.436 4.707h-1.32v4.63h2.222v.848h-2.618v1.078h2.431a5.01 5.01 0 0 1 3.575-3.115V9.598h-1.276a8.59 8.59 0 0 0 .748-1.42l-1.089-.384a14.232 14.232 0 0 1-.814 1.804h-1.518l.693-.308a8.862 8.862 0 0 0-.814-1.408l-1.045.352c.297.396.572.847.825 1.364Zm-4.18 3.564.154-1.485h1.98V8.289h-3.2v.979h2.067v1.43H7.483l-.308 3.454h2.277c0 1.166-.044 1.925-.12 2.277-.078.352-.386.528-.936.528-.308 0-.616-.022-.902-.055l.297 1.067.062.004c.285.02.551.04.818.04 1.001-.066 1.562-.418 1.694-1.056.11-.638.176-1.903.176-3.795h-2.2Zm7.458.11v-.858h-1.254v.858H15.8Zm-2.376-.858v.858h-1.199v-.858h1.2Zm-1.199-.946h1.2v-.902h-1.2v.902Zm2.321 0v-.902H15.8v.902h-1.254Zm3.517 10.594a4 4 0 1 0 0-8 4 4 0 0 0 0 8Zm-.002-1.502a2.5 2.5 0 0 1-2.217-3.657l3.326 3.398a2.49 2.49 0 0 1-1.109.259Zm2.5-2.5c0 .42-.103.815-.286 1.162l-3.328-3.401a2.5 2.5 0 0 1 3.614 2.239Z"
                  clip-rule="evenodd"></path></svg></span>
            </div>
            <div class="bpx-player-dm-switch" aria-label="弹幕设置" @click="danmuSwitch = !danmuSwitch">
              <span class="bui-danmaku-switch-off">
                <svg xmlns="http://www.w3.org/2000/svg" data-pointer="none" viewBox="0 0 24 24"><path
                    fill-rule="evenodd"
                    d="m15.645 4.881 1.06-1.473a.998.998 0 1 0-1.622-1.166L13.22 4.835a110.67 110.67 0 0 0-1.1-.007h-.131c-.47 0-.975.004-1.515.012L8.783 2.3A.998.998 0 0 0 7.12 3.408l.988 1.484c-.688.019-1.418.042-2.188.069a4.013 4.013 0 0 0-3.83 3.44c-.165 1.15-.245 2.545-.245 4.185 0 1.965.115 3.67.35 5.116a4.012 4.012 0 0 0 3.763 3.363c1.903.094 3.317.141 5.513.141a.988.988 0 0 0 0-1.975 97.58 97.58 0 0 1-5.416-.139 2.037 2.037 0 0 1-1.91-1.708c-.216-1.324-.325-2.924-.325-4.798 0-1.563.076-2.864.225-3.904.14-.977.96-1.713 1.945-1.747 2.444-.087 4.465-.13 6.063-.131 1.598 0 3.62.044 6.064.13.96.034 1.71.81 1.855 1.814.075.524.113 1.962.141 3.065v.002c.005.183.01.07.014-.038.004-.096.008-.189.011-.081a.987.987 0 1 0 1.974-.069c-.004-.105-.007-.009-.011.09-.002.056-.004.112-.007.135l-.002.01a.574.574 0 0 1-.005-.091v-.027c-.03-1.118-.073-2.663-.16-3.276-.273-1.906-1.783-3.438-3.74-3.507-.905-.032-1.752-.058-2.543-.079Zm-3.113 4.703h-1.307v4.643h2.2v.04l.651-1.234c.113-.215.281-.389.482-.509v-.11h.235c.137-.049.283-.074.433-.074h1.553V9.584h-1.264a8.5 8.5 0 0 0 .741-1.405l-1.078-.381c-.24.631-.501 1.23-.806 1.786h-1.503l.686-.305c-.228-.501-.5-.959-.806-1.394l-1.034.348c.294.392.566.839.817 1.35Zm-1.7 5.502h2.16l-.564 1.068h-1.595v-1.068Zm-2.498-1.863.152-1.561h1.96V8.289H7.277v.969h2.048v1.435h-1.84l-.306 3.51h2.254c0 1.155-.043 1.906-.12 2.255-.076.348-.38.523-.925.523-.305 0-.61-.022-.893-.055l.294 1.056.061.005c.282.02.546.039.81.039.991-.065 1.547-.414 1.677-1.046.11-.631.175-1.883.175-3.757H8.334Zm5.09-.8v.85h-1.188v-.85h1.187Zm-1.188-.955h1.187v-.893h-1.187v.893Zm2.322.007v-.893h1.241v.893h-1.241Zm.528 2.757a1.26 1.26 0 0 1 1.087-.627l4.003-.009a1.26 1.26 0 0 1 1.094.63l1.721 2.982c.226.39.225.872-.001 1.263l-1.743 3a1.26 1.26 0 0 1-1.086.628l-4.003.009a1.26 1.26 0 0 1-1.094-.63l-1.722-2.982a1.26 1.26 0 0 1 .002-1.263l1.742-3Zm1.967.858a1.26 1.26 0 0 0-1.08.614l-.903 1.513a1.26 1.26 0 0 0-.002 1.289l.885 1.492c.227.384.64.62 1.086.618l2.192-.005a1.26 1.26 0 0 0 1.08-.615l.904-1.518a1.26 1.26 0 0 0 .001-1.288l-.884-1.489a1.26 1.26 0 0 0-1.086-.616l-2.193.005Zm2.517 2.76a1.4 1.4 0 1 1-2.8 0 1.4 1.4 0 0 1 2.8 0Z"
                    clip-rule="evenodd"></path></svg>
              </span>
            </div>
            <div class="danmu-input">
              <div class="danmu-color-choose">
                <span class="color-choose">
                  <span style="display: block;height: 22px;width: 22px;">
                    <svg xmlns="http://www.w3.org/2000/svg" xml:space="preserve" data-pointer="none"
                         style="enable-background:new 0 0 22 22" viewBox="0 0 22 22"><path d="M17 16H5c-.55 0-1 .45-1 1s.45 1 1 1h12c.55 0 1-.45 1-1s-.45-1-1-1zM6.96 15c.39 0 .74-.24.89-.6l.65-1.6h5l.66 1.6c.15.36.5.6.89.6.69 0 1.15-.71.88-1.34l-3.88-8.97C11.87 4.27 11.46 4 11 4s-.87.27-1.05.69l-3.88 8.97c-.27.63.2 1.34.89 1.34zM11 5.98 12.87 11H9.13L11 5.98z">
                    </path></svg>
                  </span>
                </span>
              </div>
              <div class="danmu-info">
                <el-input v-model="danmuText" placeholder="发个友善的弹幕见证当下"/>
              </div>
              <div class="danmu-rules">
                <span>弹幕礼仪</span>
                <span class="svg-icon">
                  <svg xmlns="http://www.w3.org/2000/svg" xml:space="preserve" data-pointer="none" viewBox="0 0 16 16"><path d="m9.188 7.999-3.359 3.359a.75.75 0 1 0 1.061 1.061l3.889-3.889a.75.75 0 0 0 0-1.061L6.89 3.58a.75.75 0 1 0-1.061 1.061l3.359 3.358z"></path></svg>
                </span>
              </div>
              <div class="send-danmu">
                发送
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="video-toolbar" :style="{width: commitDanmuWidth.toString() + 'px'}">
        <div class="toolbar-left">
          <div class="toolbar-left-item-wrap">
            <div title="点赞（Q）" class="video-like video-toolbar-left-item" data-v-6ce38e46=""><!---->
              <svg width="36" height="36" viewBox="0 0 36 36" xmlns="http://www.w3.org/2000/svg"
                   class="video-like-icon video-toolbar-item-icon">
                <path fill-rule="evenodd" clip-rule="evenodd"
                      d="M9.77234 30.8573V11.7471H7.54573C5.50932 11.7471 3.85742 13.3931 3.85742 15.425V27.1794C3.85742 29.2112 5.50932 30.8573 7.54573 30.8573H9.77234ZM11.9902 30.8573V11.7054C14.9897 10.627 16.6942 7.8853 17.1055 3.33591C17.2666 1.55463 18.9633 0.814421 20.5803 1.59505C22.1847 2.36964 23.243 4.32583 23.243 6.93947C23.243 8.50265 23.0478 10.1054 22.6582 11.7471H29.7324C31.7739 11.7471 33.4289 13.402 33.4289 15.4435C33.4289 15.7416 33.3928 16.0386 33.3215 16.328L30.9883 25.7957C30.2558 28.7683 27.5894 30.8573 24.528 30.8573H11.9911H11.9902Z"
                      fill="currentColor"></path>
              </svg>
              <span class="video-like-info video-toolbar-item-text">255</span>
            </div>
          </div>
          <div class="toolbar-left-item-wrap">
            <div title="投币（W）" class="video-coin video-toolbar-left-item" data-v-36000414="" data-v-6ce38e46="">
              <svg width="28" height="28" viewBox="0 0 28 28" xmlns="http://www.w3.org/2000/svg"
                   class="video-like-icon video-toolbar-item-icon" data-v-36000414="">
                <path fill-rule="evenodd" clip-rule="evenodd"
                      d="M14.045 25.5454C7.69377 25.5454 2.54504 20.3967 2.54504 14.0454C2.54504 7.69413 7.69377 2.54541 14.045 2.54541C20.3963 2.54541 25.545 7.69413 25.545 14.0454C25.545 17.0954 24.3334 20.0205 22.1768 22.1771C20.0201 24.3338 17.095 25.5454 14.045 25.5454ZM9.66202 6.81624H18.2761C18.825 6.81624 19.27 7.22183 19.27 7.72216C19.27 8.22248 18.825 8.62807 18.2761 8.62807H14.95V10.2903C17.989 10.4444 20.3766 12.9487 20.3855 15.9916V17.1995C20.3854 17.6997 19.9799 18.1052 19.4796 18.1052C18.9793 18.1052 18.5738 17.6997 18.5737 17.1995V15.9916C18.5667 13.9478 16.9882 12.2535 14.95 12.1022V20.5574C14.95 21.0577 14.5444 21.4633 14.0441 21.4633C13.5437 21.4633 13.1382 21.0577 13.1382 20.5574V12.1022C11.1 12.2535 9.52148 13.9478 9.51448 15.9916V17.1995C9.5144 17.6997 9.10883 18.1052 8.60856 18.1052C8.1083 18.1052 7.70273 17.6997 7.70265 17.1995V15.9916C7.71158 12.9487 10.0992 10.4444 13.1382 10.2903V8.62807H9.66202C9.11309 8.62807 8.66809 8.22248 8.66809 7.72216C8.66809 7.22183 9.11309 6.81624 9.66202 6.81624Z"
                      fill="currentColor"></path>
              </svg>
              <span class="video-coin-info video-toolbar-item-text" data-v-36000414="">69</span>
            </div>
          </div>
          <div class="toolbar-left-item-wrap">
            <div title="收藏（E）" class="video-fav video-toolbar-left-item" data-v-edb4b09a="" data-v-6ce38e46="">
              <svg width="28" height="28" viewBox="0 0 28 28" xmlns="http://www.w3.org/2000/svg"
                   class="video-like-icon video-toolbar-item-icon" data-v-edb4b09a="">
                <path fill-rule="evenodd" clip-rule="evenodd"
                      d="M19.8071 9.26152C18.7438 9.09915 17.7624 8.36846 17.3534 7.39421L15.4723 3.4972C14.8998 2.1982 13.1004 2.1982 12.4461 3.4972L10.6468 7.39421C10.1561 8.36846 9.25639 9.09915 8.19315 9.26152L3.94016 9.91102C2.63155 10.0734 2.05904 11.6972 3.04049 12.6714L6.23023 15.9189C6.96632 16.6496 7.29348 17.705 7.1299 18.7605L6.39381 23.307C6.14844 24.6872 7.62063 25.6614 8.84745 25.0119L12.4461 23.0634C13.4276 22.4951 14.6544 22.4951 15.6359 23.0634L19.2345 25.0119C20.4614 25.6614 21.8518 24.6872 21.6882 23.307L20.8703 18.7605C20.7051 17.705 21.0339 16.6496 21.77 15.9189L24.9597 12.6714C25.9412 11.6972 25.3687 10.0734 24.06 9.91102L19.8071 9.26152Z"
                      fill="currentColor"></path>
              </svg>
              <span class="video-fav-info video-toolbar-item-text" data-v-edb4b09a="">348</span>
            </div>
          </div>
          <div class="toolbar-left-item-wrap">
            <div title="转发" class="video-fav video-toolbar-left-item" data-v-edb4b09a="" data-v-6ce38e46="">
              <svg data-v-c27fd710="" width="28" height="28" viewBox="0 0 28 28" xmlns="http://www.w3.org/2000/svg"
                   class="video-like-icon video-toolbar-item-icon">
                <path
                    d="M12.6058 10.3326V5.44359C12.6058 4.64632 13.2718 4 14.0934 4C14.4423 4 14.78 4.11895 15.0476 4.33606L25.3847 12.7221C26.112 13.3121 26.2087 14.3626 25.6007 15.0684C25.5352 15.1443 25.463 15.2144 25.3847 15.2779L15.0476 23.6639C14.4173 24.1753 13.4791 24.094 12.9521 23.4823C12.7283 23.2226 12.6058 22.8949 12.6058 22.5564V18.053C7.59502 18.053 5.37116 19.9116 2.57197 23.5251C2.47607 23.6489 2.00031 23.7769 2.00031 23.2122C2.00031 16.2165 3.90102 10.3326 12.6058 10.3326Z"
                    fill="currentColor"></path>
              </svg>
              <span class="video-fav-info video-toolbar-item-text" data-v-edb4b09a="">11</span>
            </div>
          </div>
        </div>
        <div class="toolbar-right">
          <div class="video-toolbar-right-item" style="margin-right: 24px">
            <svg data-v-8809ca1e="" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"
                 class="video-complaint-icon video-toolbar-item-icon">
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M9.40194 3.75C10.5566 1.74999 13.4434 1.75001 14.5981 3.75L21.7428 16.125C22.8975 18.125 21.4541 20.625 19.1447 20.625H4.8553C2.5459 20.625 1.10253 18.125 2.25723 16.125L9.40194 3.75ZM12.866 4.75C12.4811 4.08333 11.5189 4.08333 11.134 4.75L3.98928 17.125C3.60438 17.7917 4.08551 18.625 4.8553 18.625H19.1447C19.9145 18.625 20.3957 17.7917 20.0108 17.125L12.866 4.75Z"></path>
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M12 8C12.4142 8 12.75 8.33579 12.75 8.75V13.75C12.75 14.1642 12.4142 14.5 12 14.5C11.5858 14.5 11.25 14.1642 11.25 13.75V8.75C11.25 8.33579 11.5858 8 12 8Z"></path>
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M12 15.5C12.4142 15.5 12.75 15.8358 12.75 16.25V16.75C12.75 17.1642 12.4142 17.5 12 17.5C11.5858 17.5 11.25 17.1642 11.25 16.75V16.25C11.25 15.8358 11.5858 15.5 12 15.5Z"></path>
            </svg>
            <span data-v-8809ca1e="" class="video-complaint-info video-toolbar-item-text">稿件投诉</span>
          </div>
          <div class="video-toolbar-right-item" style="margin-right: 24px">
            <svg data-v-423a8fa8="" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"
                 class="video-complaint-icon video-toolbar-item-icon">
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M6.75 10C6.75 9.58579 7.08579 9.25 7.5 9.25H16.5C16.9142 9.25 17.25 9.58579 17.25 10C17.25 10.4142 16.9142 10.75 16.5 10.75H7.5C7.08579 10.75 6.75 10.4142 6.75 10Z"></path>
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M6.75 14C6.75 13.5858 7.08579 13.25 7.5 13.25H13C13.4142 13.25 13.75 13.5858 13.75 14C13.75 14.4142 13.4142 14.75 13 14.75H7.5C7.08579 14.75 6.75 14.4142 6.75 14Z"></path>
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M12 5.25C9.48998 5.25 7.29811 5.3777 5.75109 5.50315C4.79223 5.58091 4.05407 6.31053 3.96899 7.25687C3.85555 8.51874 3.75 10.1822 3.75 12C3.75 13.8178 3.85555 15.4813 3.96899 16.7431C4.05408 17.6895 4.79214 18.4191 5.75095 18.4968C7.17292 18.6122 9.14013 18.7294 11.3987 18.7476C11.951 18.752 12.3951 19.2033 12.3906 19.7556C12.3862 20.3079 11.9349 20.752 11.3826 20.7475C9.06584 20.7289 7.04905 20.6087 5.58929 20.4903C3.67182 20.3348 2.15034 18.8499 1.97703 16.9222C1.8597 15.6172 1.75 13.892 1.75 12C1.75 10.108 1.8597 8.38283 1.97703 7.07779C2.15034 5.15 3.67203 3.66518 5.58944 3.50969C7.17721 3.38094 9.42438 3.25 12 3.25C14.5759 3.25 16.8232 3.38096 18.411 3.50973C20.3281 3.6652 21.8497 5.14946 22.0231 7.07716C22.1127 8.07392 22.1977 9.31512 22.233 10.6888C22.2471 11.2409 21.811 11.6999 21.2589 11.7141C20.7068 11.7282 20.2478 11.2921 20.2336 10.74C20.1997 9.41683 20.1177 8.21901 20.0311 7.25626C19.946 6.31026 19.2081 5.58094 18.2494 5.50319C16.7023 5.37772 14.5103 5.25 12 5.25Z"></path>
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M18.2557 13.3102C19.0368 12.5292 20.3031 12.5292 21.0841 13.3102L22.4983 14.7244C23.2794 15.5055 23.2794 16.7718 22.4983 17.5528L18.5486 21.5026C18.1735 21.8777 17.6648 22.0884 17.1344 22.0884L15.0702 22.0884C14.3246 22.0884 13.7202 21.484 13.7202 20.7384V18.6742C13.7202 18.1437 13.9309 17.635 14.306 17.26L18.2557 13.3102ZM21.0841 16.1386L19.6699 14.7244L15.7202 18.6742L15.7202 20.0884L17.1344 20.0884L21.0841 16.1386Z"></path>
            </svg>
            <span data-v-423a8fa8="" class="video-note-info video-toolbar-item-text">记笔记</span>
          </div>
          <div class="video-toolbar-right-item">
            <svg data-v-46c3fd44="" width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"
                 class="video-tool-more-icon video-toolbar-item-icon van-popover__reference"
                 aria-describedby="van-popover-7076" tabindex="0">
              <path fill-rule="evenodd" clip-rule="evenodd"
                    d="M13.7484 5.49841C13.7484 6.46404 12.9656 7.24683 11.9999 7.24683C11.0343 7.24683 10.2515 6.46404 10.2515 5.49841C10.2515 4.53279 11.0343 3.75 11.9999 3.75C12.9656 3.75 13.7484 4.53279 13.7484 5.49841ZM13.7484 18.4985C13.7484 19.4641 12.9656 20.2469 11.9999 20.2469C11.0343 20.2469 10.2515 19.4641 10.2515 18.4985C10.2515 17.5328 11.0343 16.75 11.9999 16.75C12.9656 16.75 13.7484 17.5328 13.7484 18.4985ZM11.9999 13.7485C12.9656 13.7485 13.7484 12.9656 13.7484 12C13.7484 11.0343 12.9656 10.2515 11.9999 10.2515C11.0343 10.2515 10.2515 11.0343 10.2515 12C10.2515 12.9656 11.0343 13.7485 11.9999 13.7485Z"
                    fill="currentColor"></path>
            </svg>
          </div>
        </div>
      </div>
      <div style="height: 80px; border-bottom: 1px solid #E3E5E7;" :style="{width: commitDanmuWidth.toString() + 'px'}">
      </div>
      <div :style="{width: commitDanmuWidth.toString() + 'px'}">
        <div style="width: 100%; display: flex">
          <div class="reply-header">
            <span class="nav-title-text" data-v-85efed82="">评论</span>
            <span class="total-reply" data-v-85efed82="">114514</span>
          </div>
          <div class="choose-header">
            <div style="cursor: pointer;font-size: 16px" :style="{color : commitChoose ? '#9499A0' : '#18191C'}">最热
            </div>
            <div style="height: 11px;border-left: solid 1px; margin: 7px 12px 0;"></div>
            <div style="cursor: pointer;font-size: 16px" :style="{color : commitChoose ? '#18191C' : '#9499A0'}">最新
            </div>
          </div>
        </div>
        <div style="width: 100%;">

        </div>
      </div>
    </div>
    <!-- 右边 -->
    <aside style="margin-left: 20px"
           :style="{ width : masideWidth.toString() + 'px', display: asideDisplay }"
           id="maside" class="maside">
      <div class="aside-header">
        <div class="aside-header-left">
          <span>弹幕列表</span>
          <span class="bui-dropdown-icon">
            <svg style="width: 100%; height: 100%" xmlns="http://www.w3.org/2000/svg" data-pointer="none" viewBox="0 0 4 14"><path fill-rule="evenodd" d="M.5 2C.5 1.15 1.15.5 2 .5s1.5.65 1.5 1.5S2.85 3.5 2 3.5.5 2.85.5 2Zm0 5c0-.85.65-1.5 1.5-1.5s1.5.65 1.5 1.5S2.85 8.5 2 8.5.5 7.85.5 7Zm0 5c0-.85.65-1.5 1.5-1.5s1.5.65 1.5 1.5-.65 1.5-1.5 1.5S.5 12.85.5 12Z"></path></svg>
          </span>
        </div>
        <div class="aside-header-right" >
          <svg style="transition: transform 0.3s ease" :style="{transform: isHovered ? 'rotate(180deg)' : ''}" @click="isHovered = !isHovered"
               width="10" height="10" viewBox="0 0 9 9" fill="none"
               xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
                  d="M7.50588 3.40623C7.40825 3.3086 7.24996 3.3086 7.15232 3.40623L4.41244 6.14612L1.67255 3.40623C1.57491 3.3086 1.41662 3.3086 1.31899 3.40623C1.22136 3.50386 1.22136 3.66215 1.31899 3.75978L4.11781 6.5586C4.28053 6.72132 4.54434 6.72132 4.70706 6.5586L7.50588 3.75978C7.60351 3.66215 7.60351 3.50386 7.50588 3.40623Z"
                  fill="currentColor"></path>
            <path
                d="M7.15232 3.40623L7.50588 3.75978L7.50588 3.75978L7.15232 3.40623ZM7.50588 3.40623L7.15232 3.75978L7.15233 3.75978L7.50588 3.40623ZM4.41244 6.14612L4.05888 6.49967C4.15265 6.59344 4.27983 6.64612 4.41244 6.64612C4.54504 6.64612 4.67222 6.59344 4.76599 6.49967L4.41244 6.14612ZM1.67255 3.40623L2.0261 3.05268L2.0261 3.05268L1.67255 3.40623ZM1.31899 3.40623L0.965439 3.05268L0.965439 3.05268L1.31899 3.40623ZM1.31899 3.75978L1.67255 3.40623V3.40623L1.31899 3.75978ZM4.11781 6.5586L3.76425 6.91215L4.11781 6.5586ZM4.70706 6.5586L4.35351 6.20505L4.70706 6.5586ZM7.50588 3.75978L7.15233 3.40623L7.15232 3.40623L7.50588 3.75978ZM7.50588 3.75978C7.40825 3.85742 7.24996 3.85742 7.15232 3.75978L7.85943 3.05268C7.56654 2.75978 7.09166 2.75978 6.79877 3.05268L7.50588 3.75978ZM4.76599 6.49967L7.50588 3.75978L6.79877 3.05268L4.05888 5.79257L4.76599 6.49967ZM1.31899 3.75978L4.05888 6.49967L4.76599 5.79257L2.0261 3.05268L1.31899 3.75978ZM1.67254 3.75979C1.57491 3.85742 1.41662 3.85742 1.31899 3.75979L2.0261 3.05268C1.73321 2.75978 1.25833 2.75978 0.965439 3.05268L1.67254 3.75979ZM1.67255 3.40623C1.77018 3.50386 1.77018 3.66215 1.67255 3.75978L0.965439 3.05268C0.672546 3.34557 0.672546 3.82044 0.965439 4.11334L1.67255 3.40623ZM4.47136 6.20505L1.67255 3.40623L0.965439 4.11334L3.76425 6.91215L4.47136 6.20505ZM4.35351 6.20505C4.38605 6.1725 4.43882 6.1725 4.47136 6.20505L3.76425 6.91215C4.12223 7.27013 4.70264 7.27013 5.06062 6.91215L4.35351 6.20505ZM7.15232 3.40623L4.35351 6.20505L5.06062 6.91215L7.85943 4.11334L7.15232 3.40623ZM7.15233 3.75978C7.05469 3.66215 7.05469 3.50386 7.15233 3.40623L7.85943 4.11334C8.15233 3.82045 8.15233 3.34557 7.85943 3.05268L7.15233 3.75978Z"
                fill="currentColor"></path>
          </svg>
        </div>
      </div>
      <el-collapse-transition>
        <div v-if="isHovered" style="width: 100%; margin-top: 8px; margin-bottom: 5px" :style="{height : videoHeight + 'px'}">
          <div style="width: 100%" :style="{height: videoHeight + commitDanmuHeight - 44 - 31 - 8 - 18 + 'px'}">
          </div>
          <div style="margin-top: 18px;width: 100%;">
            <div class="history-btn">查看历史弹幕</div>
          </div>
        </div>
      </el-collapse-transition>
      <div style="display: flex">
        <div class="left">

        </div>
        <div class="right">

        </div>
      </div>
      <div class="aside-body">
        <div style="margin-bottom: 18px; width: 100%; display: flex; height: 12vh; margin-top: 18px">
          <el-row :gutter="10" style="width: 100%; height: 100%">
            <el-col :span="12">
              <div style="width: 100%; height: 100%;border-radius: 6px;
                    background-size: cover;background-repeat: no-repeat;background-position: center;"
                   :style="{ backgroundImage: `url(${url1})` }">
              </div>
            </el-col>
            <el-col :span="12">
              <div class="card-body">
                <div class="card-info">《卡拉彼丘》火热下载，福利满满~</div>
                <div class="card-desc">
                  <svg data-v-eba1a9e8="" width="32" height="16" viewBox="0 0 32 16" fill="none" xmlns="http://www.w3.org/2000/svg" class="ad-icon" data-v-2b0a4a0d=""><path d="M10.312 2.02015L9.364 2.18815C9.604 2.57215 9.832 3.00415 10.024 3.47215H5.56V6.98815C5.536 9.41215 5.176 11.2962 4.504 12.6642L5.212 13.3002C5.992 11.7162 6.4 9.61615 6.448 6.98815V4.30015H15.34V3.47215H10.972C10.756 2.93215 10.54 2.45215 10.312 2.02015ZM25.948 8.77615H18.124V13.1922H19V12.5562H25.072V13.1922H25.948V8.77615ZM19 11.7402V9.60415H25.072V11.7402H19ZM18.844 4.72015H21.712V6.66415H16.576V7.51615H27.424V6.66415H22.588V4.72015H26.512V3.89215H22.588V2.18815H21.712V3.89215H19.36C19.6 3.44815 19.792 2.98015 19.948 2.47615L19.084 2.38015C18.7 3.64015 17.956 4.70815 16.876 5.57215L17.392 6.25615C17.956 5.78815 18.436 5.27215 18.844 4.72015Z" fill="#9499A0"></path><rect x="0.5" y="0.50415" width="31" height="15" rx="1.5" stroke="#9499A0"></rect>
                  </svg>
                  <span data-v-eba1a9e8="" data-v-2b0a4a0d="">
                    立即下载&gt;&gt;
                  </span>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
        <div style="width: 100%; margin-top: 18px">
          <div class="next-play">
            <div style="vertical-align: middle;line-height: 16px;height: 100%; align-items: center; display: flex">接下来播放</div>
            <div style="margin-right: 4px;font-size: 14px;
              vertical-align: middle;line-height: 16px;color: #9499A0; display: flex">
              <div style="margin-right: 4px;font-size: 14px;
                  vertical-align: middle;line-height: 16px;color: #9499A0;height: 100%; align-items: center; display: flex">自动播放</div>
              <el-switch style="margin-top: -2px" size="small" v-model="autoPlay"/>
            </div>
          </div>
          <div v-for="item in cardList" style="width: 100%">
            <el-row  :gutter="10" style="width: 100%; height: 12vh;margin-top: 8px;">
              <el-col :span="12">
                <div style="width: 100%; height: 100%;border-radius: 6px; cursor: pointer;
                    background-size: cover;background-repeat: no-repeat;background-position: center;"
                     :style="{ backgroundImage: `url(${item.previewUrl})` }" @click="router.push(`/${item.id}`)">
                </div>
              </el-col>
              <el-col :span="12">
                <div class="card-body">
                  <div class="card-info" @click="router.push(`/${item.id}`)"> 1{{ item.title }}</div>
                  <div class="card-desc">
                    <svg style="width:18px;height:18px;" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 18 18" width="18" height="18"><path d="M4.612500000000001 6.186037499999999C4.92315 6.186037499999999 5.175000000000001 6.437872500000001 5.175000000000001 6.748537499999999L5.175000000000001 9.580575C5.175000000000001 10.191075000000001 5.66991 10.686 6.280425000000001 10.686C6.8909325 10.686 7.38585 10.191075000000001 7.38585 9.580575L7.38585 6.748537499999999C7.38585 6.437872500000001 7.637700000000001 6.186037499999999 7.94835 6.186037499999999C8.259 6.186037499999999 8.51085 6.437872500000001 8.51085 6.748537499999999L8.51085 9.580575C8.51085 10.8124125 7.512262499999999 11.811 6.280425000000001 11.811C5.048595000000001 11.811 4.050000000000001 10.8124125 4.050000000000001 9.580575L4.050000000000001 6.748537499999999C4.050000000000001 6.437872500000001 4.3018350000000005 6.186037499999999 4.612500000000001 6.186037499999999z" fill="currentColor"></path><path d="M9.48915 6.748537499999999C9.48915 6.437872500000001 9.7409625 6.186037499999999 10.05165 6.186037499999999L11.79375 6.186037499999999C12.984637500000002 6.186037499999999 13.950000000000001 7.151415 13.950000000000001 8.34225C13.950000000000001 9.5331375 12.984637500000002 10.4985 11.79375 10.4985L10.61415 10.4985L10.61415 11.2485C10.61415 11.55915 10.3623 11.811 10.05165 11.811C9.7409625 11.811 9.48915 11.55915 9.48915 11.2485L9.48915 6.748537499999999zM10.61415 9.3735L11.79375 9.3735C12.3633 9.3735 12.825000000000001 8.9118 12.825000000000001 8.34225C12.825000000000001 7.7727375 12.3633 7.31103 11.79375 7.31103L10.61415 7.31103L10.61415 9.3735z" fill="currentColor"></path><path d="M9 3.7485375000000003C7.111335 3.7485375000000003 5.46225 3.84462 4.2981675 3.939015C3.4891575 4.0046175 2.8620825 4.6226400000000005 2.79 5.424405C2.7045525 6.37485 2.625 7.6282499999999995 2.625 8.9985C2.625 10.368825000000001 2.7045525 11.622225 2.79 12.5726625C2.8620825 13.374412500000002 3.4891575 13.992450000000002 4.2981675 14.058074999999999C5.46225 14.152425000000001 7.111335 14.2485 9 14.2485C10.888874999999999 14.2485 12.538050000000002 14.152425000000001 13.702200000000001 14.058037500000001C14.511074999999998 13.9924125 15.138000000000002 13.3746 15.210075 12.573037500000002C15.295499999999999 11.622975 15.375 10.3698375 15.375 8.9985C15.375 7.627237500000001 15.295499999999999 6.3740775 15.210075 5.4240375C15.138000000000002 4.622475 14.511074999999998 4.00464 13.702200000000001 3.9390374999999995C12.538050000000002 3.844635 10.888874999999999 3.7485375000000003 9 3.7485375000000003zM4.2072375 2.8176975C5.39424 2.7214425 7.074434999999999 2.6235375000000003 9 2.6235375000000003C10.925775 2.6235375000000003 12.606075 2.7214575 13.793099999999999 2.81772C15.141074999999999 2.92704 16.208849999999998 3.9695849999999995 16.330575 5.323297500000001C16.418174999999998 6.297675 16.5 7.585537500000001 16.5 8.9985C16.5 10.4115375 16.418174999999998 11.6994 16.330575 12.6738C16.208849999999998 14.027474999999999 15.141074999999999 15.0700125 13.793099999999999 15.1793625C12.606075 15.275625 10.925775 15.3735 9 15.3735C7.074434999999999 15.3735 5.39424 15.275625 4.2072375 15.179400000000001C2.859045 15.070049999999998 1.7912325 14.027212500000001 1.6695225000000002 12.673425C1.5818849999999998 11.69865 1.5 10.4106 1.5 8.9985C1.5 7.586475 1.5818849999999998 6.2984025 1.6695225000000002 5.3236725C1.7912325 3.96984 2.859045 2.9270175000000003 4.2072375 2.8176975z" fill="currentColor"></path>
                    </svg>
                    <span data-v-eba1a9e8="" data-v-2b0a4a0d="">
                    {{ item.nickName }}
                  </span>
                  </div>
                  <div style="display: inline-flex;align-items: center;color: #9499A0">
                    <svg class="play" style="width:18px;height:18px;margin-right: 4px" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 18 18" width="18" height="18"><path d="M9 3.7485375000000003C7.111335 3.7485375000000003 5.46225 3.84462 4.2981675 3.939015C3.4891575 4.0046175 2.8620825 4.6226400000000005 2.79 5.424405C2.7045525 6.37485 2.625 7.6282499999999995 2.625 8.9985C2.625 10.368825000000001 2.7045525 11.622225 2.79 12.5726625C2.8620825 13.374412500000002 3.4891575 13.992450000000002 4.2981675 14.058074999999999C5.46225 14.152425000000001 7.111335 14.2485 9 14.2485C10.888874999999999 14.2485 12.538050000000002 14.152425000000001 13.702200000000001 14.058037500000001C14.511074999999998 13.9924125 15.138000000000002 13.3746 15.210075 12.573037500000002C15.295499999999999 11.622975 15.375 10.3698375 15.375 8.9985C15.375 7.627237500000001 15.295499999999999 6.3740775 15.210075 5.4240375C15.138000000000002 4.622475 14.511074999999998 4.00464 13.702200000000001 3.9390374999999995C12.538050000000002 3.844635 10.888874999999999 3.7485375000000003 9 3.7485375000000003zM4.2072375 2.8176975C5.39424 2.7214425 7.074434999999999 2.6235375000000003 9 2.6235375000000003C10.925775 2.6235375000000003 12.606075 2.7214575 13.793099999999999 2.81772C15.141074999999999 2.92704 16.208849999999998 3.9695849999999995 16.330575 5.323297500000001C16.418174999999998 6.297675 16.5 7.585537500000001 16.5 8.9985C16.5 10.4115375 16.418174999999998 11.6994 16.330575 12.6738C16.208849999999998 14.027474999999999 15.141074999999999 15.0700125 13.793099999999999 15.1793625C12.606075 15.275625 10.925775 15.3735 9 15.3735C7.074434999999999 15.3735 5.39424 15.275625 4.2072375 15.179400000000001C2.859045 15.070049999999998 1.7912325 14.027212500000001 1.6695225000000002 12.673425C1.5818849999999998 11.69865 1.5 10.4106 1.5 8.9985C1.5 7.586475 1.5818849999999998 6.2984025 1.6695225000000002 5.3236725C1.7912325 3.96984 2.859045 2.9270175000000003 4.2072375 2.8176975z" fill="currentColor"></path><path d="M11.035350000000001 8.2265625C11.6307375 8.570325 11.6307375 9.42975 11.0353125 9.773475L8.652149999999999 11.149425C8.0567625 11.49315 7.3125 11.063475 7.3125075 10.37595L7.3125075 7.624124999999999C7.3125075 6.936607500000001 8.0567625 6.5069025 8.652149999999999 6.850664999999999L11.035350000000001 8.2265625z" fill="currentColor"></path></svg>
                    <span style="margin-right: 4px">1.3万</span>
                    <svg class="dm" style="width:18px;height:18px;margin-right: 4px" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 18 18" width="18" height="18"><path d="M9 3.7485375000000003C7.111335 3.7485375000000003 5.46225 3.84462 4.2981675 3.939015C3.4891575 4.0046175 2.8620825 4.6226400000000005 2.79 5.424405C2.7045525 6.37485 2.625 7.6282499999999995 2.625 8.9985C2.625 10.368825000000001 2.7045525 11.622225 2.79 12.5726625C2.8620825 13.374412500000002 3.4891575 13.992450000000002 4.2981675 14.058074999999999C5.46225 14.152425000000001 7.111335 14.2485 9 14.2485C10.888874999999999 14.2485 12.538050000000002 14.152425000000001 13.702200000000001 14.058037500000001C14.511074999999998 13.9924125 15.138000000000002 13.3746 15.210075 12.573037500000002C15.295499999999999 11.622975 15.375 10.3698375 15.375 8.9985C15.375 7.627237500000001 15.295499999999999 6.3740775 15.210075 5.4240375C15.138000000000002 4.622475 14.511074999999998 4.00464 13.702200000000001 3.9390374999999995C12.538050000000002 3.844635 10.888874999999999 3.7485375000000003 9 3.7485375000000003zM4.2072375 2.8176975C5.39424 2.7214425 7.074434999999999 2.6235375000000003 9 2.6235375000000003C10.925775 2.6235375000000003 12.606075 2.7214575 13.793099999999999 2.81772C15.141074999999999 2.92704 16.208849999999998 3.9695849999999995 16.330575 5.323297500000001C16.418174999999998 6.297675 16.5 7.585537500000001 16.5 8.9985C16.5 10.4115375 16.418174999999998 11.6994 16.330575 12.6738C16.208849999999998 14.027474999999999 15.141074999999999 15.0700125 13.793099999999999 15.1793625C12.606075 15.275625 10.925775 15.3735 9 15.3735C7.074434999999999 15.3735 5.39424 15.275625 4.2072375 15.179400000000001C2.859045 15.070049999999998 1.7912325 14.027212500000001 1.6695225000000002 12.673425C1.5818849999999998 11.69865 1.5 10.4106 1.5 8.9985C1.5 7.586475 1.5818849999999998 6.2984025 1.6695225000000002 5.3236725C1.7912325 3.96984 2.859045 2.9270175000000003 4.2072375 2.8176975z" fill="currentColor"></path><path d="M11.90625 8.0625L7.40625 8.0625C7.0955924999999995 8.0625 6.84375 7.810649999999999 6.84375 7.5C6.84375 7.1893424999999995 7.0955924999999995 6.9375 7.40625 6.9375L11.90625 6.9375C12.2169 6.9375 12.46875 7.1893424999999995 12.46875 7.5C12.46875 7.810649999999999 12.2169 8.0625 11.90625 8.0625z" fill="currentColor"></path><path d="M13.03125 11.0625L8.53125 11.0625C8.220600000000001 11.0625 7.96875 10.810649999999999 7.96875 10.5C7.96875 10.189350000000001 8.220600000000001 9.9375 8.53125 9.9375L13.03125 9.9375C13.3419 9.9375 13.59375 10.189350000000001 13.59375 10.5C13.59375 10.810649999999999 13.3419 11.0625 13.03125 11.0625z" fill="currentColor"></path><path d="M5.90625 7.5C5.90625 7.810649999999999 5.6544075 8.0625 5.34375 8.0625L4.96875 8.0625C4.6580925 8.0625 4.40625 7.810649999999999 4.40625 7.5C4.40625 7.1893424999999995 4.6580925 6.9375 4.96875 6.9375L5.34375 6.9375C5.6544075 6.9375 5.90625 7.1893424999999995 5.90625 7.5z" fill="currentColor"></path><path d="M7.03125 10.5C7.03125 10.810649999999999 6.7794075000000005 11.0625 6.46875 11.0625L6.09375 11.0625C5.7830925 11.0625 5.53125 10.810649999999999 5.53125 10.5C5.53125 10.189350000000001 5.7830925 9.9375 6.09375 9.9375L6.46875 9.9375C6.7794075000000005 9.9375 7.03125 10.189350000000001 7.03125 10.5z" fill="currentColor"></path></svg>
                    13
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-divider/>
          </div>
        </div>
      </div>
    </aside>
  </div>
</template>

<style scoped>
.next-play {
  font-size: 16px;
  font-weight: 500;
  color: #18191C;
  display: flex;
  justify-content: space-between;
  margin-bottom: 6px;
  height: 20px;
}
.card-body {
  width: 100%;
  height: 100%;
  cursor: pointer
}
.card-body:hover .card-info {
  color: #00AEEC;
}
.ad-icon{
  width: 32px;
  height: 16px;
  margin-right: 6px;
  vertical-align: top;
}
.card-desc {
  color: #9499A0;
  letter-spacing: 0;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  word-break: break-word;
  -webkit-line-clamp: 1;
  line-height: 16px;
  display: flex;
  align-items: center;
}
.card-info {
  font-size: 16px;
  text-indent: -0.4em;
  line-height: 20px;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  word-break: break-word;
  -webkit-line-clamp: 2;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
  font-weight: 500;
  -webkit-font-smoothing: antialiased;
}
.history-btn:hover {
  color: #18191C;
}
.history-btn {
  cursor: pointer;
  position: relative;
  height: 31px;
  color: #61666D;
  font-size: 12px;
  display: block;
  border-radius: 6px;
  background-color: #f4f4f4;
  line-height: 31px;
  text-align: center;
  width: 100%;
}
.aside-header-right {
  display: flex;
  align-items: center;
  -webkit-user-select: none;
  margin-right: 16px;
  cursor: pointer;
}
.bui-dropdown-icon {
  margin-top: 2px;
  cursor: pointer;
  height: 14px;
  margin-left: 9px;
  -webkit-transition: fill .3s;
  -o-transition: fill .3s;
  transition: fill .3s;
  vertical-align: middle;
  width: 14px;
}
.aside-header-left {
  font-weight: 500;
  height: 44px;
  line-height: 44px;
  font-size: 16px;
  padding: 0 10px 0 16px;
  position: relative;
  z-index: 2;
  display: flex;
  justify-content: flex-start;
  -webkit-user-select: none;
  fill: #61666D;
}
.aside-header {
  justify-content: space-between;
  align-items: center;
  display: flex;
  vertical-align: middle;
  font-size: 12px;
  font-style: normal;
  border-radius: 6px;
  background: #F1F2F3;
  color: #18191C;
  height: 44px;
  line-height: normal;
  -webkit-user-select: none;
  width: 100%;
}

.video-complaint-icon {
  width: 24px;
  height: 24px;
  margin-right: 6px
}

.video-toolbar-right-item {
  font-size: 14px;
  position: relative;
  display: flex;
  align-items: center;
  transition: all .3s;
  color: #61666D;
  fill: #61666D;
  font-weight: 300;
  cursor: pointer;
}

.video-toolbar-right-item:hover {
  color: #00AEEC;
  fill: #00AEEC;
}

.toolbar-right {
  display: flex;
  align-items: center;
  -webkit-user-select: none;
}

.video-toolbar-item-text {
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-word;
  white-space: nowrap;
}

.video-like-icon {
  width: 36px;
  height: 36px;
  flex-shrink: 0;
  margin-right: 8px;
}

.video-toolbar-left-item {
  width: 100px;
  font-size: 14px;
  position: relative;
  display: flex;
  align-items: center;
  white-space: nowrap;
  transition: all .3s;
  color: #61666D;
  font-weight: 500;
  cursor: pointer;
}

.video-toolbar-left-item:hover {
  color: #00AEEC;
  fill: #00AEEC;
}

.toolbar-left-item-wrap {
  margin-right: 18px;
  position: relative;
}

.toolbar-left {
  position: relative;
  display: flex;
  align-items: center;
  -webkit-user-select: none;
}

.video-toolbar {
  height: 66px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  line-height: 28px;
  border-bottom: 1px solid #E3E5E7;
}

.danmu-info {
  flex: 1;
  margin-right: 5px
}

.danmu-info .el-input >>> .el-input__wrapper {
  background-color: transparent;
  box-shadow: 0 0 0 0;
}

.svg-icon {
  fill: #757575;
  height: 28px;
  width: 16px;
  display: inline-block;
  line-height: 33px;
  vertical-align: top;
}

.danmu-rules {
  margin-right: 5px;
  opacity: 1;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  font-size: 14px;
  cursor: pointer;
}

.danmu-rules:hover {
  fill: #00AEEC;
  color: #00AEEC;
}

.send-danmu {
  cursor: pointer;
  height: 40px;
  width: 64px;
  color: #fff;
  background-color: #00AEEC;
  border-radius: 0 8px 8px 0;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.color-choose {
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  display: -ms-inline-flexbox;
  display: inline-flex;
  height: 100%;
}

.danmu-color-choose {
  color: #00AEEC;
  -webkit-box-flex: 0;
  fill: #757575;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: hsla(0, 0%, 100%, .8);
  cursor: pointer;
  -ms-flex: none;
  flex: none;
  height: 30px;
  line-height: 30px;
  padding: 3px 0;
  position: relative;
  text-align: center;
  width: 36px;
}

.danmu-input {
  height: 40px;
  width: calc(100% - 84px);
  border-radius: 8px;
  min-width: 300px;
  -webkit-box-align: center;
  -webkit-box-flex: 1;
  align-items: center;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  position: relative;
  background: #F1F2F3;
  color: #999;
}

.bui-danmaku-switch-off {
  display: block;
  height: 24px;
  width: 24px;
  fill: #18191C;
}

.bui-danmaku-switch-on {
  display: block;
  height: 24px;
  width: 24px;
  fill: #00AEEC;
}

.bpx-player-dm-switch {
  cursor: pointer;
  margin-right: 18px;
  height: 24px;
  width: 24px;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  align-items: center;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  justify-content: flex-start;
  line-height: normal;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  vertical-align: middle;
}

.danmu-bottom-right {
  -webkit-box-flex: 1;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex: auto;
  flex: auto;
  height: 34px;
}

.danmu-bottom-left {
  line-height: 20px;
  margin-right: 36px;
  align-items: center;
  color: #61666D;
  display: flex;
  flex-shrink: 1;
  height: 16px;
  overflow: hidden;
  position: relative;
  -webkit-user-select: none;
  white-space: nowrap;
  width: auto;
}

.danmu-bottom {
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  position: relative;
  background-color: white;
  font-size: 14px;
  flex-shrink: 0;
}

.bpx-player-follow-text {
  -webkit-box-flex: 0;
  -ms-flex: none;
  flex: none;
  margin-left: 5px;
}

.bpx-player-follow-icon {
  fill: #fff;
  -webkit-box-flex: 0;
  -ms-flex: none;
  flex: none;
  height: 14px;
  line-height: 14px;
  margin-left: 8px;
  width: 14px;
}

.bpx-common-svg-icon {
  fill: #fff;
  line-height: 14px;
  display: -ms-inline-flexbox;
  display: inline-flex;
  height: 100%;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  width: 100%;
}

.bpx-player-follow:hover {
  background-color: rgba(0, 0, 0, .6);
}

.bpx-player-follow-face {

  -webkit-box-flex: 0;
  border-radius: 50%;
  -ms-flex: none;
  flex: none;
  height: 24px;
  margin-left: 4px;
  vertical-align: bottom;
  width: 24px;
  border-style: none;
}

.bpx-player-follow {
  position: absolute;
  fill: #fff;
  color: #fff;
  -webkit-box-align: center;
  -ms-flex-align: center;
  -webkit-box-flex: 0;
  align-items: center;
  background-color: rgba(0, 0, 0, .4);
  border-radius: 26px;
  cursor: pointer;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex: 0;
  flex: 0;
  font-size: 12px;
  font-weight: 400;
  height: 32px;
  line-height: 32px;
  margin-bottom: 8px;
  pointer-events: all;
  text-align: center;
  width: 89px;
  z-index: 2;
  margin-left: 12px;
  margin-top: 18px;
}

.speed {
  margin-right: 10px;
}

.bottom-svg {
  opacity: 1;
  height: 22px;
  vertical-align: middle;
  transition: fill .15s ease-in-out;
  transform: none !important;
}

.full-screen {
  fill: #fff;
  color: hsla(0, 0%, 100%, .8);
  font-size: 0;
  height: 22px;
  line-height: 22px;
  outline: 0;
  position: relative;
  text-align: center;
  width: 36px;
  z-index: 2;
  cursor: pointer;
}

.play {
  font-size: 0;
  height: 22px;
  line-height: 22px;
  outline: 0;
  position: relative;
  text-align: center;
  width: 36px;
  z-index: 2;
  cursor: pointer;
}

.total-reply {
  font-size: 14px;
  margin: 10px 36px 0 6px;
  font-weight: 400;
  color: #9499A0;
}

.nav-title-text {
  font-size: 24px;
  color: #18191C;
  font-family: PingFang SC, HarmonyOS_Medium, Helvetica Neue, Microsoft YaHei, sans-serif;
  font-weight: 500;
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

.choose-header {
  display: flex;
  margin-top: 33px;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
  font-weight: 400;
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
  margin-bottom: 22px;
}

.reply-header {
  display: flex;
  margin-top: 24px;
  font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
  font-weight: 400;
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
  margin-bottom: 22px;
}

@keyframes danmuAnimation {
  0% {
    right: 0;
    transform: translate(100%, 0);
  }
  100% {
    right: 100%;
    transform: translate(0, 0);
  }
}

.danmu {
  position: absolute; /*叠放在视频窗口上*/
  top: 0;
  left: 0;
  z-index: 200;
  overflow: hidden;
}

.danmu div {
  position: absolute;
  right: 100%;
  font-size: 20px;
  color: #fefcc9;
  white-space: nowrap;
  text-shadow: 0 0 2px #999;
  animation: danmuAnimation ease-in 10s;
}


.videoBoxSmall {
  box-shadow: 0 0 10px grey;
  border-radius: 5px;
  position: fixed;
  right: 110px;
  bottom: 40px;
  z-index: 100;
}

.vplay {
  position: absolute;
  left: 0;
  top: 0;
  z-index: 50;
}

.videoBox {
  position: relative;
}

.fullback-theme {
  background-color: #000000;
  display: flex;
  justify-content: center;
  position: fixed;
  z-index: 500;
  transition: all 2s;
  top: 0;
  left: 0;
}

.playWindow {
  display: flex;
  width: 920px;
  margin: 0 auto;
}

/*
进度条
 */
.progress {
  width: 600px;
  background-color: #999999;
  cursor: pointer;
  border-radius: 3px;
  height: 2px;
  position: relative;
  margin: auto;
}

.progress:hover {
  height: 5px;
  transition: all 0.2s;
  cursor: pointer;
}

.now {
  width: 0;
  background-color: #00a1d6;
  border-radius: 3px;
  height: 2px;
  transition: width 500ms;
  position: absolute;
  top: 0;
  left: 0;
}

/* 控件区 */
.consoles {
  display: flex;
  justify-content: space-between;
  height: 45px;
  font-size: 13px;
  bottom: 0;
  color: white;
  width: 100%;
}

.group1, .group2 {
  display: flex;
  padding: 10px;
}

.maside {
  width: 300px;
  flex: none;
}

.prev-false {
  opacity: 0;
  visibility: hidden;
  transition: opacity 0.5s;
}

.prev-true {
  opacity: 1;
  visibility: visible;
}
</style>