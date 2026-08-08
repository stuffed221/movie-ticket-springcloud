// pages/schedule/list.js
const api = require('../../utils/request')

Page({
  data: {
    scheduleList: []
  },

  onLoad() {
    api.get('/schedule/list', {}).then(res => {
      if (res.code === 200) {
        this.setData({ scheduleList: res.data || [] })
      }
    }).catch(() => {})
  }
})
