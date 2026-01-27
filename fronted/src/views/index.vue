<template>
  <div class="indexBody">
    <div class="head">
      <!-- 左边 Logo -->
      <span class="logo" @click="handleLogo">药膳云厨</span>

      <!-- 右边的选择框、输入框和按钮 -->
      <div class="right-content">
        <span>
          <t-select v-model="selectedValue" class="selectys">
            <t-option key="food" label="药膳" value="food" />
            <t-option key="health" label="亚健康状态" value="health"></t-option>
          </t-select>
        </span>
        <div class="separator"></div>
        <span class="queryinput">
          <t-select
            v-if="selectedValue === 'food'"
            filterable
            placeholder=""
            v-model="queryparam"
            :options="FoodOption"
          >
          </t-select>
          <t-select
            v-else
            :popup-props="{ overlayClassName: 'custom-select-dropdown' }"
            filterable
            clearable
            placeholder=""
            v-model="queryparam"
            :options="YjkOption"
          >
          </t-select>
          <t-button theme="default" type="submit" @click="handleQuery" style="border-radius: 20%">
            查询
          </t-button>
        </span>
      </div>
      <!-- 右边的用户 -->
      <div>
        <t-button theme="primary" v-if="login != 'true'" @click="Login()" size="large"
          >登录</t-button
        >
        <div v-else style="display: flex">
          <t-button theme="danger" style="margin: 5px" size="large" @click="Logout()"
            >登出</t-button
          >
          <div style="margin-top: 9px" @click="toUser()">
            <span>用户名：{{ username }}</span>
            <icon name="user-circle" size="30px" style="margin-left: 8px" />
          </div>
        </div>
      </div>
    </div>
    <div v-if="Isquery">
      <yjkquery
        v-if="selectedValue === 'health'"
        :param="queryparam"
        :key="key"
        @update="updateYjk"
      >
      </yjkquery>
      <foodquery v-else :param="queryparam" :key="keyNew"></foodquery>
    </div>
    <div v-else>
      <div v-if="id === 0">
        <div>
          <t-layout>
            <t-layout class="t-layout">
              <t-content class="t-content">
                <div class="photo">
                  <h1 class="photo-title">
                    <span>推荐药膳展示</span>
                  </h1>
                  <t-swiper class="tdesign-demo-block--swiper" :duration="300" :interval="2000">
                    <t-swiper-item v-for="(item, index) in items" :key="item">
                      <div class="demo-item">
                        <img :src="item.image" alt="Swiper Image" class="swiper-image" />
                      </div>
                    </t-swiper-item>
                  </t-swiper>
                </div>
              </t-content>
              <t-aside class="t-aside">
                <t-card :title="title1" header-bordered class="t-card">
                  <p v-for="(item, index) in yjk" :key="index" class="fondModify">
                    <span style="cursor: pointer" @click="handlecjyjk(item)"> {{ item.name }}</span>
                  </p>
                </t-card>
                <t-card :title="title2" header-bordered class="t-card">
                  <p v-for="(item, index) in ysgx" :key="index" class="fondModify">
                    <span @click="handleysgx(item)" style="cursor: pointer">
                      {{ item.benefit }}</span
                    >
                  </p>
                </t-card>
              </t-aside>
            </t-layout>
            <t-footer style="background-color: white">
              <div class="t-footer">
                <div class="footer-div" @click="goToDocumentation">
                  <t-button shape="circle" theme="primary" class="footer-button">
                    <icon name="book" size="30px" />
                  </t-button>
                  <h2>文 &nbsp;献</h2>
                </div>

                <div class="footer-div">
                  <t-button shape="circle" theme="primary" class="footer-button">
                    <icon name="hospital" size="30px" />
                  </t-button>
                  <h2>亚健康</h2>
                </div>
                <div class="footer-div">
                  <t-button shape="circle" theme="primary" class="footer-button">
                    <icon name="noodle" size="30px" />
                  </t-button>
                  <h2>药 &nbsp;膳</h2>
                </div>
                <div class="footer-div">
                  <t-button shape="circle" theme="primary" class="footer-button" @click="toUser()">
                    <icon name="user" size="30px" />
                  </t-button>
                  <h2>用 &nbsp;户</h2>
                </div>
              </div>
            </t-footer>
          </t-layout>
        </div>
      </div>
      <div v-else>
        <ys-gx :param="id" @update="updateYjk" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { MessagePlugin, type CardProps, type TabsProps } from 'tdesign-vue-next'
