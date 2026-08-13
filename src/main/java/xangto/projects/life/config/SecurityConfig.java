package xangto.projects.life.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xangto.projects.life.common.Result;
import xangto.projects.life.common.ResultCode;
import xangto.projects.life.filter.JwtAuthenticationFilter;

/**
 * Spring Security 安全配置
 *
 * <p>认证方案：JWT 无状态认证（前后端分离），服务端不保存会话，
 * 由 {@link JwtAuthenticationFilter} 在授权判定前解析 token 并写入 SecurityContext，
 * 与下方 {@code anyRequest().authenticated()} 共同构成完整认证链路。</p>
 *
 * <p>如需跨域（前后端分离部署），另行提供 {@code CorsConfigurationSource} Bean 并开启 cors 配置。</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** JWT 认证过滤器：在授权判定前解析 token 并设置认证信息 */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 安全过滤链：配置访问权限、登录、放行路径
     *
     * <p>整体策略：</p>
     * <ul>
     *   <li>无状态会话（STATELESS）：不创建、不使用 HttpSession，每次请求携带 JWT</li>
     *   <li>关闭 CSRF：Token 认证无 Cookie 会话，不存在跨站请求伪造风险</li>
     *   <li>关闭 formLogin / httpBasic / logout：认证完全由自定义登录接口 + JWT 过滤器完成</li>
     *   <li>白名单：登录/注册接口、Knife4j 接口文档资源、错误页</li>
     *   <li>其余请求一律要求认证：未认证返回 401 JSON，无权限返回 403 JSON（默认是 HTML 页面，不符合 REST 风格）</li>
     * </ul>
     *
     * @param http HTTP 安全构建器
     * @return 组装完成的安全过滤链
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) {
        http
                // JWT 认证过滤器：先于用户名密码认证过滤器执行，保证授权判定前 SecurityContext 已就绪
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 开启跨域支持：复用 WebMvcConfig 中 addCorsMappings 的 CORS 规则
                // （Security 通过 HandlerMappingIntrospector 读取 MVC 的 CORS 配置），
                // 使跨域预检 OPTIONS 由 CorsFilter 在授权判定前处理，不再被 401 拦截
                .cors(Customizer.withDefaults())
                // 关闭 CSRF：JWT 在请求头中传递，基于 Token 的无状态认证不需要 CSRF 防护
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭表单登录与 HTTP Basic 登录：统一走自定义登录接口（/api/admin/user/login）签发 JWT
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 关闭默认登出：无状态认证由前端丢弃 Token 即可，无需服务端登出接口
                .logout(AbstractHttpConfigurer::disable)
                // 无状态会话策略：禁止创建 Session，避免引入额外状态
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 请求授权规则
                .authorizeHttpRequests(auth -> auth
                        // 认证白名单：登录、注册接口无需 Token 即可访问
                        // （Security 匹配的是去掉 context-path 后的路径，故不带 /api 前缀）
                        .requestMatchers("/admin/user/login", "/admin/user/register").permitAll()
                        // 文档白名单：Knife4j 页面（/doc.html）、其静态资源、OpenAPI 描述接口、图标与错误页
                        .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**", "/favicon.ico", "/error").permitAll()
                        // admin 下除登录、注册外的全部接口必须认证
                        .requestMatchers("/admin/**").authenticated()
                        // 其余请求不用认证
                        .anyRequest().permitAll()
                )
                // 认证异常处理：返回 Result JSON 而非默认 HTML，与全局异常处理器保持同一数据结构
                .exceptionHandling(handling -> handling
                        // 未认证（401）：Token 缺失、过期或非法
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.UNAUTHORIZED)));
                        })
                        // 已认证但权限不足（403）
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(JSON.toJSONString(Result.error(ResultCode.FORBIDDEN)));
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
