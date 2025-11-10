import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/home/Home.vue'
import BookList from '@/pages/book/BookList.vue'
import AddBook from '@/pages/book/ManageListBook.vue'
import UserLayout from '@/layouts/UserLayout.vue'

import AdminLayout from '@/layouts/AdminLayout.vue'
import AdminHome from '@/pages/home/AdminHome.vue'
import BookManagement from '@/pages/book/BookManagement.vue'
import ManageListBook from '@/pages/book/ManageListBook.vue'

import LoginForm from '../components/login/LoginForm.vue'
import RegisterForm from '../components/login/RegisterForm.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: UserLayout,
      children: [
        { path: '', component: Home },
        { path: 'books', name: 'BookList', component: BookList },
        { path: 'your-books', name: 'AddBook', component: AddBook },
      ],
    },

    {
      path: '/admin',
      component: AdminLayout,
      children: [
        { path: '', name: 'AdminHome', component: AdminHome },
        { path: 'books-management', name: 'BooksManagement', component: BookManagement },
        { path: 'books-management/list-books', name: 'ListBooks', component: ManageListBook },
      ],
    },
    {
      path: '/auth/login',
      component: LoginForm,
    },
    {
      path: '/auth/register',
      component: RegisterForm,
    },
    // 404 fallback
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

export default router
