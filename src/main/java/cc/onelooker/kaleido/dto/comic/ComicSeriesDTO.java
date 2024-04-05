package cc.onelooker.kaleido.dto.comic;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 漫画系列DTO
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 * @see cc.onelooker.kaleido.entity.comic.ComicSeriesDO
 */
@Data
public class ComicSeriesDTO implements BaseDTO<String> {
    private static final long serialVersionUID = -2027321145543094030L;

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
     * 出版社
     */
    private String publisher;

    /**
     * 卷数
     */
    private Integer bookCount;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 状态
     */
    private String status;

    /**
     * 路径
     */
    private String path;

    /**
     * 封面
     */
    private String cover;

    /**
     * 番组计划编号
     */
    private String bgmId;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 更新时间
     */
    private Long updatedAt;

    // ------ 非数据库表字段 -------
    private String keyword;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
