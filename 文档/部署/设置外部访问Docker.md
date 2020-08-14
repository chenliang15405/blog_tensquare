### 设置外部访问Docker，推送镜像到docker服务器

> 在部署springboot项目到服务器时，直接推送镜像到服务器中，因为docker时在
外部无法访问的，所以需要设置docker的一些配置。

**1. 编辑docker.service文件**
> docker的2375端口时提供给外部访问docker的端口，默认时关闭的，需要修改
docker的配置文件，打开该端口

`vi /usr/lib/systemd/system/docker.service`

修改`ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock`
为: `ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock`

然后重新启动docker   
`systemctl daemon-reload` -- 加载docker守护线程   

`systemctl restart docker` -- 重新启动

**2. 如果是云数据库**
 - 增加安全组规则   
 手动添加2375的入口端口到白名单，否则会拦截
 - 防火墙添加2375端口
     ```bash
        #查看防火墙状态
        systemctl status firewalld
        #启动防火墙 没有提示
        systemctl start firewalld
        #添加2375端口
        firewall-cmd --permanent --zone=public --add-port=2375/tcp
        #防火墙重启
        firewall-cmd --reload
        #查看开放的端口
        firewall-cmd --permanent --zone=public --list-ports
    ```


