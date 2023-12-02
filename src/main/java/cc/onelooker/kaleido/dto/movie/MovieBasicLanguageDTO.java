package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影语言关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicLanguageDO
 */
@Data
public class MovieBasicLanguageDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7379057075339767875L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 语言id
     */
    private Long languageId;

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
