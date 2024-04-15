package cc.onelooker.kaleido.dto.comic;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 漫画书籍作者关联表DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see cc.onelooker.kaleido.entity.comic.ComicBookAuthorDO
 */
@Data
public class ComicBookAuthorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1255074799779143362L;

    /**
     * 主键
     */
    private String id;

    /**
     * 书籍id
     */
    private String bookId;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 角色
     */
    private String role;

    // ------ 非数据库表字段 -------
    private List<String> bookIdList;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
