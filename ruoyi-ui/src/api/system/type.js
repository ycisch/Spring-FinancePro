import request from '@/utils/request'

// 查询格信息用于保存多个格 每个格都有自己的名称 类似于Excel列表
export function listType(query) {
  return request({
    url: '/system/type/list',
    method: 'get',
    params: query
  })
}

// 查询格信息用于保存多个格 每个格都有自己的名称 类似于Excel详细
export function getType(typeId) {
  return request({
    url: '/system/type/' + typeId,
    method: 'get'
  })
}

// 新增格信息用于保存多个格 每个格都有自己的名称 类似于Excel
export function addType(data) {
  return request({
    url: '/system/type',
    method: 'post',
    data: data
  })
}

// 修改格信息用于保存多个格 每个格都有自己的名称 类似于Excel
export function updateType(data) {
  return request({
    url: '/system/type',
    method: 'put',
    data: data
  })
}

// 删除格信息用于保存多个格 每个格都有自己的名称 类似于Excel
export function delType(typeId) {
  return request({
    url: '/system/type/' + typeId,
    method: 'delete'
  })
}


export function deleteSysTypeInfos(typeId) {
  return request({
    url: '/system/type/deleteSysTypeInfos/' + typeId,
    method: 'delete'
  })
}

// 修改格信息用于保存多个格 每个格都有自己的名称 类似于Excel
export function submitInfo(data) {
  return request({
    url: '/system/type/submitInfo',
    method: 'post',
    data: data
  })
}

