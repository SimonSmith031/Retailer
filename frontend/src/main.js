import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import "../public/main.css"
import "../public/mine.css"
import axios from "axios";

// 前后端都做了处理，似乎后端的那个才是关键的
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

createApp(App).use(store).use(router).mount('#app')