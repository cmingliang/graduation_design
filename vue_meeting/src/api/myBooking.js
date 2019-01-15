import request from '@/utils/request'

export function getBooking() {
  return request({
    url: '/api/mybooking',
    method: 'get'
  })
}
