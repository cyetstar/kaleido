package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 国家地区DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieCountryDO
 */
@Data
public class MovieCountryDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -5631551598643820787L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String mc;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