import { Icon } from 'tdesign-icons-vue-next'
import { onMounted, ref, watch, computed } from 'vue'
import axios from 'axios'
import foodquery from './foodquery.vue'
import yjkquery from './Yjkquery.vue'
import ysGx from './ysgx.vue'
import router from '../router/index'

// ==================== 性能优化：防抖函数 ====================
const debounce = <T extends (...args: any[]) => any>(fn: T, delay: number) => {
  let timeoutId: ReturnType<typeof setTimeout> | null = null
  return (...args: Parameters<T>) => {
    if (timeoutId) clearTimeout(timeoutId)
    timeoutId = setTimeout(() => fn(...args), delay)
  }
}
const ysUpdate = async () => []
const items = [
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 1'
  },
  {
    image: 'https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index1.webp',
    text: 'Item 2'
  },
  {
    image: 'https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index2.jpeg',
    text: 'Item 3'
  },
  {
    image: 'https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index3.webp',
    text: 'Item 4'
  },
  {
    image: 'https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index4.webp',
    text: 'Item 5'
  },
  {
    image: 'https://1162653280-1324338617.cos.ap-guangzhou.myqcloud.com/index5.jpg',
    text: 'Item 6'
  }
]
const key = ref(0)
const selectedValue = ref('food') // 默认选中的值
const Show_yjk = ref('')
const yjk = ref([
  {
    name: '',
    benefit: ''
  }
])
const ysgx = ref([
  {
    benefit: ''
  }
])
const queryparam = ref('')
const value = ref<TabsProps['value']>('first')
const theme = ref<TabsProps['theme']>('normal')
const YjkOption = ref()
const FoodOption = ref()
const YsOption = ref()
const Isquery = ref(false)
const keyNew = ref(0)
const id = ref(0)
const isLoading = ref(false)
const hasError = ref(false)

// ==================== 生命周期：组件挂载时初始化数据 ====================
onMounted(async () => {
  isLoading.value = true
  try {
    // 并行加载所有初始数据，提升性能
    await Promise.all([getYjk(), fetchYjkOption(), fetchFoodOption(), handlerChange()])
  } catch (error) {
    console.error('初始化数据失败:', error)
    hasError.value = true
    MessagePlugin.error('数据加载失败，请刷新页面重试')
  } finally {
    isLoading.value = false
  }
})
watch(selectedValue, async (newVal, oldVal) => {
  if (queryparam.value !== '') {
    Isquery.value = false
    queryparam.value = ''
  }
  if (IsUpdate.value === '1') {
    queryparam.value = huichuan.value
    await handleQuery()
    IsUpdate.value = ''
  }
  id.value = 0
  // 在这里执行你需要的逻辑
})
const IsUpdate = ref('')
const huichuan = ref('')
const updateYjk = async (data: any) => {
  queryparam.value = ''

  selectedValue.value = 'food'
  huichuan.value = data
  IsUpdate.value = '1'
  if (selectedValue.value === 'food') {
    queryparam.value = huichuan.value
    await handleQuery()
    IsUpdate.value = ''
  }
}
const handlecjyjk = (item: any) => {
  selectedValue.value = 'health'
  IsUpdate.value = '1'
  huichuan.value = item.name
  handleQuery()
}
const handleysgx = async (item: any) => {
  console.log(item.id)
  id.value = item.id
  // const response = await axios.get(`http://localhost:9090/medicatedFood/benefit?benefitId=${item.id}`)
  // console.log(response.data.data);
  //为了跳转页面
}
const handleLogo = async () => {
  Isquery.value = false
  queryparam.value = ''
  id.value = 0
  selectedValue.value = 'food'
}
// ==================== API请求：获取亚健康选项 ====================
const fetchYjkOption = async () => {
  try {
    const response = await axios.get('http://localhost:9090/subHealth?queryName=')
    if (response.data && response.data.data) {
      YjkOption.value = response.data.data.map((option: any) => ({
        label: option.name,
        value: option.name
      }))
    }
  } catch (error) {
    console.error('获取亚健康选项失败:', error)
    MessagePlugin.warning('亚健康选项加载失败')
  }
}

