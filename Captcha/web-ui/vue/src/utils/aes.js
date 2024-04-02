import CryptoJS from 'crypto-js'
export function aesEncrypt(word) {
  var key = CryptoJS.enc.Utf8.parse('XwKsGlMcdPMEhR1B')
  var srcs = CryptoJS.enc.Utf8.parse(word)
  var encrypted = CryptoJS.AES.encrypt(srcs, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 })
  return encrypted.toString()
}
export function aesDecrypt(word) {
  var key = CryptoJS.enc.Utf8.parse('XwKsGlMcdPMEhR1B')
  var decrypt = CryptoJS.AES.decrypt(word, key, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 })
  return CryptoJS.enc.Utf8.stringify(decrypt).toString()
}
