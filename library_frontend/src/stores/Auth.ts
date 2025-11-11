import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const user = ref<any>(null)
  const role = ref<string | null>(localStorage.getItem('role'))
  const roles = ref<any[]>(JSON.parse(localStorage.getItem('roles') || '[]'))

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => role.value === 'ADMIN')
  const isCustomer = computed(() => role.value === 'CUSTOMER')

  const setUser = (userData: any) => {
    user.value = userData
    role.value = userData.role
    roles.value = userData.roles || []
    localStorage.setItem('role', userData.role)
    localStorage.setItem('roles', JSON.stringify(userData.roles || []))
  }

  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const logout = () => {
    token.value = null
    user.value = null
    role.value = null
    roles.value = []
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('roles')
  }

  return {
    token,
    user,
    role,
    roles,
    isAuthenticated,
    isAdmin,
    isCustomer,
    setUser,
    setToken,
    logout,
  }
})
