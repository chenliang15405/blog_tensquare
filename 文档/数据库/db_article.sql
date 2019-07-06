CREATE DATABASE /*!32312 IF NOT EXISTS*/`tensquare_blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tensquare_blog`;

DROP TABLE IF EXISTS `tb_article`;

CREATE TABLE `tb_article` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `summary` text default null comment '摘要',
  `content` longtext COMMENT '文章正文',
  `textcontent` longtext COMMENT '文章纯文本',
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
--   `labelname` varchar(100) default null comment '标签名称',
--   `labelcolor` varchar(20) default null comment '标签颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';


INSERT INTO `tb_article` VALUES ('1142303057879633920', NULL, 'IDEA生成java说明文档', '本章内容通过Nginx 和 FTP 搭建图片服务器。在学习本章内容前，请确保您的Linux 系统已经安装了Nginx和Vsftpd。Nginx 安装http://www.cnblogs.com/itdragon/p/7850985.htmlVsftpd 安装http://www.cnblogs.com/itdragon/p/7857649.html', '<h3>docker_fastdfs 分布式文件服务器单机版搭建</h3>\n\n<hr/>\n\n<ol><li>拉取镜像并启动\n<code>bash\ndocker run -d --privileged=true \\\n--net=host --name=fastdfs \\\n-e IP=132.232.104.247 -e WEB_PORT=9999 \\\n-v /server/fastdfs:/var/local/fdfs \\\nregistry.cn-beijing.aliyuncs.com/tianzuo/fastdfs\n</code></li></ol>\n\n<p>如果需要每次重新启动,  使用这个命令，加上 --restart=always\n<code>bash\n    docker run -d --privileged=true \\\n    --restart=always \\\n    --net=host --name=fastdfs \\\n    -e IP=132.232.104.247 -e WEB_PORT=9999 \\\n    -v /server/fastdfs:/var/local/fdfs \\\n    registry.cn-beijing.aliyuncs.com/tianzuo/fastdfs\n</code>\n<em> -e IP=132.232.104.247 是服务器的公网ip\n</em> -e WEB_PORT=9999 指定镜像中集成的nginx的端口，后面是用来访问图片或者文件\n* -v /server/fastdfs:/var/local/fdfs 挂载容器中存储文件的目录</p>\n\n<ol><li><p>容器启动之后：进行测试： \n<code>bash\n进入容器\ndocker exec -it fastdfs /bin/bash\n</code>\n<code>bash\n创建文件\necho &quot;Hello FastDFS!&quot;&gt;index.html\n</code>\n<code>bash\n上传文件,会返回上传文件的路径 一般是: /group1/M00/00 这种的\nfdfs_test /etc/fdfs/client.conf upload index.html\n</code></p></li><li><p>在浏览器访问 ip:port/path <br/>ip: 就是服务器公网ip <br/>port: 是run docker时指定的-e WEB_PORT指定的port\npath: 是上传文件之后返回的路径</p></li></ol>\n\n<hr/>\n\n<p>TODO 后面整理 springboot集成 fastdfs\n1. 两种pom依赖：\n<code>java\n&lt;dependency&gt;\n    &lt;groupId&gt;com.luhuiguo&lt;/groupId&gt;\n    &lt;artifactId&gt;fastdfs-spring-boot-starter&lt;/artifactId&gt;\n    &lt;version&gt;0.2.0&lt;/version&gt;\n&lt;/dependency&gt;\n</code></p>\n\n<p><code>java\n&lt;dependency&gt;\n    &lt;groupId&gt;com.github.tobato&lt;/groupId&gt;\n    &lt;artifactId&gt;fastdfs-client&lt;/artifactId&gt;\n    &lt;version&gt;1.26.2&lt;/version&gt;\n&lt;/dependency&gt;\n</code></p>\n\n<p>使用方式好像一样的   </p>\n\n<ul><li>[ ] springboot中集成fastdfs</li></ul>', NULL, '2019-06-22 13:27:27', NULL, NULL, NULL, 0, 0, 0, '0', '1', 'java', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142308173135351808', NULL, '这是一个标题', '本章内容通过Nginx 和 FTP 搭建图片服务器。在学习本章内容前，请确保您的Linux 系统已经安装了Nginx和Vsftpd。Nginx 安装http://www.cnblogs.com/itdragon/p/7850985.htmlVsftpd 安装http://www.cnblogs.com/itdragon/p/7857649.html', '1、打开 idea，点击 Tools-> Generate JavaDoc，这样会打开生成 javadoc 文档的配置页面。2、进行配置：标注的是重要的部分，从上往下分别是配置 javadoc 的范围，输出文件夹路径以及命令行参数。这里的命令行参数很重要，因为只有使用 utf-8 编码才能保证生成时可以正常处理中文字符，所以一定要加上：-encoding utf-8 -charset utf-81还可以配置那些注解需要生成，哪些权限类（private、package、protected、public）需要生成等等精细的控制。还有一点需要注意，即不要勾选“Include test sources”，勾选后，生成时会造成很奇怪的错误。3、配置好后，点击生成按钮，生成好后就会自动在浏览器打开进行查看咯，是不是很棒O(∩_∩)O~--------------------- 作者：deniro_li 来源：CSDN 原文：https://blog.csdn.net/deniro_li/article/details/71706267 版权声明：本文为博主原创文章，转载请附上博文链接！', NULL, '2019-06-22 13:47:46', NULL, NULL, NULL, 0, 99, 12, '0', '6', '数据库', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142308371337187328', NULL, 'spring boot的jar包的启动，停止脚本', '一、背景 spring boot的项目越来越多的打包成jar包来启动了。 二、脚本封装 脚本名为jarboot.sh，该脚本能启', '在开发过程中，经常需要和别的系统交换数据，数据交换的格式有XML、JSON等，JSON作为一个轻量级的数据格式比xml效率要高，XML需要很多的标签，这无疑占据了网络流量，JSON在这方面则做的很好，下面先看下JSON的格式，\n\nJSON可以有两种格式，一种是对象格式的，另一种是数组对象，\n\n{\"name\":\"JSON\",\"address\":\"北京市西城区\",\"age\":25}//JSON的对象格式的字符串\n \n\n[{\"name\":\"JSON\",\"address\":\"北京市西城区\",\"age\":25}]//数据对象格式\n\n从上面的两种格式可以看出对象格式和数组对象格式唯一的不同则是在对象格式的基础上加上了[]，再来看具体的结构，可以看出都是以键值对的形式出现的，中间以英文状态下的逗号（,）分隔。\n\n在前端和后端进行数据传输的时候这种格式也是很受欢迎的，后端返回json格式的字符串，前台使用js中的JSON.parse()方法把JSON字符串解析为json对象，然后进行遍历，供前端使用。\n\n下面进入正题，介绍在JAVA中JSON和java对象之间的互转。\n\n要想实现JSON和java对象之间的互转，需要借助第三方jar包，这里使用json-lib这个jar包，下载地址为：https://sourceforge.net/projects/json-lib/，json-lib需要commons-beanutils-1.8.0.jar、commons-collections-3.2.1.jar、commons-lang-2.5.jar、commons-logging-1.1.1.jar、ezmorph-1.0.6.jar五个包的支持，可以自行从网上下载，这里不再贴出下载地址。\n\njson-lib提供了几个类可以完成此功能，例，JSONObject、JSONArray。从类的名字上可以看出JSONObject转化的应该是对象格式的，而JSONArray转化的则应该是数组对象（即，带[]形式）的。\n\n一、java普通对象和json字符串的互转\n\n  java对象--》》字符串\n\njava普通对象指的是java中的一个java bean，即一个实体类，如，', NULL, '2019-06-22 13:48:33', NULL, NULL, NULL, 0, 99, 12, '0', '3', 'C++', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142308467445469184', NULL, '整理了八个开源的 Spring Boot 学习资源', 'Spring Boot 算是目前 Java 领域最火的技术栈了，松哥年初出版的 《Spring Boot + Vue 全栈开发实战》迄今为止已', 'Linux安装ftp组件(8步完成)\n\n1 安装vsftpd组件\n\n[root@bogon ~]# yum -y install vsftpd\n \n安装完后，有/etc/vsftpd/vsftpd.conf 文件，是vsftp的配置文件。\n\n2 添加一个ftp用户\n\n[root@bogon ~]# useradd ftpuser\n \n此用户就是用来登录ftp服务器用的。 \n这样一个用户建完，可以用这个登录，记得用普通登录不要用匿名了。登录后默认的路径为 /home/ftpuser.\n\n3 给ftp用户添加密码。\n\n[root@bogon ~]# passwd ftpuser\n1\n输入两次密码后修改密码。\n\n4 防火墙开启21端口\n\n因为ftp默认的端口为21，而centos默认是没有开启的，所以要修改iptables文件\n\n[root@bogon ~]# vim /etc/sysconfig/iptables\n \n在行上面有22 -j ACCEPT 下面另起一行输入跟那行差不多的，只是把22换成21，然后：wq保存。\n\n还要运行下,重启iptables\n\n[root@bogon ~]# service iptables restart\n \n5 修改selinux\n\n外网是可以访问上去了，可是发现没法返回目录（使用ftp的主动模式，被动模式还是无法访问），也上传不了，因为selinux作怪了。 \n修改selinux： \n执行以下命令查看状态：\n\n[root@bogon ~]# getsebool -a | grep ftp  \n \nallow_ftpd_anon_write –> off \nallow_ftpd_full_access –> off \nallow_ftpd_use_cifs –> off \nallow_ftpd_use_nfs –> off \nftp_home_dir –> off \nftpd_connect_db –> off \nftpd_use_passive_mode –> off \nhttpd_enable_ftp_server –> off \ntftp_anon_write –> off \n[root@bogon ~]#\n\n执行上面命令，再返回的结果看到两行都是off，代表，没有开启外网的访问\n\n[root@bogon ~]# setsebool -P allow_ftpd_full_access on\n[root@bogon ~]# setsebool -P ftp_home_dir on\n \n这样应该没问题了（如果，还是不行，看看是不是用了ftp客户端工具用了passive模式访问了，如提示Entering Passive mode，就代表是passive模式，默认是不行的，因为ftp passive模式被iptables挡住了，下面会讲怎么开启，如果懒得开的话，就看看你客户端ftp是否有port模式的选项，或者把passive模式的选项去掉。如果客户端还是不行，看看客户端上的主机的电脑是否开了防火墙，关吧）\n\nFileZilla的主动、被动模式修改： \n菜单：编辑→设置 \n这里写图片描述\n\n6 关闭匿名访问\n\n修改/etc/vsftpd/vsftpd.conf文件：\n\n[root@bogon ~]# vim /etc/vsftpd/vsftpd.conf\n \n这里写图片描述\n\n重启ftp服务：\n\n[root@bogon ~]# service vsftpd restart\n \n7 开启被动模式\n\n默认是开启的，但是要指定一个端口范围，打开vsftpd.conf文件，在后面加上\n\npasv_min_port=30000\npasv_max_port=30999\n \n \n表示端口范围为30000~30999，这个可以随意改。改完重启一下vsftpd \n由于指定这段端口范围，iptables也要相应的开启这个范围，所以像上面那样打开iptables文件。 \n也是在21上下面另起一行，更那行差不多，只是把21 改为30000:30999,然后:wq保存，重启下iptables。这样就搞定了。\n\n8 设置开机启动vsftpd ftp服务\n\n \n\n文章转载于: https://blog.csdn.net/qq_36698956\n\n版权声明：博客对我来说是记忆的笔记和知识的分享~非常感谢博客大神的帮助，若有无意侵权，请您联系我，谢谢  https://blog.csdn.net/qq_36698956\n\n[root@bogon ~]# chkconfig vsftpd on\n', NULL, '2019-06-22 13:48:56', NULL, NULL, NULL, 0, 99, 12, '0', '1', 'java', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142308636702412800', NULL, 'spring boot 单体项目 集成 spring security 实现 登录认证 权限认证 jwt token认证', '这篇博文讲述的是不集成oath，通过自己编写jwt 的 token 生成器 实现 spring security 的 登录权限token认证的', '本文将用代码来演示在spring boot里面，用hadoop client，通过restful API来上传文件 和下载文件 到 hadoop hdfs。\n\n里面有一些代码依赖坑，注意绕行。\n\n前提：\n\n如果你的代码在windows上运行，去连接linux上的hadoop(2.7.x或者以上），则需要做一下设置。\n\n1：下载下面的windows hadoop\n\nhttps://github.com/steveloughran/winutils/tree/master/hadoop-2.8.1\n\n在整个目录下载下来，放到一个目录下，比如 D:\\thb\\tools\\hadoop-2.8.1-bin-master，然后设置环境变量\n\nHADOOP_HOME=D:\\thb\\tools\\hadoop-2.8.1-bin-master\n\nPATH=%PATH%;%HADOOP_HOME%\n\n如何设置请自行百度一下如何在windows里面设置系统环境变量。\n\n2：pom.xml (注意hadoop-client，commons-io，commons-fileupload版本，否则编译或者运行会报错）\n\n<dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n \n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-test</artifactId>\n            <scope>test</scope>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.restdocs</groupId>\n            <artifactId>spring-restdocs-mockmvc</artifactId>\n            <scope>test</scope>\n        </dependency><dependency>\n            <groupId>net.sf.json-lib</groupId>\n            <artifactId>json-lib</artifactId>\n            <version>2.4</version>\n            <classifier>jdk15</classifier>\n        </dependency>\n        <dependency>\n            <groupId>commons-fileupload</groupId>\n            <artifactId>commons-fileupload</artifactId>\n            <version>1.3.3</version>\n        </dependency>\n        <dependency>\n            <groupId>commons-io</groupId>\n            <artifactId>commons-io</artifactId>\n            <version>2.6</version>\n        </dependency>\n        <dependency>\n            <groupId>org.apache.hadoop</groupId>\n            <artifactId>hadoop-client</artifactId>\n            <version>2.7.2</version>\n        </dependency>\n    </dependencies>\n代码如下\r\n--------------------- \r\n作者：大树叶 \r\n来源：CSDN \r\n原文：https://blog.csdn.net/bigtree_3721/article/details/81713162 \r\n版权声明：本文为博主原创文章，转载请附上博文链接！', NULL, '2019-06-22 13:49:37', NULL, NULL, NULL, 0, 99, 12, '0', '1', 'java', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142309390188154880', NULL, 'spring boot jar部署 控制台 日志 乱码', 'spring boot jar部署 控制台 日志 乱码', '注释掉或删除\n\n4、如果，不配置logback.xml，使用默认的配置，那么部署jar包，java -jar xxx.jar，代码中log变量输出的中文，乱码；如果，配置了logback.xml，但是charset使用UTF-8，那么中文依然乱码；只有使用logback.xml，且，charset删除掉，才能确保中文正常。 \n5、针对这个非常具体的问题，我尝试过，且无用的做法有，在pom的plugin–>spring-boot-maven-plugin–>configuration节点下增加-Dfile.encoding=UTF-8，无用；或者在运行.jar包时，增加参数-Dfile.encoding=UTF-8，依然无用。\r\n \r\n作者：leondryu \r\n来源：CSDN \r\n原文：https://blog.csdn.net/leondryu/article/details/81478114 \r\n版权声明：本文为博主原创文章，转载请附上博文链接！', NULL, '2019-06-22 13:52:36', NULL, NULL, NULL, 0, 11, 0, '0', '4', 'python', NULL, '1',null);
INSERT INTO `tb_article` VALUES ('1142309737099038720', NULL, '前端 UI 工程师的困境与破局', '前端的发展太快了，应该怎么去追求深度学习而不是一味追求广度？当讨论前端 UI 工程师困境的时候，会立足、', '早在 2000 年的时候我接触 PhotoShop 6.0，2003 年的时候我靠摸索做出了第一个网页，这也奠定了我的美工“出身”。我的职业生涯中有 3.5 年的时间在做 UI 相关的工作，对于这份工作优势劣势，困境与难处有些自己的见解。\n\n最近几年，在各种 JS 框架风生水起的大环境下，更是没有太多人愿意关注 UI 方面的东西，关注前端行业里这些对像素有特殊偏好的人了。我自己也有这样的趋势，当接触了更全面的研发流程之后，就很难保持对 UI 细节以及曾经追求的极致用户体验的关注度了。这或许也恰恰说明，对于某些类型的产品或某些阶段的产品来说，UI 细节没那么重要。而对于重视用户体验的用户体验设计部来说，隶属于设计部的编码人员也较难受到重视和提拔。\n\n这就是 UI 工程师的困境。我会在 2 周之内产出一篇 5000 字以上的文章，来与你共同探讨 UI 工程的困境和可能的改变方向，以及如何走出第一步的问题。\n\n实录提要：\n\n前端的发展太快了，应该怎么去追求深度学习而不是一味追求广度？\n当讨论前端 UI 工程师困境的时候，会立足以个体为主，还是以这个岗位群体为主？\n这次 512 的比特币勒索病毒会不会对前端浏览器兼容产生影响？\n怎么更好的学习应用基础布局及响应式布局？\n样式表现有问题如何调试？需要根据某些浏览器 hack，具体处理时除了经验还有什么好办法？\n后端开发者需要掌握哪些前端知识或技能？\n公司或者 leader 应该如何衡量一个偏科前端的价值呢？有比较合理的衡量标准吗？\n国内外 UI 方面有何差距？如何判断一套 UI 系统/框架的优劣？\n如何根据自身情况来针对某些东西做总结或计划？\n美的开发团队的工作方式是什么？', NULL, '2019-06-22 13:53:59', NULL, NULL, NULL, 0, 33, 32, '0', '5', 'JS', 'https://www.csdn.net', '1',null);



DROP TABLE IF EXISTS `tb_images`;

create table `tb_images` (
	`id` int NOT NULL auto_increment PRIMARY KEY COMMENT '图片id',
	`image_origin_name` varchar(200) DEFAULT NULL COMMENT '图片原始名称',
	`image_name` varchar(100) DEFAULT NULL COMMENT '图片名称',
	`image_path` varchar(200) DEFAULT NULL COMMENT '图片上传路径',
	`path` varchar(200) DEFAULT NULL COMMENT '图片访问路径',
	`create_time` date DEFAULT NULL COMMENT '上传时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片上传记录';
