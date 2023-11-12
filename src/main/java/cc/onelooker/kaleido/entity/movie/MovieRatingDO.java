package cc.onelooker.kaleido.entity.movie;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import java.lang.String;

/**
 * 电影评分DO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.dto.movie.MovieRatingDTO
 */
@Data
@TableName("movie_rating")
public class MovieRatingDO implements IdEntity<Long> {
    private static final long serialVersionUID = -4102462143510766611L;

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
     * 平均分
     */
    @TableField(value = "pjf")
    private String pjf;

    /**
     * 投票数
     */
    @TableField(value = "tps")
    private Integer tps;

    /**
     * 最大值
     */
    @TableField(value = "zdz")
    private Integer zdz;

    /**
     * 是否默认
     */
    @TableField(value = "sfmr")
    private String sfmr;

    /**
     * 标识类型
     */
    @TableField(value = "bslx")
    private String bslx;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
