import request from '@/utils/request'

export function getMyOrderList(params) {
  return request({
    url: '/order/me',
    method: 'get',
    params
  })
}


export function getOrderList(params) {
  return request({
    url: '/order',
    method: 'get',
    params
  })
}

export function getFinanceOrderList(params) {
  return request({
    url: '/order/finance',
    method: 'get',
    params
  })
}

export function getOrderTrail(id) {
  return request({
    url: '/order/trail',
    method: 'get',
    params:{id:id}
  })
}

export function getOrderInfo(id) {
  return request({
    url: '/order/'+id,
    method: 'get'

  })
}
export function getOrderPaymentInfo(id) {
  return request({
    url: '/order/payment/'+id,
    method: 'get'

  })
}

export function renew(params) {
  return request({
    url: '/order/renew',
    method: 'get',
    params
  })
}

export function deleteOrder(id) {
  return request({
    url: '/order/'+id,
    method: 'delete'
  })
}

export function quotedPrice(params) {
  return request({
    url: '/order/quotedPrice',
    method: 'post',
    params
  })
}

export function checkPrice(params) {
  return request({
    url: '/order/checkPrice',
    method: 'post',
    params
  })
}

export function confirm(params) {
  return request({
    url: '/order/confirm',
    method: 'post',
    params
  })
}
