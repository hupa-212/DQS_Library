<template>
    <div class="slider-container">
      <Slider />
    </div>

    <div class="book-section">
      <h2>Popular Books</h2>
      <div class="book-grid">
        <BookItem v-for="book in books" :key="book.id" :book="book" />
      </div>
    </div>
</template>

<script setup lang="ts">

import Slider from '@/components/common/Slider.vue'
import BookItem from '@/components/common/BookItem.vue'

// mock data
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
