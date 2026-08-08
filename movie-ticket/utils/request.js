const BASE_URL = 'http://localhost:8080'

// 无需登录即可访问的接口
const PUBLIC_PATHS = [
  '/user/login',
  '/user/insert',
  '/movie/page',
  '/movie/listall',
  '/schedule/list',
  '/cinema/list',
  '/order/list'
]

const request = (url, method = 'GET', data = {}) => {
  const token = wx.getStorageSync('token')

  // 无 token 且访问的是需要登录的接口 → 跳转登录页
  if (!token) {
    const needAuth = !PUBLIC_PATHS.some(p => url.indexOf(p) > -1)
    if (needAuth) {
      wx.reLaunch({ url: '/pages/user/login' })
      return new Promise((resolve, reject) => reject('未登录'))
    }
  }

  return new Promise((resolve, reject) => {
    wx.request({
      url: BASE_URL + url,
      method,
      data,
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? 'Bearer ' + token : ''
      },
      success(res) {
        if (res.statusCode === 401) {
          wx.removeStorageSync('token')
          wx.removeStorageSync('userId')
          wx.reLaunch({ url: '/pages/user/login' })
          return
        }
        if (res.statusCode === 200) {
          resolve(res.data)
        } else {
          wx.showToast({ title: '请求失败', icon: 'none' })
          reject(res)
        }
      },
      fail(err) {
        wx.showToast({ title: '网络异常', icon: 'none' })
        reject(err)
      }
    })
  })
}

module.exports = {
  get: (url, params) => request(url, 'GET', params),
  post: (url, data) => request(url, 'POST', data)
}
