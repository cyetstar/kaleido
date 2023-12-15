package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 电影DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicDO
 */
@Data
public class MovieBasicDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -763658678910957490L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影名
     */
    private String title;

    /**
     * 原片名
     */
    private String originalTitle;

    /**
     * 排序名
     */
    private String titleSort;

    /**
     * 首映年份
     */
    private String year;

    /**
     * 海报
     */
    private String thumb;

    /**
     * 艺术图
     */
    private String art;

    /**
     * 用户评分
     */
    private Integer userRating;

    /**
     * 简介
     */
    private String summary;

    /**
     * 片长(秒)
     */
    private Integer duration;

    /**
     * 电影评级
     */
    private String contentRating;

    /**
     * 首映日期
     */
    private String originallyAvailableAt;

    /**
     * 电影公司
     */
    private String studio;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 观看时间
     */
    private Long lastViewedAt;

    /**
     * 观看次数
     */
    private Integer viewCount;

    /**
     * 电影网站
     */
    private String website;

    /**
     * IMDb编号
     */
    private String imdb;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------
    /**
     * 大于等于首映日期
     */
    private String originallyAvailableAtStart;

    /**
     * 小于等于首映日期
     */
    private String originallyAvailableAtEnd;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 年代
     */
    private String decade;

    /**
     * 类型
     */
    private Long genreId;

    /**
     * 国家地区
     */
    private Long countryId;

    /**
     * 主键队列
     */
    private List<Long> idList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
