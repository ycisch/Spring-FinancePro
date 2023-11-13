import request from '@/utils/request'

// 查询财务- 主要用于存储多个格之间的信息列表
export function getStaticInfo() {
  return request({
    url: '/system/static/getStaticInfo',
    method: 'get'
  })
}

export function getStaticInfoList(type) {
  return request({
    url: '/system/static/getStaticInfoList',
    method: 'get',
    params:{
      type:type,
    }
  })
}

export function getLineInfo(query) {
  return request({
    url: '/system/static/getLineInfo',
    method: 'get',
    params:query
  })
}

export function getLineInfoList(query) {
  return request({
    url: '/system/static/getLineInfoList',
    method: 'get',
    params:query
  })
}
