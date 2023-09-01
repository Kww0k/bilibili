<script setup lang="ts">
import {onMounted, ref} from 'vue';
import Navigate from "@/components/Navigate.vue";
import request from "@/net";

const message = ref('Hello, world!');
const height = ref('simple')
const url1 = ref('')
const url2 = ref('')

window.addEventListener('scroll', function () {
  let scrollDistance = window.scrollY;
  if (scrollDistance >= 35 && height.value !== 'fly')
    height.value = 'fly'
  else if (scrollDistance < 35 && height.value !== 'simple')
    height.value = 'simple'
});

onMounted(() => {
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
      <div class="header_banner">
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
  </div>
</template>

<style scoped>
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
  align-items: flex-end;padding: 0 96px;
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