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

  
  </el-aside>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Folder, Collection, Plus } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

const activeMenu = ref('')

  if (route.path.includes('/admin/books-management/categories')) {
    activeMenu.value = 'list-categories'
  } else if (route.path.includes('/admin/books-management/list-books')) {
    activeMenu.value = 'list-books'
  } else if (route.path.includes('/admin/books-management')) {
    activeMenu.value = 'add-book'
  }

  watch(
    () => route.path,
    (newPath) => {
      if (newPath.includes('/admin/books-management/categories')) {
        activeMenu.value = 'list-categories'
      } else if (newPath.includes('/admin/books-management/list-books')) {
        activeMenu.value = 'list-books'
      } else if (newPath.includes('/admin/books-management')) {
        activeMenu.value = 'add-book'
      }
    }
  )

const handleSelect = (key: string) => {
  activeMenu.value = key
  if (key === 'add-book') router.push('/admin/books-management')
  else if (key === 'list-categories') router.push('/admin/books-management/categories')
  else if (key === 'list-books') router.push('/admin/books-management/list-books')
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
