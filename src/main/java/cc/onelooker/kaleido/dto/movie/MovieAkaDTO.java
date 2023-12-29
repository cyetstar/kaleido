package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 别名DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieAkaDO
 */
@Data
public class MovieAkaDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7866526548551830477L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 电影名
     */
    private String title;

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
