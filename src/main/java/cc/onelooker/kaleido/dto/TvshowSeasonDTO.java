package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.TvshowSeasonDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 单季DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowSeasonDO
 */
@Data
public class TvshowSeasonDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 5019751674641049715L;

    /**
     * 主键
     */
    private String id;

    /**
     * 剧集id
     */
    private String showId;

    /**
     * 标题
     */
    private String title;

    /**
     * 原标题
     */
    private String originalTitle;

    /**
     * 简介
     */
    private String summary;

    /**
     * 季号
     */
    private Integer seasonIndex;

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
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    /**
     * 导演列表
     */
    private List<ActorDTO> directorList;

    /**
     * 编剧列表
     */
    private List<ActorDTO> writerList;

    /**
     * 演员列表
     */
    private List<ActorDTO> actorList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
