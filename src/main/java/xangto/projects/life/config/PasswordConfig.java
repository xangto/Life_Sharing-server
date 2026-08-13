package xangto.projects.life.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密器配置
 *
 * <p>单独成类以打破 Bean 循环依赖：SecurityConfig 构造时需要注入 JwtAuthenticationFilter，
 * 过滤器依赖 UserDetailsService（UserDetailsServiceImpl），
 * UserDetailsServiceImpl 依赖 UserService，PasswordEncoder</p>
 *
 * <p>若 PasswordEncoder 仍定义在 SecurityConfig 的 @Bean 方法里，
 * 创建 PasswordEncoder 需要 SecurityConfig 实例，
 * 而创建 SecurityConfig 又需要过滤器 → 用户服务 → 密码器，形成循环引用导致启动失败。
 * 挪出后依赖链变为单向：SecurityConfig → 过滤器 → 用户服务 → PasswordConfig。</p>
 */
@Configuration
public class PasswordConfig {

    /**
     * BCrypt 密码加密器：注册时对明文密码加密入库，登录时由 DaoAuthenticationProvider 比对
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
