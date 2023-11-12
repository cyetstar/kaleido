package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieActorLinkDO
 */
@Data
public class MovieActorLinkDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 1811656680955923897L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 演职员id
     */
    private Long actorId;

    /**
     * 角色
     */
    private String js;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
