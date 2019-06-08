CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_category` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_category`;


DROP TABLE IF EXISTS `tb_tag`;

-- 标签表
CREATE TABLE `tb_category` (
  `id` int(20) NOT NULL auto_increment PRIMARY KEY COMMENT '分类ID',
  `categoryname` varchar(100) DEFAULT NULL COMMENT '分类名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `createdate` date default null comment '创建时间',
  `userid` varchar(20) default null comment '创建人',
  `description` varchar(100) default null comment '描述'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类';


insert  into `tb_category`(`categoryname`,`state`,`count`,`createdate`,`userid`,`description`) values
('java','1',NULL,NULL,NULL,NULL),
('PHP','1',NULL,NULL,NULL,NULL),
('C++','1',NULL,NULL,NULL,NULL),
('python','1',NULL,NULL,NULL,NULL);

