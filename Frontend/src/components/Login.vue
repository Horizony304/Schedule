<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  // 导入发送请求的axios对象
  import request from '../utils/request.js'
  // 获取编程式路由对象
  import { useRouter } from 'vue-router'
  const router = useRouter()
  // 导入Pinia中的user数据
  import { userStore } from '../store/userStore.js'
  let user = userStore()

  // 响应式数据，保存用户输入的表单信息
  const loginUser = reactive({
    username: '',
    userpwd: '',
  })
  // 响应式数据，保存校验的提示信息
  const usernameMsg = ref('')
  const userpwdMsg = ref('')
  // 校验用户名的方法
  function checkUsername() {
    // 定义正则
    let usernameReg = /^[a-zA-Z0-9]{5,10}$/
    // 校验用户名
    if (!usernameReg.test(loginUser.username)) {
      // 格式不合法
      usernameMsg.value = '格式有误'
      return false
    }
    usernameMsg.value = 'OK'
    return true
  }
  // 校验密码的方法
  function checkUserpwd() {
    // 定义正则
    let userpwdReg = /^[0-9]{6}$/
    // 校验密码
    if (!userpwdReg.test(loginUser.userpwd)) {
      // 格式不合法
      userpwdMsg.value = '格式有误'
      return false
    }
    userpwdMsg.value = 'OK'
    return true
  }
  // 登录
  async function login() {
    let flag1 = checkUsername()
    let flag2 = checkUserpwd()
    if (!(flag1 && flag2)) {
      alert('校验不通过，请检查数据')
      return
    }
    let { data } = await request.post('user/login', loginUser)

    if (data.code == 200) {
      alert('登录成功')
      // 更新Pinia数据
      user.uid = data.data.loginUser.uid
      user.username = data.data.loginUser.username
      // 跳转到showSchedule
      router.push('/showSchedule')
    } else if (data.code == 501) {
      alert('用户名有误')
    } else if (data.code == 503) {
      alert('密码有误')
    } else {
      alert('未知错误')
    }
  }

  // 重置
  function clearForm() {
    loginUser.username = ''
    loginUser.userpwd = ''
    usernameMsg.value = ''
    userpwdMsg.value = ''
  }
</script>

<template>
  <div>
    <h3 class="hd">请登录</h3>
    <!-- 把原本的table标签放在form标签内，并把登录type改为submit，即可实现按回车登录，按下回车键会优先触发submit -->
    <form >
      <table cellspacing="0px" class="tab">
        <tr class="tr">
          <td>请输入账号</td>
          <td>
            <input class="ipt" type="text" v-model="loginUser.username" @blur="checkUsername()" />
            <span id="usernameMsg">{{ usernameMsg }}</span>
          </td>
        </tr>
        <tr class="tr">
          <td>请输入密码</td>
          <td>
            <input class="ipt" type="password" v-model="loginUser.userpwd" @blur="checkUserpwd()" />
            <span id="userpwdMsg">{{ userpwdMsg }}</span>
          </td>
        </tr>
        <tr class="tr">
          <td class="buttonContainer" colspan="2">
            <input type="submit" value="登录" class="btn" @click="login()" @keyup.enter="login()" />
            <input type="button" value="重置" class="btn" @click="clearForm()" />
            <router-link to="/register">
              <button class="btn">去注册</button>
            </router-link>
          </td>
        </tr>
      </table>
    </form>
  </div>
</template>

<style scoped>
  .hd {
    text-align: center;
    color: cadetblue;
    font-family: '幼圆';
  }
  .tab {
    width: 500px;
    border: 5px solid cadetblue;
    margin: 0px auto;
    border-radius: 5px;
    font-family: '幼圆';
  }
  .ipt {
    border: 0px;
    width: 50%;
  }
  .buttonContainer {
    text-align: center;
  }
  .btn {
    border: 2px solid powderblue;
    border-radius: 4px;
    width: 60px;
    background-color: antiquewhite;
  }
  #usernameMsg,
  #userpwdMsg {
    color: gold;
  }
  .tr td {
    border: 1px solid powderblue;
  }
</style>
