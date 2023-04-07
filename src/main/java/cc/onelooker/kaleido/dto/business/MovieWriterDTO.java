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
 * @see cc.onelooker.kaleido.entity.business.MovieWriterDO
 */
@Data
public class MovieWriterDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7390087166018722699L;

    private Long id;
    /**
     * 
     */
    private Long movieId;

    /**
     * 
     */
    private Long writerId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
