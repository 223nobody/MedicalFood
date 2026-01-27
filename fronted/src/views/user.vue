<template>
  <t-space direction="vertical" style="width: 100%; background-color: white">
    <div>
      <t-layout style="width: 100%">
        <t-header>
          <div class="head">
            <!-- 左边 Logo -->
            <span class="logo" @click="handleLogo">药膳云厨</span>
          </div>
        </t-header>
        <t-content style="width: 100%; text-align: center; background-color: white">
          <div class="border-box">
            <span class="user-name"
              >用户名: {{ username }}
              <t-button size="medium" theme="primary" @click="showModal1 = true"
                >修改</t-button
              ></span
            >
            <span class="user-account">账号: {{ account }}</span>
          </div>
          <t-space direction="vertical" style="width: 95%; margin-left: 3%; text-align: center">
            <div class="border-box">
              <p style="font-size: large">地址</p>
              <t-button size="medium" theme="primary" @click="showModal2 = true">新增地址</t-button>
            </div>
            <t-table
              row-key="id"
              :data="addressData"
              :columns="address"
              :stripe="true"
              :bordered="true"
              :hover="true"
              :table-layout="false"
              :size="'small'"
              :pagination="paginationAddress"
              :show-header="true"
              cell-empty-content="-"
              resizable
              lazy-load
              style=""
            >
              <template #op="slotProps">
                <t-space>
                  <t-link theme="primary" @click="handleEdit(slotProps)"> 编辑</t-link>
                  <t-link theme="danger" @click="handleDeleteAddress(slotProps)"> 删除</t-link>
                </t-space>
              </template>
            </t-table>
          </t-space>
        </t-content>
        <t-footer style="background-color: white; width: 100%">
          <t-space direction="vertical" style="width: 95%; margin-left: 3%; text-align: center">
            <div class="border-box"><p style="font-size: large; color: black">订单</p></div>
            <t-table
              row-key="id"
              :data="orderData"
              :columns="order"
              :stripe="true"
              :bordered="true"
              :hover="true"
              :table-layout="false"
              :size="'small'"
              :pagination="paginationOrder"
              :show-header="true"
              cell-empty-content="-"
              resizable
              lazy-load
              style=""
            >
              <template #op="slotProps">
                <t-space>
                  <!-- <t-link theme="primary" @click="handleEditOrder(slotProps)"> 编辑</t-link>
                  <t-link theme="danger" @click="handleDeleteOrder(slotProps)"> 删除</t-link> -->
                  <h3 style="color: blue">{{ slotProps.row.state }}</h3>
                </t-space>
              </template>
            </t-table>
          </t-space>
        </t-footer>
      </t-layout>
      <!-- 悬浮表单 -->
      <div
        v-if="showModal1 && userId != null"
        class="modal-overlay"
        @click.self="showModal1 = false"
      >
        <div class="modal">
          <h2>修改用户名</h2>
          <form @submit.prevent="handleRegister">
            <div class="form-control">
              <label for="username">用户名</label>
              <input type="text" id="username" v-model="user.name" required />
            </div>
            <div style="display: flex; justify-content: space-between; /* 左右两边分开 */">
              <t-button type="submit">修改</t-button>
              <t-button type="button" @click="showModal1 = false">关闭</t-button>
            </div>
          </form>
        </div>
      </div>
      <!-- 悬浮表单 -->
      <div
        v-if="showModal2 && userId != null"
        class="modal-overlay"
        @click.self="showModal2 = false"
      >
        <div class="modal">
          <h2>新增地址</h2>
          <form @submit.prevent="addAddress">
            <div class="form-control">
              <label for="username">地址</label>
              <input type="text" id="address" v-model="newAddress.address" required />
            </div>
            <div class="form-control">
              <label for="username">电话</label>
              <input type="text" id="phone" v-model="newAddress.phone" required />
            </div>
            <div style="display: flex; justify-content: space-between; /* 左右两边分开 */">
              <t-button type="submit">提交</t-button>
              <t-button type="button" @click="showModal2 = false">关闭</t-button>
            </div>
          </form>
        </div>
      </div>
      <!-- 悬浮表单 -->
      <div
        v-if="showModal3 && userId != null"
        class="modal-overlay"
        @click.self="showModal3 = false"
      >
        <div class="modal">
          <h2>修改地址</h2>
          <form @submit.prevent="handleEditAddress">
            <div class="form-control">
              <label for="username">地址</label>
              <input type="text" id="address" v-model="newAddress.address" required />
            </div>
            <div class="form-control">
              <label for="username">电话</label>
              <input type="text" id="phone" v-model="newAddress.phone" required />
            </div>
            <div style="display: flex; justify-content: space-between; /* 左右两边分开 */">
              <t-button type="submit">提交</t-button>
              <t-button type="button" @click="showModal3 = false">关闭</t-button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </t-space>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import type { TableProps } from 'tdesign-vue-next'
