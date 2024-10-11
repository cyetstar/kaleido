package cc.onelooker.kaleido.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 别名DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.dto.AlternateTitleDTO
 */
@Data
@TableName("alternate_title")
public class AlternateTitleDO implements IdEntity<String> {
    private static final long serialVersionUID = -6551661061599936213L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

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

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
