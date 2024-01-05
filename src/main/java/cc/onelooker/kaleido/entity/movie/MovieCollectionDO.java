package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影集合DO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.dto.movie.MovieCollectionDTO
 */
@Data
@TableName("movie_collection")
public class MovieCollectionDO implements IdEntity<Long> {
    private static final long serialVersionUID = 2043551627067037530L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
     * 海报
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 项目数量
     */
    @TableField(value = "child_count")
    private Integer childCount;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private String updateTime;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
