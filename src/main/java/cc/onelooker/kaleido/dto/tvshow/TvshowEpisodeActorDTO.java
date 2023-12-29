package cc.onelooker.kaleido.dto.tvshow;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 单集演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeActorDO
 */
@Data
public class TvshowEpisodeActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2599665850473853369L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 单集id
     */
    private Long episodeId;

    /**
     * 演职员id
     */
    private Long actorId;

    /**
     * 角色
     */
    private String role;

    /**
     * 饰演角色
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
