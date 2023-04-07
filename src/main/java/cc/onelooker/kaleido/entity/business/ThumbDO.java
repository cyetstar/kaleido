package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.ThumbDTO
 */
@Data
@TableName("thumb")
public class ThumbDO implements IdEntity<Long> {
    private static final long serialVersionUID = -5565281190801917159L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "aspect")
    private String aspect;

    /**
     * 
     */
    @TableField(value = "preview")
    private String preview;

    /**
     * 
     */
    @TableField(value = "value")
    private String value;

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
