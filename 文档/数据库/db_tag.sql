CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_tag` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_tag`;


DROP TABLE IF EXISTS `tb_tag`;

-- 标签表
CREATE TABLE `tb_tag` (
  `id` int(20) NOT NULL auto_increment PRIMARY KEY COMMENT '标签ID',
  `tagname` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `recommend` varchar(1) DEFAULT NULL COMMENT '是否推荐',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';


insert  into `tb_tag`(`id`,`tagname`,`state`,`count`,`recommend`,`color`) values
('1','java','1',NULL,NULL,NULL),
('2','PHP','1',NULL,NULL,NULL),
('3','C++','1',NULL,NULL,NULL),
('4','python','1',NULL,NULL,NULL);



DROP TABLE IF EXISTS `tb_ul`;

-- 博客表和标签表的中间表  两个id, 可以用来标签的使用数量和关联的博客，在保存博客的时候，保存一份到这个表，并且保存标签到标签表，也可以直接标签名称
-- 和颜色保存在博客表
CREATE TABLE `tb_ul` (
  `blogid` varchar(20) NOT NULL,
  `tagid` int(20) NOT NULL,
  PRIMARY KEY (`blogid`,`tagid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `tb_ul`(`blogid`,`tagid`) values
('1',1),
('1',2),
('1',3);
