# life-server

个人博客平台后端服务（应用名 `life`），基于 Spring Boot 4.1.0 + Java 17。

## 技术栈

| 分类 | 技术                                                                   |
|---|------------------------------------------------------------------------|
| 基础框架 | Spring Boot 4.1.0（Java 17）、Maven                                    |
| Web | spring-boot-starter-webmvc、Jakarta Validation（Hibernate Validator）  |
| 安全 | Spring Security 7 + Auth0 java-jwt（JWT 无状态认证）                   |
| ORM | MyBatis-Plus 3.5.17（mybatis-plus-spring-boot4-starter）、Druid 连接池 |
| 数据库 | MySQL、Redis                                                                |
| 工具 | Lombok、MapStruct、Fastjson2、Hutool、Knife4j（接口文档）              |

## 环境要求

- JDK 17+
- Maven 3.6+（项目**没有 Maven Wrapper**，需使用本机安装的 Maven）
- MySQL 8+（本地默认 `localhost:3306`）


## 项目结构

```
src/main/java/xangto/projects/life/
├── LifeApplication.java        # 启动类
├── api/                        # 业务模块：user / category / tag / moment / blog / user_profile
│   └── <module>/
│       ├── controller/         # 前台接口（/xxx）与后台接口（/admin/xxx）
│       ├── service/            # 接口 + impl 实现
│       ├── mapper/             # MyBatis-Plus Mapper
│       ├── entity/             # 数据库实体
│       ├── dto/                # 请求参数
│       ├── vo/                 # 响应对象
│       └── converter/          # MapStruct 转换器
├── common/                     # 统一响应 Result、错误码、分页、全局异常处理
├── config/                     # Security、CORS、MyBatis-Plus、密码加密器
├── filter/                     # JwtAuthenticationFilter（JWT 认证过滤器）
└── utils/                      # JWTUtils
```

## 认证与权限

- **登录**：`POST /api/admin/user/login`，返回 `{ token, user }`
- **注册**：`POST /api/admin/user/register`
- 后续请求携带请求头：`Authorization: Bearer <token>`
- `JwtAuthenticationFilter` 在授权判定前解析 token 并写入 SecurityContext；`/admin/**`（除登录、注册外）全部要求认证
- 角色约定：`ROLE_USER`（普通用户）、`ROLE_ADMIN`（管理员），后台写操作通过 `@PreAuthorize("hasRole('ADMIN')")` 控制

## 接口约定

统一响应结构：

```json
{ "code": 200, "message": "操作成功", "data": {} }
```

**HTTP 状态码与 `code` 保持一致**，前端以 HTTP 状态码判断：

| 状态码 | 含义 |
|---|---|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录 / 登录已过期 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 409 | 资源已存在（如用户名重复） |
| 422 | 参数校验失败 |
| 500 | 系统内部错误 |

## 开发注意事项

- **JSON 列**：MySQL `json` 列在实体中映射为 `List<T>` 时，必须加 `@TableField(typeHandler = JacksonTypeHandler.class)`，且 `@TableName` 需开启 `autoResultMap = true`（否则查询时字段为 null），参考 `UserProfileEntity`
- **MapStruct**：注解处理器已在 `pom.xml` 的 `annotationProcessorPaths` 中配置（Lombok + mapstruct-processor），无需额外操作；新增转换方法后直接编译即可
- **循环依赖**：`PasswordEncoder` 单独放在 `PasswordConfig`，不要移回 `SecurityConfig`（会与过滤器链形成循环引用导致启动失败）
- **springdoc 兼容**：`override-with-generic-response: false` 配置用于绕过 springdoc 2.3.0 与 Spring 7 的二进制不兼容路径，勿删除
- 分页参数（`pageNum`/`pageSize`）已加 `@Min(1)` 校验，各 Controller 需配合 `@Valid` 使用