// ==================== API请求：获取药膳选项 ====================
const fetchFoodOption = async () => {
  try {
    const response = await axios.get('http://localhost:9090/medicatedFood?queryName')
    if (response.data && response.data.data) {
      FoodOption.value = response.data.data.map((option: any) => ({
        label: option.name,
        value: option.name
      }))
    }
  } catch (error) {
    console.error('获取药膳选项失败:', error)
    MessagePlugin.warning('药膳选项加载失败')
  }
}
const handleQuery = async () => {
  id.value = 0
  if (queryparam.value === '') {
    Isquery.value = false
  } else {
    Isquery.value = true
    if (selectedValue.value === 'food') {
      keyNew.value++
      // router.push({path:'/foodquery'})
    } else {
      console.log(queryparam.value)

      key.value++

      // router.push({ path: '/yjkquery' })
    }
  }
}
// ==================== API请求：获取药膳功效 ====================
const handlerChange = async () => {
  try {
    const response = await axios.get('http://localhost:9090/benefit')
    if (response.data && response.data.data) {
      ysgx.value = response.data.data
    }
  } catch (error) {
    console.error('获取药膳功效失败:', error)
    MessagePlugin.warning('药膳功效加载失败')
  }
}

// ==================== API请求：获取常见亚健康状态 ====================
const getYjk = async () => {
  if (Show_yjk.value === '') {
    try {
      const response = await axios.get('http://localhost:9090/subHealth/frequent')
      if (response.data && response.data.data) {
        yjk.value = response.data.data
      }
    } catch (error) {
      console.error('获取常见亚健康状态失败:', error)
      MessagePlugin.warning('亚健康状态加载失败')
    }
  }
}
//卡片
const clickHandler = () => {
  MessagePlugin.success('操作')
}
const title1: CardProps['title'] = '常见的亚健康状态'
const title2: CardProps['title'] = '药膳功效'

const goToDocumentation = () => {
  router.push('/article')
}

const login = localStorage.getItem('isLoggedIn')
const username = localStorage.getItem('username')
const account = localStorage.getItem('account')

const Logout = () => {
  localStorage.clear()
  // 刷新当前页面
  window.location.reload()
}
const Login = () => {
  router.push('/login')
}

const toUser = () => {
  if (account != 'admin') router.push('/user')
  else router.push('/administrate')
}
</script>

<style scoped>
/* ==================== 页面容器 ==================== */
.indexBody {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e8f5e9 100%);
  margin: 0;
  padding: 0;
}

/* ==================== 头部导航栏 ==================== */
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
  transition: all var(--transition-base);
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
  position: relative;
  overflow: hidden;
}

.logo::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.5s;
}

.logo:hover::before {
  left: 100%;
}

.logo:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

/* ==================== 搜索栏 ==================== */
.right-content {
  display: flex;
  align-items: center;
  background: var(--bg-primary);
  border: 2px solid var(--primary-color);
  border-radius: var(--radius-xl);
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(16, 185, 129, 0.15);
  transition: all var(--transition-base);
  max-width: 600px;
  width: 45%;
  min-width: 400px;
}

.right-content:focus-within {
  box-shadow: 0 4px 16px rgba(16, 185, 129, 0.25);
  border-color: var(--primary-dark);
  transform: translateY(-1px);
}

.separator {
  width: 1px;
  height: 24px;
  background: var(--border-light);
  margin: 0;
}

.selectys {
  min-width: 110px;
  border: none;
  background: transparent;
}

:deep(.selectys .t-input) {
  border: none;
  background: transparent;
  font-weight: 500;
  color: var(--text-primary);
}

.queryinput {
  flex: 1;
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  padding: 0 var(--spacing-sm);
}

:deep(.queryinput .t-input) {
  border: none;
  background: transparent;
  font-size: var(--text-base);
}

:deep(.t-input) {
  border: none;
  outline: none;
  transition: all var(--transition-fast);
}

:deep(.t-input--focused) {
  box-shadow: none;
}

:deep(.t-button) {
  border-radius: var(--radius-md);
  font-weight: 500;
  transition: all var(--transition-fast);
}

:deep(.t-button:hover) {
  transform: translateY(-1px);
}

