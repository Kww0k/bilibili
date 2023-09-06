<script setup lang="js">
import {onMounted, ref} from "vue";
import request from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const timer = ref(0)
const activeIndex = ref(0)
const isPrev = ref(false)
const bannerList = ref([])

const stopAutoPlay = () => {
  for (let i = 0; i <= timer.value; i++)
    clearInterval(i)
}

const startAutoPlay = () => {
  stopAutoPlay()
  timer.value = setInterval(function () {
    activeIndex.value = activeIndex.value + 1
    if (activeIndex.value > bannerList.value.length - 1)
      activeIndex.value = 0
    isPrev.value = false
    changeBanner(activeIndex.value)
  }, 3000)
}

const changeBanner = (index, prev) => {
  stopAutoPlay()
  if (index >= 0) {
    isPrev.value = index < activeIndex.value;
    activeIndex.value = index
  } else {
    if (prev) {
      activeIndex.value = activeIndex.value - 1;
      if (activeIndex.value < 0)
        activeIndex.value = bannerList.value.length - 1
    } else {
      activeIndex.value = activeIndex.value + 1;
      if (activeIndex.value > bannerList.value.length - 1)
        activeIndex.value = 0
    }
  }
  isPrev.value = prev
  document.querySelector('.img-box').style
      .setProperty('--m-left', activeIndex.value.toString())
  document.querySelector('.bottom-box').style
      .setProperty('--b-color', bannerList.value[activeIndex.value].color)
  startAutoPlay()
}

const getBannerList = () => {
  request.get('/video/web/video/listBanner').then((res) => {
    if (res.code === 200) {
      bannerList.value = res.data
      document.querySelector('.bottom-box').style
          .setProperty('--b-color', bannerList.value[activeIndex.value].color)
      startAutoPlay()
    }
    else
      ElMessage.error(res.message)
  })
}

onMounted(() => {
  getBannerList()
  let container = document.getElementById('container');
  let imgBox =  document.getElementById('img-box')
  imgBox.style.height = container.offsetWidth / (488 / 275) + 'px'
  container.style.height = container.offsetWidth / (488 / 275) + 85 + 'px'
})
</script>

<template>
  <div id="container" class="container"  @mouseout="startAutoPlay" @mouseover="stopAutoPlay">
    <div id="img-box" class="img-box">
      <img v-for="item in bannerList" @click="router.push(`/${item.id}`)"
          :src="item.previewUrl"/>
    </div>
    <div class="bottom-box">
      <div class="l-box">
        <div class="title" @click="router.push(`/${bannerList && bannerList.length > 0 ? bannerList[activeIndex].id : ''}`)">
          {{ bannerList && bannerList.length > 0 ? bannerList[activeIndex].title : '' }}
        </div>
        <ul class="dots">
          <li v-for="(item, index) in bannerList"
              :class="[index === activeIndex ? 'pacman' : 'dot', isPrev ? 'l' : null]"
              @click="changeBanner(index, index<=activeIndex)">
            <div v-if="index===activeIndex"></div>
            <div v-if="index===activeIndex"></div>
          </li>
        </ul>
      </div>
      <div class="r-box">
        <i class="fa fa-angle-left" @click="changeBanner(-1, true)"></i>
        <i class="fa fa-angle-right" @click="changeBanner(-1, false)"></i>
      </div>
    </div>
  </div>
</template>

<style scoped>
ul {
  margin: 0;
  padding: 0;
}
li {
  list-style: none;
}
.container {
  border-radius: 6px;
  overflow: hidden;
  width: 100%;
}

.img-box {
  width: 100%;
  display: flex;
  margin-left: calc( -100% * var(--m-left));
  transition: 0.35s;
}

.img-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  cursor: pointer;
}

.bottom-box {
  height: 85px;
  display: flex;
  color: white;
  --b-color: #000;
  background-color: var(--b-color);
  position: relative;
}

.bottom-box::before {
  content: "";
  width: 100%;
  height: 50px;
  background: linear-gradient(to bottom, transparent, var(--b-color));
  position: absolute;
  top: -50px;
}

.bottom-box .l-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-left: 12px;
  width: calc(100% - 104px);
}

.bottom-box .title {
  width: 100%;
  font-size: 18px;
  line-height: 25px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: pointer;
}

.bottom-box .dots {
  display: flex;
  margin-top: 10px;
}

.bottom-box li {
  width: 8px;
  height: 8px;
  background-color: rgba(255, 255, 255, 0.4);
  margin: 4px;
  border-radius: 50%;
  cursor: pointer;
}

/* 吃豆人朝向左 */
.bottom-box li.pacman.l {
  transform: rotate(180deg);
}
/* 接下来分别为吃豆人的两个部分设置动画 */
.bottom-box li.pacman div:nth-child(1) {
  /* 执行动画：动画名 时长 线性的 停留在最后一帧 */
  animation: pacman1 0.75s linear forwards;
}
.bottom-box li.pacman div:nth-child(2) {
  animation: pacman2 0.75s linear forwards;
}

/* 指示器选中态（吃豆人） */
.bottom-box li.pacman {
  position: relative;
  width: 14px;
  height: 14px;
  background-color: transparent;
  margin-top: 1px;
}
/* 吃豆人由两个div组成 */
.bottom-box li.pacman div {
  width: 0;
  height: 0;
  border: 7px solid #fff;
  border-radius: 50%;
  border-right-width: 7px;
  border-right-color: transparent;
  /* 绝对定位 两个重合 */
  position: absolute;
}

/* 指示器选中态（吃豆人） */
.bottom-box li.pacman {
  position: relative;
  width: 14px;
  height: 14px;
  background-color: transparent;
  margin-top: 1px;
}

.bottom-box .r-box {
  display: flex;
  padding-left: 12px;
  padding-top: 12px;
}

.bottom-box .r-box i:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}

.bottom-box .r-box i {
  background-color: rgba(255, 255, 255, 0.1);
  width: 28px;
  height: 28px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  font-size: 16px;
  margin-right: 12px;
}

@keyframes pacman1 {
  0% {
    transform: rotate(360deg);
  }
  40% {
    transform: rotate(270deg);
  }
  60% {
    transform: rotate(360deg);
  }
  100% {
    transform: rotate(270deg);
  }
}

@keyframes pacman2 {
  0% {
    transform: rotate(0deg);
  }
  40% {
    transform: rotate(90deg);
  }
  60% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(90deg);
  }
}
</style>