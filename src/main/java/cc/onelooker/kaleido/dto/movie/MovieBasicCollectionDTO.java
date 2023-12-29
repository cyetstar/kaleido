package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影集合关联表DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicCollectionDO
 */
@Data
public class MovieBasicCollectionDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -5440566804737789832L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 集合id
     */
    private Long collectionId;

    /**
     * 电影名
     */
    private String title;

    /**
     * 首映年份
     */
    private String year;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * 海报
     */
    private String thumb;

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
