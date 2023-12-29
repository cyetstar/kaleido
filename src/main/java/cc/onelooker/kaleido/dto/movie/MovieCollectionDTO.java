package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 电影集合DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieCollectionDO
 */
@Data
public class MovieCollectionDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2663868481724312141L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 缩略图
     */
    private String thumb;

    /**
     * 项目数量
     */
    private Integer childCount;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    /**
     * 豆瓣口碑榜
     */
    private String doubanWeekly;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------

    /**
     * 主键队列
     */
    private List<Long> idList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
