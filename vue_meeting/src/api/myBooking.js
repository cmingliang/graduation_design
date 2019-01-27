import request from '@/utils/request'

export function getBooking() {
  return request({
    url: '/api/mybooking',
    method: 'get'
  })
}

export function deleteBooking(id) {
  return request({
    url: '/api/mybooking',
    method: 'post',
    data: { id }
  })
}
