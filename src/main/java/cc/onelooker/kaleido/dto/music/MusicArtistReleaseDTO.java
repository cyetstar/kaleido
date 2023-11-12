package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * 艺术家发行品关联表DTO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.entity.music.MusicArtistReleaseDO
 */
@Data
public class MusicArtistReleaseDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7420206365872078480L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 艺术家id
     */
    private Long artistId;

    /**
     * 发行品id
     */
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
