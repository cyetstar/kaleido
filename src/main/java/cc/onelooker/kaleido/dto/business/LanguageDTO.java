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
 * @see cc.onelooker.kaleido.entity.business.LanguageDO
 */
@Data
public class LanguageDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -5960169313473233554L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String value;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
