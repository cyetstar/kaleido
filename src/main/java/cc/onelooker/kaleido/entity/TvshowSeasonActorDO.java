package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.TvshowSeasonActorDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 剧集演职员关联表DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowSeasonActorDTO
 */
@Data
@TableName("tvshow_season_actor")
public class TvshowSeasonActorDO implements IdEntity<String> {
    private static final long serialVersionUID = 5341917888433091011L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 剧集id
     */
    @TableField(value = "season_id")
    private String seasonId;

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
     * 饰演角色
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
