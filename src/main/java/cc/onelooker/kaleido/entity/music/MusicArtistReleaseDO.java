package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;

/**
 * 艺术家发行品关联表DO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO
 */
@Data
@TableName("music_artist_release")
public class MusicArtistReleaseDO implements IdEntity<Long> {
    private static final long serialVersionUID = 1896595596842684059L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 艺术家id
     */
    @TableField(value = "artist_id")
    private Long artistId;

    /**
     * 发行品id
     */
    @TableField(value = "release_id")
    private Long releaseId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
