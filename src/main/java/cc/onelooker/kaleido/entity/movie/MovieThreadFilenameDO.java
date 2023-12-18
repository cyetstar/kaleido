package cc.onelooker.kaleido.entity.movie;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.annotation.Crypto;

import java.lang.Long;
import java.lang.String;

/**
 * 电影发布文件DO
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 * @see cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO
 */
@Data
@TableName("filename")
public class MovieThreadFilenameDO implements IdEntity<Long> {
    private static final long serialVersionUID = -5999073195498320838L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "value")
    private String value;

    /**
     * 
     */
    @TableField(value = "thread_id")
    private Long threadId;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
