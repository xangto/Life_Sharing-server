package xangto.projects.life.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * JWT 工具类
 *
 * <p>以 Spring Bean 方式注入使用。注意：{@code @Value} 只能注入实例字段，
 * 静态字段不会被 Spring 注入（历史版本曾因静态注入导致密钥为 null，
 * 运行时 HMAC256(null) 直接抛异常）。</p>
 */
@Component
public class JWTUtils {

    /** 签发者标识 */
    @Value("${jwt.issure}")
    private String issure;

    /** 签名密钥 */
    @Value("${jwt.screct}")
    private String screct;

    /** token 有效期（小时） */
    @Value("${jwt.expires_time_hours}")
    private Long expiresTimeHours;

    // 生成token
    public String genJWT(String username, Long id) {
        Algorithm algorithm = Algorithm.HMAC256(screct);
        return JWT.create()
                .withIssuer(issure)                  // 设置发行者
                .withSubject(username)              // 设置主题
                .withClaim("userId", id)
                .withExpiresAt(Instant.now().plus(expiresTimeHours, ChronoUnit.HOURS)) // 设置过期时间
                .sign(algorithm);
    }

    // 解析jwt
    public String parseJWT(String token) {
        return verify(token).getSubject();
    }

    // 获取 userId
    public Long getUserId(String token) {
        return verify(token).getClaim("userId").asLong();
    }

    /**
     * 校验 token 是否有效：能通过签名与发行者校验、主题用户名与当前用户一致、且未过期
     *
     * @param token       JWT 字符串
     * @param userDetails 已加载的用户信息（用于比对用户名）
     * @return true 有效；false 无效（token 非法/已过期、用户名不匹配、用户信息为空）
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // 边界处理：用户信息为空则无从比对，直接判定无效
        if (userDetails == null) {
            return false;
        }
        try {
            DecodedJWT jwt = verify(token);
            // 有效条件：1) token 主题（签发时的用户名）与当前用户一致  2) 尚未过期
            return jwt.getSubject().equals(userDetails.getUsername())
                    && isTokenNotExpired(jwt);
        } catch (JWTVerificationException e) {
            // 签名不合法或已过期（TokenExpiredException 是 JWTVerificationException 的子类），视为无效
            return false;
        }
    }

    /**
     * 判断 token 是否尚未过期（过期时间晚于当前时间）
     *
     * <p>此方法须在 {@link #verify(String)} 校验通过之后调用：token 已过期时 verify 会直接
     * 抛出 {@link JWTVerificationException}，正常走到此处说明校验已通过；
     * 这里再显式比较一次过期时间，作为兜底并让语义更直观。</p>
     *
     * @param jwt 已通过校验的 token 解析结果
     * @return true 尚未过期；false 已过期
     */
    public boolean isTokenNotExpired(DecodedJWT jwt) {
        // token 过期时 verify 会直接抛 TokenExpiredException，正常走到这里说明尚未过期；
        // 再显式比较一次过期时间，逻辑自文档化并覆盖临界时刻
        return jwt.getExpiresAtAsInstant().isAfter(Instant.now());
    }

    /**
     * 按配置的密钥与发行者校验并解析 token，签名不合法或已过期时抛出 {@link JWTVerificationException}
     *
     * @param token JWT 字符串
     * @return 解析结果
     */
    private DecodedJWT verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(screct);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issure)
                .build();
        return verifier.verify(token);
    }
}
