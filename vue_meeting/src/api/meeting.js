import request from '@/utils/request'

export function addMeeting(data) {
  return request({
    url: '/api/meeting/add',
    method: 'post',
    data
  })
}

export function getMeeting() {
  return request({
    url: '/api/meeting/day',
    method: 'get'
  })
}

export function deleteMeeting(id) {
  return request({
    url: '/api/meeting/delete',
    method: 'post',
    data: { id }
  })
}

export function updateMeeting(data) {
  return request({
    url: '/api/meeting/update',
    method: 'post',
    data
  })
}
