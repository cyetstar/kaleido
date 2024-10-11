package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 专辑DO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see MusicAlbumDTO
 */
@Data
@TableName("music_album")
public class MusicAlbumDO implements IdEntity<String> {
    private static final long serialVersionUID = 8154656637441125538L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * MusicBrainz编号
     */
    @TableField(value = "musicbrainz_id")
    private String musicbrainzId;

    /**
     * 网易云音乐编号
     */
    @TableField(value = "netease_id")
    private String neteaseId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 艺术家
     */
    @TableField(value = "artists")
    private String artists;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 专辑类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 音乐流派
     */
    @TableField(value = "genre")
    private String genre;

    /**
     * 发行国家
     */
    @TableField(value = "release_country")
    private String releaseCountry;

    /**
     * 唱片公司
     */
    @TableField(value = "label")
    private String label;

    /**
     * 碟数
     */
    @TableField(value = "total_discs")
    private Integer totalDiscs;

    /**
     * 音轨数
     */
    @TableField(value = "total_tracks")
    private Integer totalTracks;

    /**
     * 媒体
     */
    @TableField(value = "media")
    private String media;

    /**
     * 首发年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 首发日期
     */
    @TableField(value = "originally_available_at")
    private String originallyAvailableAt;

    /**
     * 封面图
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 加入时间
     */
    @TableField(value = "added_at")
    private Long addedAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Long updatedAt;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
