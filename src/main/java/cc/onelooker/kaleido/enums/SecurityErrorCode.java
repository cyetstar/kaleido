package cc.onelooker.kaleido.enums;

import com.zjjcnt.common.core.exception.ErrorCode;
import lombok.Getter;

/**
 * @author Hyliu
 */
@Getter
public enum SecurityErrorCode implements ErrorCode {

    /**
     * 系统管理错误码
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NO_PERMISSION(2001, "没有权限，请联系管理员授权");

    /**
     * 错误类型码
     */
    private final Integer code;
    /**
     * 错误类型描述信息
     */
    private final String message;

    SecurityErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
