package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影属性值DTO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.entity.movie.MovieAttributeDO
 */
@Data
public class MovieAttributeDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -2024954205523552332L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 属性值
     */
    private String attribute;

    /**
     * 类型
     */
    private String type;

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
