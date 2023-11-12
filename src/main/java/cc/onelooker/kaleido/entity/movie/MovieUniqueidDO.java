package cc.onelooker.kaleido.entity.movie;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;

/**
 * 电影唯一标识DO
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 * @see cc.onelooker.kaleido.dto.movie.MovieUniqueidDTO
 */
@Data
@TableName("movie_uniqueid")
public class MovieUniqueidDO implements IdEntity<Long> {
    private static final long serialVersionUID = -3541471778007846547L;

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
     * 唯一标识
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 是否默认
     */
    @TableField(value = "sfmr")
    private String sfmr;

    /**
     * 标识类型
     */
    @TableField(value = "bslx")
    private String bslx;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
