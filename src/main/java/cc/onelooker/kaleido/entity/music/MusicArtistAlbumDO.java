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
 * 艺术家专辑关联表DO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO
 */
@Data
@TableName("music_artist_album")
public class MusicArtistAlbumDO implements IdEntity<Long> {
    private static final long serialVersionUID = -5937714819979786513L;

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
     * 专辑Id
     */
    @TableField(value = "album_id")
    private Long albumId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
