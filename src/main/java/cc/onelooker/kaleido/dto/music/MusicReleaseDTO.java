package cc.onelooker.kaleido.dto.music;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 发行品DTO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.entity.music.MusicReleaseDO
 */
@Data
public class MusicReleaseDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 447176085171304237L;

    /**
     * 主键
     */
    private Long id;

    /**
     * MusicBrainzId
     */
    private String musicbrainzId;

    /**
     * PlexId
     */
    private String plexId;

    /**
     * 网易音乐Id
     */
    private String neteaseId;

    /**
     * 标题
     */
    private String bt;

    /**
     * 艺术家
     */
    private String ysj;

    /**
     * 简介
     */
    private String jj;

    /**
     * 缩略图
     */
    private String plexThumb;

    /**
     * 专辑类型
     */
    private String zjlx;

    /**
     * 音乐流派
     */
    private String yylp;

    /**
     * 发行国家
     */
    private String fxgj;

    /**
     * 日期
     */
    private String rq;

    /**
     * 唱片公司
     */
    private String cpgs;

    /**
     * 首发日期
     */
    private String sfrq;

    /**
     * 碟数
     */
    private Integer zds;

    /**
     * 音轨数
     */
    private Integer ygs;

    /**
     * 媒体
     */
    private String mt;

    /**
     * 是否缺损
     */
    private String sfqs;

    /**
     * 文件路径
     */
    private String wjlj;

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
