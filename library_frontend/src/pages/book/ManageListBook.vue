<template>
  <div class="manage-container">
    <AdminMenu />

    <div class="main-content">
      <div class="content-wrapper">
        <h2 class="title">📚 List of Books</h2>

        <!-- 🔍 Search bar -->
        <div class="search-bar">
          <el-input
            v-model="searchQuery"
            placeholder="Search by title or author..."
            clearable
            prefix-icon="el-icon-search"
            class="search-input"
          />
          <el-button type="primary" @click="filterBooks">Search</el-button>
          <el-button @click="resetSearch">Reset</el-button>
        </div>

        <EditBookDialog ref="editDialog" @updated="reloadBooks" />

        <el-table
          v-loading="loading"
          :data="paginatedBooks"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column prop="title" label="Name" min-width="180" />
          <el-table-column prop="author" label="Author" min-width="140" />
          <el-table-column prop="isbn" label="ISBN" min-width="120" />
          <el-table-column prop="categoryName" label="Category" min-width="120" />
          <el-table-column
            prop="price"
            label="Price (VND)"
            min-width="120"
            align="right"
          />
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

          <el-table-column label="Actions" width="160" align="center">
            <template #default="{ row }">
              <el-button size="small" type="primary" @click="editBook(row)">Edit</el-button>
              <el-button size="small" type="danger" @click="deleteBook(row.id)">
                Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- Pagination -->
        <div class="pagination-container">
          <el-pagination
            background
            layout="prev, pager, next, jumper"
            :page-size="pageSize"
            :total="filteredBooks.length"
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
import { ElMessage, ElMessageBox } from 'element-plus'
import AdminMenu from '@/components/admin/AdminMenu.vue'
import EditBookDialog from '@/components/common/EditBookDialog.vue'

const books = ref<any[]>([])
const filteredBooks = ref<any[]>([])
const loading = ref(true)

const editDialog = ref<InstanceType<typeof EditBookDialog>>()
const token = sessionStorage.getItem('token') || localStorage.getItem('token')

const currentPage = ref(1)
const pageSize = ref(10)
const searchQuery = ref('')

onMounted(async () => {
  await loadBooks()
})

const loadBooks = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/books')
    books.value = response.data.data.map((b: any) => ({
      ...b,
      categoryName: b.category?.name || 'None',
    }))
    filteredBooks.value = books.value
  } catch (error) {
    console.error('Error when loading:', error)
  } finally {
    loading.value = false
  }
}

const paginatedBooks = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredBooks.value.slice(start, end)
})

// 🔍 Lọc theo title hoặc author
const filterBooks = () => {
  const query = searchQuery.value.trim().toLowerCase()
  if (!query) {
    filteredBooks.value = books.value
    return
  }
  filteredBooks.value = books.value.filter(
    (book) =>
      book.title.toLowerCase().includes(query) ||
      book.author.toLowerCase().includes(query)
  )
  currentPage.value = 1
}

const resetSearch = () => {
  searchQuery.value = ''
  filteredBooks.value = books.value
}

function editBook(book: any) {
  editDialog.value?.openDialog(book)
}

function reloadBooks(updatedBook: any) {
  const index = books.value.findIndex((b) => b.id === updatedBook.id)
  if (index !== -1) books.value[index] = updatedBook
  filterBooks() // Cập nhật lại danh sách hiển thị nếu đang tìm kiếm
}

const deleteBook = async (id: number) => {
  try {
    await ElMessageBox.confirm('Are you sure you want to delete this book?', 'Confirm Deletion', {
      confirmButtonText: 'Yes',
      cancelButtonText: 'Cancel',
      type: 'warning',
    })
    await axios.delete(`http://localhost:8080/api/books/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
    ElMessage.success('Book deleted successfully!')
    await loadBooks()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('Failed to delete book.')
      console.error(error)
    }
  }
}
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

/* 🔍 Search bar */
.search-bar {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 16px;
}

.search-input {
  flex: 1;
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
