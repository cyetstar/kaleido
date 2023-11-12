package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * 艺术家曲目关联表DTO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.entity.music.MusicArtistTrackDO
 */
@Data
public class MusicArtistTrackDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3446136582956033549L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 艺术家id
     */
    private Long artisId;

    /**
     * 曲目id
     */
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
