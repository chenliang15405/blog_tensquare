CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_comment` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_comment`;


/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 132.232.104.247:3306
 Source Schema         : tensquare_comment

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 23/07/2019 19:25:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;

-- int 后面的指定的长度，不影响 mysql的存储和计算，只会影响显示工具展示的数据的长度
-- oracle中好像是影响字段的长度，就不能插入数据
CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(20) DEFAULT NULL COMMENT '关联用户id',
  `parent_id` int(11) DEFAULT NULL COMMENT '关联父Id',
  `blog_id` varchar(20) DEFAULT NULL COMMENT '博客id',,
  `content` text DEFAULT NULL COMMENT '评论内容',
  `like_num` int(11) DEFAULT NULL COMMENT '点赞数量',
  `dislike_num` int(11) DEFAULT NULL COMMENT '不喜欢数量',
  `comment_level` int(11) DEFAULT NULL COMMENT '评论层级: 0：第一层，1：第二层，2：第三层',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',,
  `status` int(11) DEFAULT NULL COMMENT '状态：1 审核通过 0 审核不通过',
  `type` int(11) DEFAULT NULL COMMENT '评论类型：0文章，1，阅读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
BEGIN;
INSERT INTO `tb_comment` VALUES (1, '1', NULL, '1142308636702412800', '这是一个评论', 999, 9, 1, '2019-07-20', 1, NULL);
INSERT INTO `tb_comment` VALUES (2, '2', 1, '1142308636702412800', '22222', 222, 2, 2, NULL, 1, NULL);
INSERT INTO `tb_comment` VALUES (3, '3', NULL, '1142308636702412800', 'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure).', 11, 11, 1, NULL, 1, NULL);
INSERT INTO `tb_comment` VALUES (4, '3', 3, '1142308636702412800', '第二层评论', 11, 11, 2, NULL, 1, NULL);
INSERT INTO `tb_comment` VALUES (5, '2', 3, '1142308636702412800', '第二层评论', 11, 11, 2, NULL, 1, NULL);
INSERT INTO `tb_comment` VALUES (6, '2', 2, '1142308636702412800', '第三层评论', 11, 11, 3, NULL, 1, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

