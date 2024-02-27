### Nacos

```shell
# tips: 先修改配置与导入`nacos/nacos-mysql.sql`
docker-compose -f docker-compose.yml -p nacos up -d
```

访问地址：[`ip地址:8848/nacos`](http://127.0.0.1:8848/nacos)
登录账号密码默认：`nacos/nacos`

> java连接密码安全认证

```yml
spring:
  cloud:
    nacos:
      discovery:
        username: nacos
        password: nacos
      config:
        username: ${spring.cloud.nacos.discovery.username}
        password: ${spring.cloud.nacos.discovery.password}
```

