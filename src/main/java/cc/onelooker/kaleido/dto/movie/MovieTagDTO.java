package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影标签DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieTagDO
 */
@Data
public class MovieTagDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3575669635250022618L;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String mc;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
