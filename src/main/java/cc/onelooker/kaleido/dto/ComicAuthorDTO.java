package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ComicAuthorDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 漫画作者DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see ComicAuthorDO
 */
@Data
public class ComicAuthorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -6679793863534138954L;

    /**
     * 主键
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    // ------ 非数据库表字段 -------

    private List<String> idList;

    private String keyword;

    private String role;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
