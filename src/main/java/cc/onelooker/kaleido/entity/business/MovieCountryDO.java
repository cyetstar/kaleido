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
 * @see cc.onelooker.kaleido.dto.business.MovieCountryDTO
 */
@Data
@TableName("movie_country")
public class MovieCountryDO implements IdEntity<Long> {
    private static final long serialVersionUID = 8395126748664917554L;

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
    @TableField(value = "country_id")
    private Long countryId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
