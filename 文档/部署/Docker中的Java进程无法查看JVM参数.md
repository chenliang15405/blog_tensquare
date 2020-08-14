## Docker中无法查看JVM参数，提示无权查看


### 问题原因

经过查询资料，这个并非bug或者权限不足， Docker 自 1.10 版本开始加入的安全特性。 jmap 这类 JDK 工具依赖于 Linux 的 PTRACE_ATTACH，而 Docker 自 1.10 版本开始，默认的 seccomp 配置文件中禁用了 ptrace

### 解决方案

 1. –security-opt seccomp=unconfined
 
 `docker run --security-opt seccomp:unconfined ...`
 
 2. –cap-add=SYS_PTRACE
 
 `docker run --cap-add=SYS_PTRACE ...`
 
 3.  Docker Compose 的支持
   ```bash
    version: '2'
    
    services:
      mysql:
        ...
      api:
        ...
        cap_add:
          - SYS_PTRACE
   ```
   
### 示例
```bash
docker run --cap-add=SYS_PTRACE -d --name tensquare_category \
-p 9003:9003 \
-v /server/logs/tensquare_category:/server/logs/tensquare_category \
tensquare-blog/tensquare_category:1.0-SNAPSHOT
```