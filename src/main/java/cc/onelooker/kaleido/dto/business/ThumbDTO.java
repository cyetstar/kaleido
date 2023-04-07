package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.ThumbDO
 */
@Data
public class ThumbDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 4356639670027431149L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String aspect;

    /**
     * 
     */
    private String preview;

    /**
     * 
     */
    private String value;

    /**
     * 
     */
    private Long movieId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
