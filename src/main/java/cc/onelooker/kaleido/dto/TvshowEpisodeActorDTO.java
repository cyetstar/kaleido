package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.TvshowEpisodeActorDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 单集演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowEpisodeActorDO
 */
@Data
public class TvshowEpisodeActorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 2599665850473853369L;

    /**
     * 主键
     */
    private String id;

    /**
     * 单集id
     */
    private String episodeId;

    /**
     * 演职员id
     */
    private String actorId;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
