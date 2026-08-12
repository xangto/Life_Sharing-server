package xangto.projects.life.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
import org.springframework.web.filter.OncePerRequestFilter;
import xangto.projects.life.utils.JWTUtils;

import java.io.IOException;

/**
 * JWT 认证过滤器
 *
 * <p>Spring Security 的过滤器链先于 MVC 拦截器执行，因此 JWT 认证必须放在过滤器里：
 * 从请求头解析 token，校验通过后将认证信息写入 {@link SecurityContextHolder}，
 * 后续 Security 授权判定（{@code anyRequest().authenticated()}）才能正确放行。</p>
 *
 * <p>认证失败时不直接写响应、也不设置认证信息——请求继续走过滤链，
 * 由 SecurityConfig 中配置的 authenticationEntryPoint 统一返回 401 JSON，
 * 避免出现两套 401 响应逻辑。</p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 用户信息加载器：按用户名从数据库加载用户及角色权限
     */
    private final UserDetailsService userDetailsService;

    /**
     * JWT 工具：解析与校验 token
     */
    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 跨域 OPTIONS 预检请求不携带业务凭证，直接放行（预检最终能否通过还取决于 CORS 配置）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. 提取请求头中的 Bearer token；缺失或格式不对则不认证，交给 401 entry point 统一处理
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasLength(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        // 2. 当前请求已认证则直接放行，避免重复认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 3. 解析 token 获取签发时的用户名；token 非法或已过期会抛异常，由下方 catch 兜底
            String username = jwtUtils.parseJWT(token);
            if (username != null) {
                // 4. 按用户名加载用户信息（含角色权限），并校验 token 与用户匹配且未过期
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.isTokenValid(token, userDetails)) {
                    // 5. 构造认证令牌：凭证传 null（token 已校验通过），权限取用户角色
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 附带本次请求详情（远程地址等），便于审计
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 写入 SecurityContextHolder：Security 授权阶段据此判定放行
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // 解析失败/用户不存在/token 无效：清空上下文，不设置认证信息，由 401 entry point 统一响应
            SecurityContextHolder.clearContext();
        }

        // 无论认证是否成功都继续走过滤链，由后续授权过滤器做最终判定
        filterChain.doFilter(request, response);
    }
}
