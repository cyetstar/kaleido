package cc.onelooker.kaleido.dto;

import cc.onelooker.kaleido.entity.MovieThreadFilenameDO;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 电影发布文件DTO
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 * @see MovieThreadFilenameDO
 */
@Data
public class MovieThreadFilenameDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -6384262958419053545L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String value;

    /**
     *
     */
    private Long threadId;

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
