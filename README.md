# MedicalFood - 医疗健康食品管理系统

## 📋 项目简介

MedicalFood 是一个基于 Spring Boot 3.3.4 和 Vue 3 的全栈医疗健康食品管理系统。该系统集成了 AI 智能助手功能，为用户提供药膳食品查询、亚健康状态管理、健康文章阅读、订单管理等功能，旨在帮助用户通过科学的药膳食疗改善健康状况。

### 核心功能

- 🔐 **用户管理**：用户注册、登录、信息管理
- 🍲 **药膳食品管理**：药膳查询、分类浏览、功效查询
- 💊 **亚健康管理**：亚健康状态查询、症状匹配、推荐药膳
- 📰 **健康文章**：健康知识文章浏览
- 🛒 **订单管理**：药膳订单创建、查询、修改、删除
- 📍 **地址管理**：收货地址的增删改查
- 🤖 **AI 智能助手**：集成阿里通义千问大模型，提供健康咨询服务

---

## 🛠️ 技术栈

### 后端技术栈

| 技术         | 版本          | 说明           |
| ------------ | ------------- | -------------- |
| Spring Boot  | 3.3.4         | 核心框架       |
| Java         | 21.0.9        | 开发语言       |
| MyBatis      | 3.0.3         | ORM 框架       |
| MySQL        | 8.x           | 数据库         |
| Lombok       | -             | 简化 Java 代码 |
| LangChain4j  | 1.10.0-beta18 | AI 集成框架    |
| 阿里通义千问 | qwen-turbo    | 大语言模型     |

### 前端技术栈

| 技术             | 版本   | 说明        |
| ---------------- | ------ | ----------- |
| Vue              | 3.4.29 | 前端框架    |
| TypeScript       | 5.4.0  | 开发语言    |
| Vite             | 5.3.1  | 构建工具    |
| Vue Router       | 4.3.3  | 路由管理    |
| Pinia            | 2.1.7  | 状态管理    |
| Axios            | 1.7.7  | HTTP 客户端 |
| TDesign Vue Next | 1.10.1 | UI 组件库   |

---

## 📁 项目结构

```
MedicalFood/
├── backend/                          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/websocket/health/
│   │   │   │   ├── HealthApplication.java    # 启动类
│   │   │   │   ├── controller/               # 控制器层
│   │   │   │   │   ├── UserController.java          # 用户管理
│   │   │   │   │   ├── MedicatedFoodController.java # 药膳管理
│   │   │   │   │   ├── SubHealthController.java     # 亚健康管理
│   │   │   │   │   ├── ArticleController.java       # 文章管理
│   │   │   │   │   ├── OrderController.java         # 订单管理
│   │   │   │   │   ├── AddressController.java       # 地址管理
│   │   │   │   │   ├── BenefitController.java       # 功效管理
│   │   │   │   │   └── ChatController.java          # AI 聊天
│   │   │   │   ├── service/                  # 服务层
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   ├── MedicatedFoodService.java
│   │   │   │   │   ├── SubHealthService.java
│   │   │   │   │   ├── ArticleService.java
│   │   │   │   │   ├── OrderService.java
│   │   │   │   │   ├── AddressService.java
│   │   │   │   │   ├── BenefitService.java
│   │   │   │   │   └── Assistant.java        # AI 助手服务
│   │   │   │   ├── mapper/                   # 数据访问层
│   │   │   │   │   ├── UserMapper.java
│   │   │   │   │   ├── MedicatedFoodMapper.java
│   │   │   │   │   ├── SubHealthMapper.java
│   │   │   │   │   ├── ArticleMapper.java
│   │   │   │   │   ├── OrderMapper.java
│   │   │   │   │   ├── AddressMapper.java
│   │   │   │   │   └── BenefitMapper.java
│   │   │   │   └── pojo/                     # 实体类
│   │   │   │       ├── User.java
│   │   │   │       ├── MedicatedFood.java
│   │   │   │       ├── SubHealth.java
│   │   │   │       ├── Article.java
│   │   │   │       ├── Order.java
│   │   │   │       ├── Address.java
│   │   │   │       ├── Benefit.java
│   │   │   │       └── Result.java           # 统一响应结果
│   │   │   └── resources/
│   │   │       ├── application.properties    # 配置文件
│   │   │       └── com/websocket/health/mapper/  # MyBatis XML
│   │   └── test/                             # 测试代码
│   └── pom.xml                               # Maven 配置
│
└── fronted/                          # 前端项目
    ├── src/
    │   ├── views/                    # 页面组件
    │   │   ├── index.vue             # 首页
    │   │   ├── login.vue             # 登录页
    │   │   ├── register.vue          # 注册页
    │   │   ├── user.vue              # 用户中心
    │   │   ├── foodquery.vue         # 药膳查询
    │   │   ├── Yjkquery.vue          # 亚健康查询
    │   │   ├── article.vue           # 文章列表
    │   │   ├── administrate.vue      # 管理页面
    │   │   └── ysgx.vue              # 饮食功效
    │   ├── router/                   # 路由配置
    │   ├── stores/                   # 状态管理
    │   ├── components/               # 公共组件
    │   └── assets/                   # 静态资源
    ├── package.json                  # 依赖配置
    └── vite.config.ts                # Vite 配置
```

