<template>
  <div class="head">
    <!-- 左边 Logo -->
    <span class="logo" @click="handleLogo">药膳云厨</span>
  </div>
  <t-space direction="vertical" style="width: 100%">
    <t-table
      row-key="id"
      :data="tableData"
      :columns="columns"
      :stripe="true"
      :bordered="true"
      :hover="true"
      :table-layout="false"
      :size="'large'"
      :pagination="pagination"
      :show-header="true"
      cell-empty-content="-"
      resizable
      lazy-load
      style=""
    >
      <template #op="slotProps">
        <t-space>
          <t-link theme="primary" @click="handleEdit(slotProps)"> 编辑</t-link>
          <t-link theme="danger" @click="handleEdit(slotProps)"> 查看</t-link>
        </t-space>
      </template>
    </t-table>
  </t-space>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { TableProps } from 'tdesign-vue-next'
import axios from 'axios'
import router from '../router/index'

const queryAticle = async () => {
  const response = await axios.get('http://localhost:9090/article')
  tableData.value = response.data.data.map(
    (item: { id: any; name: any; year: any; area: any }) => ({
      id: item.id, // 或者使用 option 的其他字段作为 label
      name: item.name, // 或者使用 option 的其他字段作为 value
      year: item.year,
      area: item.area
    })
  )
  console.log(tableData.value)
}
// 表格数据
// const tableData = ref([
//   {
//     id:'',
//     name:'',
//     time:'',
//     area:''
//   }
// ])
const tableData = ref([])

const columns = ref([
  {
    colKey: 'id',
    title: 'ID',
    width: '10%'
  },
  {
    colKey: 'name',
    title: '文献名称',
    width: '60%'
  },
  {
    colKey: 'year',
    title: '时间',
    cellEmptyContent: '-',
    width: '10%'
  },
  {
    colKey: 'area',
    title: '地区',
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

onMounted(() => {
  queryAticle()
})

const pagination: TableProps['pagination'] = {
  defaultCurrent: 1,
  defaultPageSize: 5,
  total: 225
}

const handleEdit = (slot: any) => {
  console.log('编辑行:', slot.row)
  // 在这里添加你的编辑逻辑
}

const handleLogo = () => {
  router.push('/')
}
</script>
<style scoped>
/* ==================== 页面容器 ==================== */
:deep(.t-space) {
  background: linear-gradient(135deg, #f5f7fa 0%, #e8f5e9 100%);
  min-height: 100vh;
  padding: 0;
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
  margin-bottom: var(--spacing-xl);
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

/* ==================== 表格容器 ==================== */
:deep(.t-space > div) {
  width: 94%;
  max-width: 1400px;
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
  background: linear-gradient(135deg, var(--accent-color) 0%, var(--accent-dark) 100%);
  color: var(--text-inverse);
  font-weight: 600;
  font-size: var(--text-base);
}

:deep(.t-table__row) {
  transition: all var(--transition-fast);
}

:deep(.t-table__row:hover) {
  background: rgba(59, 130, 246, 0.05);
  transform: scale(1.005);
}

:deep(.t-table__cell) {
  padding: var(--spacing-md);
  font-size: var(--text-base);
}

:deep(.t-link) {
  font-weight: 500;
  transition: all var(--transition-fast);
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--radius-sm);
}

:deep(.t-link:hover) {
  transform: translateX(2px);
  background: rgba(59, 130, 246, 0.1);
}

:deep(.t-link--theme-primary) {
  color: var(--accent-color);
}

:deep(.t-link--theme-danger) {
  color: var(--error-color);
}

/* ==================== 分页样式 ==================== */
:deep(.t-pagination) {
  margin-top: var(--spacing-xl);
  display: flex;
  justify-content: center;
}

:deep(.t-pagination__btn) {
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

:deep(.t-pagination__btn:hover) {
  background: var(--primary-color);
  color: var(--text-inverse);
  transform: translateY(-1px);
}

/* ==================== 响应式设计 ==================== */
@media (max-width: 768px) {
  .head {
    padding: 0 var(--spacing-md);
  }

  .logo {
    font-size: 1.25rem;
    padding: var(--spacing-xs) var(--spacing-md);
  }

  :deep(.t-space > div) {
    width: 95%;
    padding: var(--spacing-md);
  }

  :deep(.t-table__cell) {
    padding: var(--spacing-sm);
    font-size: var(--text-sm);
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
</style>
