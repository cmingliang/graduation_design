import request from '@/utils/request'

export function addMeetingRoom(data) {
  return request({
    url: '/api/meetingRoom/add',
    method: 'post',
    data
  })
}

export function getMeetingRoom() {
  return request({
    url: '/api/meetingRoom/get',
    method: 'get'
  })
}

export function deleteMeetingRoom(id) {
  return request({
    url: '/api/meetingRoom/delete',
    method: 'post',
    data: { id }
  })
}

export function updateMeetingRoom(data) {
  return request({
    url: '/api/meetingRoom/update',
    method: 'post',
    data
  })
}
