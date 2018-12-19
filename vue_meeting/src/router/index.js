import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/views/layout/Layout'

Vue.use(Router)

export const constantRouterMap = [
  // {
  //   path: '/redirect',
  //   component: Layout,
  //   hidden: true,
  //   name: 'redirect',
  //   children: [
  //     {
  //       path: '/redirect/:path*',
  //       component: () => import('@/views/redirect/index')
  //     }
  //   ]
  // },
  {
    path: '/',
    component: Layout,
    // redirect: 'dashboard',
    children: [
      {
        path: '',
        component: () => import('@/views/dashboard/index'),
        name: 'default',
        meta: {
          name: '工作台',
          title: 'dashboard',
          icon: 'dashboard',
          noCache: true
        }
      },

      {
        path: 'myinfo',
        component: () => import('@/views/user/MyInfo'),
        name: 'MyInfo',
        meta: { name: '我的资料', title: 'MyInfo', icon: 'user', noCache: true }
      },
      {
        path: 'mybooking',
        component: () => import('@/views/user/MyBooking'),
        name: 'MyBooking',
        meta: {
          name: '我预约的',
          title: 'MyBooking',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'myjoin',
        component: () => import('@/views/user/MyJoin'),
        name: 'MyJoin',
        meta: { name: '我参与的', title: 'MyJoin', icon: 'user', noCache: true }
      },
      {
        path: 'checkmeeting',
        component: () => import('@/views/admin/CheckMeeting'),
        name: 'CheckMeeting',
        meta: {
          name: '会议审核',
          title: 'CheckMeeting',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'equipmentmanagement',
        component: () => import('@/views/admin/EquipmentManagement'),
        name: 'EquipmentManagement',
        meta: {
          name: '设备管理',
          title: 'EquipmentManagement',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'servicemanagement',
        component: () => import('@/views/admin/ServiceManagement'),
        name: 'ServiceManagement',
        meta: {
          name: '服务管理',
          title: 'ServiceManagement',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'meetingroom',
        component: () => import('@/views/super/MeetingRoom'),
        name: 'MeetingRoom',
        meta: {
          name: '会议室管理',
          title: 'MeetingRoom',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'uesrPage',
        component: () => import('@/views/super/user'),
        name: 'uesrPage',
        meta: {
          name: '用户管理',
          title: 'userPage',
          icon: 'user',
          noCache: true
        }
      }
    ]
  },
  // {
  //   path: '/',
  //   name:'default',
  //   component: () => import('@/views/dashboard/index'),
  //   hidden: true,
  //   meta: {  name: '首页',title: 'dashboard', icon: 'dashboard', noCache: true }
  // },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/newlogin/login'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    name: 'auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '404',
        name: '404',
        component: () => import('@/views/errorPage/404'),
        meta: { name: '404', title: '404' }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '401',
        name: '401',
        component: () => import('@/views/errorPage/401'),
        meta: { name: '401', title: '401' }
      }
    ]
  },

  {
    path: '/test',
    name: 'test',
    component: () => import('@/views/newlogin/login'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {
          name: '工作台',
          title: 'dashboard',
          icon: 'dashboard',
          noCache: true
        }
      },
      {
        path: 'booking',
        component: () => import('@/views/booking/index'),
        hidden: true,
        name: 'Booking',
        meta: {
          name: '预约信息填写',
          title: 'booking',
          icon: 'user',
          noCache: true
        }
      }
    ]
  }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

//动态路由
export const asyncRouterMap = [
  {
    path: '/application',
    component: Layout,
    meta: { permission: ['u_app'] },
    children: [
      {
        hidden: true, //不再菜单中显示
        path: 'index',
        component: () => import('@/views/application/index'),
        name: 'Application',
        meta: {
          name: '应用管理',
          title: 'application',
          icon: 'component',
          noCache: true
        }
      },
      {
        hidden: true, //不再菜单中显示
        path: 'detail',
        component: () => import('@/views/application/detail'),
        name: 'applicationDetail',
        meta: {
          name: '应用详情',
          title: 'applicationDetail',
          noCache: true,
          subPage: true
        }
      },
      {
        hidden: true, //不再菜单中显示
        path: 'add',
        component: () => import('@/views/application/add'),
        name: 'applicationAdd',
        meta: {
          name: '创建应用',
          title: 'addApplication',
          noCache: true,
          subPage: true
        }
      }
    ]
  },

  {
    path: '/customer',
    component: Layout,
    meta: { permission: ['a_customer', 'a_customer_all'] },
    children: [
      {
        path: 'index',
        component: () => import('@/views/customer/index'),
        name: 'customer',
        meta: {
          name: '客户管理',
          title: 'customer',
          icon: 'peoples',
          noCache: true
        }
      },
      {
        hidden: true, //不再菜单中显示
        path: 'detail',
        component: () => import('@/views/customer/detail'),
        name: 'customerDetail',
        meta: {
          name: '用户详情',
          title: 'detail',
          noCache: true,
          subPage: true
        }
      },
      {
        hidden: true, //不再菜单中显示
        path: 'appDetail',
        component: () => import('@/views/customer/appDetail'),
        name: 'customerAppDetail',
        meta: {
          name: '用户应用详情',
          title: 'appDetail',
          noCache: true,
          subPage: true
        }
      }
    ]
  },

  // {
  //   path: '/customer',
  //   component: Layout,
  //   hidden: true,//不再菜单中显示
  //   children: [
  //
  //   ]
  // },

  // {
  //   path: '/customer',
  //   component: Layout,
  //   hidden: true,//不再菜单中显示
  //   children: [
  //
  // },
  {
    path: '/order',
    component: Layout,
    meta: { permission: ['a_order', 'a_order:all', 'a_order:list'] },
    children: [
      {
        path: 'index',
        component: () => import('@/views/order/index'),
        name: 'order',
        meta: { name: '订单管理', title: 'order', icon: 'excel', noCache: true }
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    hidden: true, //不再菜单中显示
    meta: { permission: ['a_order_detail', 'a_order:all', 'a_order:list'] },
    children: [
      {
        path: 'detail',
        component: () => import('@/views/order/detail'),
        name: 'orderDetail',
        meta: {
          name: '订单详情',
          title: 'orderDetail',
          icon: 'excel',
          noCache: true,
          subPage: true,
          permission: ['a_order_detail', 'a_order:all', 'a_order:list']
        }
      }
    ]
  },
  {
    path: '/finance',
    component: Layout,
    meta: { permission: ['a_finance'] },
    children: [
      {
        path: 'index',
        component: () => import('@/views/finance/index'),
        name: 'finance',
        meta: {
          name: '财务管理',
          title: 'finance',
          icon: 'money',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    meta: { permission: ['u_myInfo'] },
    children: [
      {
        path: 'myinfo',
        component: () => import('@/views/user/MyInfo'),
        name: 'MyInfo',
        meta: { name: '我的资料', title: 'MyInfo', icon: 'user', noCache: true }
      },
      {
        path: 'mybooking',
        component: () => import('@/views/user/MyBooking'),
        name: 'MyBooking',
        meta: {
          name: '我预约的',
          title: 'MyBooking',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'myjoin',
        component: () => import('@/views/user/MyJoin'),
        name: 'MyJoin',
        meta: { name: '我参与的', title: 'MyJoin', icon: 'user', noCache: true }
      },
      {
        path: 'checkmeeting',
        component: () => import('@/views/admin/CheckMeeting'),
        name: 'CheckMeeting',
        meta: {
          name: '会议审核',
          title: 'CheckMeeting',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'equipmentmanagement',
        component: () => import('@/views/admin/EquipmentManagement'),
        name: 'EquipmentManagement',
        meta: {
          name: '设备管理',
          title: 'EquipmentManagement',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'servicemanagement',
        component: () => import('@/views/admin/ServiceManagement'),
        name: 'ServiceManagement',
        meta: {
          name: '服务管理',
          title: 'ServiceManagement',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'meetingroom',
        component: () => import('@/views/super/MeetingRoom'),
        name: 'MeetingRoom',
        meta: {
          name: '会议室管理',
          title: 'MeetingRoom',
          icon: 'user',
          noCache: true
        }
      },
      {
        path: 'uesrPage',
        component: () => import('@/views/super/user'),
        name: 'uesrPage',
        meta: {
          name: '用户管理',
          title: 'userPage',
          icon: 'user',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    hidden: true, //不再菜单中显示
    children: [
      {
        path: 'modifyPassword',
        component: () => import('@/views/user/ModifyPassword'),
        name: 'ModifyPassword',
        meta: { name: '修改密码', title: 'modifyPassword', noCache: true }
      }
    ]
  },

  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/index',
    alwaysShow: true, // will always show the root menu
    meta: {
      clickAble: false,
      name: '权限管理',
      title: 'permission',
      icon: 'lock',
      permission: ['a_permission']
    },
    name: '权限管理',
    children: [
      {
        path: 'admin',
        component: () => import('@/views/permission/administrator'),
        name: 'Administrator',
        meta: {
          name: '管理员管理',
          title: 'administrator',
          icon: 'admin'
        }
      },
      {
        path: 'role',
        component: () => import('@/views/permission/role'),
        name: 'Role',
        meta: {
          name: '角色管理',
          title: 'roleManagement',
          icon: 'role'
          // if do not set roles, means: this page does not require permission
        }
      }
    ]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/index',
    alwaysShow: true, // will always show the root menu
    meta: {
      clickAble: false,
      name: '系统管理',
      title: 'system',
      icon: 'system',
      permission: ['a_admin_system']
    },
    name: '系统管理',
    children: [
      {
        path: 'log',
        component: () => import('@/views/log/index'),
        name: 'Log',
        meta: {
          name: '日志管理',
          title: 'log',
          icon: 'documentation'
        }
      },
      {
        path: 'dict',
        component: () => import('@/views/dict/index'),
        name: 'Dict',
        meta: {
          name: '数据字典',
          title: 'dict',
          icon: 'documentation'
        }
      }
    ]
  }
]
