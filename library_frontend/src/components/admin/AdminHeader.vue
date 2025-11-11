<template>
  <div class="menu-container">

    <img src="@/assets/ada8a861-db7d-45d3-accb-905caa8c9891-removebg-preview.png" 
             alt="Library Logo" 
             class="logo-img"/>

    <div class="menu-center">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
      >
        <el-menu-item index="Books Management">Books Management</el-menu-item>
      </el-menu>
    </div>

    <div class="right-placeholder"></div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeIndex = ref('Books Management')

const handleSelect = (key: string) => {
  if (key === 'Books Management') router.push('/admin')
  else if (key === 'Profile') router.push('/admin/profile')
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

.logo-img {
  height: 60px;
  object-fit: contain;
}
</style>
