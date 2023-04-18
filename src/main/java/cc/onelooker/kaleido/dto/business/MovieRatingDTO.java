package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;

/**
 * 电影评分DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieRatingDO
 */
@Data
public class MovieRatingDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1986947017932870439L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 平均分
     */
    private BigDecimal pjf;

    /**
     * 投票数
     */
    private Integer tps;

    /**
     * 最大值
     */
    private Integer zdz;

    /**
     * 是否默认
     */
    private String sfmr;

    /**
     * 评分类型
     */
    private String pflx;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
