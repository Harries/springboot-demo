/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : orders

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-06 10:56:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `ID` varchar(40) NOT NULL,
  `PRODUCT_NAME` varchar(255) NOT NULL COMMENT '产品标题',
  `PRODUCT_DESC` varchar(255) NOT NULL COMMENT '产品描述',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '苹果电脑', '2018款Mac苹果电脑512G', '2018-11-30 10:07:40', null);
INSERT INTO `product` VALUES ('10', '优衣库夹克', '男装 摇粒绒拉链茄克(长袖) 408993 优衣库UNIQLO', '2018-11-30 10:28:37', null);
INSERT INTO `product` VALUES ('11', '优衣库裤子', '男装 仿羊羔绒运动长裤 409050 优衣库UNIQLO', '2018-11-30 10:28:59', null);
INSERT INTO `product` VALUES ('12', '资生堂uno洗面奶', '资生堂uno洗面奶吾诺男士润泽温和洁面乳+炭活洁面', '2018-11-30 10:32:06', null);
INSERT INTO `product` VALUES ('13', '资生堂Moilip药用润唇膏', 'Shiseido/资生堂Moilip药用润唇膏 改善口角炎缓解唇炎 滋润保湿', '2018-11-30 10:32:26', null);
INSERT INTO `product` VALUES ('14', '京润珍珠微米纯珍珠粉100g', '京润珍珠微米纯珍珠粉100g 美白补水外用面膜粉保湿润泽养肤女', '2018-11-30 10:34:18', null);
INSERT INTO `product` VALUES ('15', '美肤宝美白隔离', '美肤宝美白隔离防晒霜防紫外线面部遮瑕补水保湿套装女男学生军训美肤宝美白隔离防晒霜防紫外线面部遮瑕补水保湿套装女男学生军训\r\n美肤宝美白隔离防晒霜防紫外线面部遮瑕补水保湿套装女男学生军训\r\n', '2018-11-30 10:34:45', null);
INSERT INTO `product` VALUES ('16', 'Herborist/佰草集新七白大白泥美白嫩肤面膜泥500g', 'Herborist/佰草集新七白大白泥美白嫩肤面膜泥500g 焕白补水保湿\r\n深透补水，焕白肌肤，淡化肌肤斑点。', '2018-11-30 10:35:12', null);
INSERT INTO `product` VALUES ('17', 'Opera/娥佩兰薏仁水', '日本进口Opera/娥佩兰薏仁水美白保湿化妆水 500ml*2瓶', '2018-11-30 10:36:23', null);
INSERT INTO `product` VALUES ('18', 'LION/狮王', 'LION/狮王 齿力佳日本进口酵素美白牙膏130g*3支去牙渍亮白牙齿', '2018-11-30 10:36:46', null);
INSERT INTO `product` VALUES ('19', '自然共和国芦荟胶', '自然共和国芦荟胶正品祛痘淡印膏补水保湿去痘印面霜男女身体乳', '2018-11-30 10:37:45', null);
INSERT INTO `product` VALUES ('2', '苹果手机', 'iPhone XS Max 265G 最新款苹果手机', '2018-11-30 10:22:33', null);
INSERT INTO `product` VALUES ('20', '佰草世家面膜', '佰草世家面膜补水保湿美白提亮肤色男女学生正品祛斑祛痘收缩毛孔', '2018-11-30 10:38:16', null);
INSERT INTO `product` VALUES ('21', '荣耀8X', '千元屏霸 91%屏占比 2000万AI双摄 4GB+64GB 幻夜黑 移动联通电信4G全面屏手机 双卡双待', '2018-11-30 11:05:15', null);
INSERT INTO `product` VALUES ('22', '小辣椒手机', '小辣椒 红辣椒7X 4+64GB 学生智能手机 美颜双摄 微Q多开 人脸识别 移动联通电信4G全网通 蓝色', '2018-11-30 11:05:38', null);
INSERT INTO `product` VALUES ('23', '小米 8SE', '小米 8SE 全面屏智能手机 6GB+128GB 红色 全网通4G 双卡双待 拍照手机 游戏手机', '2018-11-30 11:05:56', null);
INSERT INTO `product` VALUES ('24', 'OPPO K1', '光感屏幕指纹 水滴屏拍照手机 6G+64G 摩卡红 全网通 移动联通电信4G 双卡双待手机', '2018-11-30 11:06:26', null);
INSERT INTO `product` VALUES ('25', '小米6X', '全网通 4GB+64GB 曜石黑 移动联通电信4G手机 双卡双待 智能手机 拍照手机', '2018-11-30 11:06:45', null);
INSERT INTO `product` VALUES ('26', '荣耀MagicBook', '14英寸轻薄窄边框笔记本电脑（intel八代酷睿i7-8550U 8G 256G MX150 2G独显）冰河银', '2018-11-30 11:07:47', null);
INSERT INTO `product` VALUES ('27', '神舟 (HASEE) 战神', '神舟 (HASEE) 战神Z7M-KP7GC GTX1050Ti 4G 15.6英寸游戏笔记本电脑（I7-8750H 8G 1T+128G SSD）IPS', '2018-11-30 11:08:05', null);
INSERT INTO `product` VALUES ('28', '戴尔灵越14 燃', '14英寸轻薄窄边框笔记本电脑(i5-8265U 8G 256GSSD MX150 2G独显 背光键盘）冰河银', '2018-11-30 11:08:40', null);
INSERT INTO `product` VALUES ('29', '联想(Lenovo)拯救者Y7000', '英特尔八代酷睿15.6游戏笔记本电脑(i7-8750H 8G 1T+128G GTX1050Ti 72%NTSC 黑)', '2018-11-30 11:09:00', null);
INSERT INTO `product` VALUES ('3', '宏碁笔记本', '14寸宏碁笔记本电脑', '2018-11-30 10:23:07', null);
INSERT INTO `product` VALUES ('30', '惠普（HP）暗影精灵4代', '15.6英寸游戏笔记本电脑（i5-8300H 8G 128G+1TB GTX1050Ti 4G独显 IPS FHD）', '2018-11-30 11:09:24', null);
INSERT INTO `product` VALUES ('31', '资生堂（Shiseido）', '资生堂（Shiseido）红妍肌活精华露（傲娇红腰子） 30ml套装', '2018-11-30 11:11:58', null);
INSERT INTO `product` VALUES ('32', '资生堂(SHISEIDO) uno吾诺 男士润肤乳', '日本进口 资生堂(SHISEIDO) uno吾诺 男士润肤乳 滋养型 160ml/瓶 滋养嫩滑', '2018-11-30 11:12:19', null);
INSERT INTO `product` VALUES ('33', '资生堂水之密语（AQUAIR）', '资生堂水之密语（AQUAIR）净澄水活(倍润型)洗护套装(洗发水600ml*2+护发素600ml*2)', '2018-11-30 11:12:36', null);
INSERT INTO `product` VALUES ('34', '资生堂(SHISEIDO) 尿素红罐护手霜', '日本进口 资生堂(SHISEIDO) 尿素红罐护手霜 100g/罐 男女通用 深层滋养 改善粗糙', '2018-11-30 11:12:56', null);
INSERT INTO `product` VALUES ('35', '资生堂（Shiseido）悦薇珀翡紧颜亮肤水/乳', '资生堂（Shiseido）悦薇珀翡紧颜亮肤水/乳 滋润型 清爽型 亮肤水 150ml', '2018-11-30 11:13:23', null);
INSERT INTO `product` VALUES ('36', '无印良品 MUJI 基础润肤化妆水', '无印良品 MUJI 基础润肤化妆水 高保湿型 200ml', '2018-11-30 11:14:01', null);
INSERT INTO `product` VALUES ('37', '无印良品 MUJI 超声波香薰机', '无印良品 MUJI 超声波香薰机 白色', '2018-11-30 11:14:38', null);
INSERT INTO `product` VALUES ('38', '无印良品 MUJI 大容量平衡高保湿化妆水', '无印良品 MUJI 大容量平衡高保湿化妆水', '2018-11-30 11:14:59', null);
INSERT INTO `product` VALUES ('39', '无印良品 MUJI 柔和洁面泡沫', '无印良品 MUJI 柔和洁面泡沫 200g', '2018-11-30 11:15:17', null);
INSERT INTO `product` VALUES ('4', '联想台式机', '商务工作台式机', '2018-11-30 10:23:53', null);
INSERT INTO `product` VALUES ('40', '无印良品 MUJI 小型超声波香薰机', '无印良品 MUJI 小型超声波香薰机 其他', '2018-11-30 11:15:36', null);
INSERT INTO `product` VALUES ('41', '荣耀 V10 尊享版', '荣耀 V10 尊享版 6GB+128GB 幻夜黑 移动联通电信4G全面屏游戏手机 双卡双待', '2018-11-30 11:17:07', null);
INSERT INTO `product` VALUES ('42', '【预售】荣耀10青春版系列', '【预售】荣耀10青春版系列 全网通版4GB+64GB 幻夜黑 移动联通电信4G全面屏手机 双卡双待', '2018-11-30 11:17:25', null);
INSERT INTO `product` VALUES ('43', '荣耀9i 4GB+64GB', '荣耀9i 4GB+64GB 碧玉青 移动联通电信4G全面屏手机 双卡双待', '2018-11-30 11:17:42', null);
INSERT INTO `product` VALUES ('5', '三星S9', 'Android机皇', '2018-11-30 10:24:19', null);
INSERT INTO `product` VALUES ('6', 'YSL圣罗兰纯口红', '方管正红色1星星色52正橘色13圣诞限量版 正品', '2018-11-30 10:25:24', null);
INSERT INTO `product` VALUES ('7', '美宝莲眼唇卸妆液', '脸部眼部及唇部卸妆油卸妆水温和清洁', '2018-11-30 10:26:27', null);
INSERT INTO `product` VALUES ('8', 'MUJI无印良品敏感肌用乳液', '日本 MUJI无印良品敏感肌用乳液200ml孕妇婴儿/清爽滋润高保湿', '2018-11-30 10:27:06', null);
INSERT INTO `product` VALUES ('9', '无印良品水洗棉四件套', '无印良品水洗棉四件套全棉床上用品日式床单被套纯棉简约床笠ins', '2018-11-30 10:27:38', null);
