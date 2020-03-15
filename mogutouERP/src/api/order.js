import request from '@/utils/request'

export function getCustormerOrders() {
  return request({
    url: '/orders/custormer',
    method: 'get'
  })
}

export function getPurchaseOrders() {
  return request({
    url: '/orders/purchase',
    method: 'get'
  })
}

export function createPurchaseOrder(data) {
  return request({
    url: '/order/purchase',
    method: 'post',
    data: data
  })
}

export function createCustormerOrder(data) {
  return request({
    url: '/order/custormer',
    method: 'post',
    data: data
  })
}

export function deletePurchaseOrder(id) {
  return request({
    url: '/order/purchase/' + id,
    method: 'delete'
  })
}

export function deleteCustormerOrder(data) {
  return request({
    url: '/order/custormer',
    method: 'delete',
    data: data
  })
}

export function updateCustomerOrder(data) {
  return request({
    url: '/order/custormer/update',
    method: 'post',
    data: data
  })
}

export function confirmPurchaseOrder(id, data) {
  return request({
    url: '/order/purchase/' + id + '/confirm',
    method: 'patch',
    data: data
  })
}

export function confirmCustormerOrder(data) {
  return request({
    url: '/order/custormer/confirm',
    method: 'post',
    data: data
  })
}
