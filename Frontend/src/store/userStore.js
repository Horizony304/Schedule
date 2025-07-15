import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const userStore = defineStore('user', () => {
  // 初始状态
  const initialState = {
    uid: 0,
    username: ''
  }

  // 状态
  const uid = ref(0)
  const username = ref('')

  // getters
  const getUid = computed(() => uid.value)
  const getUsername = computed(() => username.value)

  // reset
  const reset = () => {
    uid.value = initialState.uid
    username.value = initialState.username
  }

  // 向外暴露
  return {
    uid,
    username,
    getUid,
    getUsername,
    reset
  }
})