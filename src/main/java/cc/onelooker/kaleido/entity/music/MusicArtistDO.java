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

/**
 * 艺术家DO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.dto.music.MusicArtistDTO
 */
@Data
@TableName("music_artist")
public class MusicArtistDO implements IdEntity<Long> {
    private static final long serialVersionUID = -5745162324435556581L;

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
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 国家地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
