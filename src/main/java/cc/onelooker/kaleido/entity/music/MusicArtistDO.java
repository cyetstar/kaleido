package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 艺术家DO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.dto.music.MusicArtistDTO
 */
@Data
@TableName("music_artist")
public class MusicArtistDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6147084400921846806L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
     * 网易音乐Id
     */
    @TableField(value = "netease_id")
    private String neteaseId;

    /**
     * 名称
     */
    @TableField(value = "mc")
    private String mc;

    /**
     * 艺术家类型
     */
    @TableField(value = "ysjlx")
    private String ysjlx;

    /**
     * 国家地区
     */
    @TableField(value = "gjdq")
    private String gjdq;

    /**
     * 简介
     */
    @TableField(value = "jj")
    private String jj;

    /**
     * 缩略图
     */
    @TableField(value = "plex_thumb")
    private String plexThumb;

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
