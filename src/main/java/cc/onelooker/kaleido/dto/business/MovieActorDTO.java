package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Crypto;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 演职员DTO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.entity.business.MovieActorDO
 */
@Data
public class MovieActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6147609699273778563L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 本名
     */
    private String bm;

    /**
     * 豆瓣编号
     */
    private String doubanId;

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
