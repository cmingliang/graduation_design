import request from '@/utils/request'

export function joinMeeting(data) {
  return request({
    url: '/api/meetingUser/join',
    method: 'post',
    data
  })
}

export function getUserState() {
  return request({
    url: '/api/meetingUser',
    method: 'get'
  })
}
