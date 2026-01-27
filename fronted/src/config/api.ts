/**
 * API配置文件
 * 集中管理所有API端点和配置
 */

// API基础URL
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:9090'

// API端点配置
export const API_ENDPOINTS = {
  // 用户相关
  user: {
    login: `${API_BASE_URL}/user`,
    register: `${API_BASE_URL}/user/register`,
    update: `${API_BASE_URL}/user`
  },

  // 亚健康相关
  subHealth: {
    list: `${API_BASE_URL}/subHealth`,
    frequent: `${API_BASE_URL}/subHealth/frequent`
  },

  // 药膳相关
  medicatedFood: {
    list: `${API_BASE_URL}/medicatedFood`,
    detail: `${API_BASE_URL}/medicatedFood/detail`,
    benefit: `${API_BASE_URL}/medicatedFood/benefit`
  },

  // 功效相关
  benefit: {
    list: `${API_BASE_URL}/benefit`
  },

  // 地址相关
  address: {
    list: `${API_BASE_URL}/address`,
    create: `${API_BASE_URL}/address`,
    update: `${API_BASE_URL}/address`,
    delete: `${API_BASE_URL}/address`
  },

  // 订单相关
  order: {
    list: `${API_BASE_URL}/order`,
    create: `${API_BASE_URL}/order`,
    update: `${API_BASE_URL}/order`,
    delete: `${API_BASE_URL}/order`
  },

  // 文章相关
  article: {
    list: `${API_BASE_URL}/article`,
    detail: `${API_BASE_URL}/article/detail`
  }
}

// 请求超时配置
export const REQUEST_TIMEOUT = 10000 // 10秒

// 请求重试配置
export const RETRY_CONFIG = {
  maxRetries: 3,
  retryDelay: 1000 // 1秒
}

// 响应状态码
export const RESPONSE_CODE = {
  SUCCESS: 1,
  ERROR: 0,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  SERVER_ERROR: 500
}
