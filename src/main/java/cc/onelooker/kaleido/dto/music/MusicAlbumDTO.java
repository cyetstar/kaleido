package cc.onelooker.kaleido.dto.music;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 专辑DTO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.entity.music.MusicAlbumDO
 */
@Data
public class MusicAlbumDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7143079113628462143L;

    /**
     * 主键
     */
    private Long id;

    /**
     * MusicBrainz编号
     */
    private String musicbrainzId;

    /**
     * Plex编号
     */
    private String plexId;

    /**
     * Plex缩略图
     */
    private String plexThumb;

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
     * 日期
     */
    private String date;

    /**
     * 唱片公司
     */
    private String label;

    /**
     * 发行日期
     */
    private String releaseDate;

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
     * 文件路径
     */
    private String path;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

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
