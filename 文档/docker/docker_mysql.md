Docker安装mysql
----

一. 从Docker hub拉取镜像  

1. docker命令拉取镜像  
`docker pull mysql`

2. 创建容器

(1) 直接启动   
`docker run -d --name mysql  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.7`   

(2) 指定字符集启动：
```bash
docker run -d \
--name mysql \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci \
mysql:5.7
```

(3) 如果有需求可以将mysql的存储数据文件挂载到宿主机，
	可以防止容器被删除之后，存储的数据也会丢失，并指定时区
```bash
docker run -d --name mysql_master \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD='&UJM8ik,' \
-e TZ=Asia/Shanghai \
-v /server/mysql-master:/var/lib/mysql \
mysql:5.7 \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci
```

<font color=red>注意：</font>如果密码中特殊字，需要使用 '' 包裹

(4) 如果有一些数据库的初始脚本需要执行，则可以通过挂载目录，将需要执行的
	sql文件放到挂载文件夹中
```bash
docker run -d --name mysql \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=DB_USER \
-e TZ=Asia/Shanghai \
-v $PWD/sql-scripts/:/docker-entrypoint-initdb.d/ \
-v /data/mysql:/var/lib/mysql \
mysql:5.7 \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci 
```


<br>

- 目前服务器命令
```bash
docker run -d --name mysql-master \
-p 13306:3306 \
-e MYSQL_ROOT_PASSWORD='&UJM8ik,' \
-e TZ=Asia/Shanghai \
-v /server/mysql-master/data:/var/lib/mysql \
-v /server/mysql-master/conf/mysql.cnf:/etc/mysql/conf.d/mysql.cnf \
mysql:5.7
```

说明：
- -e MYSQL_ROOT_PASSWORD 指定初始密码为root
- -p 指定映射端口
- --character-set-server=utf8mb4 指定编码集，使用utf-8，可以指定为utf8mb4，
	不要直接使用utf8，utf8最后存储3字节（无法存储emoj表情等），使用utf8mb4可以存储4字节

- -v $PWD/sql-scripts/:/docker-entrypoint-initdb.d/ 将当前目录下的sql-scriptes文件夹
	挂载到docker中，初始化myql时会执行该sql文件
- MYSQL_DATABASE docker启动的时候创建该数据库，不用手动创建


3. 执行命令查看
`docker ps `

运行即可，使用工具即可连接


二. Dockerfile构建镜像