/* ==================== 用户区域 ==================== */
.head > div:last-child {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.head > div:last-child > div {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  padding: var(--spacing-sm) var(--spacing-md);
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  cursor: pointer;
  transition: all var(--transition-base);
}

.head > div:last-child > div:hover {
  background: var(--primary-color);
  color: var(--text-inverse);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.head > div:last-child span {
  font-weight: 500;
  color: var(--text-primary);
  transition: color var(--transition-fast);
}

.head > div:last-child > div:hover span {
  color: var(--text-inverse);
}

/* ==================== 主内容区域 ==================== */
.t-layout {
  display: flex;
  gap: var(--spacing-lg);
  padding: var(--spacing-xl);
  max-width: 1400px;
  margin: 0 auto;
}

.t-content {
  flex: 1;
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-lg);
  border: 1px solid var(--border-light);
  transition: all var(--transition-base);
}

.t-aside {
  width: 380px;
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

/* ==================== 轮播图区域 ==================== */
.photo {
  width: 100%;
  margin: 0 auto var(--spacing-xl);
}

.photo-title {
  text-align: center;
  margin-bottom: var(--spacing-xl);
  position: relative;
  padding-bottom: var(--spacing-md);
}

.photo-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 3px;
  background: var(--primary-gradient);
  border-radius: var(--radius-full);
}

.photo-title span {
  font-size: var(--text-3xl);
  font-weight: 700;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.tdesign-demo-block--swiper {
  width: 100%;
  height: 450px;
  border-radius: var(--radius-xl);
  overflow: hidden;
  box-shadow: var(--shadow-xl);
}

.swiper-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.demo-item:hover .swiper-image {
  transform: scale(1.05);
}

/* ==================== 侧边栏卡片 ==================== */
.t-card {
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  max-height: 420px;
  overflow-y: auto;
  transition: all var(--transition-base);
}

.t-card:hover {
  box-shadow: var(--shadow-lg);
}

:deep(.t-card__header) {
  font-size: var(--text-xl);
  font-weight: 600;
  color: var(--text-primary);
  padding-bottom: var(--spacing-md);
  border-bottom: 2px solid var(--primary-color);
  margin-bottom: var(--spacing-md);
}

.fondModify {
  padding: var(--spacing-sm) var(--spacing-md);
  margin-bottom: var(--spacing-xs);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
  font-size: var(--text-base);
  font-weight: 500;
  color: var(--text-secondary);
  background: var(--bg-secondary);
  border: 1px solid transparent;
  text-align: center;
}

.fondModify:hover {
  background: var(--primary-color);
  color: var(--text-inverse);
  transform: translateX(4px);
  border-color: var(--primary-dark);
  box-shadow: var(--shadow-sm);
}

.fondModify span {
  display: inline-block;
  width: 100%;
}

/* ==================== 页脚 ==================== */
.t-footer {
  background: var(--bg-primary);
  padding: var(--spacing-2xl) 0;
  margin-top: var(--spacing-xl);
  border-top: 1px solid var(--border-light);
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);
}

.t-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: var(--spacing-2xl);
  flex-wrap: wrap;
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-2xl);
}

.footer-div {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-md);
  cursor: pointer;
  transition: all var(--transition-base);
  padding: var(--spacing-lg);
  border-radius: var(--radius-xl);
  position: relative;
}

.footer-div:hover {
  background: var(--bg-secondary);
  transform: translateY(-8px);
}

.footer-button {
  width: 80px;
  height: 80px;
  border-radius: var(--radius-full);
  background: var(--primary-gradient);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: var(--shadow-lg);
  transition: all var(--transition-base);
  border: 3px solid var(--bg-primary);
}

.footer-div:hover .footer-button {
  transform: scale(1.15) rotate(5deg);
  box-shadow: 0 12px 24px rgba(16, 185, 129, 0.4);
}

.footer-div h2 {
  font-size: var(--text-xl);
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
  position: static;
  width: auto;
  transition: color var(--transition-fast);
}

.footer-div:hover h2 {
  color: var(--primary-color);
}

/* ==================== 响应式设计 ==================== */

/* 大屏幕优化 (1400px+) */
@media (min-width: 1400px) {
  .t-layout {
    max-width: 1600px;
  }

  .t-content {
    padding: var(--spacing-2xl);
  }

  .tdesign-demo-block--swiper {
    height: 500px;
  }
}

/* 平板横屏 (1024px - 1200px) */
@media (max-width: 1200px) {
  .t-layout {
    flex-direction: column;
    gap: var(--spacing-md);
  }

  .t-aside {
    width: 100%;
    flex-direction: row;
    gap: var(--spacing-md);
  }

  .t-card {
    flex: 1;
    max-height: 350px;
    min-width: 280px;
  }

  .right-content {
    width: 50%;
    min-width: 350px;
  }
}