---

## 🚀 环境要求

### 后端环境

- **JDK**: 17 或更高版本（推荐 Java 21）
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **IDE**: IntelliJ IDEA / Eclipse（推荐 IntelliJ IDEA）

### 前端环境

- **Node.js**: 20.x+
- **npm**: 9.x+ 或 pnpm
- **浏览器**: Chrome / Edge / Firefox（最新版本）

---

## 📦 安装与运行

### 1. 克隆项目

```bash
git clone <repository-url>
cd MedicalFood
```

### 2. 数据库配置

创建 MySQL 数据库：

```sql
CREATE DATABASE health CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

导入数据库脚本（如果有提供 SQL 文件）：

```bash
mysql -u root -p health < database.sql
```

### 3. 后端配置与启动

#### 3.1 修改配置文件

编辑 [`backend/src/main/resources/application.properties`](backend/src/main/resources/application.properties:1)：

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/health
spring.datasource.username=root
spring.datasource.password=your_password

# 通义千问 API 配置（可选，如不使用 AI 功能可跳过）
langchain4j.open-ai.chat-model.api-key=your_api_key
```

#### 3.2 启动后端服务

**方式一：使用 Maven 命令**

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**方式二：使用 IDE**

1. 在 IntelliJ IDEA 中打开 `backend` 目录
2. 等待 Maven 依赖下载完成
3. 运行 [`HealthApplication.java`](backend/src/main/java/com/websocket/health/HealthApplication.java:1) 主类

**启动成功标志**：

控制台应显示类似以下日志：

```
Started HealthApplication in X.XXX seconds (process running for X.XXX)
Tomcat started on port 8080 (http) with context path '/'
```

**注意**：您提供的日志片段显示的是测试启动阶段，并非完整的应用启动日志。完整启动成功后会显示：

- Tomcat 启动端口（默认 8080）
- 应用启动总耗时
- 数据库连接状态

### 4. 前端配置与启动

#### 4.1 安装依赖

```bash
cd fronted
npm install
```

#### 4.2 启动开发服务器

```bash
npm run dev
```

前端服务默认运行在 `http://localhost:5173`

#### 4.3 生产环境构建

```bash
npm run build
```

构建产物位于 `fronted/dist` 目录。

---

## 🔌 API 接口文档

### 基础信息

- **Base URL**: `http://localhost:8080`
- **响应格式**: JSON
- **统一响应结构**:

```json
{
  "code": 1, // 1: 成功, 0: 失败
  "msg": "success", // 响应消息
  "data": {}, // 响应数据
  "total": 0 // 总数（分页时使用）
}
```

### 用户管理 API

| 方法   | 路径    | 说明         | 参数                  |
| ------ | ------- | ------------ | --------------------- |
| GET    | `/user` | 用户登录     | `account`, `password` |
| POST   | `/user` | 用户注册     | `User` 对象（JSON）   |
| PUT    | `/user` | 更新用户信息 | `User` 对象（JSON）   |
| DELETE | `/user` | 删除用户     | `User` 对象（JSON）   |

