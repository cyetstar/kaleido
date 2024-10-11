package cc.onelooker.kaleido.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 属性DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.dto.SubjectAttributeDTO
 */
@Data
@TableName("subject_attribute")
public class SubjectAttributeDO implements BaseDTO<String> {
    private static final long serialVersionUID = -7947134619062654010L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 属性id
     */
    @TableField(value = "attribute_id")
    private String attributeId;

    /**
     * 项目id
     */
    @TableField(value = "subject_id")
    private String subjectId;

    /**
     * 项目类型
     */
    @TableField(value = "subject_type")
    private String subjectType;

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
