package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MovieBasicCollectionDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影集合关联表DTO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see MovieBasicCollectionDO
 */
@Data
public class MovieBasicCollectionDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -4142413160596508938L;

    /**
     * 主键
     */
    private String id;

    /**
     * 电影id
     */
    private String movieId;

    /**
     * 集合id
     */
    private String collectionId;

    /**
     * 电影名
     */
    private String title;

    /**
     * 原片名
     */
    private String originalTitle;

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

    /**
     * 评语
     */
    private String comment;

    /**
     * 顺序
     */
    private Integer pos;

    /**
     * 收藏状态
     */
    private String status;

    // ------ 非数据库表字段 -------

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
