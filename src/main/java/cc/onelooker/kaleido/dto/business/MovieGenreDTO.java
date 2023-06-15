package cc.onelooker.kaleido.dto.business;

import cc.onelooker.kaleido.dto.IDictionary;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 影片类型DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieGenreDO
 */
@Data
public class MovieGenreDTO implements IDictionary<Long> {
    private static final long serialVersionUID = 6592155134289237324L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String mc;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return mc;
    }
}
