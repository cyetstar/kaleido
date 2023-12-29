package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影类型DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.dto.movie.MovieGenreDTO
 */
@Data
@TableName("movie_genre")
public class MovieGenreDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6526431878147916071L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标识
     */
    @TableField(value = "tag")
    private String tag;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
