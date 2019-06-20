### docker_fastdfs 分布式文件服务器单机版搭建
----

1. 拉取镜像并启动
```bash
    docker run -d --privileged=true \
    --net=host --name=fastdfs \
    -e IP=132.232.104.247 -e WEB_PORT=9999 \
    -v /server/fastdfs:/var/local/fdfs \
    registry.cn-beijing.aliyuncs.com/tianzuo/fastdfs
```

如果需要每次重新启动,  使用这个命令，加上 --restart=always
```bash
    docker run -d --privileged=true \
    --restart=always \
    --net=host --name=fastdfs \
    -e IP=132.232.104.247 -e WEB_PORT=9999 \
    -v /server/fastdfs:/var/local/fdfs \
    registry.cn-beijing.aliyuncs.com/tianzuo/fastdfs
```
* -e IP=132.232.104.247 是服务器的公网ip
* -e WEB_PORT=9999 指定镜像中集成的nginx的端口，后面是用来访问图片或者文件
* -v /server/fastdfs:/var/local/fdfs 挂载容器中存储文件的目录

2. 容器启动之后：进行测试： 
```bash
进入容器
 docker exec -it fastdfs /bin/bash
```
```bash
创建文件
echo "Hello FastDFS!">index.html
```
```bash
上传文件,会返回上传文件的路径 一般是: /group1/M00/00 这种的
fdfs_test /etc/fdfs/client.conf upload index.html
```

3. 在浏览器访问 ip:port/path   
ip: 就是服务器公网ip   
port: 是run docker时指定的-e WEB_PORT指定的port
path: 是上传文件之后返回的路径

------
TODO 后面整理 springboot集成 fastdfs
1. 两种pom依赖：
```java
<dependency>
    <groupId>com.luhuiguo</groupId>
    <artifactId>fastdfs-spring-boot-starter</artifactId>
    <version>0.2.0</version>
</dependency>
```

```java
<dependency>
    <groupId>com.github.tobato</groupId>
    <artifactId>fastdfs-client</artifactId>
    <version>1.26.2</version>
</dependency>
```

使用方式好像一样的   

- [ ] springboot中集成fastdfs