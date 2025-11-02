<template>
  <div class="slider-wrapper">
    <el-carousel
      height="700px"
      :interval="3000"
      arrow="always"
      indicator-position="outside"
    >
      <el-carousel-item
        v-for="(image, index) in images"
        :key="index"
      >
        <img :src="image" alt="slider image" class="slider-image" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import img from '@/assets/Brown and White Real Estate (Poster (Landscape)).png'
const images = ref([
    img,
  'https://images.unsplash.com/photo-1521587760476-6c12a4b040da?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1470',
  'https://images.unsplash.com/photo-1601104809254-c399171b9fa3?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1469'
])
</script>

<style scoped>
.slider-wrapper {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 16px;
  overflow: hidden;
}

.slider-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
