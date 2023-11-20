package cc.onelooker.kaleido.dto.music;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 曲目DTO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.entity.music.MusicTrackDO
 */
@Data
public class MusicTrackDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1187624214357814438L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 专辑Id
     */
    private Long albumId;

    /**
     * MusicBrainz编号
     */
    private String musicbrainzId;

    /**
     * Plex编号
     */
    private String plexId;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * 网易云音乐编号
     */
    private String neteaseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 艺术家
     */
    private String artists;

    /**
     * 曲长
     */
    private Integer length;

    /**
     * 曲号
     */
    private Integer trackNumber;

    /**
     * 碟号
     */
    private Integer discNumber;

    /**
     * 文件格式
     */
    private String format;

    /**
     * 文件路径
     */
    private String path;

    // ------ 非数据库表字段 -------
    /**
     * 大于等于创建时间
     */
    private String cjsjStart;

    /**
     * 小于等于创建时间
     */
    private String cjsjEnd;

    /**
     * 大于等于修改时间
     */
    private String xgsjStart;

    /**
     * 小于等于修改时间
     */
    private String xgsjEnd;

    /**
     * 艺术家id列表
     */
    private List<Long> artistIdList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
