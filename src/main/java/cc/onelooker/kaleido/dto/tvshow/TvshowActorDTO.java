package cc.onelooker.kaleido.dto.tvshow;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 剧集演职员DTO
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 * @see cc.onelooker.kaleido.entity.tvshow.TvshowActorDO
 */
@Data
public class TvshowActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -2333400870826818970L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 缩略图
     */
    private String thumb;

    /**
     * 豆瓣编号
     */
    private String doubanId;

    // ------ 非数据库表字段 -------

    private String role;

    private String playRole;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
