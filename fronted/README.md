# 医疗食品管理系统 - 前端项目

## 项目简介

这是一个基于 Vue 3 + TypeScript + Vite 构建的医疗食品管理系统前端项目，提供药膳查询、亚健康管理、用户管理、订单管理等功能。

## 技术栈

- **框架**: Vue 3.4.29
- **语言**: TypeScript 5.4
- **构建工具**: Vite 5.3.1
- **UI 组件库**: TDesign Vue Next 1.10.1
- **状态管理**: Pinia 2.1.7
- **路由**: Vue Router 4.3.3
- **HTTP 客户端**: Axios 1.7.7

## 项目结构

```
fronted/
├── src/
│   ├── assets/          # 静态资源
│   ├── components/      # 公共组件
│   ├── router/          # 路由配置
│   ├── stores/          # Pinia 状态管理
│   ├── views/           # 页面组件
│   │   ├── index.vue           # 首页
│   │   ├── login.vue           # 登录页
│   │   ├── register.vue        # 注册页
│   │   ├── user.vue            # 用户中心
│   │   ├── foodquery.vue       # 药膳查询
│   │   ├── Yjkquery.vue        # 亚健康查询
│   │   ├── ysgx.vue            # 药食功效
│   │   ├── article.vue         # 文章管理
│   │   └── administrate.vue    # 管理后台
│   ├── App.vue          # 根组件
│   └── main.ts          # 入口文件
├── public/              # 公共静态资源
├── vite.config.ts       # Vite 配置文件
├── package.json         # 项目依赖
└── tsconfig.json        # TypeScript 配置
```

## 环境要求

- Node.js >= 16.x
- npm >= 8.x 或 pnpm >= 7.x

## 安装依赖

```bash
npm install
```

或使用 pnpm:

```bash
pnpm install
```

## 开发环境运行

```bash
npm run dev
```

项目将在 `http://localhost:5173` 启动

## API 代理配置

### 重要说明

前端项目已配置 Vite 开发服务器代理，自动将 API 请求转发到后端服务器。

**代理配置** ([`vite.config.ts`](vite.config.ts:18)):

```typescript
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, '')
    }
  }
}
```

### 使用方式

#### 方式一：使用代理（推荐）

修改前端代码中的 API 请求地址，将 `http://localhost:9090` 改为 `/api`：

```typescript
// 修改前
const response = await axios.get('http://localhost:9090/user')

// 修改后
const response = await axios.get('/api/user')
```

**优点**:

- 避免跨域问题
- 生产环境可以轻松切换 API 地址
- 符合前后端分离最佳实践

#### 方式二：直接访问（当前代码使用）

当前代码直接访问 `http://localhost:9090`，需要确保：

1. 后端服务运行在 9090 端口，或
2. 修改所有 API 请求地址为后端实际端口（8080）

**注意**: 当前后端实际运行在 **8080** 端口，而前端代码中使用的是 **9090** 端口，这是导致接口调用失败的原因。

### 解决方案

**推荐方案**：批量修改前端代码，使用代理方式

需要修改以下文件中的 API 请求地址：

- [`src/views/index.vue`](src/views/index.vue)
- [`src/views/login.vue`](src/views/login.vue)
- [`src/views/register.vue`](src/views/register.vue)
- [`src/views/user.vue`](src/views/user.vue)
- [`src/views/foodquery.vue`](src/views/foodquery.vue)
- [`src/views/Yjkquery.vue`](src/views/Yjkquery.vue)
- [`src/views/ysgx.vue`](src/views/ysgx.vue)
- [`src/views/article.vue`](src/views/article.vue)
- [`src/views/administrate.vue`](src/views/administrate.vue)
- [`src/views/try.vue`](src/views/try.vue)

将所有 `http://localhost:9090` 替换为 `/api`

**临时方案**：修改后端端口为 9090

