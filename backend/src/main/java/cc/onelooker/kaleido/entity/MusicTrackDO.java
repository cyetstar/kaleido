package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.MusicTrackDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 曲目DO
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 * @see MusicTrackDTO
 */
@Data
@TableName("music_track")
public class MusicTrackDO implements IdEntity<String> {
    private static final long serialVersionUID = -2647270733784062564L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 专辑id
     */
    @TableField(value = "album_id")
    private String albumId;

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
     * 文件名
     */
    @TableField(value = "filename")
    private String filename;

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
