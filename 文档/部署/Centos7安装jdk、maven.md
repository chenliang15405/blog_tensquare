### Centos7中安装jdk、maven

> 准备工作：下载需要安装的jdk版本和maven版本，然手使用ftp工具
上传到服务器中。

1. 解压jdk   
`tar -zxvf jdk-8u212-linux-x64.tar.gz`

2. 解压maven
`tar -zxvf apache-maven-3.1.1-bin.tar.gz`

3. 可以根据情况，也可以将解压之后的文件夹进行重新命名

4. 配置环境变量
`vi /etc/profile`

在`unset i`上面加入环境变量配置

```bash

export JAVA_HOME=/server/jdk/jdk1.8.0_212
export MAVEN_HOME=/server/apache-maven-3.1.1
export PATH=$JAVA_HOME/bin:$PATH:$MAVEN_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

```
说明： 对应的路径换成安装的路径即可


jenkins编译项目时-> ERROR: Maven JVM terminated unexpectedly with exit code 137

 (1) 服务器内存不足

(2)
在maven/bin/mvn
`vi apache-maven-3.1.11/bin/mvn`

在 `# MAVEN_SKIP_RC - flag to disable loading of mavenrc files`这句代码下增加内存配置：
`set MAVEN_OPTS=-Xms256m -Xmx512m `