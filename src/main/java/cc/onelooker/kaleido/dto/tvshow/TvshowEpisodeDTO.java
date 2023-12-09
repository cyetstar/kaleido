package cc.onelooker.kaleido.dto.tvshow;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.math.BigDecimal;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 单集DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeDO
 */
@Data
public class TvshowEpisodeDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 5388908911886938020L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 剧集id
     */
    private Long showId;

    /**
     * 单季id
     */
    private Long seasonId;

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
