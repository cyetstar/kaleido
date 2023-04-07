package cc.onelooker.kaleido.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ye Jianyu
 * @date 2020/12/17
 */
@Getter
public enum ButtonTypeEnum {

    /**
     * 目录
     */
    ADD("add", "新增"),

    /**
     * 修改
     */
    UPDATE("update", "修改"),

    /**
     * 删除
     */
    DELETE("delete", "删除"),

    /**
     * 详情
     */
    DETAIL("detail", "详情"),

    /**
     * 其他
     */
    OTHER("other", "其他");

    private final String value;

    private final String name;

    ButtonTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameByValue(String value) {
        for (ButtonTypeEnum buttonTypeEnum : ButtonTypeEnum.values()) {
            if (StringUtils.equals(value, buttonTypeEnum.getValue())) {
                return buttonTypeEnum.getName();
            }
        }
        return OTHER.getName();
    }
}
