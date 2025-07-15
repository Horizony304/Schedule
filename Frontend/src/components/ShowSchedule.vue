<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  // 引入axios
  import request from '../utils/request'
  // 引入Pinia数据
  import { userStore } from '../store/userStore'
  import { scheduleStore } from '../store/scheduleStore'
  let user = userStore()
  let schedule = scheduleStore()

  // 第一次挂载就立刻向后端发送请求，获取最新数据
  onMounted(() => {
    showSchedule()
  })

  // 获取我的最新日程
  async function showSchedule() {
    let { data } = await request.get('schedule/findAllSchedule', { params: { uid: user.uid } })
    schedule.itemList = data.data.itemList
  }

  // 新增日程
  async function addItem() {
    // 立刻向后端发送一个请求，让后端先为当前用户在数据库中增加一个默认格式的空数据
    let { data } = await request.get('schedule/addDefaultSchedule', { params: { uid: user.uid } })
    if (data.code == 200) {
      // 然后调用刷新页面数据方法，立刻获取最新数据
      showSchedule()
    } else {
      alert('添加异常')
    }
  }

  // 更新日程
  async function updateItem(index) {
    // 根据索引获取元素，将元素通过JSON串的形式发送给服务器端
    let { data } = await request.post('schedule/updateSchedule', schedule.itemList[index])
    if (data.code == 200) {
      // 服务器端修改完毕后，刷新页面数据
      showSchedule()
    } else {
      alert('更新异常')
    }
  }

  // 删除日程
  async function removeItem(index) {
    // 弹窗提示是否删除
    if (confirm('确定要删除此日程？')) {
      // 根据索引获取元素的id
      let sid = schedule.itemList[index].sid
      // 向服务端发送请求删除日程
      let { data } = await request.get('schedule/removeSchedule', { params: { sid: sid } })
      if (data.code == 200) {
        // 删除成功，更新页面
        showSchedule()
      } else {
        alert('删除失败')
      }
    }
  }
</script>

<template>
  <div>
    <h3 class="hd">您的日程如下</h3>
    <table class="tab" cellspacing="0px">
      <tr class="tr">
        <th>编号</th>
        <th>内容</th>
        <th>进度</th>
        <th>操作</th>
      </tr>
      <tr class="tr" v-for="(item, index) in schedule.itemList" :key="index">
        <td>{{ index + 1 }}</td>
        <td>
          <input type="text" v-model="item.title" />
        </td>
        <td>
          <input type="radio" value="1" v-model="item.completed" />
          已完成
          <input type="radio" value="0" v-model="item.completed" />
          未完成
        </td>

        <td class="buttonContainer">
          <button class="btn" @click="removeItem(index)">删除</button>
          <button class="btn" @click="updateItem(index)">保存修改</button>
        </td>
      </tr>
      <tr class="tr buttonContainer">
        <td colspan="4">
          <button class="btn" @click="addItem()">新增日程</button>
        </td>
      </tr>
    </table>
  </div>
</template>

<style scoped>
  .hd {
    text-align: center;
    color: cadetblue;
    font-family: '幼圆';
  }
  .tab {
    width: 80%;
    border: 5px solid cadetblue;
    margin: 0px auto;
    border-radius: 5px;
    font-family: '幼圆';
  }
  .tr td {
    border: 1px solid powderblue;
  }
  .ipt {
    border: 0px;
    width: 50%;
  }
  .btn {
    border: 2px solid powderblue;
    border-radius: 4px;
    width: 100px;
    background-color: antiquewhite;
  }
  .buttonContainer {
    text-align: center;
  }
</style>
