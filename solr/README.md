# springboot-solr

#### 一、项目介绍
Springboot2.1+Solr7.5搭建的企业级搜索平台，项目目前支持文档内容和数据库检索，已经集成分词技术。支持文档内容检索类型包含：pdf、doc、docx、ppt、pptx、txt、log等<br/>
数据库已支持MySQL增量自动建立索引，如果帮到您，麻烦点下Star，谢谢。

Springboot2.1.1+elasticsearch6.5.3搭建的企业级搜索平台。开源地址：
[https://gitee.com/11230595/springboot-elasticsearch](https://gitee.com/11230595/springboot-elasticsearch)

#### 二、软件架构
软件架构说明
1. SpringBoot2.1
2. Solr7.5
3. thymeleaf
4. webflux

*项目分为两个应用，其中search已经集成tomcat、Solr已经集成jetty*

#### 三、部署教程

1. solr安装启动<br/>
进入 ```solr-7.5.0/bin```目录<br/>
执行 ```solr start```<br/>
*说明：solr运行，依赖JDK8*<br/>
2. solr控制台<br/>
```http://localhost:8983/solr```，如果可以顺利打开，说明solr启动成功。
3. solr停止
进入 ```solr-7.5.0/bin```目录<br/>
执行 ```solr stop -p 8983```<br/>
4. search项目启动
search项目为普通Springboot项目，下载后将源码导入到IDE，在SearchApplication右键--> Run ...即可。

#### 四、Solr配置说明

1. Solr MySQL相关
进入 ```solr-7.5.0/server/solr/test_core/conf``` <br/>
```db-data-config.xml``` -> 需要索引的表配置<br/>
```managed-schema```     -> 需要索引的字段配置<br/>
2. Solr MySQL 数据自动增量同步配置说明
进入 ``` solr-7.5.0/server/solr/conf/```
```dataimport.properties``` -> 自动同步数据相关配置
3. Solr 文件检索配置
进入 ```solr-7.5.0/server/solr/file_core/conf```<br/>
```tika-data-config.xml``` -> 索引文件目录及类型配置<br/>
```managed-schema```     -> 需要索引的字段配置<br/>

#### 五、search配置说明

1. 配置文件
``` src/main/resources/application.properties ```<br/>
本配置文件目前只配置了三部分。
- solr 链接
- 静态资源路径
- thymeleaf
- 端口，文根等其他配置请可自行配置

#### 六、系统截图
##### - 数据库搜索截图
![image](https://images.gitee.com/uploads/images/2018/1201/141756_e5dd6af9_499215.png)
![image](https://images.gitee.com/uploads/images/2018/1201/141946_cf041593_499215.png)
##### - 文件搜索截图
![image](https://images.gitee.com/uploads/images/2018/1201/141958_d39a76cd_499215.png)
![image](https://images.gitee.com/uploads/images/2018/1201/142011_784d8f11_499215.png)

#### 七、联系开发者
```zhoudong611@163.com```

#### 八、QQ群：83402555

#### 九、关注公众号
![image](https://images.gitee.com/uploads/images/2018/1210/122022_148f50d8_499215.jpeg)

