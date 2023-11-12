package cc.onelooker.kaleido.dto.music;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

import com.zjjcnt.common.core.annotation.StringDateFormat;

import java.lang.Integer;
import java.util.List;

import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

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
     * 简介
     */
    private String jj;

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
