// pages/user/me.js
Page({
  data: {
    userName: '',
    firstChar: '',
    hasToken: false
  },

  onShow() {
    const token = wx.getStorageSync('token')
    const name = wx.getStorageSync('userName') || ''
    this.setData({
      userName: name || '未登录',
      firstChar: name ? name.charAt(0).toUpperCase() : '?',
      hasToken: !!token
    })
  },

  goOrders() {
    wx.switchTab({ url: '/pages/order/list' })
  },

  goLogin() {
    wx.navigateTo({ url: '/pages/user/login' })
  },

  doLogout() {
    wx.removeStorageSync('token')
    wx.removeStorageSync('userId')
    wx.removeStorageSync('userName')
    wx.showToast({ title: '已退出', icon: 'none' })
    this.setData({ userName: '未登录', firstChar: '?', hasToken: false })
  }
})
