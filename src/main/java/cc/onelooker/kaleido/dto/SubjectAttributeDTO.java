package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 属性DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.entity.SubjectAttributeDO
 */
@Data
public class SubjectAttributeDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 908710843269484403L;

    /**
     * 主键
     */
    private String id;

    /**
     * 属性id
     */
    private String attributeId;

    /**
     * 项目id
     */
    private String subjectId;

    /**
     * 项目类型
     */
    private String subjectType;

    // ------ 非数据库表字段 -------

    private List<String> subjectIdList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
