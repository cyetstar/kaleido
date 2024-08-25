package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MovieCollectionDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 电影集合DTO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see MovieCollectionDO
 */
@Data
public class MovieCollectionDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 8172676698256320845L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 海报
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
     * 更新时间
     */
    private String updateTime;

    // ------ 非数据库表字段 -------

    /**
     * 主键队列
     */
    private List<String> idList;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }
}
