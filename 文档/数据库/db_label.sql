CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_label` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_label`;


/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 132.232.104.247:3306
 Source Schema         : tensquare_label

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/07/2019 19:26:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_blog_label
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_label`;
CREATE TABLE `tb_blog_label` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `blogid` varchar(20) NOT NULL COMMENT '博客id',
  `labelid` int(20) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_blog_label
-- ----------------------------
BEGIN;
INSERT INTO `tb_blog_label` VALUES (1, '1142308173135351808', 1);
INSERT INTO `tb_blog_label` VALUES (2, '1142308173135351808', 2);
INSERT INTO `tb_blog_label` VALUES (3, '1142308173135351808', 3);
INSERT INTO `tb_blog_label` VALUES (4, '1142308173135351808', 5);
INSERT INTO `tb_blog_label` VALUES (5, '1142309608782696448', 6);
INSERT INTO `tb_blog_label` VALUES (6, '1142309608782696448', 8);
INSERT INTO `tb_blog_label` VALUES (9, '1146728431929659392', 11);
INSERT INTO `tb_blog_label` VALUES (10, '1146728431929659392', 1);
INSERT INTO `tb_blog_label` VALUES (11, '1147419057717383168', 8);
INSERT INTO `tb_blog_label` VALUES (12, '1147419057717383168', 12);
INSERT INTO `tb_blog_label` VALUES (13, '1147422694573215744', 13);
INSERT INTO `tb_blog_label` VALUES (18, '1147440020085411840', 18);
INSERT INTO `tb_blog_label` VALUES (19, '1147441447692275712', 19);
INSERT INTO `tb_blog_label` VALUES (20, '1147442301161836544', 20);
INSERT INTO `tb_blog_label` VALUES (21, '1147446159820853248', 21);
INSERT INTO `tb_blog_label` VALUES (22, '1147446159820853248', 16);
INSERT INTO `tb_blog_label` VALUES (23, '1147449716045385728', 8);
COMMIT;

-- ----------------------------
-- Table structure for tb_label
-- ----------------------------
DROP TABLE IF EXISTS `tb_label`;

-- 标签表
CREATE TABLE `tb_label` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `labelname` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `recommend` varchar(1) DEFAULT NULL COMMENT '是否推荐',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
-- Records of tb_label
-- ----------------------------
BEGIN;
INSERT INTO `tb_label` VALUES (1, 'java', '1', NULL, NULL, 'E52446');
INSERT INTO `tb_label` VALUES (2, 'PHP', '1', NULL, NULL, NULL);
INSERT INTO `tb_label` VALUES (3, 'C++', '1', NULL, NULL, '5631ea');
INSERT INTO `tb_label` VALUES (4, 'python', '1', NULL, NULL, '#ccc');
INSERT INTO `tb_label` VALUES (5, 'linux', '1', NULL, NULL, '64A58A');
INSERT INTO `tb_label` VALUES (6, '其他', NULL, NULL, NULL, '25F0DA');
INSERT INTO `tb_label` VALUES (7, 'css', NULL, NULL, NULL, NULL);
INSERT INTO `tb_label` VALUES (8, 'js', NULL, NULL, NULL, NULL);
INSERT INTO `tb_label` VALUES (11, 'string', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (12, '前端', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (13, '测试标签', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (14, '测试上传图片', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (15, 'toolbar', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (16, '标签1', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (17, '标签2', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (18, '123', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (19, '1111', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (20, '12', '1', 0, NULL, NULL);
INSERT INTO `tb_label` VALUES (21, '标签3', '1', 0, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
