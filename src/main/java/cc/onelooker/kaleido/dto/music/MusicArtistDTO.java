package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 艺术家DTO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.entity.music.MusicArtistDO
 */
@Data
public class MusicArtistDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -8953858587556825995L;

    /**
     * 主键
     */
    private Long id;

    /**
     * MusicBrainzId
     */
    private String musicbrainzId;

    /**
     * Plex编号
     */
    private String plexId;

    /**
     * 名称
     */
    private String mc;

    /**
     * 艺术家类型
     */
    private String ysjlx;

    /**
     * 国家地区
     */
    private String gjdq;

    /**
     * 简介
     */
    private String jj;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
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
