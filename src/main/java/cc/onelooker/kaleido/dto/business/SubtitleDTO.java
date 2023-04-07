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
 * @see cc.onelooker.kaleido.entity.business.SubtitleDO
 */
@Data
public class SubtitleDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1386350886769402591L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String language;

    /**
     * 
     */
    private Long fileinfoId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
