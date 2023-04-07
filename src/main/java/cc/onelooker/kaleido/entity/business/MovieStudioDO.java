package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;

/**
 * DO
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 * @see cc.onelooker.kaleido.dto.business.MovieStudioDTO
 */
@Data
@TableName("movie_studio")
public class MovieStudioDO implements IdEntity<Long> {
    private static final long serialVersionUID = -8027220474359608612L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 
     */
    @TableField(value = "movie_id")
    private Long movieId;

    /**
     * 
     */
    @TableField(value = "studio_id")
    private Long studioId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
