package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 语言DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieLanguageDO
 */
@Data
public class MovieLanguageDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -8535573821564130805L;

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
