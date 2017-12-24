/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : elastic

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2017-12-25 00:58:34
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
INSERT INTO `person` VALUES ('1', '广东广州', '10', '2017-12-01 10:45:45', '张三', '2017-12-06 10:45:48');
INSERT INTO `person` VALUES ('2', '广东深圳', '11', '2017-12-02 10:46:00', '李四', '2017-12-13 10:46:09');
INSERT INTO `person` VALUES ('3', '广东广州', '10', '2017-12-14 10:56:05', '张恒', '2017-12-13 10:56:14');
INSERT INTO `person` VALUES ('4', '广东广州', '10', '2017-12-06 10:57:55', '张勤', '2017-12-07 10:58:04');
INSERT INTO `person` VALUES ('5', '广东广州', '10', '2017-12-14 10:58:17', '张天翼', '2017-12-05 10:58:28');
INSERT INTO `person` VALUES ('6', '广州', '10', '2017-12-06 21:51:23', '测试', '2017-12-15 21:51:33');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projectinfo
-- ----------------------------
INSERT INTO `projectinfo` VALUES ('1', '10000', '地铁五号线', '2017-12-01 11:25:07', '五号线地铁', '0', '1', '2017-12-13 11:25:21');
INSERT INTO `projectinfo` VALUES ('2', '1000', '珠江新城', '2017-12-02 11:25:35', '五号线-珠江新城', '1', '2', '2017-12-13 11:25:51');
INSERT INTO `projectinfo` VALUES ('3', '9000', '猎德站', '2017-12-05 12:31:38', '五号线-猎德站', '1', '2', '2017-12-12 12:31:51');
INSERT INTO `projectinfo` VALUES ('4', '1000', '车陂南', '2017-12-06 21:50:49', '五号线-车陂南地铁', '0', '1', '2017-12-13 21:50:56');
INSERT INTO `projectinfo` VALUES ('5', '10000', '测试日期', '2017-12-13 23:51:28', '日期格式', '0', '1', '2017-12-15 23:51:53');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
