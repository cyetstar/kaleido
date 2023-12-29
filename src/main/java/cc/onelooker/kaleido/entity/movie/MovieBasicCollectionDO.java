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
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO
 */
@Data
@TableName("movie_basic_collection")
public class MovieBasicCollectionDO implements IdEntity<Long> {
    private static final long serialVersionUID = 3660788426543754663L;

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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
