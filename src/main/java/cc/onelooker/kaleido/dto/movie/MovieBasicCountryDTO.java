package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影国家地区关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicCountryDO
 */
@Data
public class MovieBasicCountryDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6230206680190493071L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 国家地区id
     */
    private Long countryId;

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
