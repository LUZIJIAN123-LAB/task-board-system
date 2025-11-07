<template>
  <div class="dashboard-container">
    <div class="dashboard-header">
      <h1>欢迎回来，{{ userStore.userInfo?.nickname || userStore.userInfo?.username }}！</h1>
      <el-button type="primary" @click="handleLogout">退出登录</el-button>
    </div>

    <div class="dashboard-content">
      <el-empty description="工作台功能开发中..." />
      <p class="hint">第二阶段后端功能已完成，前端登录注册功能已完成！</p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

/**
 * 退出登录
 */
const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped lang="scss">
.dashboard-container {
  min-height: 100vh;
  padding: 20px;
  background: #f5f5f5;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;

  h1 {
    margin: 0;
    font-size: 24px;
    color: #333;
  }
}

.dashboard-content {
  padding: 40px 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  text-align: center;

  .hint {
    margin-top: 20px;
    font-size: 14px;
    color: #666;
  }
}
</style>
