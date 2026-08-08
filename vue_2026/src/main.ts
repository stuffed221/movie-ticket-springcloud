import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElmentPlusIconsVue from '@element-plus/icons-vue'
import axios from 'axios'

axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = 'Bearer ' + token;
    }
    return config;
});

axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response && error.response.status === 401) {
            localStorage.clear();
            router.push('/LoginLog');
        }
        return Promise.reject(error);
    }
);

const app = createApp(App)

for(const [key,component] of Object.entries(ElmentPlusIconsVue)){
    app.component(key,component)
}

app.use(createPinia())
app.use(router)
app.use(ElementPlus)
app.mount('#app')
