package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 曲目DO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.dto.music.MusicTrackDTO
 */
@Data
@TableName("music_track")
public class MusicTrackDO implements IdEntity<Long> {
    private static final long serialVersionUID = -8382323182522951172L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发行品id
     */
    @TableField(value = "release_id")
    private Long releaseId;

    /**
     * MusicBrainzId
     */
    @TableField(value = "musicbrainz_id")
    private String musicbrainzId;

    /**
     * Plex编号
     */
    @TableField(value = "plex_id")
    private String plexId;

    /**
     * 标题
     */
    @TableField(value = "bt")
    private String bt;

    /**
     * 艺术家
     */
    @TableField(value = "ysj")
    private String ysj;

    /**
     * 长度
     */
    @TableField(value = "cd")
    private String cd;

    /**
     * 曲号
     */
    @TableField(value = "qh")
    private Integer qh;

    /**
     * 碟号
     */
    @TableField(value = "dh")
    private Integer dh;

    /**
     * 文件格式
     */
    @TableField(value = "wjgs")
    private String wjgs;

    /**
     * 文件路径
     */
    @TableField(value = "wjlj")
    private String wjlj;

    /**
     * 是否有歌词
     */
    @TableField(value = "sfygc")
    private String sfygc;

    /**
     * 是否缺损
     */
    @TableField(value = "sfqs")
    private String sfqs;

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
