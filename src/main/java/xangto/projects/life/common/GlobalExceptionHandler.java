package xangto.projects.life.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * <p>把 Controller 层抛出的异常统一转换为 Result JSON 返回。
 * 约定：HTTP 状态码与 Result.code 保持一致，与 SecurityConfig 中 401/403
 * entry point 的 HTTP 语义对齐——前端统一以 HTTP 状态码判断即可。</p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 登录认证失败（用户名或密码错误 / 用户不存在）
     *
     * <p>DaoAuthenticationProvider 默认会把 UsernameNotFoundException 也包装成 BadCredentialsException，
     * 这里不区分两者统一返回相同提示，避免向攻击者泄露"用户名是否存在"。</p>
     */
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Result<Void>> handleBadCredentials(Exception e) {
        log.warn("登录认证失败: {}", e.getMessage());
        // HTTP 401 与 SecurityConfig 的 authenticationEntryPoint 语义一致：未认证
        return ResponseEntity.status(ResultCode.UNAUTHORIZED.getCode())
                .body(Result.error(ResultCode.UNAUTHORIZED, "用户名或密码错误"));
    }

    /**
     * 业务异常：业务规则校验失败（如用户名已存在），HTTP 状态码取异常携带的结果码
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ResponseEntity.status(e.getResultCode().getCode())
                .body(Result.error(e.getResultCode(), e.getMessage()));
    }

    /**
     * 请求参数校验失败（@Valid + @NotBlank 等约束注解触发）
     * 返回第一条字段错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ResultCode.PARAM_VALIDATE_FAIL.getMessage());
        return ResponseEntity.status(ResultCode.PARAM_VALIDATE_FAIL.getCode())
                .body(Result.error(ResultCode.PARAM_VALIDATE_FAIL, message));
    }

    /**
     * 兜底异常：其余未处理的异常统一返回 500，并记录完整堆栈便于排查
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        log.error("系统异常: ", e);
        return ResponseEntity.status(ResultCode.INTERNAL_ERROR.getCode())
                .body(Result.error(ResultCode.INTERNAL_ERROR));
    }
}
