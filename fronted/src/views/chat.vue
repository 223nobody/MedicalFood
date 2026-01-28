<template>
  <div class="chat-container">
    <div class="head">
      <!-- 左边 Logo -->
      <span class="logo" @click="handleLogo">药膳云厨</span>
      <h1 class="page-title">AI 智能对话助手</h1>
    </div>

    <!-- AI 模型选择 -->
    <div class="mode-switch">
      <div class="model-selector">
        <label class="selector-label">选择 AI 模型：</label>
        <t-radio-group v-model="selectedModel" variant="default-filled" size="large">
          <t-radio-button value="qwen">
            <span class="model-option">
              <icon name="logo-wechat" size="18px" />
              千问 (Qwen)
            </span>
          </t-radio-button>
          <t-radio-button value="deepseek">
            <span class="model-option">
              <icon name="explore" size="18px" />
              DeepSeek
            </span>
          </t-radio-button>
          <t-radio-button value="glm">
            <span class="model-option">
              <icon name="lightbulb" size="18px" />
              智谱 (GLM)
            </span>
          </t-radio-button>
        </t-radio-group>
      </div>
      <div class="mode-description">
        <span v-if="selectedModel === 'qwen'">🤖 千问：阿里云通义千问，擅长中文理解和药膳知识</span>
        <span v-else-if="selectedModel === 'deepseek'"
          >🔍 DeepSeek：深度推理模型，提供更详细的分析</span
        >
        <span v-else>💡 智谱GLM：清华智谱AI，专业的中文对话模型</span>
      </div>
    </div>

    <!-- 对话历史区域 -->
    <div class="chat-history" ref="chatHistoryRef">
      <div v-if="chatHistory.length === 0" class="empty-state">
        <icon name="chat" size="80px" style="color: var(--primary-color); opacity: 0.3" />
        <p>开始您的 AI 对话之旅</p>
        <div class="quick-questions">
          <h3>快速提问：</h3>
          <t-button
            v-for="(question, index) in quickQuestions"
            :key="index"
            theme="default"
            variant="outline"
            @click="sendQuickQuestion(question)"
          >
            {{ question }}
          </t-button>
        </div>
      </div>

      <div v-for="(message, index) in chatHistory" :key="index" class="message-wrapper">
        <!-- 用户消息 -->
        <div v-if="message.role === 'user'" class="message user-message">
          <div class="message-content">
            <div class="message-text">{{ message.content }}</div>
            <div class="message-time">{{ message.time }}</div>
          </div>
          <div class="message-avatar user-avatar">
            <icon name="user" size="24px" />
          </div>
        </div>

        <!-- AI 消息 -->
        <div v-else class="message ai-message">
          <div class="message-avatar ai-avatar">
            <icon name="logo-github" size="24px" />
          </div>
          <div class="message-content">
            <MarkdownRenderer :content="message.content" />
            <div class="message-time">{{ message.time }}</div>
          </div>
        </div>
      </div>

      <!-- 加载中状态 -->
      <div v-if="isLoading" class="message ai-message loading-message">
        <div class="message-avatar ai-avatar">
          <icon name="logo-github" size="24px" />
        </div>
        <div class="message-content">
          <div class="loading-dots">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="input-area">
      <t-textarea
        v-model="userInput"
        placeholder="请输入您的问题，例如：推荐一份健康早餐、如何改善失眠..."
        :autosize="{ minRows: 1, maxRows: 4 }"
        @keydown.enter.exact.prevent="handleSend"
        :disabled="isLoading"
        class="chat-input"
      />
      <div class="input-actions">
        <t-button theme="default" variant="outline" @click="clearHistory" :disabled="isLoading">
          <icon name="delete" size="18px" />
          清空对话
        </t-button>
        <t-button
          theme="primary"
          @click="handleSend"
          :loading="isLoading"
          :disabled="!userInput.trim() || isLoading"
        >
          <icon name="send" size="18px" />
          发送
        </t-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick, onMounted } from 'vue'
import { Icon } from 'tdesign-icons-vue-next'
import { MessagePlugin } from 'tdesign-vue-next'
import axios from 'axios'
import router from '../router/index'
import MarkdownRenderer from '../components/MarkdownRenderer.vue'
import '../assets/highlight-theme.css'

