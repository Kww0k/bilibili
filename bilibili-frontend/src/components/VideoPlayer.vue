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
const videoPlayed = ref(false)

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
    videoPlayed.value = true
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
  if (video.paused) {
    video.play()
    videoPlayed.value = true
  }
  else {
    video.pause()
    videoPlayed.value = false
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
              <div :style="{display: stopDisplay}" @click="play" id="stop" style="width: 100%; text-align: right; height: 50px;">
                <svg v-if="!videoPlayed" style="margin-right: 50px; margin-top: -60px" viewBox="0 0 80 80" width="80" height="80" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><defs><path id="pid-53-svgo-a" d="M0 0h80v80H0z"></path><path d="M52.546 8.014a3.998 3.998 0 014.222 3.077c.104.446.093.808.039 1.138a2.74 2.74 0 01-.312.881c-.073.132-.16.254-.246.376l-.257.366-.521.73c-.7.969-1.415 1.926-2.154 2.866l-.015.02a240.945 240.945 0 015.986.341l1.643.123.822.066.41.034.206.018.103.008.115.012c1.266.116 2.516.45 3.677.975a11.663 11.663 0 013.166 2.114c.931.87 1.719 1.895 2.321 3.022a11.595 11.595 0 011.224 3.613c.03.157.046.316.068.474l.015.119.013.112.022.206.085.822.159 1.646c.1 1.098.19 2.198.27 3.298.315 4.4.463 8.829.36 13.255a166.489 166.489 0 01-.843 13.213c-.012.127-.034.297-.053.454a7.589 7.589 0 01-.072.475l-.04.237-.05.236a11.762 11.762 0 01-.74 2.287 11.755 11.755 0 01-5.118 5.57 11.705 11.705 0 01-3.623 1.263c-.158.024-.316.052-.475.072l-.477.053-.821.071-1.644.134c-1.096.086-2.192.16-3.288.23a260.08 260.08 0 01-6.578.325c-8.772.324-17.546.22-26.313-.302a242.458 242.458 0 01-3.287-.22l-1.643-.129-.822-.069-.41-.035-.206-.018c-.068-.006-.133-.01-.218-.02a11.566 11.566 0 01-3.7-.992 11.732 11.732 0 01-5.497-5.178 11.73 11.73 0 01-1.215-3.627c-.024-.158-.051-.316-.067-.475l-.026-.238-.013-.119-.01-.103-.07-.823-.132-1.648a190.637 190.637 0 01-.22-3.298c-.256-4.399-.358-8.817-.258-13.233.099-4.412.372-8.811.788-13.197a11.65 11.65 0 013.039-6.835 11.585 11.585 0 016.572-3.563c.157-.023.312-.051.47-.07l.47-.05.82-.07 1.643-.13a228.493 228.493 0 016.647-.405l-.041-.05a88.145 88.145 0 01-2.154-2.867l-.52-.73-.258-.366c-.086-.122-.173-.244-.246-.376a2.74 2.74 0 01-.312-.881 2.808 2.808 0 01.04-1.138 3.998 3.998 0 014.22-3.077 2.8 2.8 0 011.093.313c.294.155.538.347.742.568.102.11.19.23.28.35l.27.359.532.72a88.059 88.059 0 012.06 2.936 73.036 73.036 0 011.929 3.03c.187.313.373.628.556.945 2.724-.047 5.447-.056 8.17-.038.748.006 1.496.015 2.244.026.18-.313.364-.624.549-.934a73.281 73.281 0 011.93-3.03 88.737 88.737 0 012.059-2.935l.533-.72.268-.359c.09-.12.179-.24.281-.35a2.8 2.8 0 011.834-.881zM30.13 34.631a4 4 0 00-.418 1.42 91.157 91.157 0 00-.446 9.128c0 2.828.121 5.656.364 8.483l.11 1.212a4 4 0 005.858 3.143c2.82-1.498 5.55-3.033 8.193-4.606a177.41 177.41 0 005.896-3.666l1.434-.942a4 4 0 00.047-6.632 137.703 137.703 0 00-7.377-4.708 146.88 146.88 0 00-6.879-3.849l-1.4-.725a4 4 0 00-5.382 1.742z" id="pid-53-svgo-d"></path><filter x="-15.4%" y="-16.3%" width="130.9%" height="132.5%" filterUnits="objectBoundingBox" id="pid-53-svgo-c"><feOffset dy="2" in="SourceAlpha" result="shadowOffsetOuter1"></feOffset><feGaussianBlur stdDeviation="1" in="shadowOffsetOuter1" result="shadowBlurOuter1"></feGaussianBlur><feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.3 0" in="shadowBlurOuter1" result="shadowMatrixOuter1"></feColorMatrix><feOffset in="SourceAlpha" result="shadowOffsetOuter2"></feOffset><feGaussianBlur stdDeviation="3.5" in="shadowOffsetOuter2" result="shadowBlurOuter2"></feGaussianBlur><feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.2 0" in="shadowBlurOuter2" result="shadowMatrixOuter2"></feColorMatrix><feMerge><feMergeNode in="shadowMatrixOuter1"></feMergeNode><feMergeNode in="shadowMatrixOuter2"></feMergeNode></feMerge></filter></defs><g fill="none" fill-rule="evenodd" opacity=".8"><mask id="pid-53-svgo-b" fill="#fff"><use xlink:href="#pid-53-svgo-a"></use></mask><g mask="url(#pid-53-svgo-b)"><use fill="#000" filter="url(#pid-53-svgo-c)" xlink:href="#pid-53-svgo-d"></use><use fill="#FFF" xlink:href="#pid-53-svgo-d"></use></g></g></svg>
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