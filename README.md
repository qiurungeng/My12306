# 我的12306
东北大学软件学院《Android开发技术》小作业   
    
版本：   
1.0：   
本地数据库版——
在本地创建、存储用户   
2.0：
远程服务器版（当前版本）——远程服务器上运行服务端，客户端通过向服务端发送请求与得到响应来完成登录、注册、修改个人信息。   

> 服务端项目地址：https://github.com/qiurungeng/My12306-server

目前仅将服务端部署到本地虚拟机上。

远程服务器地址在service包下的UserService类中设置：   

```java
private static final String SERVER_ADDR="http://118.202.13.155:12306";
private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
private static final String LOGIN_URL=SERVER_ADDR+"/login";
private static final String REGISTER_URL=SERVER_ADDR+"/register";
private static final String CHANGE_PROFILE_URL=SERVER_ADDR+"/changeProfile";
```

