<br/>

## 概述

这个项目是我以前在校做的一个课程设计作业，最近又翻出来重新搞搞。

技术栈的话，前端使用 Bootstrap + JQuery 写的，后端使用 SpringBoot + Mybatis，统一使用 AJAX 交互，实现前后端分离（这样搞是有点折腾，不过想想重构更加麻烦，就凑合着写吧），用 Nginx 做反向代理解决跨域。再多的细节不想写了，把代码放上来，有兴趣的朋友可以研究一下。

前端：[Yee-Q/yeexang-community-web (github.com)](https://github.com/Yee-Q/yeexang-community-web)

后端：[Yee-Q/yeexang-community (github.com)](https://github.com/Yee-Q/yeexang-community)

<br/>

## 部署

安装 Nginx，在 nginx.conf 文件配置反向代理。我把静态文件放到 Nginx 的 html 目录下了，实现动静分离

```xml
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;
    keepalive_timeout  65;

    server {
        listen       80;
        server_name  localhost;

        location /community {
            if (!-e $request_filename){
                rewrite ^(.*)$ /$1.html last;
                break;
            }
            root   html;
            autoindex on;
        }

        location /community/api {
            proxy_pass   http://127.0.0.1:8080/yeexang-community;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
}
```

后端项目没什么好说的，配置信息改成自己的，maven 打包，数据库 sql 文件在 src/main/sql/yeexang_community_db.sql

```
mvn install
```

进入 target 目录运行 jar 包

```
nohup java -jar -Dspring.profiles.active=pro yeexang-community-0.0.1-SNAPSHOT.jar &
```

<br/>

## 效果展示

实例演示：http://styeeqan.com/community/

![](G:\Temp\community\login.jpg)

![](G:\Temp\community\index.jpg)

![](G:\Temp\community\publish.jpg)

![](G:\Temp\community\topic.jpg)

<br/>