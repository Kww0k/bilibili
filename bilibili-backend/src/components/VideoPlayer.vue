<script setup lang="js">

import {onMounted, ref} from "vue";

const asideWidth = ref(document.body.clientWidth / 5);
const videoWidth = ref(0)
const videoHeight = ref(0)
const minWidth = ref(500);
const maxWidth = ref(1400);
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
const stopDisplay = ref('none')
const consoles = ref('none')
const asideDisplay = ref('block')
const vplayDisplay = ref('none')
const isSmall = ref(false)
const isWide = ref(false)
const isTheme = ref(false)
const isHour = ref(false)
const ctime = ref('')
const dtime = ref('')
let cd;

const setProcess = () => {
  let video = document.getElementById("vd")
  nowWight.value = video.currentTime / video.duration * progressWidth.value
}

const makeTime = (time) => {
  time = Math.floor(time);
  let h = Math.floor(time / 3600);
  let m = Math.floor((time - h * 3600) / 60);
  let s = time - h * 3600 - m * 60;
  m = m < 10 ? "0" + m : m;
  s = s < 10 ? "0" + s : s;
  if (isHour.value) {
    h = h < 10 ? "0" + h : h;
    return h + ":" + m + ":" + s;
  }
  return m + ":" + s;
}

const changeProcess = (e) => {
  let video = document.getElementById("vd")
  if (video.paused) {
    video.play()
  }
  cd = setInterval(() => {
    ctime.value = makeTime(video.currentTime)
    setProcess()
  }, 1000)
  if (video.currentTime < 0) return;
  let cur = e.offsetX / progressWidth.value;
  video.currentTime = cur * video.duration;
  nowWight.value = cur
}

const fullScreen = () => {
  let video = document.getElementById("vd")
  if (video.RequestFullScreen) {
    video.RequestFullScreen()
    //兼容Firefox
  } else if (video.mozRequestFullScreen) {
    video.mozRequestFullScreen()
    //兼容Chrome, Safari and Opera等
  } else if (video.webkitRequestFullScreen) {
    video.webkitRequestFullScreen()
    //兼容IE/Edge
  } else if (video.msRequestFullscreen) {
    video.msRequestFullscreen()
  }
}

const themeScreen = () => {
  let fullback = document.getElementById('fullback')
  if (isTheme.value) {
    fullback.style.height = ""
    fullback.style.width = ""
    fullback.className = "fullback"
    commitDanmuWidth.value = videoWidth.value
    commitDanmuHeight.value = videoHeight.value / 10
  } else {
    fullback.style.width = document.body.clientWidth + 'px'
    fullback.style.height = document.body.clientHeight + 'px'
    commitDanmuWidth.value = 0
    commitDanmuHeight.value = 0
    fullback.className = 'fullback-theme'
  }
  isTheme.value = !isTheme.value
}

const wideScreen = () => {
  isWide.value = !isWide.value
  if (isWide.value) {
    asideDisplay.value = "none"
  } else {
    asideDisplay.value = "block"
  }
  setSize()
}

const showBar = () => {
  if (document.getElementById('videoBox').className === 'videoBox') {
    topButton.value = true
    vplayDisplay.value = 'block'
    stopDisplay.value = 'block'
    consoles.value = 'flex'
    topHeight.value = topHeight.value - 97
  }

}

const unShowBar = () => {
  if (document.getElementById('videoBox').className === 'videoBox') {
    topButton.value = false
    vplayDisplay.value = 'none'
    stopDisplay.value = 'none'
    consoles.value = 'none'
    topHeight.value = topHeight.value + 97
  }
}

const speed = () => {
  let video = document.getElementById("vd")
  let speedList = document.getElementById("speed_list").getElementsByTagName("div")
  for (let i in speedList){
    speedList[i].onclick = function (){
      let v = this.getAttribute("value");
      document.getElementById("curSpeed").innerHTML= v +  "x"
      video.playbackRate = v
    }}
}

const setSize = () => {
  Wah.value = 16 / 9
  let aWidth;
  if (isWide.value)
    aWidth = 0
  else
    aWidth = asideWidth.value
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
  topHeight.value = height - 100
}

const setSmall = () => {
  if (isSmall.value) return
  videoHeight.value = 400 / Wah.value
  videoWidth.value = 400
  document.getElementById('videoBox').className = 'videoBoxSmall'
  videoBoxWidth.value = 400
  videoBoxHeight.value = 400 / Wah.value
  vplayDisplay.value = 'none'
  isSmall.value = true
}

const exitSmall = () => {
  if (!isSmall.value) return
  document.getElementById('videoBox').className = 'videoBox'
  setSize()
  isSmall.value = false
}

