import request from '@/utils/request'

// 查询表格信息
export function getTypeList(query) {
  return request({
    url: '/system/redis/getTypeList',
    method: 'get',
    params: query
  })
}
