package xangto.projects.life.common;

import lombok.Getter;

/**
 * 业务异常：业务规则校验失败时抛出（如用户名已存在），
 * 由 {@link GlobalExceptionHandler} 统一转换为携带对应 HTTP 状态码的 Result 响应。
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 异常对应的结果码：决定 HTTP 状态码与默认提示信息 */
    private final ResultCode resultCode;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}
