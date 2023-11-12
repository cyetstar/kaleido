package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * 电影集合关联表DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieSetLinkDO
 */
@Data
public class MovieSetLinkDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -2014212081242913922L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 集合id
     */
    private Long setId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
