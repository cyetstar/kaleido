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
 * @see cc.onelooker.kaleido.entity.business.ResumeDO
 */
@Data
public class ResumeDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8504971406504250215L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String position;

    /**
     * 
     */
    private String total;

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
