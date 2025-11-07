/**
 * 用户状态管理
 * @author 哈雷酱
 * @date 2025
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, register as registerApi, getCurrentUser } from '@/api/auth'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore(
  'user',
  () => {
    // 状态
    const token = ref('')
    const userInfo = ref(null)
    const isLoggedIn = ref(false)

    /**
     * 用户登录
     */
    const login = async (loginForm) => {
      try {
        const res = await loginApi(loginForm)

        // 保存Token和用户信息
        token.value = res.data.token
        userInfo.value = res.data.user
        isLoggedIn.value = true

        ElMessage.success('登录成功')
        return true
      } catch (error) {
        console.error('登录失败：', error)
        return false
      }
    }

    /**
     * 用户注册
     */
    const register = async (registerForm) => {
      try {
        const res = await registerApi(registerForm)
        ElMessage.success('注册成功，请登录')
        return true
      } catch (error) {
        console.error('注册失败：', error)
        return false
      }
    }

    /**
     * 获取用户信息
     */
    const getUserInfo = async () => {
      try {
        const res = await getCurrentUser()
        userInfo.value = res.data
        isLoggedIn.value = true
        return true
      } catch (error) {
        console.error('获取用户信息失败：', error)
        logout()
        return false
      }
    }

    /**
     * 设置Token（兼容旧代码）
     */
    const setToken = (newToken) => {
      token.value = newToken
    }

    /**
     * 设置用户信息（兼容旧代码）
     */
    const setUserInfo = (info) => {
      userInfo.value = info
    }

    /**
     * 退出登录
     */
    const logout = () => {
      token.value = ''
      userInfo.value = null
      isLoggedIn.value = false
      ElMessage.info('已退出登录')
    }

    return {
      // 状态
      token,
      userInfo,
      isLoggedIn,
      // 方法
      login,
      register,
      getUserInfo,
      setToken,
      setUserInfo,
      logout
    }
  },
  {
    // 持久化配置
    persist: {
      key: 'task-board-user',
      storage: localStorage,
      paths: ['token', 'userInfo', 'isLoggedIn']
    }
  }
)
