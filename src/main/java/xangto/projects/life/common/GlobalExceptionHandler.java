package xangto.projects.life.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * <p>把 Controller 层抛出的异常统一转换为 Result JSON 返回，
 * 避免 Spring 默认把异常渲染成 500 错误页/错误 JSON，保持 REST 接口风格。</p>
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
    public Result<Void> handleBadCredentials(Exception e) {
        log.warn("登录认证失败: {}", e.getMessage());
        return Result.error(ResultCode.UNAUTHORIZED, "用户名或密码错误");
    }

    /**
     * 请求参数校验失败（@Valid + @NotBlank 等约束注解触发）
     * 返回第一条字段错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ResultCode.PARAM_VALIDATE_FAIL.getMessage());
        return Result.error(ResultCode.PARAM_VALIDATE_FAIL, message);
    }

    /**
     * 兜底异常：其余未处理的异常统一返回 500，并记录完整堆栈便于排查
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error(ResultCode.INTERNAL_ERROR);
    }
}
