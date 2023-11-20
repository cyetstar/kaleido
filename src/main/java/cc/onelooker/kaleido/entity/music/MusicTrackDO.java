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
 * 曲目DO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.dto.music.MusicTrackDTO
 */
@Data
@TableName("music_track")
public class MusicTrackDO implements IdEntity<Long> {
    private static final long serialVersionUID = 7659671685544301830L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 专辑Id
     */
    @TableField(value = "album_id")
    private Long albumId;

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
     * 创建时间
     */
    @TableField(value = "cjsj")
    private String cjsj;

    /**
     * 修改时间
     */
    @TableField(value = "xgsj")
    private String xgsj;

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
     * 曲长
     */
    @TableField(value = "length")
    private Integer length;

    /**
     * 曲号
     */
    @TableField(value = "track_number")
    private Integer trackNumber;

    /**
     * 碟号
     */
    @TableField(value = "disc_number")
    private Integer discNumber;

    /**
     * 文件格式
     */
    @TableField(value = "format")
    private String format;

    /**
     * 文件路径
     */
    @TableField(value = "path")
    private String path;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
