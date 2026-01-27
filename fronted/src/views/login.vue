<template>
  <div class="background-container">
    <div class="login-container">
      <form @submit.prevent="handleLogin" class="login-form">
        <h2 class="login-title">欢迎登录</h2>

        <!-- 错误提示 -->
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>

        <div class="form-control">
          <label for="username">用户名</label>
          <input
            type="text"
            id="username"
            v-model="account"
            :disabled="isLoading"
            required
            placeholder="请输入用户名"
          />
        </div>
        <div class="form-control">
          <label for="password">密码</label>
          <input
            type="password"
            id="password"
            v-model="password"
            :disabled="isLoading"
            required
            placeholder="请输入密码"
          />
        </div>
        <button type="submit" class="login-button" :disabled="isLoading">
          <span v-if="!isLoading">登录</span>
          <span v-else class="loading-text">登录中...</span>
        </button>
        <p class="forgot-password" @click="register()">注册账号</p>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from '../router/index'
import { useRoute } from 'vue-router'

const route = useRoute()
const account = ref('')
const password = ref('')
const isLoading = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  if (!account.value || !password.value) {
    errorMessage.value = '请输入用户名和密码'
    return
  }

  isLoading.value = true
  errorMessage.value = ''

  try {
    console.log('登录请求:', { username: account.value, password: password.value })

    const response = await axios.get('http://localhost:9090/user', {
      params: {
        account: account.value,
        password: password.value
      }
    })

    console.log(response.data.code)
    console.log(response.data.data)

    if (response.data.code === 1) {
      // 保存登录信息
      localStorage.setItem('isLoggedIn', 'true')
      localStorage.setItem('id', response.data.data.id)
      localStorage.setItem('username', response.data.data.name)
      localStorage.setItem('account', response.data.data.account)

      // 获取重定向路径，如果没有则跳转到首页
      const redirect = (route.query.redirect as string) || '/'

      // 延迟跳转，让用户看到成功提示
      setTimeout(() => {
        router.push(redirect)
      }, 300)
    } else {
      errorMessage.value = '用户名或密码错误'
    }
  } catch (error) {
    console.error('登录失败:', error)
    errorMessage.value = '登录失败，请稍后重试'
  } finally {
    isLoading.value = false
  }
}

const register = () => {
  router.push('/register')
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
  background-image: url('https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg');
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
  background: linear-gradient(135deg, rgba(16, 185, 129, 0.3) 0%, rgba(5, 150, 105, 0.4) 100%);
  backdrop-filter: blur(2px);
}

/* ==================== 登录容器 ==================== */
.login-container {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 450px;
  animation: fadeIn 0.6s ease-out;
}

/* ==================== 登录表单 ==================== */
.login-form {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  padding: var(--spacing-2xl);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-xl);
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all var(--transition-base);
}

.login-form:hover {
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

/* ==================== 标题 ==================== */
.login-title {
  text-align: center;
  font-size: var(--text-3xl);
  font-weight: 700;
  margin-bottom: var(--spacing-xl);
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  position: relative;
  padding-bottom: var(--spacing-md);
}

.login-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 3px;
  background: var(--primary-gradient);
  border-radius: var(--radius-full);
}

/* ==================== 错误提示 ==================== */
.error-message {
  background: rgba(239, 68, 68, 0.1);
  border: 1px solid var(--error-color);
  border-left: 4px solid var(--error-color);
  color: var(--error-color);
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-md);
  margin-bottom: var(--spacing-lg);
  font-size: var(--text-sm);
  animation: shake 0.4s ease-in-out;
}

@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  75% {
    transform: translateX(5px);
  }
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
  color: var(--primary-color);
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
  border-color: var(--primary-color);
  box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
  transform: translateY(-1px);
}

.form-control input::placeholder {
  color: var(--text-tertiary);
}

/* ==================== 登录按钮 ==================== */
.login-button {
  width: 100%;
  padding: var(--spacing-md) var(--spacing-lg);
  border: none;
  border-radius: var(--radius-lg);
  background: var(--primary-gradient);
  color: var(--text-inverse);
  font-size: var(--text-lg);
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-base);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
  position: relative;
  overflow: hidden;
}

.login-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.5s;
}

.login-button:hover::before {
  left: 100%;
}

.login-button:hover {
  background: linear-gradient(135deg, #059669 0%, #047857 100%);
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.4);
  transform: translateY(-2px);
}

.login-button:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(16, 185, 129, 0.3);
}

.login-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.login-button:disabled:hover {
  background: var(--primary-gradient);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
  transform: none;
}

.loading-text {
  display: inline-flex;
  align-items: center;
  gap: var(--spacing-sm);
}

.loading-text::after {
  content: '';
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.form-control input:disabled {
  background: var(--bg-secondary);
  cursor: not-allowed;
  opacity: 0.7;
}

/* ==================== 忘记密码链接 ==================== */
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
  color: var(--primary-color);
  background: rgba(16, 185, 129, 0.05);
  text-decoration: underline;
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 480px) {
  .background-container {
    padding: var(--spacing-md);
  }

  .login-form {
    padding: var(--spacing-xl);
  }

  .login-title {
    font-size: var(--text-2xl);
  }

  .form-control input {
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .login-button {
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
