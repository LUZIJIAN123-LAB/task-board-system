/**
 * 用户认证相关API
 * @author 哈雷酱
 * @date 2025
 */
import request from '@/utils/request'

/**
 * 用户注册
 */
export const register = (data) => {
  return request({
    url: '/auth/register',
    method: 'post',
    data
  })
}

/**
 * 用户登录
 */
export const login = (data) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

/**
 * 获取当前用户信息
 */
export const getCurrentUser = () => {
  return request({
    url: '/auth/me',
    method: 'get'
  })
}
