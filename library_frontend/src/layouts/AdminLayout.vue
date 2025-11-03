<template>
    <el-header>
        <AdminHeader />
    </el-header>

     <div class="content">
    <router-view />
  </div>

</template>

<script lang="ts" setup>

import AdminHeader from '@/components/admin/AdminHeader.vue';

</script>

