<template>
  <div class="myBody">
    <t-layout>
      <t-aside class="myAside"> <img class="myImg" :src="layout.photo" /></t-aside>
      <t-layout>
        <t-content>
          <div class="foodTitle">
            <h2>
              {{ layout.name }}
            </h2>
          </div>
          <div class="introduce detailTop">
            <span class="newTitle"> 药膳介绍: </span>
            <div class="newContent">
              {{ layout.introduce }}
            </div>
          </div>
          <div class="detailTop">
            <span class="newTitle"> 药膳类别: </span>

            <div class="newContent">{{ layout.type }}</div>
          </div>
          <div class="material detailTop">
            <span class="newTitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;材料:</span>
            <div class="newContent">{{ layout.material }}</div>
          </div>
          <div class="method detailTop">
            <span class="newTitle"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;做法: </span>
            <div class="newContent">
              {{ layout.method }}
            </div>
          </div>
        </t-content>
        <t-footer style="text-align: right">
          <t-button theme="warning" size="large" @click="showModal1 = true">点击下单</t-button>
        </t-footer>
      </t-layout>
    </t-layout>
  </div>
  <!-- 悬浮表单 -->
  <div v-if="showModal1 && userId != null" class="modal-overlay" @click.self="showModal1 = false">
    <div class="modal">
      <h2>用户点单</h2>
      <form @submit.prevent="handleOrder">
        <div class="form-control">
          <label for="foodName">药膳名</label>
          <input type="text" id="foodName" v-model="layout.name" required readonly />
        </div>
        <div class="form-control">
          <label for="number">数量</label>
          <input type="number" id="number" v-model="computedNumber" required />
        </div>
        <div class="form-control">
          <label for="address" style="display: flex; justify-content: space-between"
            >送餐地址<t-button
              size="small"
              theme="success"
              @click="
                () => {
                  router.push('/user')
                }
              "
              >添加地址和电话</t-button
            ></label
          >
          <!-- <input type="address" id="address" v-model="order.address" required /> -->
          <t-select
            class="t-select-input"
            type="address"
            id="address"
            v-model="order.address"
            required
            :options="options"
            filterable
          >
          </t-select>
        </div>
        <div class="form-control">
          <label for="phone">电话</label>
          <t-select
            class="t-select-input"
            type="phone"
            id="phone"
            v-model="order.phone"
            required
            :options="options1"
            filterable
          >
          </t-select>
        </div>
        <div style="display: flex; justify-content: space-between; /* 左右两边分开 */">
          <t-button type="submit">提交</t-button>
          <t-button type="button" @click="showModal1 = false">关闭</t-button>
        </div>
      </form>
    </div>
  </div>
</template>
<script setup lang="ts">
import axios from 'axios'
import { MessagePlugin, type CardProps } from 'tdesign-vue-next'
import { onMounted, ref } from 'vue'
import { reactive, toRefs, computed } from 'vue'
import router from '../router'
const options = ref()
const options1 = ref()
const queryAddress = async () => {
  const response = await axios.get('http://localhost:9090/address', {
    params: {
      userId: userId
    }
  })
  options.value = response.data.data.map((item: { id: any; address: any }) => ({
    label: item.address, // 或者使用 option 的其他字段作为 label
    value: item.address // 或者使用 option 的其他字段作为 value
  }))
  options1.value = response.data.data.map((item: { id: any; phone: any }) => ({
    label: item.phone, // 或者使用 option 的其他字段作为 label
    value: item.phone // 或者使用 option 的其他字段作为 value
  }))
  console.log(options.value)
}
const handleOrder = async () => {
  order.value.userId = Number(userId)
  order.value.foodId = layout.value.id
  order.value.number = computedNumber.value
  order.value.state = 0
  console.log(order.value)
  const response = await axios.post('http://localhost:9090/order', order.value)
  if (response.data.code == 1) {
    alert('下单成功')
    showModal1.value = false
  }
}
const clickHandler = () => {
  MessagePlugin.success('操作')
}
const layout = ref({
  id: '',
  name: '',
  photo: '',
  introduce: '',
  material: '',
  source: '',
  type: '',
  method: ''
})
onMounted(() => {
  fetchdata()
  queryAddress()
})
const tableData = ref([])
const fetchdata = async () => {
  const response = await axios.get(`http://localhost:9090/medicatedFood?queryName=${props.param}`)
  console.log(response.data.data[0])
  layout.value = response.data.data[0]
  state.myMap.set(1, '汤菜类')
  state.myMap.set(2, '粥饭类')
  state.myMap.set(3, '茶饮类')
  state.myMap.set(4, '其他类')
  state.myMap.set(5, '糕点类')
  //   infoMessage.value =  response.data.data[0].symptom
  //  const response1 =  await axios.get(`http://localhost:9090/medicatedFood/subHealth?subHealthId=${response.data.data[0].id}`)
  //  layouts.value = response1.data.data
  //  console.log(layouts.value);
  layout.value.type = state.myMap.get(layout.value.type)
}
const props = defineProps({
  // 子组件接收父组件传递过来的值
  param: String
})
const showModal1 = ref(false)
const userId = localStorage.getItem('id')

const order = ref({
  foodId: '',
  userId: 0,
  number: 0,
  address: '',
  phone: '',
  state: 0
})
// 使用 reactive 创建一个 Map 对象
const state = reactive({
  myMap: new Map()
})
// 定义一个响应式变量来存储原始数值
const num = ref({ number: 1 }) // 默认值为 1，确保初始值大于 0

