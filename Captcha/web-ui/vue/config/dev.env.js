'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
   BASE_API: '"http://localhost:8080"'   //如果在本地启动server/springboot 打开此注释
  //BASE_API: '"https://captcha.anji-plus.com/captcha-api"'
})
