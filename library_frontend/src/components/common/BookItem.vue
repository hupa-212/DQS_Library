<template>
  <el-card class="book-item" shadow="hover">
    <img :src="book.image" alt="Book cover" class="book-cover" />

    <div class="book-info">
      <h3 class="book-title">{{ book.title }}</h3>
      <p class="book-author">by {{ book.author }}</p>
      <p class="book-price">{{ book.price.toLocaleString() }} VND</p>

      <el-button type="primary" size="small" @click="viewDetails(book.id)">
        View Details
      </el-button>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()


const props = defineProps({
  book: {
    type: Object,
    required: true,
  },
})

// ✅ Giả lập điều hướng (chưa có trang chi tiết)
const viewDetails = (id: number) => {
  alert(`Book ID: ${id}`)
}
</script>

<style scoped>
.book-item {
  width: 220px;
  border-radius: 10px;
  overflow: hidden;
  text-align: center;
  transition: transform 0.2s;
}

.book-item:hover {
  transform: scale(1.03);
}

.book-cover {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-bottom: 1px solid #eee;
}

.book-info {
  padding: 10px;
}

.book-title {
  font-size: 16px;
  font-weight: 600;
  margin: 6px 0;
}

.book-author {
  font-size: 14px;
  color: #666;
}

.book-price {
  color: #409eff;
  font-weight: 500;
  margin: 8px 0;
}
</style>