### 药膳食品 API

| 方法 | 路径                       | 说明           | 参数          |
| ---- | -------------------------- | -------------- | ------------- |
| GET  | `/medicatedFood`           | 查询药膳       | `queryName`   |
| GET  | `/medicatedFood/id`        | 根据 ID 查询   | `id`          |
| GET  | `/medicatedFood/subHealth` | 根据亚健康查询 | `subHealthId` |
| GET  | `/medicatedFood/benefit`   | 根据功效查询   | `benefitId`   |
| GET  | `/medicatedFood/order`     | 按订单量排序   | -             |

### 亚健康管理 API

| 方法 | 路径                  | 说明           | 参数        |
| ---- | --------------------- | -------------- | ----------- |
| GET  | `/subHealth`          | 查询亚健康状态 | `queryName` |
| GET  | `/subHealth/frequent` | 查询常见亚健康 | -           |
| GET  | `/subHealth/article`  | 根据文章查询   | `articleId` |

### 文章管理 API

| 方法 | 路径       | 说明         | 参数 |
| ---- | ---------- | ------------ | ---- |
| GET  | `/article` | 获取所有文章 | -    |

### 订单管理 API

| 方法   | 路径     | 说明         | 参数                 |
| ------ | -------- | ------------ | -------------------- |
| GET    | `/order` | 查询用户订单 | `userId`             |
| POST   | `/order` | 创建订单     | `Order` 对象（JSON） |
| PUT    | `/order` | 更新订单     | `Order` 对象（JSON） |
| DELETE | `/order` | 删除订单     | `id`                 |

### AI 智能助手 API

| 方法 | 路径          | 说明                | 参数      |
| ---- | ------------- | ------------------- | --------- |
| GET  | `/chat/hello` | 直接调用大模型      | `message` |
| GET  | `/chat/ai`    | 通过 AiService 调用 | `message` |

---

## ⚙️ 配置说明

### 后端配置项

[`application.properties`](backend/src/main/resources/application.properties:1) 主要配置：

```properties
# 应用名称
spring.application.name=health

# 服务端口
server.port=8080

# 数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/health
spring.datasource.username=root
spring.datasource.password=123456

# MyBatis 配置
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis.configuration.map-underscore-to-camel-case=true

# 通义千问 AI 配置
langchain4j.open-ai.chat-model.api-key=your_api_key
langchain4j.open-ai.chat-model.model-name=qwen-turbo
langchain4j.open-ai.chat-model.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
langchain4j.open-ai.chat-model.timeout=30000
langchain4j.open-ai.chat-model.temperature=0.7
langchain4j.open-ai.chat-model.max-tokens=2048
```

### 前端配置项

[`vite.config.ts`](fronted/vite.config.ts:1) 可配置代理、构建选项等。

---

## 🔍 后端启动状态分析

### 当前日志分析

根据您提供的控制台日志：

```
2026-01-27T22:03:10.646+08:00  INFO 18312 --- [health] [           main] c.w.health.HealthApplicationTests        : Starting HealthApplicationTests using Java 21.0.9
2026-01-27T22:03:10.647+08:00  INFO 18312 --- [health] [           main] c.w.health.HealthApplicationTests        : No active profile set, falling back to 1 default profile: "default"
Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
```

**状态判断**：

- ✅ **当前阶段**：应用正在启动中（初始化阶段）
- ⚠️ **启动状态**：未完全启动，这是测试类 `HealthApplicationTests` 的启动日志
- 📝 **已完成步骤**：
  - Spring Boot 应用上下文初始化
  - 使用默认配置文件（default profile）
  - MyBatis 日志适配器初始化

**判断依据**：

1. 日志显示的是 `HealthApplicationTests`（测试类），而非 `HealthApplication`（主应用）
2. 缺少关键的启动完成标志：
   - Tomcat 启动端口信息
   - 应用启动总耗时
   - "Started HealthApplication in X seconds" 消息

### 完整启动成功应显示的日志

正常启动成功后，控制台应包含以下关键信息：

