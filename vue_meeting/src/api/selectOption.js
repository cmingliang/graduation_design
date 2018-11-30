import request from '@/utils/request'

export function loadSelectOption(dictName) {
  return request({
    url: '/dict',
    method: 'get',
    params:{dictName:dictName}
  })
}
