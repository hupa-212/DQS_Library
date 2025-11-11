<template>
  <div class="slider-container">
    <Slider />
  </div>

  <div class="book-section">
    <h2>Popular Books</h2>
    <div v-if="loading">Loading...</div>
    <div v-else class="book-grid">
      <BookItem v-for="book in books" :key="book.id" :book="book" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import Slider from '@/components/common/Slider.vue'
import BookItem from '@/components/common/BookItem.vue'

interface Category {
  name: string
}

interface Book {
  id: number
  title: string
  author: string
  price: number
  coverImageUrl?: string
  category?: Category
}

const books = ref<Book[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/books')

    // ✅ Lấy tối đa 10 sách
    books.value = res.data.data
      .map((b: any) => ({
        ...b,
        category: b.category || { name: 'None' },
        coverImageUrl: b.coverImageUrl || '',
      }))
      .slice(0, 10)
  } catch (error) {
    console.error('Error fetching books:', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.slider-container,
.book-section {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.book-section {
  padding: 40px 0;
  background-color: #fff;
  text-align: center;
}

.book-section h2 {
  font-size: 22px;
  font-weight: 700;
  margin-bottom: 25px;
}

.book-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 15px;
  justify-items: center;
}
</style>
