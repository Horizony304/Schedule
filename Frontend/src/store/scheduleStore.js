import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const scheduleStore = defineStore('schedule', () => {
  // 初始状态
  const initialState = {
    itemList: [] // sid uid title completed
  }

  // 状态
  const itemList = ref([])

  // getters
  const getItemList = computed(() => itemList.value)

  // reset
  const reset = () => {
    itemList.value = initialState.itemList
  }

  // 向外暴露
  return {
    itemList,
    getItemList,
    reset
  }
})