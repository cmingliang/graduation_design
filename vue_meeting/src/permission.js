import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getUser } from '@/utils/auth' // getUser from cookie

NProgress.configure({ showSpinner: false }) // NProgress Configuration

function hasPermission(permission, route) {
  if (route.meta && route.meta.permission) {
    return permission.some(permission => {
      try {
        return route.meta.permission.indexOf(permission.authority) >= 0
      } catch (error) {
        return false
      }
    })
  } else {
    return true
  }
}

const whiteList = ['/login', '/auth-redirect', '/test'] // 免登陆白名单

// 在路由跳转的时候，我们需要一些权限判断或者其他操作。这个时候就需要使用路由的钩子函数
router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar

  if (getUser() != null && getUser() != '') {
    // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      //next({path: '/'})
      next()
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.userInfo == null || store.getters.userInfo == '') {
        // 判断当前用户是否已拉取完user_info信息
        store
          .dispatch('GetUserInfo')
          .then(res => {
            // 拉取user_info
            let permission = store.getters.permissions
            store.dispatch('GenerateRoutes', { permission }).then(() => {
              // 根据roles权限生成可访问的路由表
              router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
            })
          })
          .catch(err => {
            if (err.response.status != 401) {
              //在request.js中处理了,避免重复处理
              store.dispatch('FedLogOut').then(() => {
                Message.error(err || 'Verification failed, please login again')
                next({ path: '/' })
              })
            }
          })
      } else {
        if (to.name != null && hasPermission(store.getters.permissions, to)) {
          //有权限的情况下才进入
          next()
        } else {
          next({ path: '/404', replace: true, query: { noGoBack: true } })
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
