### Docker中Mysql的主从配置

> 一般来说为了保证Mysql的高可用的高性能，会做分库分表、读写分离、Mysql集群等一些操作，如果时读写分离，那么主从配置就是读写分离的前置，需要有主从的服务，才会有主库负责写，从库负责读的优化。

### 前言：
如果是新创建Mysql服务器会比较简单，直接配置即可，如果是主库中已经有数据了，那么则需要将从库中的数据和主库中保持一致才可以。
（<font color=red>特别注意：主库和从库的版本最好保持一致，还有数据库的字符集编码，否则可能会失败，并且在同步数据会因为编码集不同无法同步数据</font>）。

### 配置
**一、创建mysql容器**
```bash
docker run -d --name mysql-slave \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-e TZ=Asia/Shanghai \
-v /server/mysql/data:/var/lib/mysql \
-v /server/mysql/conf/my.cnf:/etc/mysql/my.cnf \
mysql:5.7 \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci 
```
 说明：
    > `-v /server/mysql/data:/var/lib/mysql` 将数据挂载到宿主机，防止数据丢失，并挂载配置文件`my.cnf`
    
  - 从库配置文件`my.cnf`
    ```
    // my.cnf
    
    [mysqld]
	# 当前节点的id，不要重复
	server_id = 2
	# 使用binlog同步数据
	log-bin= mysql-bin
	
	# 可选配置，当前节点是否只读，一般从服务器建议只读
	read-only=1
	
	# 同步时忽略的DB
    replicate-ignore-db=mysql
    replicate-ignore-db=sys
    replicate-ignore-db=information_schema
    replicate-ignore-db=performance_schema
    
    
    !includedir /etc/mysql/conf.d/
    !includedir /etc/mysql/mysql.conf.d/
    
    ```

**二、备份主库数据到从库**
> 开启主从同步，则主库和从库的数据须一致，否则无法开启主从配置

1. 备份主库的数据到sql文件中
    - 进入docker
    `docker exec -it mysql-master bash`
    - 进入bin目录
    `cd /bin`
    - 并通过mysql命令备份数据到sql文件
    `mysqldump -uroot -p123456 --all-databases > /all_database.sql`
    - 复制备份文件到宿主机（这个命令宿主机中执行）
    `docker cp mysql-master:/all_database.sql /data/all_database.sql`
    - 然后将该文件上传到从库所在的服务器
 
    - 注意：
        > 如果在同步的过程中，有操作表的行为（除了查询之外的操作，增删改等），需要先进行锁定数据库再导出和导入
        
        - 锁定数据库
        `FLUSH TABLES WITH READ LOCK;`
        - 等待主从配置完成，解锁数据表
        `UNLOCK TABLES;`
 
2. 将主库备份文件导入到从库中
    - 复制备份sql文件到从库的docker容器中
    `docker cp /all_database.sql mysql-slave:/all_database.sql`
    - 进入docker容器
    `docker exec -it mysql-slave bash`
    - 导入该文件到从库中
    `cd /bin`
    `mysql -uroot -p <all_database.sql`
    - 链接mysql查看是否导入成功即可


**三、Mysql开启主从配置**

1. 如果已经存在主库，则无需创建主Mysql，直接进入Docker容器修改配置即可
    - 进入docker容器
    `docker exec -it mysql-master bash`
    - 修改配置
    `vi /etc/mysql/my.cnf`
        - 增加一下配置
        ```
          [mysqld]
          server_id = 1
          log-bin= mysql-bin
          
          # 下面的可加 也可以不加
          read-only=0
          
          replicate-ignore-db=mysql
          replicate-ignore-db=sys
          replicate-ignore-db=information_schema
          replicate-ignore-db=performance_schema
        ```
        说明：
            - `read-only=0` read-only中值为0表示支持可读写、1的话表明仅支持可读
            - server_id标识服务实例，master和slave值需要保证唯一
            - 下面的`eplicate-ignore-db` 表示哪些数据库是不同步的

    - 创建用户提供给从库访问使用
        - 登录mysql命令行
        `mysql -uroot -p`
        - 创建用户并赋权
        `CREATE USER 'slave'@'%' IDENTIFIED BY '123456';`
        `GRANT REPLICATION SLAVE ON *.* to 'slave'@'%';`
    
    - 重启主库的docker容器
    `docker restart mysql-master`
    
    - 进入mysql容器，查看状态，记住File、Position的值，在Slave中将用到
    `show master status\G;`
    
2. 从库mysql开启主从配置
    - 进入docker容器
    `docker exec -it mysql-slave bash`
    - 因为创建从库的配置的时候，就已经指定配置了，所以这里不需要再修改
        - 如果从库也是已经给创建好了的mysql, 并没有创建时就指定配置文件，则修改配置文件
        `vi /etc/mysql/my.cnf`
        - 增加配置
            ```
            [mysqld]
            server_id = 2
            log-bin= mysql-bin
            
            # 可选配置
            read-only=1
            
            replicate-ignore-db=mysql
            replicate-ignore-db=sys
            replicate-ignore-db=information_schema
            replicate-ignore-db=performance_schema
            ```
    
    - 进入mysql客户端
    `mysql -uroot -p`
    
    - 执行链接主库的命令
    `change master to master_host='172.17.0.2',master_user='slave',master_password='123456',master_log_file='mysql-bin.000001',master_log_pos=155,master_port=3306;`       
    说明：
        - `master_host` 需要查看master所在的mysql的host，在mster所在的mysql容器中查看 `cat /etc/hosts`
        - `master_user、master_password` 是访问主库的用户名称和密码
        - `master_log_file, master_log_pos` 是在主库中查看的bin-log信息
        - `master_port` 指定主库的端口
    
    - 启动从库同步
      `start slave;`
    
    - 查看链接状态
      `show slave status\G;`
      
      如果 show slave status\G命令结果中出现： Slave_IO_Running: Yes; Slave_SQL_Running: Yes 以上两项都为Yes，那说明没问题了。
    
    - 如果Slave_IO_Running: No 或者一直显示Connectinng，则可以在从库的容器中连接一下主库，查看是否可以连接到
        - 连接命令
        `mysql -uslave -p123456 -h172.17.0.2;`
        
    - 如果配置过程中过，始终无法连接到主库，则可以看这篇文章中是否有注意的问题：https://blog.csdn.net/mbytes/article/details/86711508      

3. 验证主从同步
    - 在主库中操作数据、新建数据库、表等操作，从库都会同步




