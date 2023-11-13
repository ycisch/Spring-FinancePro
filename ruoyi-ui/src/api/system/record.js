import request from '@/utils/request'

// 查询结账记录格列表
export function listRecord(query) {
  return request({
    url: '/system/record/list',
    method: 'get',
    params: query
  })
}

// 查询结账记录格详细
export function getRecord(id) {
  return request({
    url: '/system/record/' + id,
    method: 'get'
  })
}

// 新增结账记录格
export function addRecord(data) {
  return request({
    url: '/system/record',
    method: 'post',
    data: data
  })
}

// 修改结账记录格
export function updateRecord(data) {
  return request({
    url: '/system/record',
    method: 'put',
    data: data
  })
}

// 删除结账记录格
export function delRecord(data) {
  return request({
    url: '/system/record/deleteRecord',
    method: 'get',
    params:data
  })
}
