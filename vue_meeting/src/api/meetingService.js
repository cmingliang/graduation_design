import request from '@/utils/request'

export function addMeetingService(data) {
  return request({
    url: '/api/meetingService/add',
    method: 'post',
    data
  })
}

export function getMeetingService() {
  return request({
    url: '/api/meetingService/get',
    method: 'get'
  })
}

export function deleteMeetingService(id) {
  return request({
    url: '/api/meetingService/delete',
    method: 'post',
    data: { id }
  })
}

export function updateMeetingService(data) {
  return request({
    url: '/api/meetingService/update',
    method: 'post',
    data
  })
}
