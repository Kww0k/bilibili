<script setup lang="ts">
import {defineProps} from "vue";

const props = defineProps({
  name: {
    type: String,
    required: true
  },
});
</script>

<template>
<a v-if="name !== '更多'" class="channel-link">{{ name }}</a>
<div v-if="name === '更多'">
  <a class="channel-link">更多</a>
</div>
</template>

<style scoped>
.channel-link {
  letter-spacing: 2px;
  font-size: 14px;
  height: 26px;
  line-height: 26px;
  display: inline-block;
  box-sizing: content-box;
  width: 100%;
  border: 1px solid #F1F2F3;
  border-radius: 6px;
  background-color: #F6F7F8;
  color: #61666D;
  text-align: center;
  font-weight: 400;
  transition: background-color .3s,color .3s;
  cursor: pointer;
  text-decoration: none;
}
.channel-link:hover {
  background-color: #E3E5E7;
  color: #18191C;
}
a {
  margin: 0;
  padding: 0;
}
a:hover {
  outline: none 0;
}
</style>