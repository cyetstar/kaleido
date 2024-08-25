package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.TvshowEpisodeDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 单集DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowEpisodeDO
 */
@Data
public class TvshowEpisodeDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 5388908911886938020L;

    /**
     * 主键
     */
    private String id;

    /**
     * 剧集id
     */
    private String showId;

    /**
     * 单季id
     */
    private String seasonId;

    /**
     * 标题
     */
    private String title;

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
     * 集号
     */
    private Integer episodeIndex;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 片长(秒)
     */
    private Integer duration;

    /**
     * 海报
     */
    private String thumb;

    /**
     * 艺术图
     */
    private String art;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
