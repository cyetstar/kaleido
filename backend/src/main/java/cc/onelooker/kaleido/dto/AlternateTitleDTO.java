package cc.onelooker.kaleido.dto;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 别名DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 * @see cc.onelooker.kaleido.entity.AlternateTitleDO
 */
@Data
public class AlternateTitleDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -8955724852553655319L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 项目id
     */
    private String subjectId;

    /**
     * 项目类型
     */
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
