### docker_redis的搭建步骤：
-------

1. docker search redis   
2. 一般下载官方的就可以，stars 数最多的那个
3. docker pull redis
4. 获取配置文件: wget https://raw.githubusercontent.com/antirez/redis/4.0/redis.conf   
   或者已经有配置文件也可以直接复制 
5. 原本的： redis.conf 
```bash
    bind 127.0.0.1
    protected-mode yes
    appendonly no//持久化
    # requirepass foobared
```
   修改为： 
```bash
    #bind 127.0.0.1
    protected-mode no
    appendonly yes//持久化
    requirepass yourpassword
```
   ps:   
   protected-mode 是在没有显示定义 bind 地址（即监听全网断），又没有设置密码 requirepass
   时，只允许本地回环 127.0.0.1 访问。 也就是说当开启了 protected-mode 时，如果你既没有显示的定义了 bind
   监听的地址，同时又没有设置 auth 密码。那你只能通过 127.0.0.1 来访问 redis 服务
   
6. 运行镜像：
```bash
    docker run \
    -p 6379:6379 \
    -v /server/redis/data:/data \
    -v /server/redis/conf/redis.conf:/etc/redis/redis.conf \
    --privileged=true \
    --name redis \
    -d redis redis-server /etc/redis/redis.conf

```  
7. 遇到一个问题，redis不能启动，然后修改配置文件：daemonize no 就可以，奇怪！
   
