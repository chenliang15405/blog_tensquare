CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_blog`;

DROP TABLE IF EXISTS `tb_article`;

CREATE TABLE `tb_article` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `summary` text default null comment '摘要',
  `content` longtext COMMENT '文章正文',
  `image` varchar(200) DEFAULT NULL COMMENT '文章封面',
  `createtime` datetime DEFAULT NULL COMMENT '发表日期',
  `updatetime` datetime DEFAULT NULL COMMENT '修改日期',
  `ispublic` varchar(1) DEFAULT NULL COMMENT '是否公开',
  `istop` varchar(1) DEFAULT NULL COMMENT '是否置顶',
  `visits` int(20) DEFAULT NULL COMMENT '浏览量',
  `thumbup` int(20) DEFAULT NULL COMMENT '点赞数',
  `comment` int(20) DEFAULT NULL COMMENT '评论数',
  `state` varchar(1) DEFAULT NULL COMMENT '审核状态',
  `categoryid` varchar(20) DEFAULT NULL COMMENT '所属类型',
  `category_name` varchar(20) DEFAULT NULL COMMENT '类型名称',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `type` varchar(1) DEFAULT NULL COMMENT '类型（原创、转载、翻译）',
  `tag` varchar(100) default null comment '标签名称',
  `tagcolor` varchar(20) default null comment '标签颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';
