# spring_boot_security
spring_boot_security jwt

## 简介
该项目是使用spring_boot_security 的项目 前后端分离 使用jwt认证 模拟登录和授权

## 准备工作
- 该项目是基于maven的项目，里面用到了lombok  所以导入该项目后 ide需要安装lombok
- jdk的版本是java8

## 项目本地部署方案
- git clone https://github.com/djkdeveloper/spring_boot_security.git 将项目拷贝到本地
- 将security.sql 导入mysql
- 配置application.yml 中的 数据库地址 和用户名密码
- 启动SpringBootSecurityApplication main方法

## docker-compose 部署
```bash
version: '3'
services:
  mysql:
    image : mysql:5.7.20
    ports:
      - "3306:3306"
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=123456

  security:
    image: djkdeveloper/spring_boot_security
    ports:
      - "8080:8080"
    environment:
      - DB_HOST=mysql
      - DB_SCHEMA=security
    links:
      - mysql
```


## 关于sql文件的说明
- 该项目的权限是 用户名-》角色-》权限
- 初始化sql中内置了2个角色 超级管理员 和一般用户 内置了2个权限 查询所有会有信息 更新用户信息
- 超级管理员默认有查询所有会有信息权限和更新用户信息权限  一般用户只有查询所有会员信息权限


## 新增用户接口 不需要认证和权限 （说明新增用户的 roleId 目前写死 只有1 或者2 ）
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/adduser.png)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/adduser2.png)

##  登录接口（spring security 默认提供的接口）
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/login.png)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/login2.png)


## 查询当前用户信息（此接口不需要权限，但是要登录）
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/current1.png)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/current2.png)

## 查询所有会有信息 (djk 和whx 都有)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/allusers.png)

##  更新用户信息 (djk有 whx没有)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/djk.png)
![image](https://raw.githubusercontent.com/djkdeveloper/spring_boot_security/master/images/whx.png)
