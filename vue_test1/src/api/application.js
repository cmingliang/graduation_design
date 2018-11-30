import request from '@/utils/request'

export function applicationList(params) {
  return request({
    url: '/app',
    method: 'get',
    params
  })
}

export function applicationDetail(id) {
  return request({
    url: '/app/'+id,
    method: 'get'
  })
}

export function addApplication(data) {
  return request({
    url: '/app',
    method: 'post',
    data
  })
}

export function saveApplication(data) {
  return request({
    url: '/app/save',
    method: 'post',
    data
  })
}

export function getTempApplication() {
  return request({
    url: '/app/temp',
    method: 'get'

  })
}

export function getGeneralInfo() {
  return request({
    url: '/app/general',
    method: 'get'

  })
}
