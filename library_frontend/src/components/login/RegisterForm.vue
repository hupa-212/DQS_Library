<template>
  <div class="register-container">
    <div class="register-box">
      <h2>Register</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            v-model="form.username"
            type="text"
            id="username"
            placeholder="Enter username"
            required
          />
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input v-model="form.email" type="email" id="email" placeholder="Enter email" required />
        </div>
        <div class="form-group">
          <label for="firstName">First Name:</label>
          <input
            v-model="form.firstName"
            type="text"
            id="firstName"
            placeholder="Enter first name"
            required
          />
        </div>
        <div class="form-group">
          <label for="lastName">Last Name:</label>
          <input
            v-model="form.lastName"
            type="text"
            id="lastName"
            placeholder="Enter last name"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input
            v-model="form.password"
            type="password"
            id="password"
            placeholder="Enter password"
            required
          />
        </div>
        <div class="form-group">
          <label for="confirm-password">Conform Password:</label>
          <input
            v-model="form.confirmPassword"
            type="password"
            id="confirm-password"
            placeholder="Confirm password"
            required
          />
        </div>
        <button type="submit" class="btn-register">Register</button>
        <p class="login-link">Already account? <router-link to="/auth/login">Login</router-link></p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  firstName: '',
  lastName: '',
})

const handleRegister = async () => {
  if (form.value.password !== form.value.confirmPassword) {
    alert('Mật khẩu không khớp!')
    return
  }

  try {
    const response = await fetch('http://localhost:8080/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: form.value.username,
        password: form.value.password,
        email: form.value.email,
        firstName: form.value.firstName,
        lastName: form.value.lastName,
      }),
    })

    if (response.ok) {
      alert('Đăng ký thành công!')
      router.push('/login')
    }
  } catch (error) {
    console.error('Register failed:', error)
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-box {
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

.btn-register {
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

.btn-register:hover {
  background: #5568d3;
}

.login-link {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.login-link a {
  color: #667eea;
  text-decoration: none;
  font-weight: bold;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
