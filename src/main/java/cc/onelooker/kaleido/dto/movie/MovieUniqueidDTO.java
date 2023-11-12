package cc.onelooker.kaleido.dto.movie;

import cc.onelooker.kaleido.utils.KaleidoConstants;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影唯一标识DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieUniqueidDO
 */
@Data
public class MovieUniqueidDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -5549244128811281640L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 唯一标识
     */
    private String uid;

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

    public static MovieUniqueidDTO douban(String doubanId) {
        MovieUniqueidDTO movieUniqueidDTO = new MovieUniqueidDTO();
        movieUniqueidDTO.setBslx(KaleidoConstants.BSLX_DOUBAN);
        movieUniqueidDTO.setUid(doubanId);
        return movieUniqueidDTO;
    }

    public static MovieUniqueidDTO imdb(String imdb) {
        MovieUniqueidDTO movieUniqueidDTO = new MovieUniqueidDTO();
        movieUniqueidDTO.setBslx(KaleidoConstants.BSLX_IMDB);
        movieUniqueidDTO.setUid(imdb);
        return movieUniqueidDTO;
    }
}
