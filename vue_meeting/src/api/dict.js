import request from '@/utils/request'



export function getDictList(params) {
  return request({
    url: '/dict/list',
    method: 'get',
    params
  })
}


export function  saveDict(params){
  return request({
    url: '/dict',
    method: 'post',
    params
  })
}