```
// 1. 数据库连接初始化
HikariPool-1 - Starting...
HikariPool-1 - Start completed.

// 2. Tomcat 启动
Tomcat initialized with port 8080 (http)
Starting service [Tomcat]
Starting Servlet engine: [Apache Tomcat/10.1.x]

// 3. 应用启动完成（最重要）
Started HealthApplication in 3.456 seconds (process running for 4.123)
Tomcat started on port 8080 (http) with context path '/'
```

### 如何确认启动状态

**方法一：查看完整日志**

继续观察控制台输出，等待出现 "Started HealthApplication" 消息。

**方法二：检查端口占用**

```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080
```

**方法三：访问健康检查端点**

```bash
curl http://localhost:8080/actuator/health
# 或访问任意 API 端点
curl http://localhost:8080/article
```

**方法四：查看进程**

在 IDE 中查看运行状态，或使用任务管理器查看 Java 进程。

---

## 🔧 可改进的位置

### 1. 安全性改进 ⚠️ **高优先级**

#### 1.1 密码安全

- **问题**：[`User.java`](backend/src/main/java/com/websocket/health/pojo/User.java:25) 中密码明文存储
- **改进**：

  - 使用 BCrypt 或 Argon2 加密密码
  - 实现密码强度验证

  ```java
  // 示例：使用 Spring Security 的 BCryptPasswordEncoder
  @Autowired
  private PasswordEncoder passwordEncoder;

  public void registerUser(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userMapper.insert(user);
  }
  ```

#### 1.2 API 认证授权

- **问题**：所有接口未实现认证机制，任何人都可访问
- **改进**：
  - 集成 Spring Security + JWT
  - 实现基于角色的访问控制（RBAC）
  - 添加接口权限注解

#### 1.3 敏感信息保护

- **问题**：[`application.properties`](backend/src/main/resources/application.properties:11) 中数据库密码和 API Key 明文存储
- **改进**：
  - 使用环境变量或配置中心（如 Nacos、Apollo）
  - 使用 Jasypt 加密敏感配置
  - 将 `application.properties` 添加到 `.gitignore`

#### 1.4 SQL 注入防护

- **问题**：部分 Mapper 可能存在 SQL 注入风险
- **改进**：
  - 确保所有 MyBatis 查询使用 `#{}` 而非 `${}`
  - 添加输入参数验证

### 2. 代码结构优化 📐 **中优先级**

#### 2.1 统一异常处理

- **问题**：缺少全局异常处理机制
- **改进**：
  ```java
  @RestControllerAdvice
  public class GlobalExceptionHandler {
      @ExceptionHandler(Exception.class)
      public Result handleException(Exception e) {
          log.error("系统异常", e);
          return Result.error("系统异常：" + e.getMessage());
      }
  }
  ```

#### 2.2 参数校验

- **问题**：控制器层缺少参数校验
- **改进**：
  - 使用 `@Valid` 和 `@Validated` 注解
  - 在实体类中添加 JSR-303 校验注解
  ```java
  @Data
  public class User {
      @NotBlank(message = "账号不能为空")
      @Size(min = 3, max = 20, message = "账号长度为3-20位")
      private String account;

      @NotBlank(message = "密码不能为空")
      @Size(min = 6, message = "密码至少6位")
      private String password;
  }
  ```

#### 2.3 日志规范

- **问题**：缺少统一的日志记录
- **改进**：
  - 使用 SLF4J + Logback
  - 添加请求日志、业务日志、错误日志
  - 配置日志文件滚动策略

#### 2.4 DTO 与 Entity 分离

- **问题**：直接使用实体类作为接口参数和返回值
- **改进**：
  - 创建 DTO（Data Transfer Object）层
  - 使用 MapStruct 或 BeanUtils 进行对象转换

### 3. 性能优化 🚀 **中优先级**

#### 3.1 数据库优化

- **改进**：
  - 添加数据库索引（用户账号、药膳名称等）
  - 实现分页查询（避免一次性加载大量数据）
  - 使用连接池优化（HikariCP 已集成）
  - 添加数据库查询缓存

#### 3.2 缓存机制

- **问题**：每次请求都查询数据库
- **改进**：
  - 集成 Redis 缓存热点数据
  - 使用 Spring Cache 注解
  ```java
  @Cacheable(value = "articles", key = "#id")
  public Article getArticleById(Integer id) {
      return articleMapper.selectById(id);
  }
  ```

