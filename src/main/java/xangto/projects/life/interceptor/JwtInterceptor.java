package xangto.projects.life.interceptor;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import xangto.projects.life.common.Result;
import xangto.projects.life.common.ResultCode;
import xangto.projects.life.utils.JWTUtils;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final UserDetailsService userDetailsService;

    public void checkFailed(HttpServletResponse res) throws IOException {
        Result<Object> error = Result.error(ResultCode.UNAUTHORIZED);
        String jsonString = JSONObject.toJSONString(error);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(jsonString);
        res.getWriter().flush();
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull Object handler) throws Exception {
        // ✅放过跨域OPTIONS预检请求，不做token校验
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            return true;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            checkFailed(res);
            return false;
        }
        String token = authHeader.substring(7);
        if (!StringUtils.hasLength(token)) {
            checkFailed(res);
            return false;
        }

        try {
            String username = JWTUtils.parseJWT(token);
            // 用户名解析成功且当前请求还未认证时，才加载用户信息（避免每次请求重复认证）
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 按用户名从数据库加载用户及角色权限
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // token 有效（用户名匹配且未过期）才建立认证信息
                if (JWTUtils.isTokenValid(token, userDetails)) {
                    // 构造认证令牌：凭证传 null（token 已在上面校验过），权限取用户角色
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 附带本次请求详情（远程地址等），便于审计与后续使用
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    // 写入 SecurityContextHolder：本次请求后续的授权判断都依赖它
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            checkFailed(res);
            return false;
        }

        return true;
    }
}
