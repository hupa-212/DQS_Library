<template>
  <div class="sidebar">
    <!-- Categories -->
    <h3 class="sidebar-title">BOOK CATEGORIES</h3>
    <ul class="menu">
      <li
        :class="{ active: selectedCategory === null }"
        @click="selectCategory(null)"
      >
        All Categories
      </li>
      <li
        v-for="cat in categories"
        :key="cat.id"
        :class="{ active: selectedCategory === cat.id }"
        @click="selectCategory(cat.id)"
      >
        {{ cat.name }}
      </li>
    </ul>

    <!-- PRICE RANGE -->
    <h3 class="sidebar-title">PRICE RANGE</h3>
    <ul class="menu">
      <li v-for="(range, index) in priceRanges" :key="index">
        <label>
          <input
            type="radio"
            name="priceRange"
            :value="range"
            v-model="selectedPriceRange"
            @change="selectPriceRange"
          />
          {{ range.label }}
        </label>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Category {
  id: number
  name: string
}

interface PriceRange {
  label: string
  min: number
  max: number | null
}

const emit = defineEmits(['category-selected', 'price-selected'])

const categories = ref<Category[]>([])
const selectedCategory = ref<number | null>(null)

const priceRanges = ref<PriceRange[]>([
  { label: 'All Prices', min: 0, max: null },
  { label: '0 - 150,000₫', min: 0, max: 150000 },
  { label: '150,000₫ - 300,000₫', min: 150000, max: 300000 },
  { label: '300,000₫ - 500,000₫', min: 300000, max: 500000 },
  { label: '500,000₫ - 700,000₫', min: 500000, max: 700000 },
  { label: '700,000₫ and above', min: 700000, max: null },
])

const selectedPriceRange = ref<PriceRange>({
  label: 'All Prices',
  min: 0,
  max: null
})

// --- Methods ---
const selectCategory = (id: number | null) => {
  selectedCategory.value = id
  emit('category-selected', id)
}

const selectPriceRange = () => {
  emit('price-selected', selectedPriceRange.value)
}

// --- Fetch categories from backend ---
onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/categories')
    categories.value = res.data.data || res.data
  } catch (error) {
    console.error('Error fetching categories:', error)
  }
})
</script>

<style scoped>
.sidebar {
  width: 220px;
  padding: 10px;
  background: #fff;
  border-right: 1px solid #eee;
}

.sidebar-title {
  font-size: 15px;
  font-weight: 600;
  margin: 10px 0;
}

.menu {
  list-style: none;
  padding: 0;
  margin-bottom: 20px;
}

.menu li {
  margin: 6px 0;
  font-size: 14px;
  cursor: pointer;
  transition: color 0.2s;
}

.menu li:hover {
  color: #409eff;
}

.menu li.active {
  color: #409eff;
  font-weight: 600;
}
</style>
