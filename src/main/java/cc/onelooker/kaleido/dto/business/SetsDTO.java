package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.SetsDO
 */
@Data
public class SetsDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -592570590631648087L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private String doubanId;

    /**
     * 
     */
    private String doubanResult;

    /**
     * 
     */
    private Integer movieSize;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String overview;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
