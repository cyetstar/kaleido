package cc.onelooker.kaleido.entity.tvshow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 剧集类型关联表DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO
 */
@Data
@TableName("tvshow_show_genre")
public class TvshowShowGenreDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7966477597888251592L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 剧集id
     */
    @TableField(value = "show_id")
    private Long showId;

    /**
     * 类型id
     */
    @TableField(value = "genre_id")
    private Long genreId;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
