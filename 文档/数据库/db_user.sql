/*
SQLyog v10.2
MySQL - 5.5.49 : Database - tensquare_user
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tensquare_user`;

/*Table structure for table `tb_admin` */

DROP TABLE IF EXISTS `tb_admin`;

CREATE TABLE `tb_admin` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `role` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `loginname` varchar(100) DEFAULT NULL COMMENT '登陆名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `operation` varchar(100)  DEFAULT NULL COMMENT '操作',
  `create_date` Date  DEFAULT NULL COMMENT '创建日期',
  `update_date` Date  DEFAULT NULL COMMENT '更新日期',
  `birthday` Date  DEFAULT NULL COMMENT '生日',
  `login_date` Date  DEFAULT NULL COMMENT '登录日期',
  `avatar` varchar(100)  DEFAULT NULL COMMENT '头像',
  `address` varchar(100)  DEFAULT NULL COMMENT '地址',
  `login_site` varchar(100)  DEFAULT NULL COMMENT '登录地点',
  `email` varchar(100)  DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

/*Data for the table `tb_admin` */

/*Table structure for table `tb_follow` */

DROP TABLE IF EXISTS `tb_follow`;

CREATE TABLE `tb_follow` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `targetuser` varchar(20) NOT NULL COMMENT '被关注用户ID',
  PRIMARY KEY (`userid`,`targetuser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_follow` */

insert  into `tb_follow`(`userid`,`targetuser`) values ('1','1');
insert  into `tb_follow`(`userid`,`targetuser`) values ('1','10');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `followme` varchar(20) DEFAULT NULL comment '是否关注我的动态',
  `email` varchar(100) DEFAULT NULL COMMENT 'E-Mail',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `updatedate` datetime DEFAULT NULL COMMENT '修改日期',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登陆日期',
  `online` bigint(20) DEFAULT NULL COMMENT '在线时长（分钟）',
  `interest` varchar(100) DEFAULT NULL COMMENT '兴趣',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月日',
  `personality` varchar(100) DEFAULT NULL COMMENT '个性',
  `fanscount` int(20) DEFAULT NULL COMMENT '粉丝数',
  `followcount` int(20) DEFAULT NULL COMMENT '关注数',
  `github` varchar(100) DEFAULT NULL COMMENT 'github地址',
  `csdn` varchar(100) DEFAULT NULL COMMENT 'csdn地址',
  `other_account` varchar(100) DEFAULT NULL COMMENT '其他账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

/*Data for the table `tb_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;