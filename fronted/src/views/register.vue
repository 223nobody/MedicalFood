<template>
  <div class="background-container">
    <div class="register-container">
      <form @submit.prevent="handleRegister" class="register-form">
        <h2 class="register-title">注册账号</h2>
        <div class="form-control">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="user.name" required />
        </div>
        <div class="form-control">
          <label for="email">账号</label>
          <input type="text" id="email" v-model="user.account" required />
        </div>
        <div class="form-control">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="form-control">
          <label for="confirmPassword">确认密码</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword" required />
        </div>
        <button type="submit" class="register-button">注册</button>
        <p class="forgot-password" @click="Login()">返回登录</p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from '../router/index'

const user = ref({
  name: '',
  account: '',
  password: ''
})

const confirmPassword = ref('')

const handleRegister = async () => {
  if (user.value.password !== confirmPassword.value) {
    alert('密码不匹配')
    return
  }

  try {
    const response = await axios.post('http://localhost:9090/user', user.value)
    // 处理注册成功的逻辑，例如路由跳转
    if (response.data.code == 1) {
      alert('注册成功')
      router.push('/login')
    } else if (response.data.code == 0) alert(response.data.msg)
  } catch (error) {
    console.error('注册失败:', error)
    alert('注册失败，请重试')
  }
}
const Login = () => {
  router.push('/login')
}
</script>

<style scoped>
/* ==================== 背景容器 ==================== */
.background-container {
  position: relative;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url('https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index5.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  padding: var(--spacing-lg);
}

.background-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(245, 158, 11, 0.3) 0%, rgba(217, 119, 6, 0.4) 100%);
  backdrop-filter: blur(2px);
}

/* ==================== 注册容器 ==================== */
.register-container {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 480px;
  animation: fadeIn 0.6s ease-out;
}

/* ==================== 注册表单 ==================== */
.register-form {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  padding: var(--spacing-2xl);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-xl);
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all var(--transition-base);
}

.register-form:hover {
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

/* ==================== 标题 ==================== */
.register-title {
  text-align: center;
  font-size: var(--text-3xl);
  font-weight: 700;
  margin-bottom: var(--spacing-xl);
  background: linear-gradient(135deg, var(--secondary-color) 0%, var(--secondary-dark) 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  position: relative;
  padding-bottom: var(--spacing-md);
}

.register-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background: linear-gradient(135deg, var(--secondary-color) 0%, var(--secondary-dark) 100%);
  border-radius: var(--radius-full);
}

/* ==================== 表单控件 ==================== */
.form-control {
  margin-bottom: var(--spacing-lg);
  position: relative;
}

.form-control label {
  display: block;
  margin-bottom: var(--spacing-sm);
  color: var(--text-secondary);
  font-size: var(--text-sm);
  font-weight: 500;
  transition: color var(--transition-fast);
}

.form-control:focus-within label {
  color: var(--secondary-color);
}

.form-control input {
  width: 100%;
  padding: var(--spacing-md) var(--spacing-lg);
  border: 2px solid var(--border-light);
  border-radius: var(--radius-lg);
  font-size: var(--text-base);
  color: var(--text-primary);
  background: var(--bg-primary);
  outline: none;
  transition: all var(--transition-fast);
  box-sizing: border-box;
}

.form-control input:focus {
  border-color: var(--secondary-color);
  box-shadow: 0 0 0 4px rgba(245, 158, 11, 0.1);
  transform: translateY(-1px);
}

.form-control input::placeholder {
  color: var(--text-tertiary);
}

/* ==================== 注册按钮 ==================== */
.register-button {
  width: 100%;
  padding: var(--spacing-md) var(--spacing-lg);
  border: none;
  border-radius: var(--radius-lg);
  background: linear-gradient(135deg, var(--secondary-color) 0%, var(--secondary-dark) 100%);
  color: var(--text-inverse);
  font-size: var(--text-lg);
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-base);
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);
  position: relative;
  overflow: hidden;
}

.register-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.5s;
}

.register-button:hover::before {
  left: 100%;
}

.register-button:hover {
  background: linear-gradient(135deg, var(--secondary-dark) 0%, #b45309 100%);
  box-shadow: 0 6px 20px rgba(245, 158, 11, 0.4);
  transform: translateY(-2px);
}

.register-button:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(245, 158, 11, 0.3);
}

/* ==================== 返回登录链接 ==================== */
.forgot-password {
  text-align: center;
  margin-top: var(--spacing-lg);
  font-size: var(--text-sm);
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-fast);
  padding: var(--spacing-xs);
  border-radius: var(--radius-md);
}

.forgot-password:hover {
  color: var(--secondary-color);
  background: rgba(245, 158, 11, 0.05);
  text-decoration: underline;
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 480px) {
  .background-container {
    padding: var(--spacing-md);
  }

  .register-form {
    padding: var(--spacing-xl);
  }

  .register-title {
    font-size: var(--text-2xl);
  }

  .form-control input {
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .register-button {
    padding: var(--spacing-sm) var(--spacing-md);
    font-size: var(--text-base);
  }
}

/* ==================== 动画 ==================== */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
