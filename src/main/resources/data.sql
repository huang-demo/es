/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.22-log : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `hibernate_sequence` */

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `person` */

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

/*Table structure for table `projectinfo` */

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

/*Data for the table `projectinfo` */

insert  into `projectinfo`(`id`,`amount`,`content`,`createTime`,`name`,`pid`,`projectType`,`updateTime`) values 
(1,10000,'地铁五号线','2017-12-01 11:25:07','五号线地铁',0,1,'2017-12-13 11:25:21'),
(2,1000,'珠江新城','2017-12-02 11:25:35','五号线-珠江新城',1,2,'2017-12-13 11:25:51'),
(3,9000,'猎德站','2017-12-05 12:31:38','五号线-猎德站',1,2,'2017-12-12 12:31:51'),
(4,1000,'车陂南站','2017-12-06 21:50:49','五号线-车陂南地铁',0,1,'2017-12-13 21:50:56'),
(5,10000,'测试日期','2017-12-13 23:51:28','日期格式',0,1,'2017-12-15 23:51:53'),
(6,100000,'火车站','2017-12-06 16:00:08','五号线-火车东站',1,2,'2017-12-13 16:00:49'),
(7,100000,'四号线','2017-12-05 16:01:11','四号线',0,1,'2017-12-06 16:01:36'),
(8,10000,'黄村站','2017-12-06 16:01:53','四号线-黄村站',7,2,'2017-12-13 16:02:12'),
(9,10000,'车陂站','2017-12-06 16:02:27','四号线-车陂站',7,2,'2017-12-05 16:02:43'),
(10,10000,'车陂南站','2017-12-01 16:03:05','四号线-车陂南站',7,2,'2017-12-13 16:03:20'),
(11,1000,'','2017-12-01 16:19:56','test adc',0,1,'2017-12-06 16:20:04'),
(12,10,'test','2017-12-06 16:20:35','test',0,1,'2017-12-07 16:20:43'),
(13,1000,'test','2018-01-04 23:10:53','test',0,1,'2018-01-04 23:11:06'),
(14,1000,'火车站-1','2018-01-04 23:11:30','五号线-火车东站',0,1,'2018-01-04 23:11:51');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`url`,`name`) values 
(1,'/elasticPerson/searchBykw','ES按名称搜索'),
(2,'/person/insert','用户插入'),
(3,'/person/findByName','数据库按名称搜索'),
(4,'/person/queryPage','数据库用户列表'),
(5,'/project/bachAdd','批量导入es');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`type`) values 
(1,'系统管理员','100004'),
(2,'ES管理','100001'),
(3,'DB管理','100002');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`rid`,`pid`) values 
(1,3,3),
(2,3,4),
(4,3,2),
(5,2,1),
(6,2,5);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `password` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` tinyint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`email`,`password`,`create_time`,`last_login_time`,`status`,`salt`) values 
(1,'admin','admin@163.com','7B6C73B597DB0766EF1385C760925FD3','2019-05-19 17:01:02','2019-05-19 17:01:05',1,'_es'),
(2,'Elastic',NULL,'7B6C73B597DB0766EF1385C760925FD3','2019-05-19 17:02:25','2019-05-19 17:02:30',1,'_es'),
(3,'DB',NULL,'7B6C73B597DB0766EF1385C760925FD3','2019-05-19 17:02:44','2019-05-19 17:02:47',1,NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `rid` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`uid`,`rid`) values 
(1,1,1),
(2,2,2),
(3,3,3),
(4,1,2),
(5,1,3);

/*Table structure for table `transactions` */

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `sold` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `transactions` */

insert  into `transactions`(`id`,`color`,`make`,`price`,`sold`) values 
(1,'red','honda',10000,'2014-10-28 00:00:00'),
(2,'red','honda',20000,'2014-11-05 00:00:00'),
(3,'green','ford',30000,'2014-05-18 00:00:00'),
(4,'blue','toyota',15000,'2014-07-02 00:00:00'),
(5,'green','toyota',12000,'2014-08-19 00:00:00'),
(6,'red','honda',20000,'2014-11-05 00:00:00'),
(7,'red','bmw',80000,'2014-01-01 00:00:00'),
(8,'blue','ford',25000,'2014-02-12 00:00:00');

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`address`,`name`,`createtime`,`updatetime`,`sex`,`create_time`,`create_user`,`password`,`salt`) values 
(1,'广州','张三','2017-12-01 22:34:21','2017-12-02 22:34:25',1,NULL,NULL,NULL,NULL),
(2,'广州','李四','2017-12-02 22:34:42','2017-12-02 22:34:46',1,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
