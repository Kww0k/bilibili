<script setup lang="js">
import { onMounted, ref} from "vue";
let asideWidth = document.body.clientWidth / 5;
let minWidth = 500;
let maxWidth = 1400;
let Wah;
const isSmall = ref(false)
const isWide = ref(false)
const isTheme = ref(false)
const isHour = ref(false)
const ctime = ref(null)
const dtime = ref(null)
let ctid;


const makeTime = (time) => {
  time = Math.floor(time);
  let h = Math.floor (time/3600);
  let m =Math.floor((time-h * 3600)/60);
  let s = time - h * 3600 -m * 60;
  m = m < 10 ? "0" + m : m;
  s = s < 10 ? "0" + s : s;
  if (isHour.value){
    h = h < 10 ? "0" + h : h;
    return h + ":" + m + ":" + s;
  }
  return m + ":" + s;
}

const setSize = (video) => {
  let aWidth;
  if (isWide.value)
    aWidth = 0
  else
    aWidth = asideWidth
  Wah = video.clientWidth / video.clientHeight
  let dw = 440
  let width = document.body.clientWidth - aWidth - dw
  if (width < minWidth + dw)
    width = minWidth + dw
  else if (width > maxWidth + dw)
    width = maxWidth + dw

  let height = width/Wah
  video.width = width
  video.height = height
  document.getElementById("vplay").style.width = width + 'px'
  document.getElementById("vplay").style.height = height + 'px'
  document.getElementById("playWindow").style.width = width + 20 + aWidth + 'px'
  document.getElementById("playBox").style.width = width + 'px'
  document.getElementById("playBox").style.height = height + 48 + 'px'
  document.getElementById("videoBox").style.width = width + 'px'
  document.getElementById("videoBox").style.height = height + 'px'
  document.getElementById("progress").style.width = width + 'px'
  document.getElementById("maside").style.width = aWidth + 'px'
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
    fullback.style = "";
    fullback.className = "fullback"
  } else {
    fullback.style.width = document.body.clientWidth + 'px'
    fullback.style.height = document.body.clientHeight + 'px'
    fullback.className = 'fullback-theme'
  }
  console.log(isTheme)
  isTheme.value = !isTheme.value
}

const wideScreen = () => {
  isWide.value = !isWide.value
  if (isWide) {
    document.getElementById("maside").style.display = "none"
  } else {
    document.getElementById("maside").style.display = "block"
  }
  let video = document.getElementById("vd")
  setSize(video)

}

const play = () => {
  let video = document.getElementById("vd")
  if (video.paused)
    video.play()
  else {
    video.pause()
    clearInterval(ctid)
  }
  ctid = setInterval(() => {
    let video = document.getElementById("vd")
    ctime.value = makeTime(video.currentTime)
  }, 1000)
}

const setSmall = () => {
  if (isSmall.value) return
  let smallHeight = 300 / Wah
  document.getElementById("vd").height = smallHeight
  document.getElementById("vd").width = 300
  document.getElementById('videoBox').className = 'videoBoxSmall'
  document.getElementById('videoBox').style.width = '300px'
  document.getElementById('videoBox').style.height = smallHeight + 'px'
  isSmall.value = true
}

const exitSmall = () => {
  if (!isSmall.value) return
  document.getElementById('videoBox').className = 'videoBox'
  let video = document.getElementById("vd")
  setSize(video)
  isSmall.value = false
}

window.addEventListener("scroll", function () {
  let playBox = document.getElementById("playBox")
  let height = parseInt(playBox.style.height) + playBox.offsetTop
  let top = document.documentElement.scrollTop
  if (top > height - 50)
    setSmall()
  else
    exitSmall()
})

onMounted(() => {
  let video = document.getElementById("vd")
  video.oncanplay = function () {
    setSize(video)
    let time = video.duration;//时长
    isHour.value = time >= 3600;
    dtime.value = makeTime(time)
    ctime.value = makeTime(video.currentTime)
  }
})
</script>

<template>
  <!-- 整体 -->
  <div id="playWindow" class="playWindow">
    <!-- 左边 -->
    <div id="fullback" class="fullback">
      <div id="playBox" class="playBox">
        <!-- 视频 -->
        <div id="videoBox" class="videoBox">
          <video id="vd" src="../assets/img/屏幕录制2023-08-29%2012.58.04.mov" width="600"/>
          <div id="vplay" class="vplay flex items-center justify-center">
            <el-button style="width: 50px" @click="play">暂停</el-button>
          </div>
        </div>
        <div id="progress" class="progress">
          <div id="now" class="now">
          </div>
        </div>
        <!-- 其它组件 -->
        <div id="consoles" class="consoles">
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
            <div id="theme-screen" class="theme-screen" style="margin-right: 5px" title="剧场">
              <el-button @click="themeScreen">剧场</el-button>
            </div>
            <div id="wide-screen" class="wide-screen" style="margin-right: 5px" title="宽屏">
              <el-button @click="wideScreen">宽屏</el-button>
            </div>
            <div id="full-screen" class="full-screen" title="全屏" >
              <el-button @click="fullScreen">全屏</el-button>
            </div>
          </div>
        </div>
        <!-- 弹幕 -->
        <div id="danmu" class="danmu">

        </div>
      </div>
    </div>
    <!-- 右边 -->
    <aside id="maside" class="maside">
    </aside>
  </div>
</template>

<style scoped>
.time {
  line-height: 25px;
  margin: 0 10px;
}
.vplay {
  position: absolute;
  left: 0;
  top: 0;
  z-index: 50;
}
.fullback-theme {
  background-color:#000000;
   display: flex;
   justify-content: center;
   position: fixed;
   top: 0;
   left: 0;
   z-index: 500;
   transition: all 2s;
}
.videoBoxSmall {
  border: solid 10px rgba(200, 200, 200, 0.5);
  border-radius: 5px;
  position: fixed;
  right: 110px;
  bottom: 40px;
  z-index: 100;
}
.videoBox{
  position: relative;
}
.playWindow {
  display: flex;
  width: 920px;
  margin: 0 auto;
}

/*
播放器容器
 */
.playBox {
  background-color: #000000;
  width: 600px;
}

/*
进度条
 */
.progress {
  width: 580px;
  height: 3px;
  background-color: #999999;
  border-radius: 3px;
  position: relative;
}
.progress .now {
  width: 0;
  height: 3px;
  background-color: crimson;
  border-radius: 3px;
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
  color: white; /*控件颜爸*/
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