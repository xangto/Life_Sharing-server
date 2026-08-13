package xangto.projects.life.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密器配置
 *
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
