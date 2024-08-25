package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影演职员关联表DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see MovieBasicActorDTO
 */
@Data
@TableName("movie_basic_actor")
public class MovieBasicActorDO implements IdEntity<String> {
    private static final long serialVersionUID = -8909668921663389029L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 电影id
     */
    @TableField(value = "movie_id")
    private String movieId;

    /**
     * 演职员id
     */
    @TableField(value = "actor_id")
    private String actorId;

    /**
     * 角色
     */
    @TableField(value = "role")
    private String role;

    /**
     * 饰演
     */
    @TableField(value = "play_role")
    private String playRole;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
