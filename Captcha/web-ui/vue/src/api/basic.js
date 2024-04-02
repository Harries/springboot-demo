import request from './axios'
// 登录
export function reqLogin(data) {
  return request({
    // url: 'user/noauth/login',
    url: '/base/accessUser/login',
    method: 'post',
    data: data
  })
}
// 登出
export function LogOut(data) {
  return request({
    url: '/base/accessUser/logout',
    method: 'post',
    data: data
  })
}
// 项目分页接口
export function queryByPage(data) {
  return request({
    // url: 'user/noauth/login',
    url: '/base/project/queryByPage',
    method: 'post',
    data: data
  })
}
// 项目创建
export function addCreate(data) {
  return request({
    url: '/base/project/create',
    method: 'post',
    data: data
  })
}
// 项目信息更新
export function UpdataPro(data) {
  return request({
    url: '/base/project/update',
    method: 'post',
    data: data
  })
}
// 顶部项目列表
export function GetTopPro(data) {
  return request({
    url: '/base/project/selectTop4',
    method: 'post',
    data: data
  })
}
// 拖拽排序
export function DndSort(data) {
  return request({
    url: '/base/sort/setListSort',
    method: 'post',
    data: data
  })
}
// 项目置顶接口
export function SetTop(data) {
  return request({
    url: '/base/sort/setTop',
    method: 'post',
    data: data
  })
}
// 获得导航
export function getMenu() {
  return request({
    url: 'auth/menu/getList',
    method: 'post'
  })
}
export function loadOption(data1) {
  const data = {
    pageNum: 1,
    pageSize: 30,
    parameter: data1.row.code
  }
  request({
    url: 'dataDic/getValueList',
    method: 'post',
    data: data
  }).then(res => {
    return res.repData
  }).catch(error => {
    self.msgError(error.repMsg)
  })
}

// 下拉框数据
export function loadDicValList(data) {
  return request({
    url: 'dataDic/getValueList',
    method: 'post',
    data: data
  })
}

// delProject
export function delProject(data) {
  return request({
    url: '/business/project/delete/' + data,
    method: 'post',
    data: data
  })
}

// 前端所属项目下拉接口
export function queryForProjectSelect() {
  return request({
    url: '/base/project/queryForProjectSelect',
    method: 'post'
  })
}

// 项目Id查询
export function projectQueryById(data) {
  return request({
    url: '/base/project/queryById',
    method: 'post',
    data: data
  })
}

// 前端select组件接口
export function queryForCodeSelect() {
  return request({
    url: '/base/baseCode/queryForCodeSelect',
    method: 'post'
  })
}
