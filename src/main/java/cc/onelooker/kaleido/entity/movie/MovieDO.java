package cc.onelooker.kaleido.entity.movie;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影DO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.dto.movie.MovieDTO
 */
@Data
@TableName("movie")
public class MovieDO implements IdEntity<Long> {
    private static final long serialVersionUID = -3379712579528858558L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 电影名称
     */
    @TableField(value = "dymc")
    private String dymc;

    /**
     * 原电影名称
     */
    @TableField(value = "ydymc")
    private String ydymc;

    /**
     * 用户评分
     */
    @TableField(value = "yhpf")
    private Integer yhpf;

    /**
     * 电影类型
     */
    @TableField(value = "dylx")
    private String dylx;

    /**
     * 国家地区
     */
    @TableField(value = "gjdq")
    private String gjdq;

    /**
     * 电影语言
     */
    @TableField(value = "dyyy")
    private String dyyy;

    /**
     * 电影简介
     */
    @TableField(value = "dyjj")
    private String dyjj;

    /**
     * 电影标语
     */
    @TableField(value = "dyby")
    private String dyby;

    /**
     * 电影时长
     */
    @TableField(value = "dysc")
    private Integer dysc;

    /**
     * 电影等级
     */
    @TableField(value = "dydj")
    private String dydj;

    /**
     * 上映日期
     */
    @TableField(value = "syrq")
    private String syrq;

    /**
     * 上映年份
     */
    @TableField(value = "synf")
    private String synf;

    /**
     * 官网地址
     */
    @TableField(value = "gwdz")
    private String gwdz;

    /**
     * 是否观看
     */
    @TableField(value = "gkbz")
    private String gkbz;

    /**
     * 观看时间
     */
    @TableField(value = "gksj")
    private String gksj;

    /**
     * 是否收藏
     */
    @TableField(value = "scbz")
    private String scbz;

    /**
     * 收藏时间
     */
    @TableField(value = "scsj")
    private String scsj;

    /**
     * plex编号
     */
    @TableField(value = "plex_id")
    private String plexId;

    /**
     * 评分
     */
    @TableField(value = "pf")
    private String pf;

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
