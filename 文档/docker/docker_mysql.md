##### DOcker安装mysql服务器
---
一. 从Docker hub拉取镜像  

1. docker命令拉取镜像  
`docker pull mysql`

2. 创建容器
`docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7`   
- -e MYSQL_ROOT_PASSWORD 指定初始密码为root
- -p 指定映射端口

3. 执行命令查看
`docker ps `
运行即可，使用工具即可连接


二. Dockerfile构建镜像
