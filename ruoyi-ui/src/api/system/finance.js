import request from '@/utils/request'

// 查询财务- 主要用于存储多个格之间的信息列表
export function listFinance(query) {
  return request({
    url: '/system/finance/list',
    method: 'get',
    params: query
  })
}

// 查询财务格- 主要用于存储多个格之间的信息详细
export function getFinance(financeId) {
  return request({
    url: '/system/finance/' + financeId,
    method: 'get'
  })
}

// 新增财务格- 主要用于存储多个格之间的信息
export function addFinance(data) {
  return request({
    url: '/system/finance',
    method: 'post',
    data: data
  })
}

// 修改财务格- 主要用于存储多个格之间的信息
export function updateFinance(data) {
  return request({
    url: '/system/finance',
    method: 'put',
    data: data
  })
}

// 删除财务格- 主要用于存储多个格之间的信息
export function delFinance(financeId) {
  return request({
    url: '/system/finance/' + financeId,
    method: 'delete'
  })
}
