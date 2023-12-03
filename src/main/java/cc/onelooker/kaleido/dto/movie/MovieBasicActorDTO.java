package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicActorDO
 */
@Data
public class MovieBasicActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7707036345383665551L;

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
    private String role;

    /**
     * 饰演
     */
    private String playRole;

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
