import request from '@/utils/request'

export function getLicenceList(params) {
  return request({
    url: '/licence',
    method: 'get',
    params
  })
}

export function  addLicence(params){
  return request({
    url: '/licence',
    method: 'post',
    params
  })
}

export function  addLicenceBatch(params){
  return request({
    url: '/licence/batch',
    method: 'post',
    headers: {'Content-Type': 'multipart/form-data'},
    data:params
  })
}


export function  downloadBatch(params){
  return request({
    url: '/licence/download',
    method: 'get',
    params
  })
}
