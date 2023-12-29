package cc.onelooker.kaleido.entity.tvshow;

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
 * @see cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO
 */
@Data
@TableName("tvshow_season")
public class TvshowSeasonDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7278928061570586203L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 剧集id
     */
    @TableField(value = "show_id")
    private Long showId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

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
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
