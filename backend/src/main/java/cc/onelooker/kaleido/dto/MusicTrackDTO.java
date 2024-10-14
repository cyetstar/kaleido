package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MusicTrackDO;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 曲目DTO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see MusicTrackDO
 */
@Data
public class MusicTrackDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 1574076813692141671L;

    /**
     * 主键
     */
    private String id;

    /**
     * 专辑id
     */
    private String albumId;

    /**
     * MusicBrainz编号
     */
    private String musicbrainzId;

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
     * 文件格式
     */
    private String format;

    /**
     * 曲长(毫秒)
     */
    private Integer duration;

    /**
     * 曲号
     */
    private Integer trackIndex;

    /**
     * 碟号
     */
    private Integer discIndex;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSimpleTitle() {
        return KaleidoUtil.genSongSimpleName(title);
    }
}
