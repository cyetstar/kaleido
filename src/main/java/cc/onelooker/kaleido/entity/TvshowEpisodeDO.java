package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 单集DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowEpisodeDTO
 */
@Data
@TableName("tvshow_episode")
public class TvshowEpisodeDO implements IdEntity<String> {
    private static final long serialVersionUID = 9145954770603941504L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 剧集id
     */
    @TableField(value = "show_id")
    private String showId;

    /**
     * 单季id
     */
    @TableField(value = "season_id")
    private String seasonId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 制片公司
     */
    @TableField(value = "studio")
    private String studio;

    /**
     * 剧集评级
     */
    @TableField(value = "content_rating")
    private String contentRating;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 首播年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 首播日期
     */
    @TableField(value = "originally_available_at")
    private String originallyAvailableAt;

    /**
     * 集号
     */
    @TableField(value = "episode_index")
    private Integer episodeIndex;

    /**
     * 评分
     */
    @TableField(value = "rating")
    private Float rating;

    /**
     * 片长(秒)
     */
    @TableField(value = "duration")
    private Integer duration;

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
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
