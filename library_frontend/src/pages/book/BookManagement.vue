<template>
  <div class="manage-container">
    <AdminMenu />

    <div class="main-content">
      <AddBookForm />
    </div>
  </div>
</template>

<script setup lang="ts">
import AdminMenu from '@/components/admin/AdminMenu.vue'
import AddBookForm from '@/components/admin/AddBookForm.vue'
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
  min-height: 100%;
  flex: 1;
  padding:0;
  background-color: #f5f5f5;
}
</style>
