import request from '@/utils/request'

export function customerList(params) {
  return request({
    url: '/customer',
    method: 'get',
    params
  })
}
