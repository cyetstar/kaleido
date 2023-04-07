package cc.onelooker.kaleido.enums;

import com.zjjcnt.common.core.exception.ErrorCode;
import lombok.Getter;

/**
 * @author yejianyu
 */
@Getter
public enum SysadminErrorCode implements ErrorCode {

    /**
     * 系统管理错误码
     */
    USERNAME_ALREADY_EXISTS(10101, "用户名已存在"),
    MOBILE_ALREADY_EXISTS(10102, "手机号已存在"),
    PASSWORD_INCORRECT(10103, "密码不正确"),
    TWO_PASSWORD_NOT_MATCH(10104, "两次输入的密码不一致"),
    USER_NOT_FOUND(20201, "用户不存在"),
    ROLE_NOT_FOUND(20202, "角色不存在"),
    ROLE_CODE_ALREADY_EXISTS(20203, "角色编码已存在"),
    MENU_NOT_FOUND(20204, "菜单不存在"),
    DICT_TYPE_ALREADY_EXISTS(20205, "字典类型已存在"),
    DICT_ALREADY_EXISTS(20206, "字典已存在"),
    POST_NAME_ALREADY_EXISTS(20207, "岗位名称已存在"),
    POST_CODE_ALREADY_EXISTS(20208, "岗位编码已存在"),
    PARENT_CANT_BE_ITSELF(20209, "父节点不能是自己"),
    SYS_CONFIG_ALREADY_EXISTS(20210, "系统配置已存在"),
    APP_CONFIG_NOT_EXISTS(20212, "客户端不存在"),
    DEPT_CODE_ALREADY_EXISTS(20211, "部门编码已存在");

    /**
     * 错误类型码
     */
    private final Integer code;
    /**
     * 错误类型描述信息
     */
    private final String message;

    SysadminErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
