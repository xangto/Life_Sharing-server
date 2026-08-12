package xangto.projects.life.interceptor;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            }
        } catch (Exception e) {
            checkFailed(res);
            return false;
        }

        return true;
    }
}
