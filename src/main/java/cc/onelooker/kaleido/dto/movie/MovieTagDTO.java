package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影标签DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieTagDO
 */
@Data
public class MovieTagDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 5227649764440694560L;

    /**
     * 电影id
     */
    private Long movieId;

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
}
