import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/home/Home.vue'
import BookList from '@/pages/book/BookList.vue'
import AddBook from '@/pages/book/ManageListBook.vue'
import UserLayout from '@/layouts/UserLayout.vue'

import AdminLayout from '@/layouts/AdminLayout.vue'
import AdminHome from '@/pages/home/AdminHome.vue'
import BookManagement from '@/pages/book/BookManagement.vue'
import ManageListBook from '@/pages/book/ManageListBook.vue'
import CategoryManagement from '@/pages/book/CategoryManagement.vue'

import LoginForm from '@/components/login/LoginForm.vue'
import RegisterForm from '@/components/login/RegisterForm.vue'
import BookDetail from '@/pages/book/BookDetail.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // --- USER ROUTES ---
    {
      path: '/',
      component: UserLayout,
      children: [
        { path: '', component: Home },
        { path: 'books', name: 'BookList', component: BookList },
        { path: 'your-books', name: 'AddBook', component: AddBook },
        { path: '/books/:id', name: 'BookDetail', component: BookDetail },
      ],
    },


    {
      path: '/admin',
      component: AdminLayout,
      meta: { requiresAuth: true },
      children: [
        { path: 'books-management', name: 'BooksManagement', component: BookManagement },
        { path: 'books-management/list-books', name: 'ListBooks', component: ManageListBook },
        { path: 'books-management/categories', name: 'CategoryList', component: CategoryManagement },
      ],
    },


    {
      path: '/auth/login',
      name: 'Login',
      component: LoginForm,
    },
    {
      path: '/auth/register',
      name: 'Register',
      component: RegisterForm,
    },


    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('token') || localStorage.getItem('token')

  if (to.meta.requiresAuth && !token) {
    return next('/auth/login')
  }

  if ((to.path === '/auth/login' || to.path === '/auth/register') && token) {
    return next('/admin/books-management')
  }

  next() 
})

export default router
