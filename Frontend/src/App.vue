<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  import Header from './components/Header.vue';
</script>

<template>
  <div>
    <Header></Header>
    <hr>
    <!-- 用于路由切换视图使用 -->
     <router-view></router-view>
  </div>
</template>

<style scoped></style>
