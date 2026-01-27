<template>
  <div>
    <!-- 删除按钮 -->
    <button @click="handleDelete">删除</button>
  </div>
</template>

<script setup>
const handleDelete = () => {
  // 使用 confirm 函数弹出警告框
  const isConfirmed = window.confirm('你确定要删除吗？')

  // 如果用户点击“确定”，则执行删除逻辑
  if (isConfirmed) {
    // 在这里添加你的删除逻辑，例如发送一个请求到后端 API
    console.log('执行删除操作')
    alert('删除成功')
  } else {
    // 如果用户点击“取消”，则不执行任何操作
    console.log('删除操作已取消')
  }
}
</script>
