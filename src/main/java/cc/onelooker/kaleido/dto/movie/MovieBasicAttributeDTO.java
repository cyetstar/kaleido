package cc.onelooker.kaleido.dto.movie;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影属性值关联表DTO
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 * @see cc.onelooker.kaleido.entity.movie.MovieBasicAttributeDO
 */
@Data
public class MovieBasicAttributeDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1931339751483304236L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 属性值id
     */
    private Long attributeId;

    // ------ 非数据库表字段 -------

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
