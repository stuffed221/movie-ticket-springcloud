// pages/order/list.js
const api = require('../../utils/request')

Page({
  data: {
    orderList: [],
    showDetail: false,
    detail: {},
    qrcodeUrl: '',
    statusMap: { 0: '待支付', 1: '已支付', 2: '已完成', 3: '已取消', 4: '已退款' }
  },

  onShow() {
    const token = wx.getStorageSync('token')
    if (!token) {
      wx.reLaunch({ url: '/pages/user/login' })
      return
    }
    this.loadOrders()
  },

  loadOrders() {
    const userId = wx.getStorageSync('userId')
    api.get('/order/list', { userId: userId }).then(res => {
      if (res.code === 200) {
        const orders = res.data || []
        api.get('/schedule/list', {}).then(sres => {
          const schedules = sres.data || []
          const list = orders.map(o => {
            o.orderSeatDisplay = o.orderSeat || '--'
            const s = schedules.find(sc => sc.scheduleId === o.orderScheduleId)
            o.showAmount = s ? s.schedulePrice : o.orderAmount
            return o
          })
          this.setData({ orderList: list })
        }).catch(() => {
          const list = orders.map(o => {
            o.orderSeatDisplay = o.orderSeat || '--'
            o.showAmount = o.orderAmount
            return o
          })
          this.setData({ orderList: list })
        })
      }
    }).catch(() => {})
  },

  showDetail(e) {
    const order = this.data.orderList[e.currentTarget.dataset.index]
    var url = 'https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=' + encodeURIComponent('ORDER:' + order.orderNo + '|' + order.orderId) + '&t=' + Date.now()
    this.setData({ detail: order, showDetail: true, qrcodeUrl: url })
  },

  hideDetail() {
    this.setData({ showDetail: false })
  },

  noop() {},

  goPay(e) {
    const orderId = parseInt(e.currentTarget.dataset.id)
    const order = this.data.orderList.find(o => o.orderId === orderId)
    const amount = order ? (order.showAmount || order.orderAmount || 0) : 0
    wx.showModal({
      title: '模拟支付',
      content: '确认支付 ¥' + amount + '？',
      success: (res) => {
        if (res.confirm) {
          api.post('/order/pay', {
            orderId: orderId,
            payMethod: 1,
            payAmount: amount
          }).then(() => {
            wx.showToast({ title: '支付成功', icon: 'success' })
            this.loadOrders()
          }).catch(() => {
            wx.showToast({ title: '支付失败', icon: 'none' })
          })
        }
      }
    })
  },

  cancelOrder(e) {
    const orderId = e.currentTarget.dataset.id
    wx.showModal({
      title: '取消订单',
      content: '确定取消该订单？',
      success: (res) => {
        if (res.confirm) {
          api.post('/order/cancel?orderId=' + orderId + '&reason=用户取消', {}).then(() => {
            wx.showToast({ title: '已取消', icon: 'success' })
            this.loadOrders()
          }).catch(() => {
            wx.showToast({ title: '取消失败', icon: 'none' })
          })
        }
      }
    })
  },

  refundOrder(e) {
    const orderId = e.currentTarget.dataset.id
    wx.showModal({
      title: '退款',
      content: '确定申请退款？',
      success: (res) => {
        if (res.confirm) {
          api.post('/order/refund?orderId=' + orderId, {}).then(() => {
            wx.showToast({ title: '退款成功', icon: 'success' })
            this.loadOrders()
          }).catch(() => {
            wx.showToast({ title: '退款失败', icon: 'none' })
          })
        }
      }
    })
  }
})
