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
 * @see cc.onelooker.kaleido.entity.business.MovieGenreDO
 */
@Data
public class MovieGenreDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3082669821107784939L;

    private Long id;
    /**
     * 
     */
    private Long movieId;

    /**
     * 
     */
    private Long genreId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
