<template>
  <div class="login-container">
    <div class="login-box">
      <h2>Login</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Username:</label>
          <input
            v-model="form.username"
            type="text"
            id="username"
            placeholder="Nhập tên đăng nhập"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input
            v-model="form.password"
            type="password"
            id="password"
            placeholder="Nhập mật khẩu"
            required
          />
        </div>
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
        <button type="submit" class="btn-login" :disabled="loading">
          {{ loading ? 'Logging in...' : 'Login' }}
        </button>
        <p class="register-link">
          Don't have account? <router-link to="/auth/register">Register</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/Auth'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)
const errorMessage = ref('')

const form = ref({
  username: '',
  password: '',
})

const handleLogin = async () => {
  loading.value = true
  errorMessage.value = ''
  
  try {
    const loginResponse = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(form.value),
    })

    if (!loginResponse.ok) {
      const error = await loginResponse.json()
      errorMessage.value = error.message || 'Login failed'
      return
    }

    const loginData = await loginResponse.json()
    const token = loginData.data.token

    sessionStorage.setItem('token', token)

    const userResponse = await fetch('http://localhost:8080/api/users/me', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    })

    if (!userResponse.ok) {
      errorMessage.value = 'Failed to fetch user info'
      return
    }

    const userData = await userResponse.json()
    const user = userData.data

    authStore.setToken(token)
    authStore.setUser({
      id: user.id,
      username: user.username,
      email: user.email,
      name: user.name,
      roles: user.roles,
      role: user.roles && user.roles.length > 0 ? user.roles[0].name : 'CUSTOMER'
    })

    const userRole = user.roles && user.roles.length > 0 ? user.roles[0].name : 'CUSTOMER'
    
    if (userRole === 'ADMIN') {
      router.push('/admin/books-management')
    } else {
      router.push('/home')
    }
  } catch (error) {
    console.error('Login failed:', error)
    errorMessage.value = 'An error occurred. Please try again.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 600px;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  color: #555;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
}

input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
}

.error-message {
  color: #dc3545;
  padding: 10px;
  background: #f8d7da;
  border: 1px solid #f5c6cb;
  border-radius: 5px;
  margin-bottom: 15px;
  font-size: 14px;
}

.btn-login {
  width: 100%;
  padding: 12px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 10px;
}

.btn-login:hover:not(:disabled) {
  background: #5568d3;
}

.btn-login:disabled {
  background: #999;
  cursor: not-allowed;
}

.register-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.register-link a {
  color: #667eea;
  text-decoration: none;
  font-weight: bold;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>