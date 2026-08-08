// pages/order/confirm.js
const api = require('../../utils/request')

Page({
  data: {
    scheduleId: null,
    seats: '',
    seatCount: 0,
    totalPrice: 0,
    schedule: null
  },

  onLoad(options) {
    const schedule = JSON.parse(decodeURIComponent(options.schedule))
    const seats = options.seats || ''
    const count = seats ? seats.split(',').length : 0
    // 座位号转 排-列 格式显示："35" -> "3-5"
    const displaySeats = seats
      ? seats.split(',').map(s => {
          const num = parseInt(s)
          return Math.floor(num / 10) + '-' + (num % 10)
        }).join('，')
      : ''
    this.setData({
      schedule,
      scheduleId: schedule.scheduleId,
      seats,
      displaySeats,
      seatCount: count,
      totalPrice: count * (schedule.schedulePrice || 0)
    })
  },

  submitOrder() {
    const token = wx.getStorageSync('token')
    if (!token) {
      wx.navigateTo({ url: '/pages/user/login' })
      return
    }
    const orderData = {
      orderScheduleId: this.data.scheduleId,
      orderUserId: wx.getStorageSync('userId') || 1,
      orderSeat: this.data.seats
    }
    api.post('/order/insert', orderData).then(res => {
      if (res.code === 200) {
        wx.showToast({ title: '下单成功', icon: 'success' })
        setTimeout(() => {
          wx.switchTab({ url: '/pages/order/list' })
        }, 1500)
      } else {
        wx.showToast({ title: res.msg || '下单失败', icon: 'none' })
      }
    }).catch(() => {
      wx.showToast({ title: '提交失败', icon: 'none' })
    })
  }
})
