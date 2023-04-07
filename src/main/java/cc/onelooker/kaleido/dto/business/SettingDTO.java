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
 * @see cc.onelooker.kaleido.entity.business.SettingDO
 */
@Data
public class SettingDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8773062573583771645L;

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String name;

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