#### 3.3 接口性能

- **改进**：
  - 实现接口限流（使用 Guava RateLimiter 或 Redis）
  - 添加接口响应时间监控
  - 优化 N+1 查询问题

### 4. 功能完善 ✨ **中优先级**

#### 4.1 API 文档

- **问题**：缺少 API 文档
- **改进**：
  - 集成 Swagger/Knife4j 自动生成 API 文档
  ```xml
  <dependency>
      <groupId>com.github.xiaoymin</groupId>
      <artifactId>knife4j-spring-boot-starter</artifactId>
      <version>4.3.0</version>
  </dependency>
  ```

#### 4.2 数据校验

- **改进**：
  - 添加业务逻辑校验（如订单金额、库存等）
  - 实现幂等性控制（防止重复提交）

#### 4.3 事务管理

- **问题**：Service 层未明确声明事务
- **改进**：
  - 在 Service 方法上添加 `@Transactional` 注解
  - 配置事务传播行为和隔离级别

#### 4.4 文件上传

- **改进**：
  - 实现药膳图片上传功能
  - 集成 OSS（阿里云、七牛云等）

### 5. 测试完善 🧪 **低优先级**

#### 5.1 单元测试

- **问题**：测试覆盖率低
- **改进**：
  - 为 Service 层编写单元测试
  - 使用 Mockito 模拟依赖
  - 目标测试覆盖率 > 80%

#### 5.2 集成测试

- **改进**：
  - 编写 Controller 层集成测试
  - 使用 TestContainers 进行数据库测试

### 6. 运维部署 🐳 **低优先级**

#### 6.1 容器化

- **改进**：
  - 编写 Dockerfile
  - 使用 Docker Compose 编排服务
  ```dockerfile
  FROM openjdk:21-jdk-slim
  COPY target/health-0.0.1-SNAPSHOT.jar app.jar
  ENTRYPOINT ["java", "-jar", "/app.jar"]
  ```

#### 6.2 配置管理

- **改进**：
  - 使用 Spring Cloud Config 或 Nacos
  - 实现多环境配置（dev、test、prod）

#### 6.3 监控告警

- **改进**：
  - 集成 Spring Boot Actuator
  - 使用 Prometheus + Grafana 监控
  - 配置日志收集（ELK）

### 7. 前端优化 🎨 **中优先级**

#### 7.1 代码规范

- **改进**：
  - 统一组件命名规范
  - 使用 ESLint + Prettier 格式化代码
  - 添加 Git Hooks（husky + lint-staged）

#### 7.2 性能优化

- **改进**：
  - 实现路由懒加载
  - 图片懒加载和压缩
  - 使用 CDN 加速静态资源

#### 7.3 用户体验

- **改进**：
  - 添加 Loading 状态
  - 实现错误提示和重试机制
  - 优化移动端适配

### 8. 文档完善 📚 **低优先级**

- **改进**：
  - 编写数据库设计文档
  - 添加接口调用示例
  - 编写开发规范文档
  - 添加常见问题（FAQ）

---

## 📝 开发规范建议

### 代码提交规范

使用 Conventional Commits 规范：

```
feat: 新增药膳推荐功能
fix: 修复订单创建时的空指针异常
docs: 更新 API 文档
style: 格式化代码
refactor: 重构用户服务层
test: 添加订单服务单元测试
chore: 更新依赖版本
```

### 分支管理

```
main/master  - 生产环境分支
develop      - 开发分支
feature/*    - 功能分支
hotfix/*     - 紧急修复分支
release/*    - 发布分支
```

---

## 🤝 贡献指南

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'feat: Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

---

## 📄 许可证

本项目采用 MIT 许可证。详见 `LICENSE` 文件。

---

## 👥 联系方式

- 项目维护者：[您的名字]
- 邮箱：[您的邮箱]
- 项目地址：[GitHub 仓库地址]

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [MyBatis](https://mybatis.org/)
- [LangChain4j](https://github.com/langchain4j/langchain4j)
- [TDesign](https://tdesign.tencent.com/)

---

**最后更新时间**：2026-01-27
