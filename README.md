# 电影购票系统

## 技术栈
Spring Cloud Alibaba + Redis + Vue3 + 微信小程序 + Docker

## 环境要求
- JDK 17、Maven 3.8+
- MySQL 8.0、Redis 7
- Nacos 2.x、Seata 1.6.1、Zipkin
- Node 18+（前端）
- Docker（可选，容器化部署）

## 项目结构
  demo2 -->后端微服务
  movie-ticket -->微信小程序
  vue_2026 -->前端管理端

## 快速启动（5 步）
1. 初始化数据库：
   mysql -u root -p < backend/sql/init.sql

2. 启动中间件：
   Nacos、Redis、MySQL、Seata、Zipkin（各自命令）

3. 启动后端（IDEA 或 Docker）：
   # Docker 方式
   cd backend/demo2 && docker compose up -d

   # IDEA 方式：启动 6 个 Application

4. 启动前端：
   cd admin-web && npm install && npm run dev

5. 小程序：
   微信开发者工具导入 miniprogram，改 BASE_URL

## 注意事项
请在各业务模块的配置文件中将占位符注释替换为自己的MySQL配置和阿里云oss镜像配置

