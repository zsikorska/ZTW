import Vue from 'vue'
import App from './App.vue'
import SmartTable from 'vuejs-smart-table'

import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Multiselect from 'vue-multiselect'
Vue.use(BootstrapVue)
Vue.component('multiselect', Multiselect)
Vue.use(SmartTable)


Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
