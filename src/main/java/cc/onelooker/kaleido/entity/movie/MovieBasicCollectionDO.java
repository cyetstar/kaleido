package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影集合关联表DO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO
 */
@Data
@TableName("movie_basic_collection")
public class MovieBasicCollectionDO implements IdEntity<Long> {
    private static final long serialVersionUID = 1578389294736421112L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 电影id
     */
    @TableField(value = "movie_id")
    private Long movieId;

    /**
     * 集合id
     */
    @TableField(value = "collection_id")
    private Long collectionId;

    /**
     * 电影名
     */
    @TableField(value = "title")
    private String title;

    /**
     * 原片名
     */
    @TableField(value = "original_title")
    private String originalTitle;

    /**
     * 首映年份
     */
    @TableField(value = "year")
    private String year;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 海报
     */
    @TableField(value = "thumb")
    private String thumb;

    /**
     * 评语
     */
    @TableField(value = "comment")
    private String comment;

    /**
     * 顺序
     */
    @TableField(value = "pos")
    private Integer pos;

    /**
     * 收藏状态
     */
    @TableField(value = "status")
    private String status;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
