package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MusicAlbumArtistDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 艺术家专辑关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see MusicAlbumArtistDO
 */
@Data
public class MusicAlbumArtistDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 3222113576892121793L;

    /**
     * 主键
     */
    private String id;

    /**
     * 艺术家id
     */
    private String artistId;

    /**
     * 专辑id
     */
    private String albumId;

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
