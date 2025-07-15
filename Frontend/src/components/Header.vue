<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  // 导入编程式路由
  import { useRouter } from 'vue-router'
  let router = useRouter()
  import { userStore } from '../store/userStore';
  import { scheduleStore } from '../store/scheduleStore';
  let user = userStore()
  let schedule = scheduleStore()

  // 退出登录
  function logout() {
    // 清除userPinia 和 schedulePinia
    user.reset()
    schedule.reset()
    // 通过路由回到登录页
    router.push('/login')
  }
</script>

<template>
  <div>
    <h1 class="hd">欢迎使用日程管理系统</h1>
    <div>
      <div class="optionDiv" v-if="user.username == ''">
        <router-link to="/login">
          <button class="btn1">登录</button>
        </router-link>
        <router-link to="/register">
          <button class="btn1">注册</button>
        </router-link>
      </div>

      <div class="optionDiv" v-else>
        欢迎{{ user.username }}
        <button class="btn2" @click="logout()">退出登录</button>
        <router-link to="/showSchedule">
          <button class="btn2">查看我的日程</button>
        </router-link>
      </div>
      <br />
    </div>
  </div>
</template>

<style scoped>
  .hd {
    text-align: center;
    color: cadetblue;
    font-family: '幼圆';
  }
  .optionDiv {
    width: 400px;
    float: right;
  }
  .btn1 {
    border: 2px solid powderblue;
    border-radius: 4px;
    width: 60px;
    background-color: antiquewhite;
  }
  .btn2 {
    border: 2px solid powderblue;
    border-radius: 4px;
    width: 100px;
    background-color: antiquewhite;
  }
</style>
