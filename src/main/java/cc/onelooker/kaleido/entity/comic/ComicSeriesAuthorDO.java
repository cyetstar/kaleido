package cc.onelooker.kaleido.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 漫画系列作者关联表DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see cc.onelooker.kaleido.dto.comic.ComicSeriesAuthorDTO
 */
@Data
@TableName("comic_series_author")
public class ComicSeriesAuthorDO implements IdEntity<String> {
    private static final long serialVersionUID = -1336017431200799384L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 系列id
     */
    @TableField(value = "series_id")
    private String seriesId;

    /**
     * 作者id
     */
    @TableField(value = "author_id")
    private String authorId;

    /**
     * 角色
     */
    @TableField(value = "role")
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
