<br/>

## 概述

这个项目是我以前在校做的一个课程设计作业，最近又翻出来重新搞搞。

技术栈的话，前端使用 Bootstrap + JQuery 写的，后端使用 SpringBoot + Mybatis，基于 RESTFul 风格编写接口。再多的细节不想写了，把代码放上来，有兴趣的朋友可以研究一下。

代码：[Yee-Q/yeexang-community (github.com)](https://github.com/Yee-Q/yeexang-community)

<br/>

## 部署

代码拉下来以后，配置信息改成自己的，maven 打包，再执行数据库 sql 文件，sql 文件在 src/main/sql/yeexang_community_db.sql

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