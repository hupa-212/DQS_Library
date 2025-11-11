<template>
  <div class="book-detail-container">
    <el-card class="book-detail-card">
      <div class="book-top">

        <div class="book-images">
          <el-carousel height="300px" trigger="click">
            <el-carousel-item v-for="(img, index) in book.images" :key="index">
              <img :src="img" alt="Book Image" class="main-image"/>
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

 
        <div class="book-info">
          <h2>{{ book.title }}</h2>
          <p><strong>Author:</strong> {{ book.author }}</p>
          <p><strong>Category:</strong> {{ book.category }}</p>
          <p><strong>Description:</strong></p>
          <p>{{ book.description }}</p>

          <el-button type="primary" @click="goBack">Back</el-button>
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
  images: [],
})

const activeImage = ref(0)

const goBack = () => {
  router.back()
}

const fetchBook = async (id: string) => {
  try {
    const res = await axios.get(`http://localhost:8080/api/books/${id}`)
    const data = res.data.data || res.data  
    book.value = {
      id: data.id,
      title: data.title,
      author: data.author,
      category: data.category?.name || 'None',
      description: data.description || 'No description',
      images: data.coverImageUrl ? [data.coverImageUrl] : ['https://via.placeholder.com/400x300?text=No+Image'],
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
}

.book-detail-card {
  width: 100%;
  max-width: 1000px;
  padding: 20px;
}

.book-top {
  display: flex;
  gap: 30px;
}

.book-images {
  flex: 1;
}

.main-image {
  width: 100%;
  height: 300px;
  object-fit: contain;
}


.thumbnails {
  display: flex;
  margin-top: 10px;
  gap: 10px;
}

.thumb {
  width: 60px;
  height: 60px;
  object-fit: contain; 
  background-color: #f5f5f5;
  cursor: pointer;
  border: 2px solid transparent;
}


.thumb.active {
  border-color: #409eff; 
}

.book-info {
  flex: 1;
}
</style>
