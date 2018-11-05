# project-seed
Spring Boot种子项目

## 技术选型

### 后端

- SpringBoot
- Mybatis-Plus
- Shiro
- Redis
- Quartz
- RabbitMQ
- Swagger
- log4j2

### 前端

- Vue.js
- @vue/cli
- Ant Design
- Vuex
- Vue Router
- axios
- dayjs

## 模块说明

|模块名|说明|
|---|---|
|commons|封装项目中公共的方法、参数和对象，供其它模块依赖|
|generator|代码生成器，可一键生成entity、mapper、service、serviceImpl、controller类的完整代码，mapper.xml文件，swagger接口文档|
|server|后台服务，提供业务相关的RESTful接口，拥有完善的应用认证、授权、鉴权功能，还包含定时任务调度、session管理等功能|
|ui|后台管理页面，基于Vue.js生态和Ant-Design-Vue进行开发|

## 运行项目

1. 下载代码

    ```
    git clone https://github.com/YupaiTS/project-seed.git
    ```

1. 执行sql脚本

    在本地MySQL数据库创建默认的 `test` 数据库，执行 **generator** 模块 resources 目录下的 sql 脚本。

1. 执行单元测试代码插入测试数据

    执行 **server** 模块中的单元测试用例，向数据库中插入测试数据。

1. 构建后台管理页面（ui）

    进入 **ui** 目录，依次执行 `npm install` 和 `npm run build` 进行构建。

1. 执行 `copy_res.sh` 脚本将构建好的前端资源一键拷贝至后台服务相应目录

1. 运行后台服务（server）

    运行 **server** 模块中 `Application` 类的 `main()` 方法运行服务。

## Todo

- [x] 代码生成器
- [x] 鉴权规则动态管理
- [x] Session管理
- [x] RBAC管理
- [x] 定时任务调度管理
- [x] 内嵌Druid监控
- [x] 内嵌Swagger接口文档
- [x] 通用的管理页面vue组件
- [ ] 消息中心
- [ ] 基于Redis的接口缓存