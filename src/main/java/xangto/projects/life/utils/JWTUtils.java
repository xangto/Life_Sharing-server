package xangto.projects.life.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
        Algorithm ALGORITHM = Algorithm.HMAC256(screct);
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer(issure)
                .build();
        DecodedJWT verify = verifier.verify(token);
        return verify.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {


    }

    public boolean isTokenExpired(String token, UserDetails userDetails) {


    }
}
