package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MusicTrackArtistDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * @author xiadawei
 * @date 2024-10-16 23:29:00
 * @see MusicTrackArtistDO
 */
@Data
public class MusicTrackArtistDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1250799847339711735L;

    /**
     * 主键
     */
    private String id;

    /**
     * 艺术家id
     */
    private String artistId;

    /**
     * 歌曲id
     */
    private String trackId;

    // ------ 非数据库表字段 -------

    private List<String> trackIdList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
