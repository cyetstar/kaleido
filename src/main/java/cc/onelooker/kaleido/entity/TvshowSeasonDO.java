package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 单季DO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see TvshowSeasonDTO
 */
@Data
@TableName("tvshow_season")
public class TvshowSeasonDO implements IdEntity<String> {
    private static final long serialVersionUID = -7278928061570586203L;

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
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 原标题
     */
    @TableField(value = "original_title")
    private String originalTitle;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 季号
     */
    @TableField(value = "season_index")
    private Integer seasonIndex;

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
     * IMDb编号
     */
    @TableField(value = "imdb_id")
    private String imdbId;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * TMDB编号
     */
    @TableField(value = "tmdb_id")
    private String tmdbId;

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
