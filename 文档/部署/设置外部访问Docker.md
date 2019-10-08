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



