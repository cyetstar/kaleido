package cc.onelooker.kaleido.entity.movie;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.math.BigDecimal;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateFormat;

/**
 * 电影DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.dto.movie.MovieBasicDTO
 */
@Data
@TableName("movie_basic")
public class MovieBasicDO implements IdEntity<Long> {
    private static final long serialVersionUID = 8704665334135080977L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 电影名
     */
    @TableField(value = "title")
    private String title;

    /**
     * 原片名
     */
    @TableField(value = "original_title")
    private String originalTitle;

    /**
     * 排序名
     */
    @TableField(value = "title_sort")
    private String titleSort;

    /**
     * 首映年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 海报
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 艺术图
     */
    @TableField(value = "art")
    private String art;

    /**
     * 用户评分
     */
    @TableField(value = "user_rating")
    private Integer userRating;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 片长(秒)
     */
    @TableField(value = "duration")
    private Integer duration;

    /**
     * 电影评级
     */
    @TableField(value = "content_rating")
    private String contentRating;

    /**
     * 首映日期
     */
    @TableField(value = "originally_available_at")
    private String originallyAvailableAt;

    /**
     * 电影公司
     */
    @TableField(value = "studio")
    private String studio;

    /**
     * 评分
     */
    @TableField(value = "rating")
    private Float rating;

    /**
     * 观看时间
     */
    @TableField(value = "last_viewed_at")
    private Long lastViewedAt;

    /**
     * 观看次数
     */
    @TableField(value = "view_count")
    private Integer viewCount;

    /**
     * IMDb编号
     */
    @TableField(value = "imdb")
    private String imdb;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 加入时间
     */
    @TableField(value = "added_at")
    private Long addedAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Long updatedAt;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
