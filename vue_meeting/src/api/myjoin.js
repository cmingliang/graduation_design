import request from '@/utils/request'

export function getJoin() {
  return request({
    url: '/api/myjoin',
    method: 'get'
  })
}
