package cc.onelooker.kaleido.dto.tvshow;

import cc.onelooker.kaleido.dto.IDictionary;
import lombok.Data;

/**
 * 剧集类型DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowGenreDO
 */
@Data
public class TvshowGenreDTO implements IDictionary<Long> {
    private static final long serialVersionUID = -4906586977315822805L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标识
     */
    private String tag;

    // ------ 非数据库表字段 -------

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return getTag();
    }
}
