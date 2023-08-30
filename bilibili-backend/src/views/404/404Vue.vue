<script setup lang="ts">
import router from "@/router";
</script>

<template>
<div style="height: 100vh; width: 100%;
background-image: url(https://ts1.cn.mm.bing.net/th/id/R-C.1e6d6f2f55a17d4fdfd9c7b5d4a358f9?rik=fw08DTBBw8%2bLwg&riu=http%3a%2f%2fpic.bizhi360.com%2fbbpic%2f98%2f9398.jpg&ehk=GjeIsiSMcUmTtf9nhW1Tx7q7ChY0Qe7%2fM%2bcgYvrRLZE%3d&risl=&pid=ImgRaw&r=0);
     background-size: cover;background-repeat: no-repeat;background-position: center;">
  <div style="text-align: center; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: rgba(255, 255, 255, 0.5)">
    <div style="font-size: 70px; margin-top: 20px; font-weight: bold">404</div>
    <div style="font-size: 50px; margin-top: 20px;">未找到页面</div>
    <div style="font-size: 30px; margin-top: 20px;">您正在寻找的页面不存在</div>
    <div style="margin-top: 20px; margin-bottom: 20px">
      <el-button style="width: 290px" type="primary" plain @click="router.push('/')">返回首页</el-button>
    </div>
  </div>
</div>
</template>

<style scoped>

</style>