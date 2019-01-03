import request from '@/utils/request'

export function getMeetingCheck() {
  return request({
    url: '/api/meetingCheck',
    method: 'get'
  })
}

export function acceptMeeting(id) {
  return request({
    url: '/api/acceptMeeting',
    method: 'post',
    data: { id }
  })
}

export function refuseMeeting(data) {
  return request({
    url: '/api/refuseMeeting',
    method: 'post',
    data
  })
}
