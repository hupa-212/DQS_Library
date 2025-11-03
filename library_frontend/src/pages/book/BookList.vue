<template>
  <div class="book-list-page">
    <!-- Sidebar -->
    <BookFilterMenu />

    <main class="book-list-container">
      <div class="header">
        <!-- 🔍 Search bar -->
        <div class="search-bar">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="🔍 Search books by title or author..."
            class="search-input"
          />
        </div>

        <!-- Sort menu -->
        <div class="sort-menu">
          <label for="sort" class="sort-label">Sort by:</label>
          <select id="sort" v-model="sortOption" class="sort-select">
            <option value="default">Default</option>
            <option value="price-asc">Price: Low → High</option>
            <option value="price-desc">Price: High → Low</option>
            <option value="title-asc">Title: A → Z</option>
            <option value="title-desc">Title: Z → A</option>
          </select>
        </div>
      </div>

      <!-- Book grid -->
      <div class="book-grid">
        <BookItem
          v-for="book in paginatedBooks"
          :key="book.id"
          :book="book"
        />
      </div>

      <!-- Pagination -->
      <div class="pagination" v-if="filteredBooks.length > itemsPerPage">
        <button
          class="page-btn"
          :disabled="currentPage === 1"
          @click="prevPage"
        >
          ⬅ Prev
        </button>

        <span class="page-info">Page {{ currentPage }} / {{ totalPages }}</span>

        <button
          class="page-btn"
          :disabled="currentPage === totalPages"
          @click="nextPage"
        >
          Next ➡
        </button>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import BookFilterMenu from '@/components/book/BookFilter.vue'
import BookItem from '@/components/common/BookItem.vue'

const sortOption = ref('default')
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 9

const books = [
  { id: 1, title: 'Clean Code', author: 'Robert C. Martin', price: 150000, image: 'https://images-na.ssl-images-amazon.com/images/I/41xShlnTZTL._SX374_BO1,204,203,200_.jpg' },
  { id: 2, title: 'The Pragmatic Programmer', author: 'Andrew Hunt', price: 180000, image: 'https://images-na.ssl-images-amazon.com/images/I/518FqJvR9aL._SX377_BO1,204,203,200_.jpg' },
  { id: 3, title: 'Design Patterns', author: 'Erich Gamma', price: 200000, image: 'https://images-na.ssl-images-amazon.com/images/I/51k+e3WGLpL._SX376_BO1,204,203,200_.jpg' },
  { id: 4, title: 'Refactoring', author: 'Martin Fowler', price: 170000, image: 'https://images-na.ssl-images-amazon.com/images/I/41SH-SvWPxL._SX396_BO1,204,203,200_.jpg' },
  { id: 5, title: 'Clean Architecture', author: 'Robert C. Martin', price: 190000, image: 'https://images-na.ssl-images-amazon.com/images/I/41-sN-mzwKL._SX374_BO1,204,203,200_.jpg' },
  { id: 6, title: 'Code Complete', author: 'Steve McConnell', price: 210000, image: 'https://images-na.ssl-images-amazon.com/images/I/41W4T3jA9hL._SX382_BO1,204,203,200_.jpg' },
  { id: 7, title: 'You Don’t Know JS', author: 'Kyle Simpson', price: 160000, image: 'https://images-na.ssl-images-amazon.com/images/I/41+e3refnZL._SX331_BO1,204,203,200_.jpg' },
  { id: 8, title: 'Introduction to Algorithms', author: 'Cormen', price: 250000, image: 'https://images-na.ssl-images-amazon.com/images/I/41SNJZ0RqSL._SX379_BO1,204,203,200_.jpg' },
  { id: 9, title: 'JavaScript: The Good Parts', author: 'Douglas Crockford', price: 140000, image: 'https://images-na.ssl-images-amazon.com/images/I/41jEbK-jG+L._SX380_BO1,204,203,200_.jpg' },
  { id: 10, title: 'The Mythical Man-Month', author: 'Fred Brooks', price: 180000, image: 'https://images-na.ssl-images-amazon.com/images/I/51MT0MbpD0L._SX331_BO1,204,203,200_.jpg' },
]

/* 🔎 Lọc sách theo từ khóa */
const filteredBooks = computed(() => {
  const query = searchQuery.value.toLowerCase()
  return books.filter(
    (book) =>
      book.title.toLowerCase().includes(query) ||
      book.author.toLowerCase().includes(query)
  )
})

/* 🔽 Sắp xếp sách */
const sortedBooks = computed(() => {
  let sorted = [...filteredBooks.value]
  switch (sortOption.value) {
    case 'price-asc':
      return sorted.sort((a, b) => a.price - b.price)
    case 'price-desc':
      return sorted.sort((a, b) => b.price - a.price)
    case 'title-asc':
      return sorted.sort((a, b) => a.title.localeCompare(b.title))
    case 'title-desc':
      return sorted.sort((a, b) => b.title.localeCompare(a.title))
    default:
      return sorted
  }
})

/* 📄 Phân trang */
const totalPages = computed(() => Math.ceil(sortedBooks.value.length / itemsPerPage))
const paginatedBooks = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return sortedBooks.value.slice(start, end)
})

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++
}
const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--
}
</script>

<style scoped>
.book-list-page {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  padding: 30px;
  background-color: #f7f7f7;
  height: 100%;
}

.book-list-container {
  flex: 1;
  background-color: #fff;
  border-radius: 8px;
  padding: 30px 25px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.08);
  height: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

/* 🔍 Search bar */
.search-bar {
  flex: 1;
  display: flex;
  justify-content: flex-start;
}

.search-input {
  width: 300px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
  transition: border-color 0.2s ease;
}

.search-input:focus {
  outline: none;
  border-color: #007bff;
}

.sort-menu {
  display: flex;
  align-items: center;
  gap: 10px;
}

.sort-select {
  padding: 6px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
}

/* Grid */
.book-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  justify-items: center;
}

/* Pagination */
.pagination {
  margin-top: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.page-btn {
  padding: 6px 14px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.page-btn:hover:not(:disabled) {
  background: #0056b3;
}

.page-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.page-info {
  font-weight: 500;
}
</style>
