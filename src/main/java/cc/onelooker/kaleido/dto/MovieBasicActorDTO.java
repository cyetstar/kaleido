package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MovieBasicActorDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影演职员关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see MovieBasicActorDO
 */
@Data
public class MovieBasicActorDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 7707036345383665551L;

    /**
     * 主键
     */
    private String id;

    /**
     * 电影id
     */
    private String movieId;

    /**
     * 演职员id
     */
    private String actorId;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
