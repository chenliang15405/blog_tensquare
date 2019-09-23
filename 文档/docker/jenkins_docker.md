##### Docker安装Jenkins并配置

> Jenkins是Java写的，所以就是一个war包，当然可以使用tomcat运行war包或者使用yum直接安装到服务器，但是和Docker比起来
感觉还是Docker使用方便，记录一下如何安装Jenkins并且使用Docker安装Jenkins的坑


###### 一. 直接拉取Jenkins镜像
**1. 拉取Docker镜像**
- 搜索Jenkins镜像
`docker search jenkins`

- 排名第一的是jenins的官方镜像，其实这个官方镜像有点问题，不过没有关系，可以配置一下是一样的。
`docker pull jenkins`

**2. 运行容器**
```bash
docker run -d --name jenkins -u root -p 8080:8080  -p 50000:50000 \
 -v /test/jenkins-test:/var/jenkins_home \
 -v /var/run/docker.sock:/var/run/docker.sock \
 -v /server/apache-maven-3.1.1:/usr/local/maven3.6 \
 -v /server/jdk/jdk1.8.0_212:/usr/local/jdk1.8 \
 jenkins
 ```

 其中:
   * -p 8080:8080 映射端口
   * -v /test/jenkins-test:/var/jenkins_home 将宿主机的/test/jenkins-test 挂载到 /var/jenkins_home
   `需要先创建/test/jenkins-test目录`,并通过`chmod 777 -R jenkins-test` 进行赋权，否则可能启动会报错

   * -v /var/run/docker.sock:/var/run/docker.sock 将宿主机的docker挂载到jenkins中，如果jenkins不使用dokcer部署项目的话
   可以不用挂载
   * -v /server/apache-maven-3.1.1:/usr/local/maven3.6 将宿主机的maven挂载到jenkins中
   * -v /server/jdk/jdk1.8.0_212:/usr/local/jdk1.8 将宿主机的jdk挂载到jenkins中

**3. 配置Jenkins**
 使用官方镜像启动的Jenkins在安装插件的时候，大部分都是安装不上的，不要急，点击Continue，先进入到Jenkins
 插件安装失败是由于Jenkins的版本问题，插件安装失败也不要一直重试了，进入到Jenkins还可以自定义安装的。

 1. 升级Jenkin版本
    因为Jenkins版本原因，所以现在的Jenkins是不能够用的，所以需要升级一下版本（不要怕，一键自动升级）

	(1) 点击管理Jenkins
	![Manage Jenkins](./md_images/install_jenkins_3.jpg)

	(2) 进入之后，会看见大量的错误，直接拉到最下面，然后有一个[是否自动升级]按钮，点击自动升级
	 ![upgrade Jenkins](./md_images/install_jenkins.jpg)

	(3) 然后等待升级的出现 Success，然后再点击管理Jenkins，刚才升级的地方变成了Restart,点击Restart重启Jenkins
	![upgrade Jenkins](./md_images/install_jenkins_2.jpg)

 2. 等待升级完成，重新进入Jenkins，现在可以为Jenkins安装需要使用的插件了，Jenkins已经可以正常使用


###### 二. 通过Dockerfile构建jenkins镜像

**1. 创建目录并赋权**
`mkdir /sever/jenkins`

`mkdir /var/jenkins_home`

`chmod 777 -R jenkins`
`chmod 777 -R /var/jenkins_home`

**2. 创建Dockerfile**

```bash
FROM jenkins/jenkins:2.138.4
USER root
ARG dockerGid=999
RUN echo "docker:x:${dockerGid}:jenkins" >> /etc/group
RUN apt-get update && apt-get install -y sudo && rm -rf /var/lib/apt/lists/*
RUN apt-get update && apt-get install -y libltdl7 && apt-get update
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
USER jenkins

```

**3. 创建Jenkins镜像**
`docker build -t jenkins/jenkins:1.0 .`

说明：
  - `命令最后有一个 . 标示dockerfile在当前目录`
  - jenkins:1.0 表示容器名称为jenkins, tag为1.0

**4. 创建Jenkins容器**
```bash

sudo docker run -d -p 8080:8080 -p 50000:50000 --name jenkins \
-v /usr/bin/docker:/usr/bin/docker \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /usr/bin/mv:/usr/bin/mv \
-v /server/jdk/jdk1.8.0_212/bin/java:/usr/bin/jdk1.8.0_212/java \
-v /server/jdk/jdk1.8.0_212:/var/local/jdk1.8.0_212 \
-v /server/apache-maven-3.1.1:/var/local/apache-maven-3.1.1 \
-v /var/jenkins_home:/var/jenkins_home \
-v /etc/localtime:/etc/localtime \
jenkins/jenkins:1.0

```

说明：
  - -p 8080:8080 -p 50000:50000 映射端口
  - -v /usr/bin/docker:/usr/bin/docker 挂载宿主机docker
  - /server/jdk/jdk1.8.0_212/bin/java 挂载宿主机jdk
  - -v /var/jenkins_home:/var/jenkins_home 将宿主机的目录挂载到jenkins的工作目录，方便查看工作空间`需要给/var/jenkins_home授权` `chown -R 1000 /var/jenkins_home`
  - -v /etc/localtime:/etc/localtime 挂载服务器的时间到容器的时间

注意：
    <font color=red>注意：如果没有jenkins_home，则需要创建该目录并给权限</font>
    否则启动日志中会报错： Permission denied

**5. 最后，如果在Jenkins配置jdk和maven**
 （1） 先尝试使用挂载的宿主机目录配置jdk和maven目录，git自动安装即可

  (2) 如果配置宿主机的jdk、maven目录,构建项目时提示无法找到mvn命令，那么使用jenkins容器中的目录配置jdk、maven

  (3) 一般需要安装的插件和jenkins配置：

		1. pushlish over ssh

		2. 配置server节点

		3. 配置jdk maven git

		4. 配置maven intergration




**6. 配置Jenkins肯能遇到的问题及解决方法**
(1) ERROR: Maven Home /server/apache-maven-3.1.1 doesnt exist
	Finished: FAILURE
	`按照上面的配置maven目录即可`
   

(2) 如果在上传jar/war到远程服务器，一直无法传递过去，检查一下路径：上传jar包时的目录是相对的工作区目录
	workspace/testPro/
	配置的目录是从这个开始的,workspace下面的创建的小项目的路径视为跟路径















