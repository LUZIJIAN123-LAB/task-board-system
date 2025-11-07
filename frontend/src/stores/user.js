import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref('')
    const userInfo = ref(null)

    // 设置Token
    const setToken = (newToken) => {
      token.value = newToken
    }

    // 设置用户信息
    const setUserInfo = (info) => {
      userInfo.value = info
    }

    // 登出
    const logout = () => {
      token.value = ''
      userInfo.value = null
    }

    return {
      token,
      userInfo,
      setToken,
      setUserInfo,
      logout,
    }
  },
  {
    persist: true, // 持久化
  }
)
