package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;

/**
 * DTO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.entity.business.MovieActorDO
 */
@Data
public class MovieActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6631961069843812569L;

    private Long id;
    /**
     *
     */
    private Long movieId;

    /**
     *
     */
    private Long actorId;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
