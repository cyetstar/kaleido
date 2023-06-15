package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件DO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.dto.business.MovieFileDTO
 */
@Data
@TableName("movie_file")
public class MovieFileDO implements IdEntity<Long> {
    private static final long serialVersionUID = 2479570845779658193L;

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
     * 文件名
     */
    @TableField(value = "wjm")
    private String wjm;

    /**
     * 文件路径
     */
    @TableField(value = "wjlj")
    private String wjlj;

    /**
     * 文件大小
     */
    @TableField(value = "wjdx")
    private Integer wjdx;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
