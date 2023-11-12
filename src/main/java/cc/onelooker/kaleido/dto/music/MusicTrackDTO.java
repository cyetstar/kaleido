package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 曲目DTO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.entity.music.MusicTrackDO
 */
@Data
public class MusicTrackDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -647851996709236430L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 发行品id
     */
    private Long releaseId;

    /**
     * MusicBrainzId
     */
    private String musicbrainzId;

    /**
     * Plex编号
     */
    private String plexId;

    /**
     * 标题
     */
    private String bt;

    /**
     * 艺术家
     */
    private String ysj;

    /**
     * 长度
     */
    private String cd;

    /**
     * 曲号
     */
    private Integer qh;

    /**
     * 碟号
     */
    private Integer dh;

    /**
     * 文件格式
     */
    private String wjgs;

    /**
     * 文件路径
     */
    private String wjlj;

    /**
     * 是否有歌词
     */
    private String sfygc;

    /**
     * 是否缺损
     */
    private String sfqs;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

    //
    private List<Long> artistIdList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
