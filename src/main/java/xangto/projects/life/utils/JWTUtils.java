package xangto.projects.life.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JWTUtils {
    @Value("${jwt.issure}")
    private static String issure;
    @Value("${jwt.screct}")
    private static String screct;
    @Value("${jwt.expires_time_hours}")
    private static Long expiresTimeHours;

    // 生成token
    public static String genJWT(String username) {
        Algorithm ALGORITHM = Algorithm.HMAC256(screct);
        return JWT.create()
                .withIssuer(issure)                  // 设置发行者
                .withSubject(username)              // 设置主题
                .withExpiresAt(Instant.now().plus(expiresTimeHours, ChronoUnit.HOURS)) // 设置过期时间
                .sign(ALGORITHM);
    }

    // 解析jwt
    public static String parseJWT(String token) {
        return verify(token).getSubject();
    }

    /**
     * 校验 token 是否有效：能通过签名与发行者校验、主题用户名与当前用户一致、且未过期
     *
     * @param token       JWT 字符串
     * @param userDetails 已加载的用户信息（用于比对用户名）
     * @return true 有效；false 无效（token 非法/已过期、用户名不匹配、用户信息为空）
     */
    public static boolean isTokenValid(String token, UserDetails userDetails) {
        // 边界处理：用户信息为空则无从比对，直接判定无效
        if (userDetails == null) {
            return false;
        }
        try {
            DecodedJWT jwt = verify(token);
            // 有效条件：1) token 主题（签发时的用户名）与当前用户一致  2) 过期时间晚于当前时间
            return jwt.getSubject().equals(userDetails.getUsername())
                    && isTokenExpired(jwt);
        } catch (JWTVerificationException e) {
            // 签名不合法或已过期（TokenExpiredException 是 JWTVerificationException 的子类），视为无效
            return false;
        }
    }

    /**
     * 判断 token 是否已过期
     *
     * @param jwt
     * @return boolean
     */
    public static boolean isTokenExpired(DecodedJWT jwt) {
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
    private static DecodedJWT verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(screct);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issure)
                .build();
        return verifier.verify(token);
    }
}
