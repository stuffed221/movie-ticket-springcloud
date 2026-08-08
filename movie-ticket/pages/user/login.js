// pages/user/login.js
const api = require('../../utils/request')

Page({
  data: {
    username: '',
    password: '',
    showRegister: false,
    confirmPassword: '',
    sentCode: '',
    inputCode: '',
    countdown: 0,
    reg: {
      userName: '',
      userPassword: '',
      userSex: '男',
      userAge: '',
      userPhoneNumber: ''
    }
  },

  onUsernameInput(e) {
    this.setData({ username: e.detail.value })
  },

  onPasswordInput(e) {
    this.setData({ password: e.detail.value })
  },

  onRegInput(e) {
    const field = e.currentTarget.dataset.field
    const value = e.detail.value
    this.setData({ ['reg.' + field]: value })
  },

  onConfirmInput(e) {
    this.setData({ confirmPassword: e.detail.value })
  },

  onSexChange(e) {
    this.setData({ 'reg.userSex': e.detail.value })
  },

  onInputCode(e) {
    this.setData({ inputCode: e.detail.value })
  },

  sendCode() {
    const phone = this.data.reg.userPhoneNumber
    if (!phone || phone.length < 11) {
      wx.showToast({ title: '请输入正确手机号', icon: 'none' })
      return
    }
    const code = String(Math.floor(100000 + Math.random() * 900000))
    this.setData({ sentCode: code, countdown: 60 })
    wx.showModal({
      title: '验证码',
      content: '验证码：' + code + '（模拟发送，实际会通过短信下发）',
      showCancel: false
    })
    const timer = setInterval(() => {
      if (this.data.countdown <= 1) {
        clearInterval(timer)
        this.setData({ countdown: 0 })
      } else {
        this.setData({ countdown: this.data.countdown - 1 })
      }
    }, 1000)
  },

  doLogin() {
    if (!this.data.username || !this.data.password) {
      wx.showToast({ title: '请输入用户名和密码', icon: 'none' })
      return
    }
    api.post('/user/login', {
      userName: this.data.username,
      userPassword: this.data.password
    }).then(res => {
      if (res.code === 200) {
        const data = res.data
        wx.setStorageSync('token', data.token)
        wx.setStorageSync('userId', data.userId)
        wx.setStorageSync('userName', data.userName)
        wx.showToast({ title: '登录成功', icon: 'success' })
        setTimeout(() => {
          wx.switchTab({ url: '/pages/index/index' })
        }, 1500)
      } else {
        wx.showToast({ title: res.msg || '登录失败', icon: 'none' })
      }
    }).catch(() => {
      wx.showToast({ title: '登录失败', icon: 'none' })
    })
  },

  goRegister() {
    this.setData({ showRegister: true })
  },

  hideRegister() {
    this.setData({ showRegister: false })
  },

  doRegister() {
    const reg = this.data.reg
    if (!reg.userName || !reg.userPassword || !reg.userPhoneNumber) {
      wx.showToast({ title: '信息不完善，注册失败', icon: 'none' })
      return
    }
    if (reg.userPassword !== this.data.confirmPassword) {
      wx.showToast({ title: '两次密码不一致', icon: 'none' })
      return
    }
    if (this.data.inputCode !== this.data.sentCode) {
      wx.showToast({ title: '验证码错误', icon: 'none' })
      return
    }
    api.post('/user/insert', {
      userName: reg.userName,
      userPassword: reg.userPassword,
      userSex: reg.userSex,
      userAge: parseInt(reg.userAge) || 0,
      userPhoneNumber: reg.userPhoneNumber
    }).then(res => {
      if (res.code === 200) {
        wx.showToast({ title: '注册成功请登录', icon: 'success' })
        this.setData({
          showRegister: false,
          confirmPassword: '',
          reg: { userName: '', userPassword: '', userSex: '男', userAge: '', userPhoneNumber: '' }
        })
      } else {
        wx.showToast({ title: res.msg || '注册失败', icon: 'none' })
      }
    }).catch(() => {
      wx.showToast({ title: '注册失败', icon: 'none' })
    })
  }
})
