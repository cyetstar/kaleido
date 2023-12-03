package cc.onelooker.kaleido.dto.movie;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 演职员DTO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.entity.movie.MovieActorDO
 */
@Data
public class MovieActorDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1702790923657981726L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 豆瓣编号
     */
    private String doubanId;

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
