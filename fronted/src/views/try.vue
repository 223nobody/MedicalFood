<template>
  <div class="indexBody">
    <div class="head">
      <!-- 左边 Logo -->
      <span class="logo" @click="handleLogo"> 药膳云厨 </span>

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
          <t-button theme="default" type="submit" @click="handleQuery"> 查询 </t-button>
        </span>
        <span> </span>
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
      <foodquery v-else-if="selectedValue === 'food'" :param="queryparam" :key="keyNew"></foodquery>
    </div>
    <div v-else>
      <t-space v-if="id === 0" class="indexMiddle" direction="vertical">
        <h2>知识科普</h2>
        <t-tabs style="width: 600px" :value="value" :theme="theme" @change="handlerChange">
          <t-tab-panel value="first">
            <template #label>
              <div @click="getYjk" style="font-size: 20px">
                <t-icon name="calendar" class="tabs-icon-margin" />
                常见的亚健康状态
              </div>
            </template>
            <p v-for="(item, index) in yjk" :key="index" class="fondModify">
              <span style="cursor: pointer"> {{ item.name }}</span>
            </p>
          </t-tab-panel>
          <t-tab-panel value="second">
            <template #label>
              <t-icon name="layers" class="tabs-icon-margin" style="font-size: 20px" />
              药膳功效
            </template>
            <p v-for="(item, index) in ysgx" :key="index" class="fondModify">
              <span @click="handleysgx(item)" style="cursor: pointer"> {{ item.benefit }}</span>
            </p>
          </t-tab-panel>
        </t-tabs>
      </t-space>
      <div v-else>
        <ys-gx :param="id" />
      </div>
      <div class="photo" v-if="id === 0">
        <h1>
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
    </div>
  </div>
</template>

<script setup lang="ts">
import { MessagePlugin, type TabsProps } from 'tdesign-vue-next'
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import foodquery from './foodquery.vue'
import yjkquery from './Yjkquery.vue'
import ysGx from './ysgx.vue'
const items = [
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 1'
  },
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 2'
  },
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 3'
  },
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 4'
  },
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
    text: 'Item 5'
  },
  {
    image:
      'https://bpic.588ku.com/back_origin_min_pic/19/10/22/d4dc36eaa36409c0db18974b79f2f936.jpg',
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
const Isquery = ref(false)
const keyNew = ref(0)
onMounted(() => {
  getYjk()
  fetchYjkOption()
  fetchFoodOption()
})
const IsUpdate = ref('')
const huichuan = ref('')
const id = ref(0)
const handleysgx = async (item: any) => {
  console.log(item.id)
  id.value = item.id
  // const response = await axios.get(`http://localhost:9090/medicatedFood/benefit?benefitId=${item.id}`)
  // console.log(response.data.data);
  //为了跳转页面
}
const updateYjk = async (data: any) => {
  queryparam.value = ''
  selectedValue.value = 'food'
  huichuan.value = data
  IsUpdate.value = '1'
}
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
const fetchYjkOption = async () => {
  const response = await axios.get('http://localhost:9090/subHealth?queryName=')
  YjkOption.value = response.data.data.map((option: any) => ({
    label: option.name, // 或者使用 option 的其他字段作为 label
    value: option.name // 或者使用 option 的其他字段作为 value
  }))
  console.log(YjkOption.value)
}
const fetchFoodOption = async () => {
  const response = await axios.get('http://localhost:9090/medicatedFood?queryName')
  FoodOption.value = response.data.data.map((option: any) => ({
    label: option.name, // 或者使用 option 的其他字段作为 label
    value: option.name // 或者使用 option 的其他字段作为 value
  }))
  console.log(YjkOption.value)
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
const handlerChange: TabsProps['onChange'] = async (newValue) => {
  value.value = newValue
  if (value.value === 'second') {
    const response = await axios.get('http://localhost:9090/benefit')
    console.log(response.data.data)
    ysgx.value = response.data.data
  }
}
const getYjk = async () => {
  if (Show_yjk.value === '') {
    const response = await axios.get('http://localhost:9090/subHealth/frequent')
    yjk.value = response.data.data
  }
}
const getYsgx = async () => {
  console.log('666')
}
const handleLogo = async () => {
  Isquery.value = false
  queryparam.value = ''
}
</script>

<style scoped>
.just {
  /* margin: auto; */
  margin-top: 20px;
  margin-left: 560px;
  display: flex;
}
.yjk {
  padding-left: 0px;
  width: 200px;
  background-color: skyblue;
  font-family: 'Courier New', Courier, monospace;
}
.ys {
  padding-left: 50px;
  width: 150px;
  background-color: skyblue;
  font-family: 'Courier New', Courier, monospace;
}
.separator {
  width: 1px; /* 分隔符的宽度 */
  height: 18px; /* 分隔符的高度 */
  background-color: #ccc; /* 分隔符的颜色 */
  margin: 0 0px; /* 分隔符的左右间距 */
}
.queryinput {
  width: 90%;
}
.selectys {
  text-align: right;
  width: 110px;
}
.selectys .t-select-option {
  text-align: right;
}
h1 span {
  background-color: skyblue;
  margin-left: 650px;
}
.tdesign-demo-block--swiper {
  margin: 0 auto;
  width: 100%; /* 轮播图的宽度，可以根据需求调整 */
  max-width: 700px; /* 最大宽度限制 */
  height: 400px; /* 轮播图的高度 */
}
.swiper-image {
  width: 700px;
  height: 400px;
  object-fit: cover; /* 让图片覆盖整个容器，且保持比例 */
}
.indexBody {
  margin: 0;
  padding: 0;
  background-color: rgb(238, 238, 238);
}

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
}
.t-button {
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}
:deep(.t-input) {
  border: none;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  outline: none;
}
:deep(.queryinput .t-input) {
  border-radius: 0px;
}
:deep(.t-input--focused) {
  box-shadow: none;
}
.t-input :hover {
}
.right-content {
  margin: auto; /* 水平居中 */
  border: 3px solid salmon;
  border-radius: 10px;
  display: flex;
  align-items: center;
  gap: 0px; /* 元素之间的间距 */
  width: 50%;
}

span {
  display: inline-flex;
}
.tabs-icon-margin {
  margin-right: 0px;
  margin-top: -2px;
}
.indexMiddle {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.fondModify {
  font-size: 23px;
  display: inline-block;
  margin: 20px 10px 10px 0;
}
:deep(.t-tabs__nav-scroll) {
  background-color: rgb(243, 243, 243); /* 灰色背景 */
}
</style>
