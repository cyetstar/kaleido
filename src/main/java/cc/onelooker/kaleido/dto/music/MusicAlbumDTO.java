package cc.onelooker.kaleido.dto.music;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 专辑DTO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see cc.onelooker.kaleido.entity.music.MusicAlbumDO
 */
@Data
public class MusicAlbumDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -6145424566170578790L;

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
     * 标题
     */
    private String title;

    /**
     * 艺术家
     */
    private String artists;

    /**
     * 简介
     */
    private String summary;

    /**
     * 专辑类型
     */
    private String type;

    /**
     * 音乐流派
     */
    private String genre;

    /**
     * 发行国家
     */
    private String releaseCountry;

    /**
     * 唱片公司
     */
    private String label;

    /**
     * 碟数
     */
    private Integer totalDiscs;

    /**
     * 音轨数
     */
    private Integer totalTracks;

    /**
     * 媒体
     */
    private String media;

    /**
     * 首发年份
     */
    private String year;

    /**
     * 首发日期
     */
    private String originallyAvailableAt;

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

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 首发年代
     */
    private String decade;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
