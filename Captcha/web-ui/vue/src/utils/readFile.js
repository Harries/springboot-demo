import axios from '@/api/axios'

export const readFile = (filePath) => {
  return new Promise((resolve, reject) => {
    axios.get(filePath).then(res => {
      resolve(res)
    }).catch((err) => {
      reject(err)
    })
  })
}
