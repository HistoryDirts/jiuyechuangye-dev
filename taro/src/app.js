import { createApp } from 'vue'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css'

import './app.scss'

const App = createApp({
    onShow(options) {},
    // 入口组件不需要实现 render 方法，即使实现了也会被 taro 所覆盖
})

App.use(store)
App.use(ElementPlus)
App.mount('#App')

export default App