package cc.onelooker.kaleido.entity.tvshow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 剧集DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO
 */
@Data
@TableName("tvshow_show")
public class TvshowShowDO implements IdEntity<Long> {
    private static final long serialVersionUID = 3984092259729683063L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 剧集名
     */
    @TableField(value = "title")
    private String title;

    /**
     * 原剧集名
     */
    @TableField(value = "original_title")
    private String originalTitle;

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
     * 评分
     */
    @TableField(value = "rating")
    private Float rating;

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
     * 季数
     */
    @TableField(value = "total_seasons")
    private Integer totalSeasons;

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
