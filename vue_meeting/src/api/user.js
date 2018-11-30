import request from '@/utils/request'

//获取用户信息
export function getUserInfo(userId) {
  return request({
    url: '/user/detail/'+userId,
    method: 'get'
  })
}
export function getMyUserInfo(userId) {
  return request({
    url: '/user/me',
    method: 'get'
  })
}

export function updateUserInfo(data) {
  return request({
    url: '/user/',
    method: 'put',
    data
  })
}

export function modifyPassword(data) {
  return request({
    url: '/user/modifyPassword',
    method: 'put',
    data
  })
}

export function getAdmin() {
  return request({
    url: '/user/admin',
    method: 'get'
  })
}

export function  getRoleList(){
  return request({
    url: '/role',
    method: 'get'
  })
}
export function  getPermissionList(){
  return request({
    url: '/permission',
    method: 'get'
  })
}


export function  addAdmin(params){
  return request({
    url: '/user/admin',
    method: 'post',
    params
  })
}
export function  updateAdmin(params){
  return request({
    url: '/user/admin',
    method: 'put',
    params
  })
}


export function  addRole(data){
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

