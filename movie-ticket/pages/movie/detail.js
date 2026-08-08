// pages/movie/detail.js
const api = require('../../utils/request')

Page({
  data: {
    movieId: null,
    movie: {},
    scheduleList: []
  },

  onLoad(options) {
    this.setData({ movieId: options.id })
    this.loadMovie()
    this.loadSchedules()
  },

  loadMovie() {
    api.post('/movie/page?npage=1', { movieName: '' }).then(res => {
      if (res.code === 200) {
        const list = res.data.records
        const movie = list.find(m => m.movieId == this.data.movieId)
        if (movie) {
          if (movie.releaseTime && movie.releaseTime.length > 10) {
            movie.releaseTime = movie.releaseTime.substring(0, 10)
          }
          this.setData({ movie })
        }
      }
    }).catch(() => {
      wx.showToast({ title: '加载失败', icon: 'none' })
    })
  },

  loadSchedules() {
    api.get('/schedule/list', {}).then(res => {
      if (res.code === 200) {
        const list = res.data || []
        const schedules = list.filter(s => s.scheduleMovieId == this.data.movieId)
        this.setData({ scheduleList: schedules })
      }
    }).catch(() => {})
  },

  goSeat(e) {
    const schedule = e.currentTarget.dataset.schedule
    wx.navigateTo({
      url: '/pages/seat/select?schedule=' + JSON.stringify(schedule)
    })
  }
})
