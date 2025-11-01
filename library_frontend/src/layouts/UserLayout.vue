<template>
 

        
      <el-header>
        <Header />
      </el-header>
    
      <div class = "content">
        <slot />
      </div>
      
        <Footer />
    

 
</template>

<script lang="ts" setup>
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'
</script>

<style scoped>

.el-container {
  padding: 0;
}
.content {
  background-color: #f5f5f5;

  padding: 20px 20px 0 20px;

}

.el-header {
  padding: 0 20px;
}

.common-layout {
  min-height: 100vh;
}
</style>