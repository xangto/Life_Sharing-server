package xangto.projects.life.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

//    private final JwtInterceptor jwtInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/employee/login",           // 后台登录放行
//                        "/user/login",              // 用户端登录放行
//                        "/user/loginout",           // 用户端退出放行
//                        "/common/**",               // 文件上传下载
//                        "/backend/**",              // 后台管理静态资源
//                        "/front/**",                // 用户前端静态资源
//                        "/doc.html",                // Knife4j 文档页面
//                        "/v3/api-docs/**",          // OpenAPI 文档接口
//                        "/swagger-ui/**",           // Swagger UI 资源
//                        "/webjars/**"               // Knife4j/Swagger 静态依赖
//                );
//    }

    /**
     * 全部跨域全通过，开发环境用；生产不要用*
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许所有来源
                .allowedOrigins("http://192.168.0.105:3000")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许全部请求头
                .allowedHeaders("*")
                // 是否允许携带cookie；注意：allowedOrigins为*时不能设true，所以这里关闭
                .allowCredentials(true)
                // 预检请求有效期，单位秒
                .maxAge(3600);
    }
}