import axios from 'axios'

import router from '../router/index'
const handleLogo = () => {
  router.push('/')
}

const queryOrder = async () => {
  const response = await axios.get('http://localhost:9090/order', {
    params: {
      userId: userId
    }
  })
  orderData.value = response.data.data.map(
    (item: {
      id: any
      foodId: any
      number: any
      rq: any
      address: any
      phone: any
      foodName: any
      state: any
    }) => ({
      id: item.id, // 或者使用 option 的其他字段作为 label
      foodId: item.foodId, // 或者使用 option 的其他字段作为 value
      number: item.number,
      rq: item.rq,
      address: item.address, // 或者使用 option 的其他字段作为 value
      phone: item.phone,
      food: item.foodName,
      state: map.myMap.get(item.state)
    })
  )
  orderTotal.value = response.data.total
  console.log(orderData.value)
  console.log(orderTotal.value)
}
const queryAddress = async () => {
  const response = await axios.get('http://localhost:9090/address', {
    params: {
      userId: userId
    }
  })
  addressData.value = response.data.data.map((item: { id: any; address: any; phone: any }) => ({
    id: item.id, // 或者使用 option 的其他字段作为 label
    address: item.address, // 或者使用 option 的其他字段作为 value
    phone: item.phone
  }))
  console.log(addressData.value)
  addressTotal.value = response.data.total
}

const addressData = ref([])
const orderData = ref([])
const map = reactive({
  myMap: new Map()
})
map.myMap.set(1, '商家已经接单')
map.myMap.set(2, '骑手配送中')
map.myMap.set(3, '订单已经送达')
map.myMap.set(4, '订单已经完成')
map.myMap.set(0, '用户已经下单')

const address = ref([
  {
    colKey: 'id',
    title: 'ID',
    width: '10%'
  },
  {
    colKey: 'address',
    title: '地址',
    width: '60%'
  },
  {
    colKey: 'phone',
    title: '电话',
    cellEmptyContent: '-',
    width: '10%'
  },
  {
    align: 'left',
    fixed: 'right',
    width: 120,
    colKey: 'op',
    title: '操作'
  }
])

const order = ref([
  {
    colKey: 'id',
    title: 'ID',
    width: '5%'
  },
  {
    colKey: 'food',
    title: '药膳名',
    width: '30%'
  },
  {
    colKey: 'number',
    title: '数量',
    cellEmptyContent: '-',
    width: '5%'
  },
  {
    colKey: 'rq',
    title: '订单时间',
    width: '10%'
  },
  {
    colKey: 'address',
    title: '地址',
    width: '30%'
  },
  {
    colKey: 'phone',
    title: '电话',
    cellEmptyContent: '-',
    width: '10%'
  },
  {
    align: 'left',
    fixed: 'right',
    width: 120,
    colKey: 'op',
    title: '订单状态'
  }
])

onMounted(() => {
  queryAddress(), queryOrder()
})
const orderTotal = ref()
const addressTotal = ref()

const paginationOrder: TableProps['pagination'] = {
  defaultCurrent: 1,
  defaultPageSize: 5,
  total: orderTotal.value
}
const paginationAddress: TableProps['pagination'] = {
  defaultCurrent: 1,
  defaultPageSize: 5,
  total: addressTotal.value
}

const handleEdit = (slot: any) => {
  showModal3.value = true
  newAddress.value.address = slot.row.address
  newAddress.value.phone = slot.row.phone
  newAddress.value.id = slot.row.id
}

const handleEditAddress = async () => {
  console.log('编辑行')
  // 在这里添加你的编辑逻辑
  const response = await axios.put('http://localhost:9090/address', newAddress.value)
  if (response.data.code == 1) {
    alert('修改成功')
    window.location.reload()
  }
}
const handleDeleteAddress = async (slot: any) => {
  // 使用 confirm 函数弹出警告框
  const isConfirmed = window.confirm('你确定要删除吗？')
  // 如果用户点击“确定”，则执行删除逻辑
  if (isConfirmed) {
    // 在这里添加你的删除逻辑，例如发送一个请求到后端 API
    const response = await axios.delete('http://localhost:9090/address', {
      params: {
        id: slot.row.id
      }
    })
    console.log('执行删除操作')
    alert('删除成功')
    window.location.reload()
  } else {
    // 如果用户点击“取消”，则不执行任何操作
    console.log('删除操作已取消')
  }
}
const handleEditOrder = async (slot: any) => {
  console.log('编辑行:', slot.row)
  // 在这里添加你的编辑逻辑
}
const handleDeleteOrder = async (slot: any) => {
  console.log('删除行:', slot.row)
  // const response = await axios.delete('http://localhost:9090/order', {
  //   params: {
  //     id: slot.row.id
  //   }
  // })
}

let username = localStorage.getItem('username')
const account = localStorage.getItem('account')
const userId = localStorage.getItem('id')

