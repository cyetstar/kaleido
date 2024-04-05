package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影属性值关联表DO
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 * @see cc.onelooker.kaleido.dto.movie.MovieBasicAttributeDTO
 */
@Data
@TableName("movie_basic_attribute")
public class MovieBasicAttributeDO implements IdEntity<Long> {
    private static final long serialVersionUID = -4874097971230340837L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 电影id
     */
    @TableField(value = "movie_id")
    private Long movieId;

    /**
     * 属性值id
     */
    @TableField(value = "attribute_id")
    private Long attributeId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
