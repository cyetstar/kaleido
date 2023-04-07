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
 * @see cc.onelooker.kaleido.entity.business.MovieStudioDO
 */
@Data
public class MovieStudioDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8593636785825244838L;

    private Long id;
    /**
     * 
     */
    private Long movieId;

    /**
     * 
     */
    private Long studioId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
