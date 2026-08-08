import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/LoginLog'
    },
    {
      path: "/",
      name: "Home",
      component: Home,
      children: [
          {
              path: "/index",
              name: "index",
              meta: {
                  title: '系统首页'
              },
              component: () => import ("../views/Index.vue")
          }, {
            path: "/ManagerManager",
            name: "ManagerManager",
            meta: {
                title: '管理员管理'
            },
            component: () => import ("../views/ManagerManager.vue")
          },  {
            path: "/cinemamanager",
            name: "cinemamanager",
            meta: {
                title: '影院管理'
            },
            component: () => import ("../views/CinemaManager.vue")
          }, {
            path: "/ManagerLogin",
            name: "ManagerLogin",
            meta: {
                title: '管理员名单'
            },
            component: () => import ("../views/ManagerLogin.vue")
          }, {
              path: "/userManager",
              name: "userManager",
              meta: {
                  title: '用户管理'
              },
              component: () => import ("../views/UserManager.vue")
          }, {
            path: "/batteryManager",
            name: "batteryManager",
            meta: {
                title: '电瓶车管理'
            },
            component: () => import ("../views/BatteryManager.vue")
        }, {
            path: "/storeManager",
            name: "storeManager",
            meta: {
                title: '店铺管理'
            },
            component: () => import ("../views/StoreManager.vue")
        },{
          path: '/manager',
          name: 'manager',
          meta: {
            title: '管理员信息'
          },
          component: () => import('../views/Manager.vue')
        },{
          path: "/moviemanager",
          name: "moviemanager",
          meta: {
              title: '订单管理'
          },
          component: () => import ("../views/MovieManager.vue")
        },{
          path: "/schedulemanager",
          name: "schedulemanager",
          meta: {
              title: '电影排片'
          },
          component: () => import ("../views/ScheduleManager.vue")
        },
          {
            path: "/orderManager",
            name: "orderManager",
            meta: {
                title: '订单管理'
            },
            component: () => import ("../views/OrderManager.vue")
          },{
            path: "/form",
            name: "baseform",
            meta: {
                title: '表单'
            },
            component: () => import ("../views/BaseForm.vue")
        },
        {
          path: "/storeadd",
          name: "storeadd",
          meta: {
              title: '表单'
          },
          component: () => import ("../views/StoreAdd.vue")
      },{
        path: "/batteryadd",
        name: "batteryadd",
        meta: {
            title: '表单'
        },
        component: () => import ("../views/BatteryAdd.vue")
    },{
      path: "/manageradd",
      name: "manageradd",
      meta: {
          title: '表单'
      },
      component: () => import ("../views/ManagerAdd.vue")
  },
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: {
        title: '登录'
      },
      component: () => import('../views/Login.vue')
    }, {
      path: "/rentadd",
      name: "rentadd",
      meta: {
          title: '租用新增'
      },
      component: () => import ("../views/RentAdd.vue")
    }, {
      path: "/LoginLog",
      name: "LoginLog",
      meta: {
          title: '登入'
      },
      component: () => import ("../views/LoginLog.vue")
    }
    
  ]
})

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | 电影购票系统`;
  const token = localStorage.getItem('token');

  if (!token && to.path !== '/LoginLog') {
      next('/LoginLog');              // 未登录 → 强制去登录页
  } else if (token && to.path === '/LoginLog') {
      next('/index');                 // 已登录 → 登录页不让进，踢去首页
  } else {
      next();                         // 已登录且不是登录页 → 放行
  }
});

export default router
