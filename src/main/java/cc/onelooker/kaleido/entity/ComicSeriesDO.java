package cc.onelooker.kaleido.entity;

import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 漫画系列DO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see ComicSeriesDTO
 */
@Data
@TableName("comic_series")
public class ComicSeriesDO implements IdEntity<String> {
    private static final long serialVersionUID = 747000965805567860L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

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
     * 年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 出版社
     */
    @TableField(value = "publisher")
    private String publisher;

    /**
     * 卷数
     */
    @TableField(value = "book_count")
    private Integer bookCount;

    /**
     * 评分
     */
    @TableField(value = "rating")
    private Float rating;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 番组计划编号
     */
    @TableField(value = "bgm_id")
    private String bgmId;

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
