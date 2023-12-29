package cc.onelooker.kaleido.dto.tvshow;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 剧集类型关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowShowGenreDO
 */
@Data
public class TvshowShowGenreDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6665932699741520264L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 剧集id
     */
    private Long showId;

    /**
     * 类型id
     */
    private Long genreId;

    // ------ 非数据库表字段 -------

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
