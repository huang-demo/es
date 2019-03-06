/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : elastic

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-01-06 01:22:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
insert  into `person`(`id`,`address`,`age`,`createTime`,`name`,`updateTime`) values
(1,'广东广州',10,'2017-12-01 10:45:45','张三','2017-12-06 10:45:48'),
(2,'广东深圳',11,'2017-12-02 10:46:00','李四','2017-12-13 10:46:09'),
(3,'广东广州',10,'2017-12-14 10:56:05','张恒','2017-12-13 10:56:14'),
(4,'广东广州',10,'2017-12-06 10:57:55','张勤','2017-12-07 10:58:04'),
(5,'广东广州',10,'2017-12-14 10:58:17','张天翼','2017-12-05 10:58:28'),
(6,'广东广州',10,'2017-12-06 21:51:23','测试','2017-12-15 21:51:33'),
(7,'广东广州',1,'2019-03-06 18:31:12','张秀英','2017-12-06 21:51:23'),
(8,'广东广州',12,'2019-03-06 18:31:15','刘伟','2017-12-06 21:51:23'),
(9,'广东广州',11,'2017-12-06 21:51:23','王静','2017-12-06 21:51:23'),
(10,'广东广州',12,'2017-12-06 21:51:23','王丽','2017-12-06 21:51:23'),
(11,'广东广州',11,'2017-12-06 21:51:23','王芳','2017-12-06 21:51:23'),
(12,'广东广州',11,'2017-12-06 21:51:23','刘伟','2017-12-06 21:51:23'),
(13,'广东广州',12,'2017-12-06 21:51:23','王芳芳','2017-12-06 21:51:23');


-- ----------------------------
-- Table structure for `projectinfo`
-- ----------------------------
DROP TABLE IF EXISTS `projectinfo`;
CREATE TABLE `projectinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `projectType` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projectinfo
-- ----------------------------
INSERT INTO `projectinfo` VALUES ('1', '10000', '地铁五号线', '2017-12-01 11:25:07', '五号线地铁', '0', '1', '2017-12-13 11:25:21');
INSERT INTO `projectinfo` VALUES ('2', '1000', '珠江新城', '2017-12-02 11:25:35', '五号线-珠江新城', '1', '2', '2017-12-13 11:25:51');
INSERT INTO `projectinfo` VALUES ('3', '9000', '猎德站', '2017-12-05 12:31:38', '五号线-猎德站', '1', '2', '2017-12-12 12:31:51');
INSERT INTO `projectinfo` VALUES ('4', '1000', '车陂南站', '2017-12-06 21:50:49', '五号线-车陂南地铁', '0', '1', '2017-12-13 21:50:56');
INSERT INTO `projectinfo` VALUES ('5', '10000', '测试日期', '2017-12-13 23:51:28', '日期格式', '0', '1', '2017-12-15 23:51:53');
INSERT INTO `projectinfo` VALUES ('6', '100000', '火车站', '2017-12-06 16:00:08', '五号线-火车东站', '1', '2', '2017-12-13 16:00:49');
INSERT INTO `projectinfo` VALUES ('7', '100000', '四号线', '2017-12-05 16:01:11', '四号线', '0', '1', '2017-12-06 16:01:36');
INSERT INTO `projectinfo` VALUES ('8', '10000', '黄村站', '2017-12-06 16:01:53', '四号线-黄村站', '7', '2', '2017-12-13 16:02:12');
INSERT INTO `projectinfo` VALUES ('9', '10000', '车陂站', '2017-12-06 16:02:27', '四号线-车陂站', '7', '2', '2017-12-05 16:02:43');
INSERT INTO `projectinfo` VALUES ('10', '10000', '车陂南站', '2017-12-01 16:03:05', '四号线-车陂南站', '7', '2', '2017-12-13 16:03:20');
INSERT INTO `projectinfo` VALUES ('11', '1000', '', '2017-12-01 16:19:56', 'test adc', '0', '1', '2017-12-06 16:20:04');
INSERT INTO `projectinfo` VALUES ('12', '10', 'test', '2017-12-06 16:20:35', 'test', '0', '1', '2017-12-07 16:20:43');
INSERT INTO `projectinfo` VALUES ('13', '1000', 'test', '2018-01-04 23:10:53', 'test', '0', '1', '2018-01-04 23:11:06');
INSERT INTO `projectinfo` VALUES ('14', '1000', '火车站-1', '2018-01-04 23:11:30', '五号线-火车东站', '0', '1', '2018-01-04 23:11:51');

-- ----------------------------
-- Table structure for `transactions`
-- ----------------------------
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `sold` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transactions
-- ----------------------------
INSERT INTO `transactions` VALUES ('1', 'red', 'honda', '10000', '2014-10-28 00:00:00');
INSERT INTO `transactions` VALUES ('2', 'red', 'honda', '20000', '2014-11-05 00:00:00');
INSERT INTO `transactions` VALUES ('3', 'green', 'ford', '30000', '2014-05-18 00:00:00');
INSERT INTO `transactions` VALUES ('4', 'blue', 'toyota', '15000', '2014-07-02 00:00:00');
INSERT INTO `transactions` VALUES ('5', 'green', 'toyota', '12000', '2014-08-19 00:00:00');
INSERT INTO `transactions` VALUES ('6', 'red', 'honda', '20000', '2014-11-05 00:00:00');
INSERT INTO `transactions` VALUES ('7', 'red', 'bmw', '80000', '2014-01-01 00:00:00');
INSERT INTO `transactions` VALUES ('8', 'blue', 'ford', '25000', '2014-02-12 00:00:00');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '广州', '张三', '2017-12-01 22:34:21', '2017-12-02 22:34:25', '1');
INSERT INTO `userinfo` VALUES ('2', '广州', '李四', '2017-12-02 22:34:42', '2017-12-02 22:34:46', '1');
