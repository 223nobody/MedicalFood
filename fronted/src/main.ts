import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { Axios } from 'axios'
import App from './App.vue'
import router from './router'
import TDesign from 'tdesign-vue-next';
const app = createApp(App)

app.use(createPinia())
app.use(router)
app.mount('#app')
app.use(TDesign);