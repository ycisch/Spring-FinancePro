import request from '@/utils/request'

// 查询类型的其他字段列表
export function listInfo(query) {
  return request({
    url: '/system/info/list',
    method: 'get',
    params: query
  })
}

// 查询类型的其他字段详细
export function getInfo(infoId) {
  return request({
    url: '/system/info/' + infoId,
    method: 'get'
  })
}

// 新增类型的其他字段
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}

// 修改类型的其他字段
export function updateInfo(data) {
  return request({
    url: '/system/info',
    method: 'put',
    data: data
  })
}

// 删除类型的其他字段
export function delInfo(infoId) {
  return request({
    url: '/system/info/' + infoId,
    method: 'delete'
  })
}
export function getFinanceBasicInfo(query) {
  return request({
    url: '/system/info/getFinanceBasicInfo',
    method: 'get',
    params: query
  })
}