/* 平板竖屏 (768px - 1024px) */
@media (max-width: 1024px) {
  .head {
    padding: 0 var(--spacing-lg);
  }

  .t-layout {
    padding: var(--spacing-lg);
  }

  .tdesign-demo-block--swiper {
    height: 380px;
  }

  .footer-button {
    width: 70px;
    height: 70px;
  }
}

/* 手机横屏和小平板 (640px - 768px) */
@media (max-width: 768px) {
  .head {
    height: auto;
    flex-direction: column;
    padding: var(--spacing-md);
    gap: var(--spacing-md);
  }

  .logo {
    font-size: 1.5rem;
  }

  .right-content {
    width: 100%;
    min-width: auto;
    max-width: 100%;
    flex-wrap: wrap;
  }

  .queryinput {
    flex: 1;
    min-width: 200px;
  }

  .t-layout {
    padding: var(--spacing-md);
    gap: var(--spacing-md);
  }

  .t-aside {
    flex-direction: column;
    gap: var(--spacing-md);
  }

  .t-card {
    max-height: 300px;
  }

  .tdesign-demo-block--swiper {
    height: 300px;
  }

  .t-footer {
    gap: var(--spacing-lg);
    padding: var(--spacing-xl);
  }

  .footer-button {
    width: 60px;
    height: 60px;
  }

  .footer-div h2 {
    font-size: var(--text-lg);
  }

  .fondModify {
    font-size: var(--text-sm);
    padding: var(--spacing-xs) var(--spacing-sm);
  }
}

/* 手机竖屏 (480px - 640px) */
@media (max-width: 640px) {
  .head {
    padding: var(--spacing-sm);
  }

  .logo {
    font-size: 1.25rem;
    padding: var(--spacing-xs) var(--spacing-md);
    letter-spacing: 1px;
  }

  .right-content {
    padding: var(--spacing-xs);
  }

  .selectys {
    min-width: 90px;
    font-size: var(--text-sm);
  }

  .t-layout {
    padding: var(--spacing-sm);
  }

  .t-content,
  .t-card {
    padding: var(--spacing-md);
  }

  .photo-title span {
    font-size: var(--text-2xl);
  }

  .tdesign-demo-block--swiper {
    height: 250px;
  }

  .footer-button {
    width: 55px;
    height: 55px;
  }

  .footer-div {
    padding: var(--spacing-md);
  }

  .footer-div h2 {
    font-size: var(--text-base);
  }
}

/* 小屏手机 (< 480px) */
@media (max-width: 480px) {
  .logo {
    font-size: 1.125rem;
    padding: 6px var(--spacing-sm);
  }

  .right-content {
    flex-direction: column;
    gap: var(--spacing-xs);
  }

  .selectys {
    min-width: 100%;
  }

  .queryinput {
    width: 100%;
    flex-direction: column;
    gap: var(--spacing-xs);
  }

  :deep(.queryinput .t-select),
  :deep(.queryinput .t-button) {
    width: 100%;
  }

  .separator {
    display: none;
  }

  .t-content,
  .t-card {
    padding: var(--spacing-sm);
  }

  .photo-title span {
    font-size: var(--text-xl);
  }

  .tdesign-demo-block--swiper {
    height: 220px;
  }

  .t-footer {
    padding: var(--spacing-lg) var(--spacing-sm);
  }

  .footer-button {
    width: 48px;
    height: 48px;
  }

  .footer-div h2 {
    font-size: var(--text-sm);
  }

  .fondModify {
    font-size: 0.8125rem;
  }
}

/* 超小屏设备 (< 360px) */
@media (max-width: 360px) {
  .logo {
    font-size: 1rem;
  }

  .photo-title span {
    font-size: var(--text-lg);
  }

  .tdesign-demo-block--swiper {
    height: 200px;
  }

  .footer-button {
    width: 42px;
    height: 42px;
  }

  .footer-div h2 {
    font-size: 0.8125rem;
  }
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

.t-content,
.t-card,
.footer-div {
  animation: fadeIn 0.6s ease-out;
}

/* ==================== 滚动条美化 ==================== */
.t-card::-webkit-scrollbar {
  width: 6px;
}

.t-card::-webkit-scrollbar-track {
  background: var(--bg-secondary);
  border-radius: var(--radius-sm);
}

.t-card::-webkit-scrollbar-thumb {
  background: var(--primary-color);
  border-radius: var(--radius-sm);
}

.t-card::-webkit-scrollbar-thumb:hover {
  background: var(--primary-dark);
}
</style>
