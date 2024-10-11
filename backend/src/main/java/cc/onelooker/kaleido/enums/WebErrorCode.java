package cc.onelooker.kaleido.enums;

import com.zjjcnt.common.core.exception.ErrorCode;
import lombok.Getter;

/**
 * @author Hyliu
 */
@Getter
public enum WebErrorCode implements ErrorCode {

    /**
     * 系统管理错误码
     */
    WRONG_PASSWORD(401, "用户名或密码错误"),
    ACCOUNT_FREEZE(401, "帐号已被禁用");

    /**
     * 错误类型码
     */
    private final Integer code;
    /**
     * 错误类型描述信息
     */
    private final String message;

    WebErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