// ==================== 数据定义 ====================
interface ChatMessage {
  role: 'user' | 'ai'
  content: string
  time: string
}

// AI 模型选择（qwen/deepseek/glm）
const selectedModel = ref<'qwen' | 'deepseek' | 'glm'>('qwen')
const userInput = ref('')
const chatHistory = ref<ChatMessage[]>([])
const isLoading = ref(false)
const chatHistoryRef = ref<HTMLElement | null>(null)

// 快速提问选项
const quickQuestions = [
  '推荐一份健康早餐',
  '如何改善失眠',
  '适合高血压患者的药膳',
  '缓解疲劳的食疗方法'
]

// ==================== 生命周期 ====================
onMounted(() => {
  // 从本地存储加载历史记录
  const savedHistory = localStorage.getItem('chatHistory')
  if (savedHistory) {
    try {
      chatHistory.value = JSON.parse(savedHistory)
    } catch (error) {
      console.error('加载历史记录失败:', error)
    }
  }
})

// ==================== 工具函数 ====================
const getCurrentTime = (): string => {
  const now = new Date()
  return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`
}

const scrollToBottom = () => {
  nextTick(() => {
    if (chatHistoryRef.value) {
      chatHistoryRef.value.scrollTop = chatHistoryRef.value.scrollHeight
    }
  })
}

const saveChatHistory = () => {
  try {
    localStorage.setItem('chatHistory', JSON.stringify(chatHistory.value))
  } catch (error) {
    console.error('保存历史记录失败:', error)
  }
}

// ==================== AI 对话功能 ====================
const sendMessage = async (message: string) => {
  if (!message.trim()) {
    MessagePlugin.warning('请输入问题内容')
    return
  }

  // 添加用户消息
  const userMessage: ChatMessage = {
    role: 'user',
    content: message,
    time: getCurrentTime()
  }
  chatHistory.value.push(userMessage)
  scrollToBottom()

  // 清空输入框
  userInput.value = ''
  isLoading.value = true

  try {
    // 调用 AI 接口，传递用户消息和选择的模型
    const response = await axios.get('/chat/ai', {
      params: {
        message: message,
        model: selectedModel.value
      }
    })

    // 添加 AI 回复
    const aiMessage: ChatMessage = {
      role: 'ai',
      content: response.data || '抱歉，我暂时无法回答这个问题。',
      time: getCurrentTime()
    }
    chatHistory.value.push(aiMessage)
    scrollToBottom()
    saveChatHistory()
  } catch (error) {
    console.error('AI 对话失败:', error)
    MessagePlugin.error('AI 对话失败，请检查网络连接或稍后重试')

    // 添加错误提示消息
    const errorMessage: ChatMessage = {
      role: 'ai',
      content: '抱歉，服务暂时不可用，请稍后再试。',
      time: getCurrentTime()
    }
    chatHistory.value.push(errorMessage)
    scrollToBottom()
  } finally {
    isLoading.value = false
  }
}

// ==================== 事件处理 ====================
const handleSend = () => {
  if (userInput.value.trim() && !isLoading.value) {
    sendMessage(userInput.value)
  }
}

const sendQuickQuestion = (question: string) => {
  sendMessage(question)
}

const clearHistory = () => {
  if (chatHistory.value.length === 0) {
    MessagePlugin.info('对话历史已经是空的')
    return
  }

  const confirmed = window.confirm('确定要清空所有对话记录吗？')
  if (confirmed) {
    chatHistory.value = []
    localStorage.removeItem('chatHistory')
    MessagePlugin.success('对话历史已清空')
  }
}

const handleLogo = () => {
  router.push('/')
}
</script>

<style scoped>
/* ==================== 页面容器 ==================== */
.chat-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e8f5e9 100%);
  display: flex;
  flex-direction: column;
}

/* ==================== 头部导航 ==================== */
.head {
  height: 80px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 var(--spacing-xl);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: var(--z-sticky);
}

.logo {
  cursor: pointer;
  font-size: 1.75rem;
  font-weight: 700;
  letter-spacing: 1.5px;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  padding: var(--spacing-sm) var(--spacing-lg);
  border: 2px solid var(--primary-color);
  border-radius: var(--radius-lg);
  transition: all var(--transition-base);
  animation: fadeIn 0.6s ease-out;
}

.logo:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.page-title {
  font-size: var(--text-2xl);
  font-weight: 700;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  margin: 0;
}

/* ==================== 模型选择区域 ==================== */
.mode-switch {
  background: var(--bg-primary);
  padding: var(--spacing-lg);
  margin: var(--spacing-lg) auto 0;
  max-width: 1200px;
  width: 95%;
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-md);
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
  align-items: center;
}

.model-selector {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-md);
  width: 100%;
}

.selector-label {
  font-size: var(--text-lg);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-xs);
}

.model-option {
  display: inline-flex;
  align-items: center;
  gap: var(--spacing-xs);
  font-weight: 500;
}

.mode-description {
  font-size: var(--text-base);
  color: var(--text-secondary);
  font-weight: 500;
  text-align: center;
  padding: var(--spacing-sm) var(--spacing-lg);
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  border-left: 4px solid var(--primary-color);
}

.mode-description span {
  display: inline-flex;
  align-items: center;
  gap: var(--spacing-xs);
}

/* ==================== 对话历史区域 ==================== */
.chat-history {
  flex: 1;
  max-width: 1200px;
  width: 95%;
  margin: var(--spacing-lg) auto;
  padding: var(--spacing-xl);
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-lg);
  overflow-y: auto;
  min-height: 800px;
  max-height: calc(100vh);
}

/* 空状态 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-height: 300px;
  color: var(--text-secondary);
}

.empty-state p {
  font-size: var(--text-xl);
  margin: var(--spacing-lg) 0;
  font-weight: 500;
}

.quick-questions {
  margin-top: var(--spacing-xl);
  text-align: center;
}

.quick-questions h3 {
  font-size: var(--text-lg);
  color: var(--text-primary);
  margin-bottom: var(--spacing-md);
}

.quick-questions .t-button {
  margin: var(--spacing-xs);
}

/* 消息容器 */
.message-wrapper {
  margin-bottom: var(--spacing-lg);
  animation: fadeIn 0.3s ease-out;
}

.message {
  display: flex;
  gap: var(--spacing-md);
  align-items: flex-start;
}

/* 用户消息 */
.user-message {
  flex-direction: row-reverse;
}

.user-message .message-content {
  background: var(--primary-gradient);
  color: var(--text-inverse);
  border-radius: var(--radius-lg) var(--radius-lg) 0 var(--radius-lg);
}

/* AI 消息 */
.ai-message .message-content {
  background: var(--bg-secondary);
  color: var(--text-primary);
  border-radius: var(--radius-lg) var(--radius-lg) var(--radius-lg) 0;
}

/* 消息内容 */
.message-content {
  max-width: 80%;
  padding: var(--spacing-md) var(--spacing-lg);
  box-shadow: var(--shadow-sm);
  word-wrap: break-word;
}

/* 用户消息文本 */
.user-message .message-text {
  font-size: var(--text-base);
  line-height: 1.6;
  white-space: pre-wrap;
}

/* AI消息的Markdown渲染器样式调整 */
.ai-message .message-content :deep(.markdown-renderer) {
  color: var(--text-primary);
}

.ai-message .message-content :deep(.markdown-renderer h1),
.ai-message .message-content :deep(.markdown-renderer h2),
.ai-message .message-content :deep(.markdown-renderer h3),
.ai-message .message-content :deep(.markdown-renderer h4),
.ai-message .message-content :deep(.markdown-renderer h5),
.ai-message .message-content :deep(.markdown-renderer h6) {
  margin-top: 1em;
  margin-bottom: 0.5em;
}

.ai-message .message-content :deep(.markdown-renderer h1:first-child),
.ai-message .message-content :deep(.markdown-renderer h2:first-child),
.ai-message .message-content :deep(.markdown-renderer h3:first-child) {
  margin-top: 0;
}

.ai-message .message-content :deep(.markdown-renderer p:last-child) {
  margin-bottom: 0;
}

.ai-message .message-content :deep(.markdown-renderer pre) {
  margin: 1em 0;
}

.ai-message .message-content :deep(.markdown-renderer blockquote) {
  margin: 1em 0;
}

.message-time {
  font-size: var(--text-xs);
  margin-top: var(--spacing-xs);
  opacity: 0.7;
}

/* 头像 */
.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  box-shadow: var(--shadow-sm);
}

.user-avatar {
  background: var(--primary-gradient);
  color: var(--text-inverse);
}

.ai-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: var(--text-inverse);
}

/* 加载动画 */
.loading-message .message-content {
  padding: var(--spacing-lg);
}

.loading-dots {
  display: flex;
  gap: var(--spacing-xs);
}

.loading-dots span {
  width: 8px;
  height: 8px;
  border-radius: var(--radius-full);
  background: var(--primary-color);
  animation: bounce 1.4s infinite ease-in-out both;
}

.loading-dots span:nth-child(1) {
  animation-delay: -0.32s;
}

.loading-dots span:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes bounce {
  0%,
  80%,
  100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

/* ==================== 输入区域 ==================== */
.input-area {
  max-width: 1200px;
  width: 95%;
  margin: 0 auto var(--spacing-lg);
  background: var(--bg-primary);
  padding: var(--spacing-lg);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-lg);
}

.chat-input {
  width: 100%;
  margin-bottom: var(--spacing-md);
}

:deep(.chat-input .t-textarea__inner) {
  border: 2px solid var(--border-light);
  border-radius: var(--radius-lg);
  font-size: var(--text-base);
  transition: all var(--transition-fast);
}

:deep(.chat-input .t-textarea__inner:focus) {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
}

.input-actions {
  display: flex;
  justify-content: flex-end;
  gap: var(--spacing-md);
}

:deep(.t-button) {
  border-radius: var(--radius-lg);
  font-weight: 500;
  transition: all var(--transition-fast);
}

:deep(.t-button:hover:not(:disabled)) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

/* ==================== 滚动条美化 ==================== */
.chat-history::-webkit-scrollbar {
  width: 8px;
}

.chat-history::-webkit-scrollbar-track {
  background: var(--bg-secondary);
  border-radius: var(--radius-sm);
}

.chat-history::-webkit-scrollbar-thumb {
  background: var(--primary-color);
  border-radius: var(--radius-sm);
}

.chat-history::-webkit-scrollbar-thumb:hover {
  background: var(--primary-dark);
}

/* ==================== 动画效果 ==================== */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 768px) {
  .head {
    padding: 0 var(--spacing-md);
    flex-direction: column;
    height: auto;
    gap: var(--spacing-sm);
    padding-top: var(--spacing-md);
    padding-bottom: var(--spacing-md);
  }

  .logo {
    font-size: 1.25rem;
  }

  .page-title {
    font-size: var(--text-xl);
  }

  .mode-switch {
    width: 90%;
    padding: var(--spacing-md);
  }

  .selector-label {
    font-size: var(--text-base);
  }

  .model-selector :deep(.t-radio-group) {
    flex-direction: column;
    width: 100%;
  }

  .model-selector :deep(.t-radio-button) {
    width: 100%;
    margin-bottom: var(--spacing-xs);
  }

  .mode-description {
    font-size: var(--text-sm);
    padding: var(--spacing-sm);
  }

  .chat-history {
    width: 90%;
    padding: var(--spacing-md);
    max-height: calc(100vh - 450px);
  }

  .message-content {
    max-width: 90%;
  }

  /* 移动端Markdown样式优化 */
  .ai-message .message-content :deep(.markdown-renderer) {
    font-size: 14px;
  }

  .ai-message .message-content :deep(.markdown-renderer pre) {
    font-size: 12px;
  }

  .ai-message .message-content :deep(.markdown-renderer table) {
    font-size: 12px;
  }

  .input-area {
    width: 90%;
  }

  .input-actions {
    flex-direction: column;
  }

  .input-actions .t-button {
    width: 100%;
  }
}

@media (max-width: 480px) {
  .logo {
    font-size: 1.125rem;
    padding: var(--spacing-xs) var(--spacing-md);
  }

  .page-title {
    font-size: var(--text-lg);
  }

  .message-content {
    max-width: 90%;
    padding: var(--spacing-sm) var(--spacing-md);
  }

  .message-avatar {
    width: 32px;
    height: 32px;
  }

  .quick-questions .t-button {
    width: 100%;
    margin: var(--spacing-xs) 0;
  }
}
</style>
