package cc.onelooker.kaleido.entity.music;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;

import java.lang.Long;
import java.lang.String;

import com.zjjcnt.common.core.annotation.StringDateFormat;

import java.lang.Integer;

import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 发行品DO
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 * @see cc.onelooker.kaleido.dto.music.MusicReleaseDTO
 */
@Data
@TableName("music_release")
public class MusicReleaseDO implements IdEntity<Long> {
    private static final long serialVersionUID = -2720638831386821475L;

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
     * 简介
     */
    @TableField(value = "jj")
    private String jj;

    /**
     * 专辑类型
     */
    @TableField(value = "zjlx")
    private String zjlx;

    /**
     * 音乐流派
     */
    @TableField(value = "yylp")
    private String yylp;

    /**
     * 发行国家
     */
    @TableField(value = "fxgj")
    private String fxgj;

    /**
     * 日期
     */
    @TableField(value = "rq")
    private String rq;

    /**
     * 唱片公司
     */
    @TableField(value = "cpgs")
    private String cpgs;

    /**
     * 首发日期
     */
    @TableField(value = "sfrq")
    private String sfrq;

    /**
     * 碟数
     */
    @TableField(value = "zds")
    private Integer zds;

    /**
     * 音轨数
     */
    @TableField(value = "ygs")
    private Integer ygs;

    /**
     * 媒体
     */
    @TableField(value = "mt")
    private String mt;

    /**
     * 是否缺损
     */
    @TableField(value = "sfqs")
    private String sfqs;

    /**
     * 文件路径
     */
    @TableField(value = "wjlj")
    private String wjlj;

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
