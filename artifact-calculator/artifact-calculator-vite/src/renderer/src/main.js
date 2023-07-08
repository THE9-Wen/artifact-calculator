import { createApp } from 'vue'
import App from './App.vue'
import { Axios } from 'axios'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/common.css'

const app = createApp(App)

app.config.globalProperties.$http = Axios

app.use(ElementPlus)
app.mount('#app')
