CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_label` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_label`;


DROP TABLE IF EXISTS `tb_label`;

-- 标签表
CREATE TABLE `tb_label` (
  `id` int(20) NOT NULL auto_increment PRIMARY KEY COMMENT '标签ID',
  `labelname` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `count` bigint(20) DEFAULT NULL COMMENT '使用数量',
  `recommend` varchar(1) DEFAULT NULL COMMENT '是否推荐',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';


insert  into `tb_label`(`id`,`labelname`,`state`,`count`,`recommend`,`color`) values
('1','java','1',NULL,NULL,NULL),
('2','PHP','1',NULL,NULL,NULL),
('3','C++','1',NULL,NULL,NULL),
('4','python','1',NULL,NULL,NULL);



DROP TABLE IF EXISTS `tb_blog_label`;

-- 博客表和标签表的中间表  两个id, 可以用来标签的使用数量和关联的博客，在保存博客的时候，保存一份到这个表，并且保存标签到标签表，也可以直接标签名称
-- 和颜色保存在博客表
CREATE TABLE `tb_blog_label` (
  `id` int(50) NOT NULL auto_increment PRIMARY KEY COMMENT '主键ID',
  `blogid` varchar(20) NOT NULL COMMENT '博客id',
  `labelid` int(20) NOT NULL COMMENT '标签id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142308173135351808', 1);
INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142308173135351808', 2);
INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142308173135351808', 3);
INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142308173135351808', 5);
INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142309608782696448', 6);
INSERT INTO `tb_blog_label` (blogid,labelid) VALUES ('1142309608782696448', 8);
