CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_comment` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_comment`;

DROP TABLE IF EXISTS `tb_comment`;

-- int 后面的指定的长度，不影响 mysql的存储和计算，只会影响显示工具展示的数据的长度
-- oracle中好像是影响字段的长度，就不能插入数据
create table tb_comment (
  id int auto_increment comment '主键' primary key,
  user_id varchar(20) comment '关联用户id',
  parent_id int  comment '关联父Id',
  blog_id varchar(20)  comment '关联博客Id',
  content text  comment '评论内容',
  like_num int  comment '点赞数量',
  dislike_num int  comment '不喜欢数量',
  comment_level int  comment '评论层级: 0：第一层，1：第二层，2：第三层',
  create_date date  comment '创建时间',
  status int comment '状态：1 审核通过 0 审核不通过',
  type int  comment '评论类型：0文章，1，阅读'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '评论表' ;