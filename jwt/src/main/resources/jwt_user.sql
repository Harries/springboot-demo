DROP TABLE IF EXISTS `jwt_user`; 
CREATE TABLE `jwt_user`(
 `id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户ID',
 `username` varchar(100) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '登录账号',
 `password` varchar(255) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '密码'
)ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '用户表' ROW_FORMAT = Compact;

INSERT INTO jwt_user VALUES('1','admin','123');