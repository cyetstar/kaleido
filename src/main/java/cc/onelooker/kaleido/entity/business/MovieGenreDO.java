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
 * @see cc.onelooker.kaleido.dto.business.MovieGenreDTO
 */
@Data
@TableName("movie_genre")
public class MovieGenreDO implements IdEntity<Long> {
    private static final long serialVersionUID = 8308266468342362326L;

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
    @TableField(value = "genre_id")
    private Long genreId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
