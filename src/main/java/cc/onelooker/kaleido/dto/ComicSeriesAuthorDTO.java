package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ComicSeriesAuthorDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 漫画系列作者关联表DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see ComicSeriesAuthorDO
 */
@Data
public class ComicSeriesAuthorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1255074799779143362L;

    /**
     * 主键
     */
    private String id;

    /**
     * 书籍id
     */
    private String seriesId;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 角色
     */
    private String role;

    // ------ 非数据库表字段 -------
    private List<String> seriesIdList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