const showModal1 = ref(false)
const showModal2 = ref(false)
const showModal3 = ref(false)

const user = ref({
  id: '',
  name: ''
})
const newAddress = ref({
  id: '',
  userId: '',
  address: '',
  phone: ''
})
const handleRegister = async () => {
  if (userId != null) user.value.id = userId
  console.log('修改用户名请求:', user.value)
  // 在这里添加你的注册逻辑，例如发送一个请求到后端 API
  const response = await axios.put('http://localhost:9090/user', user.value)
  if (response.data.code == 1) {
    alert('修改成功')
    window.location.reload()
    localStorage.setItem('username', user.value.name)
  }
  // 注册成功后关闭表单
  showModal1.value = false
}
const addAddress = async () => {
  if (userId != null) newAddress.value.userId = userId
  console.log('新增地址请求:', newAddress.value)
  const response = await axios.post('http://localhost:9090/address', newAddress.value)
  if (response.data.code == 1) {
    alert('新增成功')
    window.location.reload()
  }
}
</script>

<style scoped>
/* ==================== 页面容器 ==================== */
:deep(.t-space) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e8f5e9 100%);
  min-height: 100vh;
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

/* ==================== 用户信息卡片 ==================== */
.border-box {
  background: var(--bg-primary);
  border: 2px solid var(--primary-color);
  border-radius: var(--radius-xl);
  padding: var(--spacing-xl);
  margin: var(--spacing-xl) auto;
  width: 94%;
  max-width: 1200px;
  box-shadow: var(--shadow-lg);
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all var(--transition-base);
  animation: fadeIn 0.6s ease-out;
}

.border-box:hover {
  border-color: var(--primary-dark);
  box-shadow: var(--shadow-xl);
  transform: translateY(-2px);
}

.user-name,
.user-account {
  font-size: var(--text-xl);
  font-weight: 600;
  color: var(--text-primary);
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.user-name {
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

/* ==================== 表格容器 ==================== */
:deep(.t-space > div) {
  width: 94%;
  max-width: 1200px;
  margin: 0 auto var(--spacing-xl);
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-lg);
  animation: fadeIn 0.8s ease-out;
}

:deep(.t-table) {
  border-radius: var(--radius-lg);
  overflow: hidden;
}

:deep(.t-table__header) {
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-dark) 100%);
  color: var(--text-inverse);
  font-weight: 600;
}

:deep(.t-table__row:hover) {
  background: rgba(16, 185, 129, 0.05);
  transform: scale(1.01);
  transition: all var(--transition-fast);
}

:deep(.t-link) {
  font-weight: 500;
  transition: all var(--transition-fast);
}

:deep(.t-link:hover) {
  transform: translateX(2px);
}

/* ==================== 模态框 ==================== */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--bg-overlay);
  backdrop-filter: blur(4px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: var(--z-modal-backdrop);
  animation: fadeIn var(--transition-base);
}

.modal {
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  padding: var(--spacing-2xl);
  width: 90%;
  max-width: 450px;
  box-shadow: var(--shadow-xl);
  z-index: var(--z-modal);
  animation: slideIn 0.3s ease-out;
}

.modal h2 {
  font-size: var(--text-2xl);
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: var(--spacing-xl);
  text-align: center;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

/* ==================== 表单控件 ==================== */
.form-control {
  margin-bottom: var(--spacing-lg);
}

.form-control label {
  display: block;
  margin-bottom: var(--spacing-sm);
  color: var(--text-secondary);
  font-size: var(--text-sm);
  font-weight: 500;
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

/* ==================== 按钮样式 ==================== */
:deep(.t-button) {
  border-radius: var(--radius-lg);
  font-weight: 500;
  padding: var(--spacing-sm) var(--spacing-lg);
  transition: all var(--transition-fast);
}

:deep(.t-button:hover) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

:deep(.t-button--theme-primary) {
  background: var(--primary-gradient);
}

:deep(.t-button--theme-primary:hover) {
  background: linear-gradient(135deg, #059669 0%, #047857 100%);
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 768px) {
  .head {
    padding: 0 var(--spacing-md);
  }

  .border-box {
    flex-direction: column;
    gap: var(--spacing-md);
    padding: var(--spacing-lg);
  }

  .user-name,
  .user-account {
    font-size: var(--text-lg);
  }

  :deep(.t-space > div) {
    width: 95%;
    padding: var(--spacing-md);
  }

  .modal {
    width: 95%;
    padding: var(--spacing-xl);
  }
}

@media (max-width: 480px) {
  .logo {
    font-size: 1.25rem;
    padding: var(--spacing-xs) var(--spacing-md);
  }

  .border-box {
    padding: var(--spacing-md);
  }

  .user-name,
  .user-account {
    font-size: var(--text-base);
    flex-direction: column;
    text-align: center;
  }
}

/* ==================== 动画 ==================== */
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

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-30px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}
</style>
