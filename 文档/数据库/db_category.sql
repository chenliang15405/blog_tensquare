CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_category` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_category`;


DROP TABLE IF EXISTS `tb_category`;

-- 标签表
CREATE TABLE `tb_category` (
  `id` varchar(20) NOT NULL PRIMARY KEY COMMENT '分类ID',
  `categoryname` varchar(100) DEFAULT NULL COMMENT '分类名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `createdate` date default null comment '创建时间',
  `userid` varchar(20) default null comment '创建人',
  `description` varchar(100) default null comment '描述',
  `color` varchar(50) default null comment '颜色'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类';


INSERT INTO `tb_category` VALUES ('1', 'java', '1', NULL, NULL, NULL, NULL, 'd876e3');
INSERT INTO `tb_category` VALUES ('1142309021223620608', '前端', '0', 0, '2019-06-22', NULL, NULL, 'c4426b');
INSERT INTO `tb_category` VALUES ('1142309252610789376', '服务器', '0', 0, '2019-06-22', NULL, NULL, 'E52446');
INSERT INTO `tb_category` VALUES ('2', 'PHP', '0', NULL, NULL, NULL, NULL, 'E52446');
INSERT INTO `tb_category` VALUES ('3', 'C++', '0', NULL, NULL, NULL, NULL, '5631ea');
INSERT INTO `tb_category` VALUES ('4', 'python', '0', NULL, NULL, NULL, NULL, '9ff931');
INSERT INTO `tb_category` VALUES ('5', 'JS', '0', NULL, NULL, NULL, NULL, '25F0DA');
INSERT INTO `tb_category` VALUES ('6', '数据库', '0', NULL, NULL, NULL, NULL, '64A58A');


