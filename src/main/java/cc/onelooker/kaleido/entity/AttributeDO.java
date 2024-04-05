package cc.onelooker.kaleido.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 属性DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.dto.AttributeDTO
 */
@Data
@TableName("attribute")
public class AttributeDO implements IdEntity<String> {
    private static final long serialVersionUID = -5942764788461927530L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 属性值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
