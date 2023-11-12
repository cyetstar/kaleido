package cc.onelooker.kaleido.dto.movie;

import cc.onelooker.kaleido.utils.KaleidoConstants;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.Integer;
import java.lang.String;

/**
 * 电影评分DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieRatingDO
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
    private String pjf;

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
     * 标识类型
     */
    private String bslx;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static MovieRatingDTO douban(String pjf, Integer tps) {
        MovieRatingDTO movieRatingDTO = new MovieRatingDTO();
        movieRatingDTO.setBslx(KaleidoConstants.BSLX_DOUBAN);
        movieRatingDTO.setPjf(pjf);
        movieRatingDTO.setTps(tps);
        return movieRatingDTO;
    }

    public static MovieRatingDTO imdb(String pjf, Integer tps) {
        MovieRatingDTO movieRatingDTO = new MovieRatingDTO();
        movieRatingDTO.setBslx(KaleidoConstants.BSLX_IMDB);
        movieRatingDTO.setPjf(pjf);
        movieRatingDTO.setTps(tps);
        return movieRatingDTO;
    }
}
