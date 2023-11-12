package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影集合DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.movie.MovieSetDO
 */
@Data
public class MovieSetDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3338152509424716690L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String mc;

    /**
     * 集合说明
     */
    private String jhsm;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
