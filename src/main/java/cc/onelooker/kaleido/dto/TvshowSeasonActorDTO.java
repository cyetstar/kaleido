package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.TvshowSeasonActorDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 剧集演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowSeasonActorDO
 */
@Data
public class TvshowSeasonActorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 3030790950755409355L;

    /**
     * 主键
     */
    private String id;

    /**
     * 集id
     */
    private String seasonId;

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
