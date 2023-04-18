package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;

/**
 * 电影唯一标识DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieUniqueidDO
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
}
