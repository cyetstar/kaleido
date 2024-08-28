package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MovieBasicDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 电影DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see MovieBasicDO
 */
@Data
public class MovieBasicDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -763658678910957490L;

    /**
     * 主键
     */
    private String id;

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
    private String imdbId;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * TMDB编号
     */
    private String tmdbId;

    /**
     * 多文件
     */
    private String multipleFiles;

    /**
     * 无字幕
     */
    private String noSubtitle;

    /**
     * 国语配音
     */
    private String mandarin;

    /**
     * 低质量
     */
    private String lowQuality;

    /**
     * 路径
     */
    private String path;

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
     * 关键字类型
     */
    private String keywordType;

    /**
     * 年代
     */
    private String decade;

    /**
     * 海报
     */
    private String poster;

    /**
     * 主键队列
     */
    private List<String> idList;

    /**
     * 别名列表
     */
    private List<String> akaList;

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 导演列表
     */
    private List<MovieActorDTO> directorList;

    /**
     * 编剧列表
     */
    private List<MovieActorDTO> writerList;

    /**
     * 演员列表
     */
    private List<MovieActorDTO> actorList;

    /**
     * 电影类型列表
     */
    private List<String> genreList;

    /**
     * 语言列表
     */
    private List<String> languageList;

    /**
     * 国家地区列表
     */
    private List<String> countryList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }
}
