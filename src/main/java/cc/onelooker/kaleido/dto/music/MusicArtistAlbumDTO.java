package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * 艺术家专辑关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.entity.music.MusicArtistAlbumDO
 */
@Data
public class MusicArtistAlbumDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8101786525430057278L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 艺术家id
     */
    private Long artistId;

    /**
     * 专辑Id
     */
    private Long albumId;

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
