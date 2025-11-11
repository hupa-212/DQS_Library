<template>
  <div class="menu-container">

    <el-page-header :icon="ArrowLeft">
      <template #content>
        <span class="text-large font-600 mr-3">Title</span>
      </template>
    </el-page-header>

    <div class="menu-center">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
      >
        <el-menu-item index="Home" >Home</el-menu-item>
        <el-menu-item index="Books">Books</el-menu-item>
      </el-menu>
    </div>

    <div class="right-placeholder"></div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeIndex = ref('Home')

const handleSelect = (key: string, keyPath: string[]) => {
if (key === 'Home') router.push('/')
  else if (key === 'Books') router.push('/books')
}
</script>

<style scoped>
.menu-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #eee;
  background-color: #fff;
  padding: 0;
}

.menu-center {
  flex: 1; 
  display: flex;
  justify-content: center; 
}

.el-menu {
  border-bottom: none;
}

.right-placeholder {
  width: 120px;
}
</style>