// 创建一个计算属性，确保数值始终大于 0
const computedNumber = computed({
  get() {
    return num.value.number
  },
  set(value) {
    if (value > 0) {
      num.value.number = value
    } else {
      // 如果输入的值不大于 0，可以设置为 1 或其他默认值
      num.value.number = 1
    }
  }
})
</script>
<style scoped>
/* ==================== 页面容器 ==================== */
.myBody {
  background: linear-gradient(135deg, #f5f7fa 0%, #e8f5e9 100%);
  min-height: 100vh;
  padding: var(--spacing-xl);
}

/* ==================== 布局容器 ==================== */
:deep(.t-layout) {
  max-width: 1400px;
  margin: 0 auto;
  background: var(--bg-primary);
  border-radius: var(--radius-xl);
  box-shadow: var(--shadow-xl);
  overflow: hidden;
  animation: fadeIn 0.6s ease-out;
}

/* ==================== 侧边图片区域 ==================== */
.myAside {
  width: 450px;
  height: auto;
  min-height: 400px;
  background: var(--bg-secondary);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-lg);
}

.myImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
  transition: transform var(--transition-base);
}

.myImg:hover {
  transform: scale(1.05);
}

/* ==================== 内容区域 ==================== */
:deep(.t-content) {
  padding: var(--spacing-2xl);
  background: var(--bg-primary);
}

.foodTitle {
  margin-bottom: var(--spacing-xl);
  padding-bottom: var(--spacing-md);
  border-bottom: 3px solid var(--primary-color);
}

.foodTitle h2 {
  font-size: var(--text-3xl);
  font-weight: 700;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  margin: 0;
}

/* ==================== 详情项 ==================== */
.detailTop {
  margin-bottom: var(--spacing-xl);
  padding: var(--spacing-lg);
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  border-left: 4px solid var(--primary-color);
  transition: all var(--transition-base);
}

.detailTop:hover {
  background: rgba(16, 185, 129, 0.05);
  transform: translateX(4px);
  box-shadow: var(--shadow-sm);
}

.newTitle {
  display: inline-block;
  font-size: var(--text-lg);
  font-weight: 600;
  color: var(--primary-color);
  margin-bottom: var(--spacing-sm);
  min-width: 120px;
}

.newContent {
  font-size: var(--text-base);
  color: var(--text-secondary);
  line-height: 1.8;
  margin-top: var(--spacing-sm);
  padding-left: 0;
}

.introduce .newContent,
.material .newContent,
.method .newContent {
  white-space: pre-wrap;
  word-wrap: break-word;
}

/* ==================== 页脚按钮 ==================== */
:deep(.t-footer) {
  padding: var(--spacing-xl);
  background: var(--bg-secondary);
  border-top: 1px solid var(--border-light);
  display: flex;
  justify-content: flex-end;
}

:deep(.t-button--theme-warning) {
  background: linear-gradient(135deg, var(--secondary-color) 0%, var(--secondary-dark) 100%);
  color: var(--text-inverse);
  padding: var(--spacing-md) var(--spacing-2xl);
  font-size: var(--text-lg);
  font-weight: 600;
  border-radius: var(--radius-lg);
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);
  transition: all var(--transition-base);
}

:deep(.t-button--theme-warning:hover) {
  background: linear-gradient(135deg, var(--secondary-dark) 0%, #b45309 100%);
  box-shadow: 0 6px 20px rgba(245, 158, 11, 0.4);
  transform: translateY(-2px);
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
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
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

.form-control input,
.t-select-input {
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

.form-control input:focus,
.t-select-input:focus {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
}

.form-control input[readonly] {
  background: var(--bg-secondary);
  cursor: not-allowed;
}

/* ==================== 按钮组 ==================== */
.modal form > div:last-child {
  display: flex;
  gap: var(--spacing-md);
  margin-top: var(--spacing-xl);
}

:deep(.t-button) {
  flex: 1;
  padding: var(--spacing-md);
  border-radius: var(--radius-lg);
  font-weight: 500;
  transition: all var(--transition-fast);
}

:deep(.t-button:hover) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 1024px) {
  :deep(.t-layout) {
    flex-direction: column;
  }

  .myAside {
    width: 100%;
    height: 350px;
  }

  :deep(.t-content) {
    padding: var(--spacing-xl);
  }
}

@media (max-width: 768px) {
  .myBody {
    padding: var(--spacing-md);
  }

  :deep(.t-content) {
    padding: var(--spacing-lg);
  }

  .foodTitle h2 {
    font-size: var(--text-2xl);
  }

  .detailTop {
    padding: var(--spacing-md);
  }

  .newTitle {
    font-size: var(--text-base);
    min-width: 100px;
  }

  .newContent {
    font-size: var(--text-sm);
  }

  .modal {
    width: 95%;
    padding: var(--spacing-xl);
  }
}

@media (max-width: 480px) {
  .myAside {
    height: 250px;
  }

  .foodTitle h2 {
    font-size: var(--text-xl);
  }

  .detailTop {
    padding: var(--spacing-sm);
  }

  .newTitle {
    font-size: var(--text-sm);
    display: block;
    margin-bottom: var(--spacing-xs);
  }

  :deep(.t-button--theme-warning) {
    padding: var(--spacing-sm) var(--spacing-lg);
    font-size: var(--text-base);
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

/* ==================== 滚动条美化 ==================== */
.modal::-webkit-scrollbar {
  width: 6px;
}

.modal::-webkit-scrollbar-track {
  background: var(--bg-secondary);
  border-radius: var(--radius-sm);
}

.modal::-webkit-scrollbar-thumb {
  background: var(--primary-color);
  border-radius: var(--radius-sm);
}

.modal::-webkit-scrollbar-thumb:hover {
  background: var(--primary-dark);
}
</style>
