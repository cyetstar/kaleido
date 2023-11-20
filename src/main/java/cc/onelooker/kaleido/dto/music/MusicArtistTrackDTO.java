package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * 艺术家曲目关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.entity.music.MusicArtistTrackDO
 */
@Data
public class MusicArtistTrackDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7568850694018952687L;

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
