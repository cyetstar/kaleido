package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.Long;

/**
 * 艺术家曲目关联表DO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO
 */
@Data
@TableName("music_artist_track")
public class MusicArtistTrackDO implements IdEntity<Long> {
    private static final long serialVersionUID = -4607158984094638852L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 艺术家id
     */
    @TableField(value = "artis_id")
    private Long artisId;

    /**
     * 曲目id
     */
    @TableField(value = "track_id")
    private Long trackId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
