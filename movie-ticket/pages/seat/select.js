// pages/seat/select.js
const api = require('../../utils/request')

Page({
  data: {
    schedule: null,
    rows: [1, 2, 3, 4, 5],
    cols: [1, 2, 3, 4, 5, 6, 7, 8],
    selectedSeats: [],
    occupiedSeats: [],
    totalPrice: 0
  },

  onLoad(options) {
    const token = wx.getStorageSync('token')
    if (!token) {
      wx.reLaunch({ url: '/pages/user/login' })
      return
    }
    const schedule = JSON.parse(decodeURIComponent(options.schedule))
    this.setData({ schedule })
    this.loadOccupiedSeats()
  },

  // 根据行列算出座位号：(row-1)*8 + col
  getSeatNo(row, col) {
    return (row - 1) * this.data.cols.length + col
  },

  loadOccupiedSeats() {
    api.get('/order/list', {}).then(res => {
      if (res.code === 200) {
        const orders = res.data || []
        const occupied = []
        orders.forEach(o => {
          if (o.orderScheduleId !== this.data.schedule.scheduleId) return
          if (o.orderStatus !== 0 && o.orderStatus !== 1) return
          if (!o.orderSeat) return
          o.orderSeat.split(',').forEach(s => {
            const num = parseInt(s)
            if (!isNaN(num)) occupied.push(num)
          })
        })
        this.setData({ occupiedSeats: occupied })
      }
    }).catch(() => {})
  },

  onSeatTap(e) {
    const seat = parseInt(e.currentTarget.dataset.seat)
    if (this.data.occupiedSeats.indexOf(seat) > -1) {
      wx.showToast({ title: '该座位已被选择', icon: 'none' })
      return
    }
    let selectedSeats = [...this.data.selectedSeats]
    const idx = selectedSeats.indexOf(seat)
    if (idx > -1) {
      selectedSeats.splice(idx, 1)
    } else {
      selectedSeats.push(seat)
    }
    const price = this.data.schedule.schedulePrice || 0
    this.setData({
      selectedSeats,
      totalPrice: selectedSeats.length * price
    })
  },

  genOrderNo() {
    var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
    var rand = ''
    for (var i = 0; i < 6; i++) {
      rand += chars.charAt(Math.floor(Math.random() * chars.length))
    }
    return 'MO' + Date.now().toString(36).toUpperCase() + rand
  },

  confirmOrder() {
    const schedule = this.data.schedule
    const seats = this.data.selectedSeats
    if (seats.length === 0) {
      wx.showToast({ title: '请先选择座位', icon: 'none' })
      return
    }

    const userId = wx.getStorageSync('userId') || 1
    const promises = seats.map(seatNo => {
      return api.post('/order/creatord', {
        ordNo: this.genOrderNo(),
        ordScheduleId: schedule.scheduleId,
        ordUserId: userId,
        ordSeat: seatNo,
        ordStatus: 0
      })
    })

    wx.showLoading({ title: '提交中...' })
    Promise.all(promises).then(results => {
      wx.hideLoading()
      const failed = results.find(r => r.code !== 200)
      if (failed) {
        wx.showToast({ title: failed.msg || '下单失败', icon: 'none' })
      } else {
        wx.showToast({ title: '下单成功', icon: 'success' })
        setTimeout(() => {
          wx.switchTab({ url: '/pages/order/list' })
        }, 1000)
      }
    }).catch(() => {
      wx.hideLoading()
      wx.showToast({ title: '提交失败', icon: 'none' })
    })
  }
})
