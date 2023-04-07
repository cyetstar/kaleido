package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.UniqueidDO
 */
@Data
public class UniqueidDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7253614546256943676L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Boolean def;

    /**
     * 
     */
    private String type;

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
