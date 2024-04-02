// 加密数据函数 工具crypto.js 文件工具
function aesEncrypt(word){
  var key = CryptoJS.enc.Utf8.parse("XwKsGlMcdPMEhR1B");
  var srcs = CryptoJS.enc.Utf8.parse(word);
  var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
  return encrypted.toString();
}
