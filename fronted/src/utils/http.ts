/**
 * HTTP请求封装
 * 统一处理请求和响应
 */

import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios'
import { MessagePlugin } from 'tdesign-vue-next'
import { API_BASE_URL, REQUEST_TIMEOUT, RESPONSE_CODE } from '@/config/api'

// 创建axios实例
const instance: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: REQUEST_TIMEOUT,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 添加token到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    // 添加时间戳防止缓存
    if (config.method === 'get') {
      config.params = {
        ...config.params,
        _t: Date.now()
      }
    }

    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response

    // 根据业务状态码处理
    if (data.code === RESPONSE_CODE.SUCCESS) {
      return data
    }

    // 处理业务错误
    const errorMessage = data.message || '请求失败'
    MessagePlugin.error(errorMessage)
    return Promise.reject(new Error(errorMessage))
  },
  (error) => {
    // 处理HTTP错误
    let errorMessage = '网络错误，请稍后重试'

    if (error.response) {
      const { status } = error.response
      switch (status) {
        case 401:
          errorMessage = '未授权，请重新登录'
          // 清除登录信息并跳转到登录页
          localStorage.clear()
          window.location.href = '/login'
          break
        case 403:
          errorMessage = '拒绝访问'
          break
        case 404:
          errorMessage = '请求的资源不存在'
          break
        case 500:
          errorMessage = '服务器错误'
          break
        case 503:
          errorMessage = '服务不可用'
          break
        default:
          errorMessage = `请求失败 (${status})`
      }
    } else if (error.request) {
      errorMessage = '网络连接失败，请检查网络'
    }

    MessagePlugin.error(errorMessage)
    console.error('响应错误:', error)
    return Promise.reject(error)
  }
)

// 封装请求方法
export const http = {
  /**
   * GET请求
   */
  get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.get(url, config)
  },

  /**
   * POST请求
   */
  post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return instance.post(url, data, config)
  },

  /**
   * PUT请求
   */
  put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return instance.put(url, data, config)
  },

  /**
   * DELETE请求
   */
  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return instance.delete(url, config)
  },

  /**
   * PATCH请求
   */
  patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return instance.patch(url, data, config)
  }
}

export default http
