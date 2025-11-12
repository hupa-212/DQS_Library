<template>
  <div class="book-detail-container">
    <el-card class="book-detail-card">
      <div class="book-top">

        <!-- Bên trái: Hình ảnh -->
        <div class="book-images">
          <el-carousel height="350px" trigger="click" indicator-position="outside">
            <el-carousel-item v-for="(img, index) in book.images" :key="index">
              <img :src="img" alt="Book Image" class="main-image" />
            </el-carousel-item>
          </el-carousel>

          <div class="thumbnails">
            <img
              v-for="(img, index) in book.images"
              :key="'thumb-' + index"
              :src="img"
              class="thumb"
              :class="{ active: activeImage === index }"
              @click="activeImage = index"
            />
          </div>
        </div>

        <!-- Bên phải: Thông tin chi tiết -->
        <div class="book-info">
          <h2 class="book-title">{{ book.title }}</h2>
          <p class="author">✍️ {{ book.author }}</p>
          <p class="category-tag">
            <el-tag type="success">{{ book.category }}</el-tag>
          </p>

          <div class="price-section">
            <span class="label">Price:</span>
            <span class="price">{{ formatPrice(book.price) }}</span>
          </div>

          <div class="isbn-section">
            <span class="label">🔢 ISBN:</span>
            <span class="isbn">{{ book.isbn }}</span>
          </div>

          <el-divider></el-divider>

          <div class="description-section">
            <h3>📖 Description</h3>
            <p>{{ book.description }}</p>
          </div>

          <el-button type="primary" class="back-btn" @click="goBack">
            ← Back
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

interface Book {
  id: number
  title: string
  author: string
  category: string
  description: string
  price: number
  isbn: string
  images: string[]
}

const route = useRoute()
const router = useRouter()

const book = ref<Book>({
  id: 0,
  title: '',
  author: '',
  category: '',
  description: '',
  price: 0,
  isbn: '',
  images: [],
})

const activeImage = ref(0)

const goBack = () => {
  router.back()
}

const formatPrice = (price: number) => {
  return price
    ? price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })
    : 'Contact'
}

const fetchBook = async (id: string) => {
  try {
    const res = await axios.get(`http://localhost:8080/api/books/${id}`)
    const data = res.data.data || res.data
    book.value = {
      id: data.id,
      title: data.title,
      author: data.author,
      category: data.category?.name || 'Undefined',
      description: data.description || 'No Description',
      price: data.price || 0,
      isbn: data.isbn || 'No ISBN',
      images: data.coverImageUrl
        ? [data.coverImageUrl]
        : ['https://via.placeholder.com/400x300?text=No+Image'],
    }
  } catch (error) {
    console.error('Error fetching book detail:', error)
  }
}

onMounted(() => {
  const id = route.params.id as string
  fetchBook(id)
})
</script>

<style scoped>
.book-detail-container {
  display: flex;
  justify-content: center;
  padding: 40px 20px;
  /* background-color: #f9fafc; */
}

.book-detail-card {
  width: 100%;
  max-width: 1100px;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.book-top {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
}


.book-images {
  flex: 1;
}

.main-image {
  width: 100%;
  height: 350px;
  object-fit: contain;
  border-radius: 12px;
  background-color: #fafafa;
}

.thumbnails {
  display: flex;
  margin-top: 12px;
  gap: 10px;
  justify-content: center;
}

.thumb {
  width: 65px;
  height: 65px;
  object-fit: contain;
  background-color: #f5f5f5;
  border-radius: 8px;
  cursor: pointer;
  border: 2px solid transparent;
  transition: 0.3s;
}

.thumb:hover {
  transform: scale(1.05);
}

.thumb.active {
  border-color: #409eff;
}

/* --- Thông tin --- */
.book-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.book-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 8px;
}

.author {
  font-size: 16px;
  color: #666;
  margin-bottom: 4px;
}

.category-tag {
  margin-bottom: 12px;
}

.price-section,
.isbn-section {
  font-size: 18px;
  margin: 8px 0;
}

.label {
  font-weight: 600;
  color: #333;
}

.price {
  color: #e53935;
  font-weight: bold;
  margin-left: 5px;
}

.isbn {
  font-weight: 500;
  margin-left: 5px;
  color: #555;
}

.description-section {
  margin-top: 16px;
  line-height: 1.6;
}

.back-btn {
  margin-top: 20px;
  align-self: flex-start;
}
</style>
