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
/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 132.232.104.247:3306
 Source Schema         : tensquare_user

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/07/2019 19:27:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;

CREATE TABLE `tb_admin` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `role` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `loginname` varchar(100) DEFAULT NULL COMMENT '登陆名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `operation` varchar(100) DEFAULT NULL COMMENT '操作',
  `create_date` date DEFAULT NULL COMMENT '创建日期',
  `update_date` date DEFAULT NULL COMMENT '更新日期',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `login_date` date DEFAULT NULL COMMENT '登录日期',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `login_site` varchar(100) DEFAULT NULL COMMENT '登录地点',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `blogger` varchar(10) DEFAULT NULL,
  `motto` varchar(100) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
BEGIN;
INSERT INTO `tb_admin` VALUES ('1145697086432481280', 'ROLE_ADMIN', '啦啦啦', 'admin', '$2a$10$02/AZy8iiDMnOZHFfGJXb.lj6GisT.FaR9WuGBB3lOaprezR453QK', '0', NULL, '2019-07-01', NULL, NULL, NULL, 'https://b-ssl.duitang.com/uploads/item/201810/27/20181027203954_wiwop.jpg', NULL, NULL, 'admin@admin.com', 'Y', '一枚小码匠', '全栈工程师');
INSERT INTO `tb_admin` VALUES ('1149984370027794432', NULL, 'admin', 'adminn', '$2a$10$5WqWwPg0vgisnosabMUxKu7EfkuWqvmFYYRXsor/vkucVn.BmHbQu', '0', NULL, '2019-07-13', NULL, NULL, NULL, NULL, NULL, NULL, 'admin@admin.com', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow`;

CREATE TABLE `tb_follow` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `targetuser` varchar(20) NOT NULL COMMENT '被关注用户ID',
  PRIMARY KEY (`userid`,`targetuser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_follow
-- ----------------------------
BEGIN;
INSERT INTO `tb_follow` VALUES ('1', '1');
INSERT INTO `tb_follow` VALUES ('1', '10');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `followme` varchar(20) DEFAULT NULL COMMENT '是否关注我的动态',
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

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES ('1', '1', 'admin@qq.com', '小明', 'http://u.thsi.cn/imgsrc/sns/3ba262255b32a428a3e0b44cce35d3ee_259_346.jpg', NULL, NULL, NULL, 1, 'java', '190000993', 'admin', '1', '2019-06-29 11:38:05', NULL, 99, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES ('2', '0', 'test1@qq.com', '天线', 'http://b-ssl.duitang.com/uploads/item/201810/27/20181027203954_wiwop.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES ('3', '1', 'man@man.com', '小红', 'http://u.thsi.cn/imgsrc/sns/3ba262255b32a428a3e0b44cce35d3ee_259_346.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;



DROP TABLE IF EXISTS `tb_blogger_message`;

-- 通知博主消息数据表
CREATE TABLE `tb_blogger_message` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论ID',
  `blog_id` varchar(20) DEFAULT NULL COMMENT '博客ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `type` varchar(100) DEFAULT NULL COMMENT '消息通知类型',
  `status` int(1) DEFAULT 0 COMMENT '是否阅读'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博主消息通知表';