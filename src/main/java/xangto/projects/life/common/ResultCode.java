package xangto.projects.life.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    // 成功
    SUCCESS(200, "操作成功"),
    // 失败
    ERROR(500, "操作失败"),
    // 客户端错误 4xx
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    PARAM_VALIDATE_FAIL(422, "参数校验失败"),

    // 服务端错误 5xx
    INTERNAL_ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
