<template>
  <el-aside width="260px" class="sidebar-container">
    <!-- Section: Management -->
    <div class="menu-section">
      <h3 class="menu-title">Management</h3>
      <el-menu :default-active="activeMenu" class="el-menu-vertical" @select="handleSelect">
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

    <!-- Logout Button -->
    <div class="logout-section">
      <el-button type="danger" plain @click="handleLogout">
        <el-icon><SwitchButton /></el-icon>
        Logout
      </el-button>
    </div>
  </el-aside>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { Folder, Collection, Plus, SwitchButton } from '@element-plus/icons-vue'

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
  },
)

const handleSelect = (key: string) => {
  activeMenu.value = key
  if (key === 'add-book') router.push('/admin/books-management')
  else if (key === 'list-categories') router.push('/admin/books-management/categories')
  else if (key === 'list-books') router.push('/admin/books-management/list-books')
}

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  localStorage.removeItem('role')
  localStorage.removeItem('roles')
  router.push('/auth/login')
}
</script>

<style scoped>
.sidebar-container {
  background-color: #ffffff;
  border-right: 1px solid #e4e4e4;
  height: 100vh;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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

.logout-section {
  margin-top: 20px;
  padding-top: 10px;
  border-top: 1px solid #eee;
}

.logout-section .el-button {
  width: 100%;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
