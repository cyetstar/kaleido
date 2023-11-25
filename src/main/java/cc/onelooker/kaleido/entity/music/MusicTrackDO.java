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
 * @date 2023-11-25 22:16:58
 * @see cc.onelooker.kaleido.dto.music.MusicTrackDTO
 */
@Data
@TableName("music_track")
public class MusicTrackDO implements IdEntity<Long> {
    private static final long serialVersionUID = -2647270733784062564L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 文件格式
     */
    @TableField(value = "format")
    private String format;

    /**
     * 文件路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 专辑id
     */
    @TableField(value = "album_id")
    private Long albumId;

    /**
     * 曲长(毫秒)
     */
    @TableField(value = "duration")
    private Integer duration;

    /**
     * 曲号
     */
    @TableField(value = "track_index")
    private Integer trackIndex;

    /**
     * 碟号
     */
    @TableField(value = "disc_index")
    private Integer discIndex;

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