window.addEventListener("scroll", function () {
  let playBox = document.getElementById("playBox")
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
  if (video.paused)
    video.play()
  else {
    video.pause()
    clearInterval(cd)
  }
  cd = setInterval(() => {
    let video = document.getElementById("vd")
    ctime.value = makeTime(video.currentTime)
    setProcess()
  }, 1000)
}

onMounted(() => {
  setSize()
  let video = document.getElementById("vd")
  video.oncanplay = function () {
    let time = video.duration;//时长
    isHour.value = time >= 3600;
    dtime.value = makeTime(time)
    ctime.value = makeTime(video.currentTime)
  }
})

</script>

<template>
  <!-- 整体 -->
  <div :style="{ width: playWindowWidth.toString() + 'px', height : playWindowHeight.toString() + 'px'}" id="playWindow"
       class="playWindow">
    <!-- 左边 -->
    <div id="fullback" class="fullback" style=" box-shadow: 0 0 10px grey;">
      <div :style="{ height: playBoxHeight.toString( ) + 'px', width: playBoxWidth.toString() + 'px'}" id="playBox"
           class="playBox">
        <!-- 视频 -->
        <div @mouseenter="showBar" @mouseleave="unShowBar"
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
            <div @click="play" id="care" :style="{height: (topHeight + 97)+ 'px' }">
              <el-button :style="{display: vplayDisplay}" @click.stop v-if="topButton">关注</el-button>
            </div>
            <div>
              <div :style="{display: stopDisplay}" @click="play" id="stop" style="width: 100%; text-align: right; height: 50px">
                <el-button style="width: 50px">暂停</el-button>
              </div>
              <div style="height: 5px; width: 100%">
                <div @click="changeProcess" id="progress" :style="{ width : progressWidth.toString() + 'px' }"
                     class="progress">
                  <div id="now" :style="{ width : nowWight.toString() + 'px' }" class="now">
                  </div>
                </div>
              </div>
              <div id="consoles" class="consoles" :style="{display : consoles}">
                <!-- 左边组件 -->
                <div id="group1" class="group1">
                  <div id="play" class="play">
                    <el-button @click="play">开始</el-button>
                  </div>
                  <div id="time" class="time">
                    <span class="ctime">{{ ctime }}</span> / <span class="dtime">{{ dtime }}</span>
                  </div>
                </div>
                <!-- 右边组件 -->
                <div id="group2" class="group2">
                  <div @click="speed" id="speed" class="speed">
                    <div id="curSpeed" style="font-weight: 600">倍速</div>
                    <div  id="speed_list" class="speed_list">
                      <div value="2">2 &times;</div>
                      <div value="1.5">1.5 &times;</div>
                      <div value="1.25">1.25 &times;</div>
                      <div value="1">1 &times;</div>
                      <div value="0.75">0.75 &times;</div>
                      <div value="0.5">0.5 &times;</div>
                    </div>
                  </div>
                  <div>
                    <el-button style="margin-right: 5px">音量</el-button>
                  </div>
                  <div id="theme-screen" class="theme-screen" style="margin-right: 5px" title="剧场">
                    <el-button @click="themeScreen">剧场</el-button>
                  </div>
                  <div id="wide-screen" class="wide-screen" style="margin-right: 5px" title="宽屏">
                    <el-button @click="wideScreen">宽屏</el-button>
                  </div>
                  <div id="full-screen" class="full-screen" title="全屏">
                    <el-button @click="fullScreen">全屏</el-button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div :style="{ height: commitDanmuHeight.toString( ) + 'px', width: commitDanmuWidth.toString() + 'px'}"
           style=" box-shadow: 0 0 10px grey;background-color: white" class="flex items-center">
        <div>
          <el-button>弹幕</el-button>
        </div>
      </div>
    </div>
    <!-- 右边 -->
    <aside style="margin-left: 20px"
           :style="{ width : masideWidth.toString() + 'px', height : masideHeight.toString() + 'px', display: asideDisplay }"
           id="maside" class="maside">
    </aside>
  </div>
</template>

<style scoped>
@keyframes danmuAnimation{
  0%{
    right: 0;
    transform:translate(100%, 0);
  }
  100%{
    right: 100%;
    transform:translate(0, 0);
  }
}
.danmu {
  position:absolute;/*叠放在视频窗口上*/
  top: 0;
  left: 0;
  z-index: 200;
  overflow: hidden;
}
.danmu div{
  position: absolute;
  right: 100%;
  font-size: 20px;
  color: #fefcc9;
  white-space: nowrap;
  text-shadow: 0 0 2px #999;
  animation: danmuAnimation ease-in 10s;
}
.speed {
  width: 80px;
  text-align: center;
  height: 100%;
  line-height: 25px;
  padding: 0 5px;
  cursor: pointer;
  position: relative;
}

.speed .speed_list {
  display: none; /*默认人隐藏*/
  background-color: hsla(0,0%,8%,.9);
  border-radius: 2px;
  line-height: 35px;
  position: absolute;
  bottom: 25px;
  width: 70px;
  left: 0;
  z-index: 50;
}

.speed .speed_list div:hover{
  cursor: pointer;
  background-color: hsla(0,0%,100%,.1)
}

.speed:hover .speed_list{
  display:block;
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
}

.group1, .group2 {
  display: flex;
  padding: 10px;
}

.maside {
  width: 300px;
  flex: none;
  background-color: #fefcc9;
}
</style>