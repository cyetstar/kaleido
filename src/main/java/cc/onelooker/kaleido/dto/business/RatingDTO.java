package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.RatingDO
 */
@Data
public class RatingDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1066690964742155967L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Double average;

    /**
     * 
     */
    private Boolean def;

    /**
     * 
     */
    private Integer max;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private Integer votes;

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
