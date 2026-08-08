// pages/index/index.js
const api = require('../../utils/request')

Page({
  data: {
    keyword: '',
    movieList: [],
    pageIndex: 1,
    pageTotal: 0,
    hasMore: true,
    loading: false
  },

  onLoad() {
    this.loadMovies()
  },

  onSearchInput(e) {
    this.setData({ keyword: e.detail.value })
  },

  onSearch() {
    this.setData({ pageIndex: 1, movieList: [], hasMore: true })
    this.loadMovies()
  },

  onClear() {
    this.setData({ keyword: '', pageIndex: 1, movieList: [], hasMore: true })
    this.loadMovies()
  },

  loadMovies() {
    if (this.data.loading || !this.data.hasMore) return
    this.setData({ loading: true })

    const url = '/movie/page?npage=' + this.data.pageIndex
    const body = { movieName: this.data.keyword }

    api.post(url, body).then(res => {
      if (res.code === 200) {
        const pageInfo = res.data
        const records = pageInfo.records || []
        const total = pageInfo.total

        api.get('/schedule/list', {}).then(sres => {
          if (sres.code === 200) {
            const schedules = sres.data || []
            records.forEach(m => {
              const prices = schedules
                .filter(s => s.scheduleMovieId === m.movieId)
                .map(s => s.schedulePrice)
              m.minPrice = prices.length > 0 ? Math.min(...prices) : null
            })
          }
          const list = this.data.pageIndex === 1 ? records : this.data.movieList.concat(records)
          this.setData({
            movieList: list,
            pageTotal: total,
            hasMore: list.length < total,
            loading: false
          })
        }).catch(() => {
          const list = this.data.pageIndex === 1 ? records : this.data.movieList.concat(records)
          this.setData({
            movieList: list,
            pageTotal: total,
            hasMore: list.length < total,
            loading: false
          })
        })
      }
    }).catch(() => {
      this.setData({ loading: false })
      wx.showToast({ title: '加载失败', icon: 'none' })
    })
  },

  loadMore() {
    if (!this.data.hasMore || this.data.loading) return
    this.setData({ pageIndex: this.data.pageIndex + 1 })
    this.loadMovies()
  },

  goDetail(e) {
    const movieId = e.currentTarget.dataset.id
    wx.navigateTo({ url: '/pages/movie/detail?id=' + movieId })
  }
})
