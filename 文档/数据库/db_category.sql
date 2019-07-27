CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_category` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_category`;

/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 132.232.104.247:3306
 Source Schema         : tensquare_category

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/07/2019 19:24:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;

-- 标签表
CREATE TABLE `tb_category` (
  `id` varchar(20) NOT NULL COMMENT '分类ID',
  `categoryname` varchar(100) DEFAULT NULL COMMENT '分类名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `createdate` date DEFAULT NULL COMMENT '创建时间',
  `userid` varchar(20) DEFAULT NULL COMMENT '创建人',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `color` varchar(50) DEFAULT NULL COMMENT '颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类';

-- ----------------------------
-- Records of tb_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_category` VALUES ('1', 'java', '1', NULL, NULL, NULL, NULL, 'd876e3');
INSERT INTO `tb_category` VALUES ('1142309021223620608', '前端', '0', 0, '2019-06-22', NULL, NULL, 'c4426b');
INSERT INTO `tb_category` VALUES ('1142309252610789376', '服务器', '0', 0, '2019-06-22', NULL, NULL, 'E52446');
INSERT INTO `tb_category` VALUES ('1147422697949630464', '测试分类', '0', 0, '2019-07-06', NULL, NULL, NULL);
INSERT INTO `tb_category` VALUES ('1147425409953042432', '分类1', '0', 0, '2019-07-06', NULL, NULL, NULL);
INSERT INTO `tb_category` VALUES ('1147437111490777088', '分类123', '0', 0, '2019-07-06', NULL, NULL, NULL);
INSERT INTO `tb_category` VALUES ('1147441448988315648', '12321', '0', 0, '2019-07-06', NULL, NULL, NULL);
INSERT INTO `tb_category` VALUES ('1149927250985095168', '其他', '0', 0, '2019-07-13', NULL, NULL, NULL);
INSERT INTO `tb_category` VALUES ('2', 'PHP', '0', NULL, NULL, NULL, NULL, 'E52446');
INSERT INTO `tb_category` VALUES ('3', 'C++', '0', NULL, NULL, NULL, NULL, '5631ea');
INSERT INTO `tb_category` VALUES ('4', 'python', '0', NULL, NULL, NULL, NULL, '9ff931');
INSERT INTO `tb_category` VALUES ('5', 'JS', '0', NULL, NULL, NULL, NULL, '25F0DA');
INSERT INTO `tb_category` VALUES ('6', '数据库', '0', NULL, NULL, NULL, NULL, '64A58A');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
