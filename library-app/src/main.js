import Vue from 'vue'
import App from './App.vue'
import SmartTable from 'vuejs-smart-table'
import VueRouter from 'vue-router';
import HomePage from "./components/HomePage.vue";
import AuthorsPage from "./components/authors/AuthorsPage.vue";
import BooksPage from "./components/books/BooksPage.vue";
import RentalsPage from "./components/rentals/RentalsPage.vue";
import ReadersPage from "@/components/readers/ReadersPage.vue";

import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Multiselect from 'vue-multiselect'
Vue.use(VueRouter);
const router = new VueRouter({
  routes: [
    {
      path: '/',
      component: HomePage,
      name: 'home'
    },
    {
      path: '/authors',
      component: AuthorsPage,
      name: 'authors'
    },
    {
      path: '/books',
      component: BooksPage,
      name: 'books'
    },
    {
      path: '/readers',
      component: ReadersPage,
      name: 'readers'
    },
    {
      path: '/rentals',
      component: RentalsPage,
      name: 'rentals'
    }
  ],
  mode: 'history'
})
Vue.use(BootstrapVue)
Vue.component('multiselect', Multiselect)
Vue.use(SmartTable)


Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
