<script setup lang="ts">
import {Apple} from '@element-plus/icons-vue'
import {computed, onMounted, ref} from "vue";
import {More} from '@element-plus/icons-vue'

const userId = ref(0)
const test = ref([
  {
    id : 1,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 2,
    nickname : 'cxc6666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 3,
    nickname : 'cxc66666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 4,
    nickname : 'cxc666666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 5,
    nickname : 'cxc6666666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 6,
    nickname : 'cxc66',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 7,
    nickname : 'cxc6',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 8,
    nickname : 'cxc',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 9,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 10,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 11,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 12,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 13,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
  {
    id : 4,
    nickname : 'cxc666',
    desc : 'asdfasdfasfdasfasdfasdfasfasfasdasfasdfasdf',
    url : 'src/assets/img/defaultAvatar.jpg'
  },
])
const maxChars = 500; // 最大字符数
const desc = ref('')
const remainingChars = computed(() => desc.value.length);

onMounted(() => {
  userId.value = test.value[0].id
})

</script>

<template>
  <div style="width: calc(100% - 20px); margin-left: 10px; height: 52px; ">
    <div style="width: 100%; height: 10px">
    </div>
    <div class="title">
      <span>我的消息</span>
      <div class="title-right">
        <div style="margin-right: 3px; margin-top: 2px">
          <el-icon>
            <Apple/>
          </el-icon>
        </div>
        <div>
          应援团助手
        </div>
      </div>
    </div>
  </div>
  <div class="main-info">
    <div class="left">
      <div class="left-title">
        <span style="line-height: 35px;-webkit-user-select: none;-webkit-box-direction: normal;">近期消息</span>
      </div>
      <div class="left-body">
        <el-scrollbar style="height: 100%;width: 100%;">
          <div class="left-card" :style="{backgroundColor : userId === user.id ? '#e4e5e6' : ''}" v-for="user in test" @click="userId = user.id">
            <div class="close">
              <svg data-v-18a3b98b="" viewBox="0 0 40 40" class="css-1dtzbno"><path d="M22.83,20,38.42,4.41a2,2,0,1,0-2.83-2.83h0L20,17.17,4.41,1.58A2,2,0,0,0,1.58,4.41L17.17,20,1.58,35.59a2,2,0,0,0,2.83,2.83L20,22.83,35.59,38.42a2,2,0,1,0,2.83-2.83Z"></path></svg>
            </div>
            <div style="margin-top: 19px; display: flex">
              <el-avatar :src="user.url" style="width: 40px; height: 40px; margin-right: 8px"/>
              <div style="width: 150px; height: 40px">
                <div style="color: #333;font-size: 14px;min-height: 16px;line-height: 1;">
                  {{ user.nickname }}
                </div>
                <div style="color: #999;padding: 5px 0;overflow: hidden;width: 155px;white-space: nowrap;text-overflow: ellipsis;">
                  {{ user.desc }}
                </div>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
    <div class="right">
      <div class="right-title">
        <span>{{ test.find(t => t.id === userId) ? test.find(t => t.id === userId).nickname : '' }}</span>
        <div style="position: absolute; right: 10px; top: 6px;cursor:pointer;">
          <el-icon><More /></el-icon>
        </div>
      </div>
      <div class="right-body">
        <div class="message-body">
          <el-scrollbar style="width: 100%; height: 100%">

          </el-scrollbar>
        </div>
        <div class="send-box">
          <div style="height: 48px; width: calc(100% - 16px);margin-left: 16px; display: flex; align-items: center">
            todo 表情
          </div>
          <div class="desc-input" style="height: 60px; width: calc(100% - 16px);margin-left: 16px">
            <el-input v-model="desc" :rows="2" :maxlength="maxChars" type="textarea" :resize="'none'"/>
          </div>
          <div style="height: 46px; width: calc(100% - 16px);margin-left: 16px;">
              <div style="position: absolute; right: 88px;font-size: 12px;color: #c0c0c0; bottom: 25px">
                {{ remainingChars }}/ {{ maxChars }}
              </div>
              <el-button style="position: absolute; right: 16px" type="primary" plain>发送</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.desc-input  >>> .el-textarea__inner {
  background-color: transparent;
  color: #222;
  letter-spacing: 1px;
  box-shadow: 0 0 0 0;
}
.right-body {
  height: calc(100% - 36px);
  width: 100%;
}
.message-body {
  width: 100%;
  height: calc(100% - 162px);
}
.send-box {
  height: 161px;
  border-top: 1px solid #d8d8d8;
  border-bottom-right-radius: 4px;
  background-color: #f4f5f7;
  flex-shrink: 0;
  padding: 0 16px;
  position: relative;
  z-index: 2;
}
.right-title {
  font-weight: 350;
  font-size: 14px;
  height: 35px;
  flex-shrink: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  border-bottom: 1px solid #e9eaec;
  position: relative;
  text-align: center;
  color: #333;
}
.css-1dtzbno {
  fill: currentcolor;
  width: 1em;
  height: 1em;
}
.left-card:hover .close {
  transform: translateX(0);
  opacity: 1;
}

.close {
  display: flex;
  color: #999;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  z-index: 100;
  height: 100%;
  width: 24px;
  transform: translateX(-100%);
  opacity: 0;
  -webkit-transition: 300ms;
  transition: 300ms;
}

.left-card:hover {
  background-color: #e4e5e6;
}

.left-card {
  font-size: 12px;
  color: #666;
  height: 78px;
  width: 100%;
  display: flex;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  flex-direction: row;
  position: relative;
  overflow: hidden;
  cursor: pointer;
}
.left-body {
  height: calc(100% - 36px);
  width: 100%;
}

.left-title {
  font-size: 12px;
  color: #666;
  font-weight: 300;
  padding-left: 24px;
  line-height: 35px;
  height: 35px;
  border-bottom: 1px solid #e9eaec;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  overflow: hidden;
}

.right {
  width: calc(100% - 240px);
  height: 100%;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
}

.left {
  width: 240px;
  height: 100%;
  border-right: 1px solid #e9eaec;
  position: relative;
}

.main-info {
  display: flex;
  width: calc(100% - 20px);
  margin-left: 10px;
  margin-top: 10px;
  height: calc(100% - 76px);
  background-color: #fff;
  -webkit-box-shadow: 0 2px 4px 0 rgba(121, 146, 185, 0.54);
  box-shadow: 0 2px 4px 0 rgba(121, 146, 185, 0.54);
  border-radius: 4px;
}

.title-right {
  position: relative;
  display: flex;
  cursor: pointer;
}

.title {
  height: 42px;
  background-color: #fff;
  -webkit-box-shadow: 0 2px 4px 0 rgba(121, 146, 185, 0.54);
  box-shadow: 0 2px 4px 0 rgba(121, 146, 185, 0.54);
  flex-shrink: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  justify-content: space-between;
  padding: 0 16px;
  font-size: 15px;
  color: #666;
  font-weight: 300;
  border-radius: 4px;
}
</style>