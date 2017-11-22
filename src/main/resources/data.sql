/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2017-11-22 21:18:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '广东省广州市', '张三');
INSERT INTO `person` VALUES ('2', '广东省深圳市', '李四');
INSERT INTO `person` VALUES ('3', '广东省广州市', '王五');
INSERT INTO `person` VALUES ('4', '广州', '张琦');
INSERT INTO `person` VALUES ('5', '广州', '张子琪');
INSERT INTO `person` VALUES ('6', '广州', '张3');
INSERT INTO `person` VALUES ('7', '广州', '张兴');
INSERT INTO `person` VALUES ('8', '广州', '张光');
