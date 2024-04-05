package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 属性DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.entity.AttributeDO
 */
@Data
public class AttributeDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 3592452190234449273L;

    /**
     * 主键
     */
    private String id;

    /**
     * 属性值
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    // ------ 非数据库表字段 -------


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
