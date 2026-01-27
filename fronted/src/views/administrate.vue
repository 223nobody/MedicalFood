<template>
  <t-space
    direction="vertical"
    style="width: 95%; margin-left: 3%; text-align: center"
    v-if="account == 'admin'"
  >
    <div class="border-box">
      <p style="font-size: large; color: black">订单</p>
      <p>订单状态 0-用户已经下单 1-商家已经接单 2-骑手配送中 3-订单已经送达 4-订单已经完成</p>
    </div>
    <t-table
      row-key="id"
      :data="orderData"
      :columns="order"
      :stripe="true"
      :bordered="true"
      :hover="true"
      :table-layout="false"
      :size="'medium'"
      :pagination="paginationOrder"
      :show-header="true"
      cell-empty-content="-"
      resizable
      lazy-load
      style=""
    >
      <template #op="slotProps">
        <t-space>
          <span style="font-size: large; color: green">{{ slotProps.row.state }}</span>
          <input
            v-model="slotProps.row.inputData"
            style="max-width: 150px; max-height: 50px"
            placeholder="请输入修改后的状态"
          />
          <t-link theme="primary" @click="confirmChange(slotProps)"> 修改订单状态</t-link>
        </t-space>
      </template>
    </t-table>
  </t-space>
</template>
<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { TableProps } from 'tdesign-vue-next'
import axios from 'axios'
import router from '../router/index'

const account = localStorage.getItem('account')

const queryOrder = async () => {
  const response = await axios.get('http://localhost:9090/order')
  console.log(response.data)
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
      inputData: any
      userId: any
    }) => ({
      id: item.id, // 或者使用 option 的其他字段作为 label
      foodId: item.foodId, // 或者使用 option 的其他字段作为 value
      number: item.number,
      rq: item.rq,
      address: item.address, // 或者使用 option 的其他字段作为 value
      phone: item.phone,
      food: item.foodName,
      state: map.myMap.get(item.state),
      userId: item.userId
    })
  )
  orderTotal.value = response.data.total
  console.log(orderData.value)
  console.log(orderTotal.value)
}
// 使用 reactive 创建一个 Map 对象
const map = reactive({
  myMap: new Map()
})
map.myMap.set(1, '商家已经接单')
map.myMap.set(2, '骑手配送中')
map.myMap.set(3, '订单已经送达')
map.myMap.set(4, '订单已经完成')
map.myMap.set(0, '用户已经下单')

const orderData = ref([])

const order = ref([
  {
    colKey: 'id',
    title: 'ID',
    width: '5%'
  },
  {
    colKey: 'userId',
    title: '用户id',
    width: '5%'
  },
  {
    colKey: 'food',
    title: '药膳名',
    width: '15%'
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
    width: '20%'
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
    width: '30%',
    colKey: 'op',
    title: '订单状态'
  }
])

onMounted(() => {
  queryOrder()
})
const inputData = ref()
const orderTotal = ref()
const paginationOrder: TableProps['pagination'] = {
  defaultCurrent: 1,
  defaultPageSize: 5,
  total: 0
}
paginationOrder.total = orderTotal.value

// 定义一个响应式变量来存储用户的选择
const userConfirmed = ref(false)

// 定义一个函数来处理确认修改的逻辑
const confirmChange = async (slot: any) => {
  // 使用window.confirm弹出确认对话框
  const confirmed = window.confirm('是否确认修改？')

  if (confirmed) {
    alert('修改已确认！')
    // 在这里添加你的修改逻辑代码
    slot.row.state = slot.row.inputData
    console.log(slot.row)
    const response = await axios.put('http://localhost:9090/order', slot.row)
    if (response.data.code == 1) {
      alert('修改成功')
      window.location.reload()
    } else alert('修改失败')
  } else {
    alert('修改已取消！')
  }
}
</script>
<style scoped>
.head {
  height: 70px;
  background-color: white;
  display: flex;
  justify-content: space-between; /* 左右两边分开 */
  align-items: center; /* 垂直居中 */
  padding: 0 20px; /* 给左右两边增加一点内边距 */
}
.logo {
  cursor: pointer;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  font-family: 'Helvetica', sans-serif; /* 使用现代字体 */
  color: #3498db; /* 品牌色彩 */
  font-size: 2em; /* 字体大小 */
  font-weight: bold; /* 加粗 */
  letter-spacing: 2px; /* 字间距 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); /* 文本阴影 */
  background: linear-gradient(to right, #3498db, #9b59b6); /* 渐变背景 */
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent; /* 使文本颜色透明，以显示背景渐变 */
  animation: moveIn 1s ease-out forwards; /* 动画 */
  border: 3px solid #3498db; /* 边框颜色和大小 */
  border-radius: 10px; /* 边框圆角 */
  padding: 10px 20px; /* 内边距 */
}
/* 动画效果 */
@keyframes moveIn {
  0% {
    transform: translateY(-20px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}
.border-box {
  border: 2px solid #007bff; /* 蓝色边框 */
  padding: 15px; /* 内边距 */
  border-radius: 8px; /* 边框圆角 */
  background-color: #f8f9fa; /* 浅灰色背景 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 阴影效果 */
  margin-left: 4%;
  margin-top: 20px; /* 外边距 */
  margin-bottom: 20px;
  transition: border-color 0.3s ease; /* 边框颜色变化的过渡效果 */
  width: 88%;

  display: flex;
  justify-content: space-between;
  align-items: center;
  /* flex-direction: column; */
  margin-top: 20px;
}

.border-box:hover {
  border-color: #0056b3; /* 鼠标悬停时边框颜色变深 */
}

.user-name,
.user-account {
  font-size: 1.2em;
  color: #3498db;
  font-weight: 500;
  margin: 5px 0;
}
</style>
