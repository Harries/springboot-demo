export function setItem(k, v) {
  if (typeof (v) == 'undefined' || v == null) {
    return
  }

  var val = v
  if (typeof (v) == 'object') {
    val = JSON.stringify(v)
  }
  sessionStorage.setItem(k, val)
}

export function getItem(k) {
  var val = sessionStorage.getItem(k)
  try {
    // 如果是number boolean jsonstring是不会报错的
    return JSON.parse(val)
  } catch (e) {
    return val
  }
}
