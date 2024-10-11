package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.TvshowShowDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 剧集DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowShowDO
 */
@Data
public class TvshowShowDTO implements BaseDTO<String>, IUnique {
    private static final long serialVersionUID = 7717222741608978157L;

    /**
     * 主键
     */
    private String id;

    /**
     * 剧集名
     */
    private String title;

    /**
     * 原剧集名
     */
    private String originalTitle;

    /**
     * 排序名
     */
    private String sortTitle;

    /**
     * 制片公司
     */
    private String studio;

    /**
     * 剧集评级
     */
    private String contentRating;

    /**
     * 简介
     */
    private String summary;

    /**
     * 首播年份
     */
    private String year;

    /**
     * 首播日期
     */
    private String originallyAvailableAt;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 海报
     */
    private String thumb;

    /**
     * 艺术图
     */
    private String art;

    /**
     * 季数
     */
    private Integer totalSeasons;

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
     * 关键字
     */
    private String keyword;

    /**
     * 关键字类型
     */
    private String keywordType;

    /**
     * 类型
     */
    private Long genreId;

    /**
     * 别名列表
     */
    private List<String> akaList;

    /**
     * 标签列表
     */
    private List<String> tagList;

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

    /**
     * 主键队列
     */
    private List<String> idList;

    /**
     * 季列表
     */
    private List<TvshowSeasonDTO> seasonList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public TvshowSeasonDTO getSeason(Integer seasonIndex) {
        if (CollectionUtils.isEmpty(seasonList) || seasonIndex == null) {
            return null;
        }
        return seasonList.stream().filter(s -> seasonIndex.equals(s.getSeasonIndex())).findFirst().orElse(null);
    }

    public TvshowSeasonDTO getFirstSeason() {
        TvshowSeasonDTO tvshowSeasonDTO = getSeason(1);
        if (tvshowSeasonDTO == null && CollectionUtils.isNotEmpty(seasonList)) {
            tvshowSeasonDTO = CollectionUtils.get(seasonList, 0);
        }
        return tvshowSeasonDTO;
    }

}
