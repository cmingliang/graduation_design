import request from '@/utils/request'



export function getLogList(params) {
  return request({
    url: '/log',
    method: 'get',
    params
  })
}

