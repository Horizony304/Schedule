<script setup>
  import { ref, reactive, computed, onMounted } from 'vue'
  // 导入发送请求的axios对象
  import request from '../utils/request.js'

  import { useRouter } from 'vue-router'
  const router = useRouter()

  // 响应式数据，保存用户输入的表单信息
  const registerUser = reactive({
    username: '',
    userpwd: '',
  })

  // 响应式数据，保存校验的提示信息
  const reUserpwd = ref('')
  const reUserpwdMsg = ref('')
  const usernameMsg = ref('')
  const userpwdMsg = ref('')

  // 校验用户名的方法
  async function checkUsername() {
    // 定义正则
    let usernameReg = /^[a-zA-Z0-9]{5,10}$/
    // 校验用户名
    if (!usernameReg.test(registerUser.username)) {
      // 格式不合法
      usernameMsg.value = '格式有误'
      return false
    }
    // 继续校验用户名是否被占用
    let { data } = await request.post(`user/checkUsernameUsed?username=${registerUser.username}`)
    if (data.code != 200) {
      usernameMsg.value = '用户名已被占用'
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
    if (!userpwdReg.test(registerUser.userpwd)) {
      // 格式不合法
      userpwdMsg.value = '格式有误'
      return false
    }
    userpwdMsg.value = 'OK'
    return true
  }
  // 校验第二次密码的方法
  function checkReUserpwd() {
    // 定义正则
    let userpwdReg = /^[0-9]{6}$/
    // 校验密码
    if (!userpwdReg.test(reUserpwd.value)) {
      // 格式不合法
      reUserpwdMsg.value = '格式有误'
      return false
    }
    // 校验是否与第一次相同
    if (registerUser.userpwd != reUserpwd.value) {
      // 不合法
      reUserpwdMsg.value = '不一致'
      return false
    }
    reUserpwdMsg.value = 'OK'
    return true
  }

  // 注册
  async function register() {
    // 校验所有的输入框是否合法
    let flag1 = await checkUsername()
    let flag2 = checkUserpwd()
    let flag3 = checkReUserpwd()
    if (flag1 && flag2 && flag3) {
      let { data } = await request.post('user/register', registerUser)
      if (data.code == 200) {
        // 注册成功跳转 登录页
        alert('注册成功，去登录吧')
        router.push('/login')
      } else {
        alert('抱歉，用户名被抢注了')
      }
    } else {
      alert('校验不通过，请检查数据')
    }
  }

  // 重置
  function clearForm() {
    registerUser.username = ''
    registerUser.userpwd = ''
    usernameMsg.value = ''
    userpwdMsg.value = ''
    reUserpwd.value = ''
    reUserpwdMsg.value = ''
  }
</script>

<template>
  <div>
    <h3 class="hd">请注册</h3>
    <!-- 把原本的table标签放在form标签内，并把注册type改为submit，即可实现按回车注册，按下回车键会优先触发submit -->
    <form @keyup.enter="register()">
      <table class="tab" cellspacing="0px">
        <tr class="tr">
          <td>请输入账号</td>
          <td>
            <input
              type="text"
              class="ipt"
              v-model="registerUser.username"
              @blur="checkUsername()"
            />
            <span class="msg" v-text="usernameMsg"></span>
          </td>
        </tr>
        <tr class="tr">
          <td>请输入密码</td>
          <td>
            <input
              type="password"
              class="ipt"
              v-model="registerUser.userpwd"
              @blur="checkUserpwd()"
            />
            <span class="msg" v-text="userpwdMsg"></span>
          </td>
        </tr>
        <tr class="tr">
          <td>确认密码</td>
          <td>
            <input type="password" class="ipt" v-model="reUserpwd" @blur="checkReUserpwd" />
            <span class="msg" v-text="reUserpwdMsg"></span>
          </td>
        </tr>
        <tr class="tr">
          <td colspan="2" class="buttonContainer">
            <input type="submit" class="btn" value="注册" @click="register()" />
            <input type="button" class="btn" value="重置" @click="clearForm()" />
            <router-link to="/login">
              <button class="btn">去登录</button>
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
    width: 60px;
    background-color: antiquewhite;
  }
  .msg {
    color: gold;
  }
  .buttonContainer {
    text-align: center;
  }
</style>
