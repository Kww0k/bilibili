<script setup lang="ts">
import {onMounted, ref} from 'vue';

const cardHeight = ref(0);
const imgHeight = ref(0)

onMounted( () => {
  let card = document.getElementById('card');
  let info = document.getElementById('info')
  // @ts-ignore
  cardHeight.value = card.offsetWidth / (312 / 251) || 0;
  // @ts-ignore
  imgHeight.value = card.offsetWidth / (312 / 251) - info.offsetHeight || 0;
});
</script>

<template>
  <div id="card" class="card" :style="{height: cardHeight + 'px'}">
    <div>
      <div class="card-img" :style="{height : imgHeight + 'px'}"
        style="background-image: url(http://192.168.233.150:9000/picture/v2-3497e1897e65fce08e61f4a0d0a8a4d6_1440w_1693449649542.webp?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20230905%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230905T012819Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=880e81f9f57f32089af2296bcf36639bc373edb233fd14dfa921f7647898e4da)"
      >
      </div>
      <div id="info" class="card-info">
        <div class="card-info-right">
          <h1 class="card-info-title">我发撒开就会发生的阿萨德附近哪开始的那份恐惧撒比空间里撒比撒贷款办法少康百废待举回来撒比的房间里挥洒</h1>
          <div class="card-info-bottom">
            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 24 24" width="24" height="24" fill="currentColor" class="bili-video-card__info--owner__up">
              <!--[--><path d="M6.15 8.24805C6.5642 8.24805 6.9 8.58383 6.9 8.99805L6.9 12.7741C6.9 13.5881 7.55988 14.248 8.3739 14.248C9.18791 14.248 9.8478 13.5881 9.8478 12.7741L9.8478 8.99805C9.8478 8.58383 10.1836 8.24805 10.5978 8.24805C11.012 8.24805 11.3478 8.58383 11.3478 8.99805L11.3478 12.7741C11.3478 14.41655 10.01635 15.748 8.3739 15.748C6.73146 15.748 5.4 14.41655 5.4 12.7741L5.4 8.99805C5.4 8.58383 5.73578 8.24805 6.15 8.24805z" fill="currentColor"></path><path d="M12.6522 8.99805C12.6522 8.58383 12.98795 8.24805 13.4022 8.24805L15.725 8.24805C17.31285 8.24805 18.6 9.53522 18.6 11.123C18.6 12.71085 17.31285 13.998 15.725 13.998L14.1522 13.998L14.1522 14.998C14.1522 15.4122 13.8164 15.748 13.4022 15.748C12.98795 15.748 12.6522 15.4122 12.6522 14.998L12.6522 8.99805zM14.1522 12.498L15.725 12.498C16.4844 12.498 17.1 11.8824 17.1 11.123C17.1 10.36365 16.4844 9.74804 15.725 9.74804L14.1522 9.74804L14.1522 12.498z" fill="currentColor"></path><path d="M12 4.99805C9.48178 4.99805 7.283 5.12616 5.73089 5.25202C4.65221 5.33949 3.81611 6.16352 3.72 7.23254C3.60607 8.4998 3.5 10.171 3.5 11.998C3.5 13.8251 3.60607 15.4963 3.72 16.76355C3.81611 17.83255 4.65221 18.6566 5.73089 18.7441C7.283 18.8699 9.48178 18.998 12 18.998C14.5185 18.998 16.7174 18.8699 18.2696 18.74405C19.3481 18.65655 20.184 17.8328 20.2801 16.76405C20.394 15.4973 20.5 13.82645 20.5 11.998C20.5 10.16965 20.394 8.49877 20.2801 7.23205C20.184 6.1633 19.3481 5.33952 18.2696 5.25205C16.7174 5.12618 14.5185 4.99805 12 4.99805zM5.60965 3.75693C7.19232 3.62859 9.43258 3.49805 12 3.49805C14.5677 3.49805 16.8081 3.62861 18.3908 3.75696C20.1881 3.90272 21.6118 5.29278 21.7741 7.09773C21.8909 8.3969 22 10.11405 22 11.998C22 13.88205 21.8909 15.5992 21.7741 16.8984C21.6118 18.7033 20.1881 20.09335 18.3908 20.23915C16.8081 20.3675 14.5677 20.498 12 20.498C9.43258 20.498 7.19232 20.3675 5.60965 20.2392C3.81206 20.0934 2.38831 18.70295 2.22603 16.8979C2.10918 15.5982 2 13.8808 2 11.998C2 10.1153 2.10918 8.39787 2.22603 7.09823C2.38831 5.29312 3.81206 3.90269 5.60965 3.75693z" fill="currentColor">
            </path><!--]-->
            </svg>
            <span class="card-info-bottom-author">青空光 · 8-23</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card-img {
  width: 100%;
  transition: color .2s linear;
  cursor: pointer;
  position: relative;
  z-index: 1;
  border-radius: 6px;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.card {
  width: 100%;
  --subtitle-font-size: 13px;
  --title-padding-right: 24px;
  position: relative;
  --title-font-size: 15px;
  --title-line-height: 22px;
  --subtitle-line-height: 17px;
  --avatar-size: 36px;
  --avatar-margin-right: 8px;
  --icon-size: 18px;
  --follow-icon-font-size: 12px;
  --follow-icon-line-height: 17px;
}
.card-info-bottom {
  margin-top: 4px;
  color: #9499A0;
  font-size: var(--subtitle-font-size);
  display: -webkit-flex;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  cursor: pointer;
}
.card-info-bottom:hover {
  color: #00AEEC;
}
.card-info-title{
  padding-right: var(--title-padding-right);
  color: #18191C;
  font-size: var(--title-font-size);
  line-height: var(--title-line-height);
  height: calc(2 * var(--title-line-height));
  display: -webkit-flex;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  line-break: anywhere;
  -webkit-line-clamp: 2;
  cursor: pointer;
}
.card-info-title:hover {
  color: #00AEEC;
}
.card-info-bottom-author {
  flex: 1;
  display: -webkit-box;
  overflow: hidden;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  line-break: anywhere;
  -webkit-line-clamp: 1;
  line-height: var(--subtitle-line-height);
}
.bili-video-card__info--owner__up {
  margin-right: 2px;
  width: var(--subtitle-line-height);
  height: var(--subtitle-line-height);
}
.card-info-right {
  min-width: 0;
  flex: 1;
}
.card-info {
  min-height: calc(var(--title-line-height) * 2 + var(--subtitle-line-height) + 4px);
  display: -webkit-flex;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}
</style>