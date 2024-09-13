package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 艺术家专辑关联表DO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see MusicAlbumArtistDTO
 */
@Data
@TableName("music_album_artist")
public class MusicAlbumArtistDO implements IdEntity<String> {
    private static final long serialVersionUID = 2698762272051883189L;

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
    @TableField(value = "album_id")
    private String albumId;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
