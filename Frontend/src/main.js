import { createApp } from 'vue'

import App from './App.vue'
// 导入路由
import router from './router/router.js'
// 导入Pinia
import pinia from './pinia.js'

let app = createApp(App)
// 全局使用路由
app.use(router)
// 全局使用Pinia
app.use(pinia)

app.mount('#app')
