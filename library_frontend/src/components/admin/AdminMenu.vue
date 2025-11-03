<template>
  <el-aside width="260px" class="sidebar-container">

    <div class="menu-section">
      <h3 class="menu-title">Management</h3>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        @select="handleSelect"
      >
        <el-menu-item index="add-book">
          <el-icon><Plus /></el-icon>
          <span>Add Book</span>
        </el-menu-item>

        <el-menu-item index="list-books">
          <el-icon><Collection /></el-icon>
          <span>Book List</span>
        </el-menu-item>

        <el-menu-item index="list-categories">
          <el-icon><Folder /></el-icon>
          <span>Manage Categories</span>
        </el-menu-item>

      </el-menu>
    </div>

    <el-divider />

    <div class="filter-section">
      <h3 class="menu-title">Filter</h3>

      <div class="filter-item">
        <span class="filter-label">Price Range</span>
        <el-slider
          v-model="priceRange"
          range
          :max="1000000"
          :step="10000"
          show-stops
        />
        <div class="filter-values">
          <span>{{ priceRange[0].toLocaleString() }}</span> -
          <span>{{ priceRange[1].toLocaleString() }}</span>
        </div>
      </div>

      <div class="filter-item">
        <span class="filter-label">Created Date</span>
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="→"
          start-placeholder="From"
          end-placeholder="To"
          format="DD/MM/YYYY"
          value-format="YYYY-MM-DD"
        />
      </div>

      <div class="filter-actions">
        <el-button type="primary" @click="applyFilters">Apply</el-button>
        <el-button @click="resetFilters">Reset</el-button>
      </div>
    </div>
  </el-aside>
</template>

<script lang="ts" setup>
import { ref} from 'vue'
import { useRouter } from 'vue-router'
import { Folder, Collection, Plus } from '@element-plus/icons-vue'

const router = useRouter()
const activeMenu = ref('add-book')

const priceRange = ref<[number, number]>([0, 500000])
const dateRange = ref<[string, string] | null>(null)


const handleSelect = (key: string) => {
  activeMenu.value = key
  if (key === 'add-book') router.push('/admin/books-management')
  else if (key === 'list-categories') router.push('/admin/books-management/categories')
  else if (key === 'list-books') router.push('/admin/books-management/list-books')
}

const applyFilters = () => {
  console.log('Apply filters:', {
    priceRange: priceRange.value,
    dateRange: dateRange.value,
  })
  // 🔹 Bạn có thể emit event hoặc gọi API tại đây
}

const resetFilters = () => {
  priceRange.value = [0, 500000]
  dateRange.value = null
}
</script>

<style scoped>
.sidebar-container {
  background-color: #ffffff;
  border-right: 1px solid #e4e4e4;
  height: 100vh;
  padding: 20px;
  overflow-y: auto;
}

.menu-title {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 8px;
  color: #333;
}

.el-menu-vertical {
  border-right: none;
}

.filter-section {
  margin-top: 20px;
}

.filter-item {
  margin-bottom: 20px;
}

.filter-label {
  display: block;
  font-size: 13px;
  color: #666;
  margin-bottom: 8px;
}

.filter-values {
  font-size: 12px;
  color: #888;
  margin-top: 5px;
}

.filter-actions {
  display: flex;
  justify-content: space-between;
}
</style>
