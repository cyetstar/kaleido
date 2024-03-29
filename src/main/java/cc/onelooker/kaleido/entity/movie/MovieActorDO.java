package cc.onelooker.kaleido.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 演职员DO
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 * @see cc.onelooker.kaleido.dto.movie.MovieActorDTO
 */
@Data
@TableName("movie_actor")
public class MovieActorDO implements IdEntity<Long> {
    private static final long serialVersionUID = 5953030545604783665L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 豆瓣编号
     */
    @TableField(value = "douban_id")
    private String doubanId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 原名
     */
    @TableField(value = "original_name")
    private String originalName;

    /**
     * 中文名
     */
    @TableField(value = "cn_name")
    private String cnName;

    /**
     * 缩略图
     */
    @TableField(value = "thumb")
    private String thumb;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