在后端 [`application.properties`](../backend/src/main/resources/application.properties) 中修改：

```properties
server.port=9090
```

## 构建生产版本

```bash
npm run build
```

构建产物将输出到 `dist/` 目录

## 预览生产构建

```bash
npm run preview
```

## 代码检查

```bash
npm run lint
```

## 代码格式化

```bash
npm run format
```

## 单元测试

```bash
npm run test:unit
```

## 主要功能模块

### 1. 用户模块

- 用户注册 ([`register.vue`](src/views/register.vue))
- 用户登录 ([`login.vue`](src/views/login.vue))
- 个人中心 ([`user.vue`](src/views/user.vue))
- 订单管理
- 地址管理

### 2. 药膳查询模块

- 药膳搜索 ([`foodquery.vue`](src/views/foodquery.vue))
- 药膳详情展示
- 药食功效查询 ([`ysgx.vue`](src/views/ysgx.vue))

### 3. 亚健康管理模块

- 亚健康症状查询 ([`Yjkquery.vue`](src/views/Yjkquery.vue))
- 推荐药膳方案
- 常见症状展示

### 4. 文章模块

- 健康文章列表 ([`article.vue`](src/views/article.vue))
- 文章详情查看

### 5. 管理后台

- 订单管理 ([`administrate.vue`](src/views/administrate.vue))
- 订单状态更新

## API 接口说明

### 用户相关

- `GET /user` - 用户登录
- `POST /user` - 用户注册
- `PUT /user` - 更新用户信息

### 药膳相关

- `GET /medicatedFood` - 查询药膳列表
- `GET /medicatedFood/benefit` - 查询药膳功效
- `GET /medicatedFood/subHealth` - 根据亚健康查询药膳

### 亚健康相关

- `GET /subHealth` - 查询亚健康信息
- `GET /subHealth/frequent` - 查询常见亚健康症状

### 订单相关

- `GET /order` - 查询订单列表
- `POST /order` - 创建订单
- `PUT /order` - 更新订单状态
- `DELETE /order` - 删除订单

### 地址相关

- `GET /address` - 查询地址列表
- `POST /address` - 添加地址
- `PUT /address` - 更新地址
- `DELETE /address` - 删除地址

### 文章相关

- `GET /article` - 查询文章列表

### 功效相关

- `GET /benefit` - 查询功效列表

## 常见问题

### 1. 接口调用失败

**问题**: 前端无法调用后端接口

**原因**:

- 后端服务未启动
- 端口号不匹配（前端使用 9090，后端运行在 8080）
- 跨域问题

**解决方案**:

1. 确保后端服务已启动在 8080 端口
2. 使用代理方式（推荐）：将所有 API 请求地址改为 `/api`
3. 或修改后端端口为 9090

### 2. 依赖安装失败

**解决方案**:

```bash
# 清除缓存
npm cache clean --force

# 删除 node_modules 和 package-lock.json
rm -rf node_modules package-lock.json

# 重新安装
npm install
```

### 3. 构建失败

**解决方案**:

```bash
# 类型检查
npm run type-check

# 查看具体错误信息
npm run build-only
```

## 开发规范

### 代码风格

- 使用 ESLint 进行代码检查
- 使用 Prettier 进行代码格式化
- 遵循 Vue 3 Composition API 风格

### 命名规范

- 组件文件名使用 PascalCase
- 普通文件名使用 kebab-case
- 变量和函数使用 camelCase

### Git 提交规范

```
feat: 新功能
fix: 修复 bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建/工具链相关
```

## 部署说明

### 开发环境

```bash
npm run dev
```

### 生产环境

1. 构建项目

```bash
npm run build
```

2. 将 `dist/` 目录部署到 Web 服务器（Nginx、Apache 等）

3. Nginx 配置示例：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    root /path/to/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # API 代理
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 联系方式

如有问题，请联系开发团队。

## 许可证

本项目仅供学习和研究使用。
