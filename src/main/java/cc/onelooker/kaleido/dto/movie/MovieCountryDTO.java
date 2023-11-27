package cc.onelooker.kaleido.dto.movie;

import cc.onelooker.kaleido.dto.IDictionary;
import lombok.Data;

/**
 * 国家地区DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieCountryDO
 */
@Data
public class MovieCountryDTO implements IDictionary<Long> {
    private static final long serialVersionUID = -3266020576272542248L;

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
