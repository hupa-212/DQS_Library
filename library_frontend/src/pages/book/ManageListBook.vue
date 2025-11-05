<template>
  <div class="manage-container">
    <AdminMenu />

    <div class="main-content">
      <div class="content-wrapper">
        <h2 class="title">📚 List of Books</h2>

        <el-table
          v-loading="loading"
          :data="paginatedBooks"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column prop="id" label="ID" width="80" align="center" />
          <el-table-column prop="title" label="Name" min-width="180" />
          <el-table-column prop="author" label="Author" min-width="150" />
          <el-table-column prop="isbn" label="ISBN" min-width="120" />
          <el-table-column prop="categoryName" label="Category" min-width="120" />
          <el-table-column prop="price" label="Price (VND)" min-width="120" />
          <el-table-column prop="quantity" label="Quantity" width="100" align="center" />
          <el-table-column prop="quantitySold" label="Sold" width="100" align="center" />
          <el-table-column label="Cover Image" width="120" align="center">
            <template #default="{ row }">
              <img
                v-if="row.coverImageUrl"
                :src="row.coverImageUrl"
                alt="Cover"
                class="cover-img"
              />
              <span v-else>None</span>
            </template>
          </el-table-column>
        </el-table>

        <!-- Pagination -->
        <div class="pagination-container">
          <el-pagination
            background
            layout="prev, pager, next, jumper"
            :page-size="pageSize"
            :total="books.length"
            v-model:current-page="currentPage"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import AdminMenu from '@/components/admin/AdminMenu.vue'

const books = ref<any[]>([])
const loading = ref(true)


const currentPage = ref(1)
const pageSize = ref(10)


const paginatedBooks = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return books.value.slice(start, end)
})

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/books')
    books.value = response.data.data.map((b: any) => ({
      ...b,
      categoryName: b.category?.name || 'None'
    }))
  } catch (error) {
    console.error('Error when loading:', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.manage-container {
  display: flex;
  min-height: 100vh;
}

.manage-container > *:first-child {
  flex: 0 0 250px;
}

.main-content {
  flex: 1;
  background-color: #f5f5f5;
  padding: 20px;
}

.content-wrapper {
  background-color: #fff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 16px;
}

.cover-img {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
