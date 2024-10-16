package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.ArtistDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 艺术家DTO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see ArtistDO
 */
@Data
public class ArtistDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 6665783261137191212L;

    /**
     * 主键
     */
    private String id;

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
     * 原名
     */
    private String originalTitle;

    /**
     * 排序名称
     */
    private String sortTitle;

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

    private List<String> idList;

    private String keyword;

    private List<String> styleList;

    private List<String> genreList;

    private String trackId;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
