package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家DTO
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 * @see cc.onelooker.kaleido.entity.music.MusicArtistDO
 */
@Data
public class MusicArtistDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 4684807543259679622L;

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
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * Plex缩略图
     */
    private String plexThumb;

    /**
     * 网易云音乐编号
     */
    private String neteaseId;

    /**
     * 名称
     */
    private String name;

    /**
     * 国家地区
     */
    private String area;

    /**
     * 简介
     */
    private String summary;

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



    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
