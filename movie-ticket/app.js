App({
  onLaunch() {
    const token = wx.getStorageSync('token')
    if (token) {
      // 检查 token 是否过期
      const payload = JSON.parse(atob(token.split('.')[1]))
      if (payload.exp * 1000 < Date.now()) {
        wx.removeStorageSync('token')
        wx.removeStorageSync('userId')
      }
    }
  },

  globalData: {
    baseUrl: 'http://localhost:8080',
    token: null,
    userId: null
  }
})
