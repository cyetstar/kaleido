package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.RatingDTO
 */
@Data
@TableName("rating")
public class RatingDO implements IdEntity<Long> {
    private static final long serialVersionUID = 4301382913491236900L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "average")
    private Double average;

    /**
     * 
     */
    @TableField(value = "def")
    private Boolean def;

    /**
     * 
     */
    @TableField(value = "max")
    private Integer max;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "votes")
    private Integer votes;

    /**
     * 
     */
    @TableField(value = "movie_id")
    private Long movieId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
