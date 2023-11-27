package cc.onelooker.kaleido.dto.tvshow;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 剧集演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowShowActorDO
 */
@Data
public class TvshowShowActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 3030790950755409355L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 剧集id
     */
    private Long showId;

    /**
     * 演职员id
     */
    private Long actorId;

    /**
     * 角色
     */
    private String role;

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
