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

    /**
     * 全部跨域全通过，开发环境用；生产不要用*
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许所有来源
                .allowedOrigins("*")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许全部请求头
                .allowedHeaders("*")
                // 是否允许携带cookie；注意：allowedOrigins为*时不能设true，所以这里关闭
                .allowCredentials(false)
                // 预检请求有效期，单位秒
                .maxAge(3600);
    }
}
