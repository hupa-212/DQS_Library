<script setup lang="ts">
import UserLayout from '@/layouts/UserLayout.vue';
</script>

<template>
  <UserLayout>
    <router-view />
  </UserLayout>
</template>

<style>

@import url('https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,500;1,500&display=swap');

* {
  font-family: "Open Sans", sans-serif;
  font-optical-sizing: auto;
  font-weight: 500;
  font-style: normal;
  font-variation-settings: "wdth" 100;
  padding: 0;
}

html, body, #app, .common-layout ,.el-container {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}



</style>
