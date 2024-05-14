### mysql database
```shell
docker run --name docker-mysql-5.7 -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:5.7
```



### init data

```
create database demo;


DROP TABLE IF EXISTS `jwt_user`; 
CREATE TABLE `jwt_user`(
 `id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户ID',
 `username` varchar(100) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '登录账号',
 `password` varchar(255) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '密码'
)ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '用户表' ROW_FORMAT = Compact;

INSERT INTO jwt_user VALUES('1','admin','123');


-- ----------------------------
-- Table structure for menu
-- ----------------------------
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '/db/**');
INSERT INTO `menu` VALUES ('2', '/admin/**');
INSERT INTO `menu` VALUES ('3', '/user/**');
 
-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
CREATE TABLE `menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES ('1', '1', '1');
INSERT INTO `menu_role` VALUES ('2', '2', '2');
INSERT INTO `menu_role` VALUES ('3', '3', '3');
 
-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `nameZh` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'dba', '数据库管理员');
INSERT INTO `role` VALUES ('2', 'admin', '系统管理员');
INSERT INTO `role` VALUES ('3', 'user', '用户');
 
-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of user
-- password is 123
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('2', 'admin', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('3', 'sang', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
 
-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '1', '2');
INSERT INTO `user_role` VALUES ('3', '2', '2');
INSERT INTO `user_role` VALUES ('4', '3', '3');

```
### remark
```
msyql account:root
mysql password:123456
```

