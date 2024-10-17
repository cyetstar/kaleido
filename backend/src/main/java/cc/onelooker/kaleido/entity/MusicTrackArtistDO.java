package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * @author xiadawei
 * @date 2024-10-16 23:31:00
 * @see MusicAlbumArtistDTO
 */
@Data
@TableName("music_track_artist")
public class MusicTrackArtistDO implements IdEntity<String> {
    private static final long serialVersionUID = -6583766451015225117L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 艺术家id
     */
    @TableField(value = "artist_id")
    private String artistId;

    /**
     * 专辑id
     */
    @TableField(value = "track_id")
    private String trackId;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
