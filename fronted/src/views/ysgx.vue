<template>
  <h1 style="text-align: center">相关药膳推荐</h1>
  <div v-for="(layout, index) in layouts" :key="index">
    <t-layout class="myLayout">
      <t-aside class="myAside">
        <img class="myImage" :src="layout.photo" alt="" />
      </t-aside>
      <t-layout class="layout-container">
        <t-content class="myContent">{{ layout.name }}</t-content>
        <t-footer class="myFooter">
          <span class="tag" @click="ljxq(layout.name)"> 了解详情 </span>
        </t-footer>
      </t-layout>
    </t-layout>
  </div>
</template>
<script setup lang="tsx">
import axios from 'axios'
import { MessagePlugin, type CardProps } from 'tdesign-vue-next'
import { onMounted, ref } from 'vue'
const clickHandler = () => {
  MessagePlugin.success('操作')
}
const title = ref('')
const infoMessage = ref('')
const layouts = ref([
  {
    id: '',
    name: '',
    photo: '',
    introduce: '',
    material: '',
    source: '',
    type: '',
    method: ''
  }
])
onMounted(() => {
  fetchdata()
})
const emit = defineEmits(['update'])
const ljxq = async (data: any) => {
  emit('update', data)
}
const tableData = ref([])
const fetchdata = async () => {
  const response = await axios.get(
    `http://localhost:9090/medicatedFood/benefit?benefitId=${props.param}`
  )
  console.log(response.data.data)
  layouts.value = response.data.data
  // const response = await axios.get(`http://localhost:9090/subHealth?queryName=${props.param}`)
  //     console.log(response.data.data[0]);
  //     title.value =   response.data.data[0].name
  //     infoMessage.value =  response.data.data[0].symptom
  //    const response1 =  await axios.get(`http://localhost:9090/medicatedFood/subHealth?subHealthId=${response.data.data[0].id}`)
  //    layouts.value = response1.data.data
  //    console.log(layouts.value);
}
const props = defineProps({
  // 子组件接收父组件传递过来的值
  param: Number
})
</script>
<style scoped>
.TitleCard {
  width: 80%;

  margin: auto;
  margin-top: 30px;
}
.myLayout {
  margin-left: 200px;
  display: flex;
  justify-content: center; /* 水平居中 */
  margin-top: 20px;
  border: 2px solid #007bff; /* 蓝色边框 */
  padding: 5px; /* 内边距 */
  border-radius: 8px; /* 边框圆角 */
  width: 76%;
}
.myImage {
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  /* width: 250px;
  height: 250px; */
  width: 100%;
  height: 100%;
}
.myContent {
  border-top-right-radius: 10px;
  font-size: 40px;
  font-family: '微软雅黑';
  /* width: 740px; */
  background-color: white;
  padding-left: 20px;
  padding-top: 20px;
}
.myAside {
  /* width: 250px;
  height: 250px; */
}
.myFooter {
  border-bottom-right-radius: 10px;
  /* width: 713px; */
  background-color: white;
}
.tag {
  float: right;
  cursor: pointer;
  font-family: '微软雅黑';
  font-size: 25px;
  color: black;
}
.layout-container {
  /* display: flex; */
  flex-direction: column; /* 垂直排列 */
  /* 设置容器宽度为100% */
  /* width: 50%; */
  background-color: white;
}
</style>
