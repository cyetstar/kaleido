package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家DTO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see cc.onelooker.kaleido.entity.music.MusicArtistDO
 */
@Data
public class MusicArtistDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6665783261137191212L;

    /**
     * 主键
     */
    private Long id;

    /**
     * MusicBrainz编号
     */
    private String musicbrainzId;

    /**
     * 网易云音乐编号
     */
    private String neteaseId;

    /**
     * 国家地区
     */
    private String area;

    /**
     * 简介
     */
    private String summary;

    /**
     * 名称
     */
    private String title;

    /**
     * 排序名称
     */
    private String titleSort;

    /**
     * 封面图
     */
    private String thumb;

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
