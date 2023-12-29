package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 电影标签DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.dto.movie.MovieTagDTO
 */
@Data
@TableName("movie_tag")
public class MovieTagDO implements IdEntity<Long> {
    private static final long serialVersionUID = -1114211264537010594L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 电影id
     */
    @TableField(value = "movie_id")
    private Long movieId;

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
