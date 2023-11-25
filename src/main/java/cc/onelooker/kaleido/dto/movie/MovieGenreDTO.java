package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影类型DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieGenreDO
 */
@Data
public class MovieGenreDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 5790661587747408916L;

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
