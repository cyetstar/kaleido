package cc.onelooker.kaleido.dto.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.MovieLanguageDO
 */
@Data
public class MovieLanguageDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7633442532158299781L;

    private Long id;
    /**
     * 
     */
    private Long movieId;

    /**
     * 
     */
    private Long languageId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
