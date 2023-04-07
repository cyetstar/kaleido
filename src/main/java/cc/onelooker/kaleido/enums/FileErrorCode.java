package cc.onelooker.kaleido.enums;

import com.zjjcnt.common.core.exception.ErrorCode;
import lombok.Getter;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName FileErrorCode.java
 * @Description TODO
 * @createTime 2022年02月26日 12:05:00
 */
@Getter
public enum FileErrorCode implements ErrorCode {
    /**
     * 文件管理错误码
     */
    ERROR(2400, "上传失败");

    /**
     * 错误类型码
     */
    private final Integer code;
    /**
     * 错误类型描述信息
     */
    private final String message;

    FileErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
