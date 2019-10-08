### Docker安装nginx


**1. 准备目录**
`mkdir /server/nginx/html`
`mkdir /server/nginx/conf`
`mkdir /server/nginx/logs`

**2. 准备配置文件 nginx.conf**
```
#user  nginx;
worker_processes  1;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;     
    keepalive_timeout  65;

    server {
        listen 80;
        server_name locahost;
        client_max_body_size 5000M;

       # location / {
       #     root /server/nginx/html/;
       #     index index.html;
       # }

        location /admin {
             root /server/nginx/html/;
             index index.html;
             expires 30d; #缓存30天
        }

        location /blog {
             try_files $uri /blog/index.html;
             root /server/nginx/html/;
             autoindex on;
             autoindex_exact_size off;
             index index.html;
             expires 30d; #缓存30天
        }

        location /api {
             proxy_pass http://47.98.232.143:9011/;
             proxy_set_header Host $host:$server_port;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }

        location /admin-api {
             proxy_pass http://47.98.232.143:9010/;
             proxy_set_header Host $host:$server_port;
             proxy_set_header X-Real-IP $remote_addr;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }

       error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }

}
```

**3. 运行命令**
```bash
docker run -d \
 --name nginx \
 -p 443:443 \
 -p 80:80 \
--restart=always \
--privileged=true \
-e TZ="Asia/Shanghai" \
 -v /server/nginx/html:/server/nginx/html \
 -v /server/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
 -v /server/nginx/logs:/var/log/nginx \
hub.c.163.com/library/nginx
```

一定要加 `--privileged=true`或者在挂载文件后面加上ro 或rw应该也可以
否则访问404