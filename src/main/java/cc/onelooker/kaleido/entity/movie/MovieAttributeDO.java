package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影属性值DO
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 * @see cc.onelooker.kaleido.dto.movie.MovieAttributeDTO
 */
@Data
@TableName("movie_attribute")
public class MovieAttributeDO implements IdEntity<Long> {
    private static final long serialVersionUID = 7097970859030902019L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性值
     */
    @TableField(value = "attribute")
    private String attribute;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
