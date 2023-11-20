package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 专辑DO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.dto.music.MusicAlbumDTO
 */
@Data
@TableName("music_album")
public class MusicAlbumDO implements IdEntity<Long> {
    private static final long serialVersionUID = -1926537435118896456L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * MusicBrainz编号
     */
    @TableField(value = "musicbrainz_id")
    private String musicbrainzId;

    /**
     * Plex编号
     */
    @TableField(value = "plex_id")
    private String plexId;

    /**
     * Plex缩略图
     */
    @TableField(value = "plex_thumb")
    private String plexThumb;

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
     * 日期
     */
    @TableField(value = "date")
    private String date;

    /**
     * 唱片公司
     */
    @TableField(value = "label")
    private String label;

    /**
     * 发行日期
     */
    @TableField(value = "release_date")
    private String releaseDate;

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
     * 文件路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj")
    private String cjsj;

    /**
     * 修改时间
     */
    @TableField(value = "xgsj")
    private String xgsj;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
